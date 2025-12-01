package com.example.roteiro.ui.responsaveis

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roteiro.databinding.FragmentListaResponsaveisBinding

class ListaResponsaveisFragment : Fragment() {

    private var _binding: FragmentListaResponsaveisBinding? = null
    private val binding get() = _binding!!

    private val responsavelViewModel: ResponsavelViewModel by viewModels()
    private lateinit var responsavelAdapter: ResponsavelAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListaResponsaveisBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()

        responsavelViewModel.carregarResponsaveis()
    }

    private fun setupRecyclerView() {
        responsavelAdapter = ResponsavelAdapter(emptyList(),
            onEditClick = { responsavel ->
                val action = ListaResponsaveisFragmentDirections.actionListaResponsaveisFragmentToEditResponsavelFragment(responsavel.id)
                findNavController().navigate(action)
            },
            onDeleteClick = { responsavel ->
                Toast.makeText(context, "Excluir ${responsavel.nome}", Toast.LENGTH_SHORT).show()
                // Implementar a exclusão
            }
        )

        binding.recyclerViewResponsaveis.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = responsavelAdapter
        }
    }

    private fun observeViewModel() {
        responsavelViewModel.responsaveis.observe(viewLifecycleOwner) { responsaveis ->
            responsaveis?.let {
                responsavelAdapter.updateResponsaveis(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
