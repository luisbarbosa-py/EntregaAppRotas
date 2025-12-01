package com.example.roteiro.ui.turma

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roteiro.databinding.ItemAlunoSimplesBinding
import com.example.roteiro.model.Aluno

// Um adapter simples para mostrar o nome do aluno e um botão de remover.
// Vamos criar o layout item_aluno_simples.xml a seguir.
class TurmaAlunoAdapter(
    private var alunos: List<Aluno>,
    private val onRemoveClick: (Aluno) -> Unit
) : RecyclerView.Adapter<TurmaAlunoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAlunoSimplesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(alunos[position])
    }

    override fun getItemCount() = alunos.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateAlunos(novosAlunos: List<Aluno>) {
        alunos = novosAlunos
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemAlunoSimplesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(aluno: Aluno) {
            binding.textViewNomeAluno.text = aluno.nome
            binding.buttonRemover.setOnClickListener { onRemoveClick(aluno) }
        }
    }
}
