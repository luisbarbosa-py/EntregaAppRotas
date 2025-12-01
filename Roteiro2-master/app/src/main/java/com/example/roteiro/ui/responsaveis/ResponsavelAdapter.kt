package com.example.roteiro.ui.responsaveis

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roteiro.databinding.ItemResponsavelBinding
import com.example.roteiro.model.Responsavel

class ResponsavelAdapter(
    private var responsaveis: List<Responsavel>,
    private val onEditClick: (Responsavel) -> Unit,
    private val onDeleteClick: (Responsavel) -> Unit
) : RecyclerView.Adapter<ResponsavelAdapter.ResponsavelViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResponsavelViewHolder {
        val binding = ItemResponsavelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ResponsavelViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ResponsavelViewHolder, position: Int) {
        val responsavel = responsaveis[position]
        holder.bind(responsavel)
    }

    override fun getItemCount() = responsaveis.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateResponsaveis(novosResponsaveis: List<Responsavel>) {
        responsaveis = novosResponsaveis
        notifyDataSetChanged()
    }

    inner class ResponsavelViewHolder(private val binding: ItemResponsavelBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(responsavel: Responsavel) {
            binding.textViewNomeResponsavel.text = responsavel.nome
            binding.textViewResponsavelId.text = "#${responsavel.id}"
            binding.textViewTelefone.text = responsavel.telefone
            binding.textViewEndereco.text = "${responsavel.endereco}, ${responsavel.numero}"

            binding.buttonEditar.setOnClickListener {
                onEditClick(responsavel)
            }

            binding.buttonExcluir.setOnClickListener {
                onDeleteClick(responsavel)
            }
        }
    }
}
