package com.example.roteiro.ui.condutor

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roteiro.databinding.FragmentListaCondutoresBinding
import com.example.roteiro.model.Condutor

class ListaCondutoresFragment : Fragment() {

    private var _binding: FragmentListaCondutoresBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CondutorViewModel by viewModels()
    private lateinit var adapter: CondutorAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListaCondutoresBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        adapter = CondutorAdapter(emptyList(),
            onEditClick = { condutor ->
                Toast.makeText(context, "Editar ${condutor.nome}", Toast.LENGTH_SHORT).show()
                // A lógica de navegação para edição virá aqui
            },
            onDeleteClick = { condutor ->
                showDeleteConfirmationDialog(condutor)
            }
        )

        binding.recyclerViewCondutores.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@ListaCondutoresFragment.adapter
        }
    }

    private fun observeViewModel() {
        viewModel.condutores.observe(viewLifecycleOwner) { condutores ->
            adapter.updateCondutores(condutores)
        }
    }

    private fun showDeleteConfirmationDialog(condutor: Condutor) {
        AlertDialog.Builder(requireContext())
            .setTitle("Excluir Condutor")
            .setMessage("Tem certeza que deseja excluir o condutor '${condutor.nome}'?")
            .setPositiveButton("Excluir") { _, _ ->
                viewModel.deleteCondutor(condutor)
                Toast.makeText(requireContext(), "Condutor '${condutor.nome}' excluído.", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    override fun onResume() {
        super.onResume()
        // Recarrega os condutores toda vez que a tela se torna visível
        viewModel.carregarCondutores()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
