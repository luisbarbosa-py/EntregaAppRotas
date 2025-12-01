package com.example.roteiro.ui.alunos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.roteiro.R
import com.example.roteiro.database.AppDatabase
import com.example.roteiro.dao.AlunoDao
import com.example.roteiro.dao.ResponsavelDao
import com.example.roteiro.databinding.FragmentAlunosBinding
import com.example.roteiro.model.Aluno
import com.example.roteiro.model.Responsavel
import kotlinx.coroutines.launch

class AlunosFragment : Fragment() {

    private var _binding: FragmentAlunosBinding? = null
    private val binding get() = _binding!!

    private lateinit var alunoDao: AlunoDao
    private lateinit var responsavelDao: ResponsavelDao
    private lateinit var responsaveis: List<Responsavel>
    private var responsavelSelecionado: Responsavel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlunosBinding.inflate(inflater, container, false)
        val db = AppDatabase.getDatabase(requireContext())
        alunoDao = db.alunoDao()
        responsavelDao = db.responsavelDao()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupResponsavelDropDown()
        setupTurnoDropDown()

        binding.buttonSalvar.setOnClickListener {
            salvarAluno()
        }
    }

    private fun setupResponsavelDropDown() {
        lifecycleScope.launch {
            responsaveis = responsavelDao.getAll()
            val nomesResponsaveis = responsaveis.map { it.nome }
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, nomesResponsaveis)
            binding.autoCompleteTextViewResponsavel.setAdapter(adapter)

            binding.autoCompleteTextViewResponsavel.setOnItemClickListener { parent, _, position, _ ->
                val nomeSelecionado = parent.getItemAtPosition(position) as String
                responsavelSelecionado = responsaveis.find { it.nome == nomeSelecionado }

                responsavelSelecionado?.let {
                    binding.editTextEnderecoResponsavel.setText(it.endereco)
                    binding.editTextNumeroResponsavel.setText(it.numero)
                    binding.editTextBairroResponsavel.setText(it.bairro)
                    binding.editTextTelefoneResponsavel.setText(it.telefone)
                }
            }
        }
    }

    private fun setupTurnoDropDown() {
        val turnos = resources.getStringArray(R.array.turnos_array)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, turnos)
        binding.autoCompleteTextViewTurno.setAdapter(adapter)
    }

    private fun salvarAluno() {
        val nome = binding.editTextNomeAluno.text.toString()
        val escola = binding.editTextEscola.text.toString()
        val turno = binding.autoCompleteTextViewTurno.text.toString()

        if (responsavelSelecionado == null) {
            Toast.makeText(requireContext(), "Por favor, selecione um responsável.", Toast.LENGTH_SHORT).show()
            return
        }

        if (nome.isNotEmpty() && escola.isNotEmpty() && turno.isNotEmpty()) {
            val aluno = Aluno(
                nome = nome,
                escola = escola,
                turno = turno,
                responsavelId = responsavelSelecionado!!.id
            )

            lifecycleScope.launch {
                alunoDao.insert(aluno)
                Toast.makeText(requireContext(), "Aluno '$nome' salvo com sucesso!", Toast.LENGTH_SHORT).show()
                clearFields()
            }
        } else {
            Toast.makeText(requireContext(), "Preencha todos os campos do aluno!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun clearFields() {
        binding.autoCompleteTextViewResponsavel.text.clear()
        binding.editTextEnderecoResponsavel.text?.clear()
        binding.editTextNumeroResponsavel.text?.clear()
        binding.editTextBairroResponsavel.text?.clear()
        binding.editTextTelefoneResponsavel.text?.clear()
        binding.editTextNomeAluno.text?.clear()
        binding.editTextEscola.text?.clear()
        binding.autoCompleteTextViewTurno.text?.clear()
        responsavelSelecionado = null
        binding.autoCompleteTextViewResponsavel.requestFocus()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
