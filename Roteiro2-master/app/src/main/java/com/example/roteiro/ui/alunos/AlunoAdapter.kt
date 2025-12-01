package com.example.roteiro.ui.alunos

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roteiro.databinding.ItemAlunoBinding
import com.example.roteiro.model.Aluno
import com.example.roteiro.model.AlunoComResponsavel

class AlunoAdapter(
    private var alunos: List<AlunoComResponsavel>,
    private val onEditClick: (Aluno) -> Unit,
    private val onDeleteClick: (Aluno) -> Unit
) : RecyclerView.Adapter<AlunoAdapter.AlunoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlunoViewHolder {
        val binding = ItemAlunoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlunoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlunoViewHolder, position: Int) {
        val alunoComResponsavel = alunos[position]
        holder.bind(alunoComResponsavel)
    }

    override fun getItemCount() = alunos.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateAlunos(novosAlunos: List<AlunoComResponsavel>) {
        alunos = novosAlunos
        notifyDataSetChanged()
    }

    inner class AlunoViewHolder(private val binding: ItemAlunoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(alunoComResponsavel: AlunoComResponsavel) {
            val aluno = alunoComResponsavel.aluno
            val responsavel = alunoComResponsavel.responsavel

            binding.textViewNomeAluno.text = aluno.nome
            binding.textViewAlunoId.text = "#${aluno.id}"
            binding.textViewEscola.text = aluno.escola
            binding.textViewTurno.text = "Turno: ${aluno.turno}"
            binding.textViewResponsavel.text = "Responsável: ${responsavel?.nome ?: "Não definido"}"

            binding.buttonEditar.setOnClickListener {
                onEditClick(aluno)
            }

            binding.buttonExcluir.setOnClickListener {
                onDeleteClick(aluno)
            }
        }
    }
}
