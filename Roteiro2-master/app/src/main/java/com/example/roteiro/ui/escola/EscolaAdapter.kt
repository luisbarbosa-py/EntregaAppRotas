package com.example.roteiro.ui.escola

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roteiro.databinding.ItemEscolaBinding
import com.example.roteiro.model.Escola

class EscolaAdapter(
    private var escolas: List<Escola>,
    private val onEditClick: (Escola) -> Unit,
    private val onDeleteClick: (Escola) -> Unit
) : RecyclerView.Adapter<EscolaAdapter.EscolaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EscolaViewHolder {
        val binding = ItemEscolaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EscolaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EscolaViewHolder, position: Int) {
        val escola = escolas[position]
        holder.bind(escola)
    }

    override fun getItemCount() = escolas.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateEscolas(novasEscolas: List<Escola>) {
        escolas = novasEscolas
        notifyDataSetChanged()
    }

    inner class EscolaViewHolder(private val binding: ItemEscolaBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(escola: Escola) {
            binding.textViewNomeEscola.text = escola.nome
            binding.textViewEnderecoEscola.text = "${escola.endereco}, ${escola.numero}"
            binding.textViewPeriodoEscola.text = escola.periodo

            binding.buttonEditar.setOnClickListener { onEditClick(escola) }
            binding.buttonExcluir.setOnClickListener { onDeleteClick(escola) }
        }
    }
}
