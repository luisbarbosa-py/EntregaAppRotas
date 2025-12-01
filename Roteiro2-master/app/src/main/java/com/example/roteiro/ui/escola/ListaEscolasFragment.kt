package com.example.roteiro.ui.escola

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roteiro.databinding.FragmentListaEscolasBinding
import com.example.roteiro.model.Escola

class ListaEscolasFragment : Fragment() {

    private var _binding: FragmentListaEscolasBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EscolaViewModel by viewModels()
    private lateinit var adapter: EscolaAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListaEscolasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        adapter = EscolaAdapter(emptyList(),
            onEditClick = { escola ->
                Toast.makeText(context, "Editar ${escola.nome}", Toast.LENGTH_SHORT).show()
                // A lógica de navegação para edição virá aqui
            },
            onDeleteClick = { escola ->
                showDeleteConfirmationDialog(escola)
            }
        )

        binding.recyclerViewEscolas.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@ListaEscolasFragment.adapter
        }
    }

    private fun observeViewModel() {
        viewModel.escolas.observe(viewLifecycleOwner) { escolas ->
            adapter.updateEscolas(escolas)
        }
    }

    private fun showDeleteConfirmationDialog(escola: Escola) {
        AlertDialog.Builder(requireContext())
            .setTitle("Excluir Escola")
            .setMessage("Tem certeza que deseja excluir a escola '${escola.nome}'?")
            .setPositiveButton("Excluir") { _, _ ->
                viewModel.deleteEscola(escola)
                Toast.makeText(requireContext(), "Escola '${escola.nome}' excluída.", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    override fun onResume() {
        super.onResume()
        // Recarrega as escolas toda vez que a tela se torna visível
        viewModel.carregarEscolas()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
