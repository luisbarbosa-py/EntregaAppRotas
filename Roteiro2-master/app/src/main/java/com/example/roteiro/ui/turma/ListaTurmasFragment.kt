package com.example.roteiro.ui.turma

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roteiro.databinding.FragmentListaTurmasBinding
import com.example.roteiro.model.Turma

class ListaTurmasFragment : Fragment() {

    private var _binding: FragmentListaTurmasBinding? = null
    private val binding get() = _binding!!

    private val turmaViewModel: TurmaViewModel by viewModels()
    private lateinit var turmaAdapter: TurmaAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListaTurmasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()

        turmaViewModel.carregarTurmas()
    }

    private fun setupRecyclerView() {
        turmaAdapter = TurmaAdapter(emptyList(),
            onTurmaClick = { turma ->
                val action = ListaTurmasFragmentDirections.actionListaTurmasFragmentToTurmaAlunosFragment(turma.id)
                findNavController().navigate(action)
            },
            onEditClick = { turma ->
                val action = ListaTurmasFragmentDirections.actionListaTurmasFragmentToEditTurmaFragment(turma.id)
                findNavController().navigate(action)
            },
            onDeleteClick = { turma ->
                showDeleteConfirmationDialog(turma)
            }
        )

        binding.recyclerViewTurmas.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = turmaAdapter
        }
    }

    private fun observeViewModel() {
        turmaViewModel.turmas.observe(viewLifecycleOwner) { turmas ->
            turmas?.let { turmaAdapter.updateTurmas(it) }
        }
    }

    private fun showDeleteConfirmationDialog(turma: Turma) {
        AlertDialog.Builder(requireContext())
            .setTitle("Excluir Turma")
            .setMessage("Tem certeza que deseja excluir a turma '${turma.nome}'?")
            .setPositiveButton("Excluir") { _, _ ->
                turmaViewModel.deleteTurma(turma)
                Toast.makeText(requireContext(), "Turma '${turma.nome}' excluída.", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    override fun onResume() {
        super.onResume()
        turmaViewModel.carregarTurmas()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
