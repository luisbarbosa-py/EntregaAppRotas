package com.example.roteiro.ui.alunos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roteiro.databinding.FragmentListaAlunosBinding
import com.example.roteiro.model.AlunoComResponsavel

class ListaAlunosFragment : Fragment() {

    private var _binding: FragmentListaAlunosBinding? = null
    private val binding get() = _binding!!

    private val alunoViewModel: AlunoViewModel by viewModels()
    private lateinit var alunoAdapter: AlunoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListaAlunosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()

        alunoViewModel.carregarAlunos()
    }

    private fun setupRecyclerView() {
        alunoAdapter = AlunoAdapter(emptyList(),
            onEditClick = { aluno ->
                val action = ListaAlunosFragmentDirections.actionListaAlunosFragmentToEditAlunoFragment(aluno.id)
                findNavController().navigate(action)
            },
            onDeleteClick = { aluno ->
                Toast.makeText(context, "Excluir ${aluno.nome}", Toast.LENGTH_SHORT).show()
                // Implementar a exclusão no ViewModel e DAO
            }
        )

        binding.recyclerViewAlunos.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = alunoAdapter
        }
    }

    private fun observeViewModel() {
        alunoViewModel.alunos.observe(viewLifecycleOwner) { alunosComResponsaveis ->
            alunosComResponsaveis?.let {
                alunoAdapter.updateAlunos(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        alunoViewModel.carregarAlunos()
    }
}
