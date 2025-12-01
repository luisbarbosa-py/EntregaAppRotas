package com.example.roteiro.ui.turma

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roteiro.databinding.FragmentTurmaAlunosBinding
import com.example.roteiro.model.Aluno

class TurmaAlunosFragment : Fragment() {

    private var _binding: FragmentTurmaAlunosBinding? = null
    private val binding get() = _binding!!

    private val args: TurmaAlunosFragmentArgs by navArgs()
    private val viewModel: TurmaAlunosViewModel by viewModels()
    private lateinit var adapter: TurmaAlunoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTurmaAlunosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()

        binding.fabAdicionarAluno.setOnClickListener {
            viewModel.carregarTodosOsAlunos() // Prepara a lista de todos os alunos para o diálogo
        }
    }

    private fun setupRecyclerView() {
        adapter = TurmaAlunoAdapter(emptyList()) { aluno ->
            showRemoveConfirmationDialog(aluno)
        }
        binding.recyclerViewAlunosDaTurma.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewAlunosDaTurma.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.turmaComAlunos.observe(viewLifecycleOwner) { turmaComAlunos ->
            turmaComAlunos?.let {
                binding.textViewNomeTurma.text = it.turma.nome
                adapter.updateAlunos(it.alunos)
            }
        }

        viewModel.todosOsAlunos.observe(viewLifecycleOwner) { todosOsAlunos ->
            showAddAlunoDialog(todosOsAlunos)
        }

        // Carrega os dados iniciais da turma e seus alunos
        viewModel.carregarTurmaEAlunos(args.turmaId)
    }

    private fun showAddAlunoDialog(todosOsAlunos: List<Aluno>) {
        val alunosJaNaTurma = viewModel.turmaComAlunos.value?.alunos ?: emptyList()
        val alunosDisponiveis = todosOsAlunos.filter { it !in alunosJaNaTurma }

        if (alunosDisponiveis.isEmpty()) {
            Toast.makeText(context, "Todos os alunos já estão nesta turma.", Toast.LENGTH_SHORT).show()
            return
        }

        val nomesAlunos = alunosDisponiveis.map { it.nome }.toTypedArray()

        AlertDialog.Builder(requireContext())
            .setTitle("Adicionar Aluno à Turma")
            .setItems(nomesAlunos) { _, which ->
                val alunoSelecionado = alunosDisponiveis[which]
                viewModel.adicionarAlunoNaTurma(args.turmaId, alunoSelecionado.id)
                Toast.makeText(context, "${alunoSelecionado.nome} adicionado.", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun showRemoveConfirmationDialog(aluno: Aluno) {
        AlertDialog.Builder(requireContext())
            .setTitle("Remover Aluno")
            .setMessage("Tem certeza que deseja remover ${aluno.nome} da turma?")
            .setPositiveButton("Remover") { _, _ ->
                viewModel.removerAlunoDaTurma(args.turmaId, aluno.id)
                Toast.makeText(context, "${aluno.nome} removido.", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}