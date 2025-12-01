package com.example.roteiro.ui.turma

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roteiro.databinding.ItemTurmaBinding
import com.example.roteiro.model.Turma

class TurmaAdapter(
    private var turmas: List<Turma>,
    private val onTurmaClick: (Turma) -> Unit, // Novo clique para o card inteiro
    private val onEditClick: (Turma) -> Unit,
    private val onDeleteClick: (Turma) -> Unit
) : RecyclerView.Adapter<TurmaAdapter.TurmaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TurmaViewHolder {
        val binding = ItemTurmaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TurmaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TurmaViewHolder, position: Int) {
        val turma = turmas[position]
        holder.bind(turma)
    }

    override fun getItemCount() = turmas.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateTurmas(novasTurmas: List<Turma>) {
        turmas = novasTurmas
        notifyDataSetChanged()
    }

    inner class TurmaViewHolder(private val binding: ItemTurmaBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(turma: Turma) {
            binding.textViewNomeTurma.text = turma.nome
            binding.textViewTurnoTurma.text = turma.turno

            // Configura os cliques
            itemView.setOnClickListener { onTurmaClick(turma) }
            binding.buttonEditar.setOnClickListener { onEditClick(turma) }
            binding.buttonExcluir.setOnClickListener { onDeleteClick(turma) }
        }
    }
}
