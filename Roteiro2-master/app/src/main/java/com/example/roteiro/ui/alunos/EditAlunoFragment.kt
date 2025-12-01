package com.example.roteiro.ui.alunos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.roteiro.R
import com.example.roteiro.database.AppDatabase
import com.example.roteiro.dao.AlunoDao
import com.example.roteiro.dao.ResponsavelDao
import com.example.roteiro.databinding.FragmentEditAlunoBinding
import com.example.roteiro.model.Aluno
import com.example.roteiro.model.Responsavel
import kotlinx.coroutines.launch

class EditAlunoFragment : Fragment() {

    private var _binding: FragmentEditAlunoBinding? = null
    private val binding get() = _binding!!
    private val args: EditAlunoFragmentArgs by navArgs()

    private lateinit var alunoDao: AlunoDao
    private lateinit var responsavelDao: ResponsavelDao

    private lateinit var todosResponsaveis: List<Responsavel>
    private var responsavelSelecionado: Responsavel? = null
    private var alunoAtual: Aluno? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditAlunoBinding.inflate(inflater, container, false)
        val db = AppDatabase.getDatabase(requireContext())
        alunoDao = db.alunoDao()
        responsavelDao = db.responsavelDao()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadInitialData()
        binding.buttonAtualizar.setOnClickListener { updateAluno() }
    }

    private fun loadInitialData() {
        lifecycleScope.launch {
            // 1. Busca todos os responsáveis para popular o dropdown
            todosResponsaveis = responsavelDao.getAll()
            val nomesResponsaveis = todosResponsaveis.map { it.nome }
            val responsavelAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, nomesResponsaveis)
            binding.autoCompleteTextViewResponsavel.setAdapter(responsavelAdapter)

            // 2. Busca os dados completos do aluno que está sendo editado
            val alunoComResponsavel = alunoDao.getAlunoComResponsavelById(args.alunoId)
            alunoAtual = alunoComResponsavel?.aluno

            if (alunoComResponsavel != null && alunoAtual != null) {
                // 3. Preenche os campos com os dados do aluno
                binding.editTextNomeAluno.setText(alunoAtual!!.nome)
                binding.editTextEscola.setText(alunoAtual!!.escola)

                // 4. Preenche e pré-seleciona o responsável e seus dados
                responsavelSelecionado = alunoComResponsavel.responsavel
                responsavelSelecionado?.let {
                    binding.autoCompleteTextViewResponsavel.setText(it.nome, false) // false para não disparar o listener
                    binding.editTextEnderecoResponsavel.setText(it.endereco)
                    binding.editTextNumeroResponsavel.setText(it.numero)
                    binding.editTextTelefoneResponsavel.setText(it.telefone)
                }

                // 5. Preenche e pré-seleciona o turno
                val turnoAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, resources.getStringArray(R.array.turnos_array))
                binding.autoCompleteTextViewTurno.setAdapter(turnoAdapter)
                binding.autoCompleteTextViewTurno.setText(alunoAtual!!.turno, false)
            }

            // 6. Configura o listener para trocas de responsável
            binding.autoCompleteTextViewResponsavel.setOnItemClickListener { parent, _, position, _ ->
                val nomeSelecionado = parent.getItemAtPosition(position) as String
                responsavelSelecionado = todosResponsaveis.find { it.nome == nomeSelecionado }
                responsavelSelecionado?.let {
                    binding.editTextEnderecoResponsavel.setText(it.endereco)
                    binding.editTextNumeroResponsavel.setText(it.numero)
                    binding.editTextTelefoneResponsavel.setText(it.telefone)
                }
            }
        }
    }

    private fun updateAluno() {
        val nome = binding.editTextNomeAluno.text.toString()
        val escola = binding.editTextEscola.text.toString()
        val turno = binding.autoCompleteTextViewTurno.text.toString()

        if (responsavelSelecionado == null) {
            Toast.makeText(requireContext(), "Por favor, selecione um responsável.", Toast.LENGTH_SHORT).show()
            return
        }

        if (nome.isNotEmpty() && escola.isNotEmpty() && turno.isNotEmpty()) {
            val alunoAtualizado = alunoAtual?.copy(
                nome = nome,
                escola = escola,
                turno = turno,
                responsavelId = responsavelSelecionado!!.id
            )

            if (alunoAtualizado != null) {
                lifecycleScope.launch {
                    alunoDao.update(alunoAtualizado)
                    Toast.makeText(requireContext(), "Aluno atualizado com sucesso!", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
            } else {
                Toast.makeText(requireContext(), "Erro ao preparar atualização do aluno!", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(requireContext(), "Preencha todos os campos do aluno!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
