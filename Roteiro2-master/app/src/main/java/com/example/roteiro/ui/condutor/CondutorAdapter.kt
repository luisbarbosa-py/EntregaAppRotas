package com.example.roteiro.ui.condutor

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roteiro.databinding.ItemCondutorBinding
import com.example.roteiro.model.Condutor

class CondutorAdapter(
    private var condutores: List<Condutor>,
    private val onEditClick: (Condutor) -> Unit,
    private val onDeleteClick: (Condutor) -> Unit
) : RecyclerView.Adapter<CondutorAdapter.CondutorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CondutorViewHolder {
        val binding = ItemCondutorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CondutorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CondutorViewHolder, position: Int) {
        val condutor = condutores[position]
        holder.bind(condutor)
    }

    override fun getItemCount() = condutores.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateCondutores(novosCondutores: List<Condutor>) {
        condutores = novosCondutores
        notifyDataSetChanged()
    }

    inner class CondutorViewHolder(private val binding: ItemCondutorBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(condutor: Condutor) {
            binding.textViewNomeCondutor.text = condutor.nome
            binding.textViewCpfCondutor.text = condutor.cpf
            binding.textViewTurnoCondutor.text = condutor.turno

            binding.buttonEditar.setOnClickListener { onEditClick(condutor) }
            binding.buttonExcluir.setOnClickListener { onDeleteClick(condutor) }
        }
    }
}
