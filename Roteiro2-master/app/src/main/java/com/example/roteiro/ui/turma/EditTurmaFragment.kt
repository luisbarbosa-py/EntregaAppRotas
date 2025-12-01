package com.example.roteiro.ui.turma

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
import com.example.roteiro.databinding.FragmentEditTurmaBinding
import com.example.roteiro.model.Turma
import kotlinx.coroutines.launch

class EditTurmaFragment : Fragment() {

    private var _binding: FragmentEditTurmaBinding? = null
    private val binding get() = _binding!!

    private val args: EditTurmaFragmentArgs by navArgs()
    private lateinit var turmaDao: com.example.roteiro.dao.TurmaDao
    private var turma: Turma? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditTurmaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = AppDatabase.getDatabase(requireContext())
        turmaDao = db.turmaDao()

        setupTurnoDropDown()
        loadTurmaData()

        binding.buttonAtualizarTurma.setOnClickListener {
            updateTurma()
        }
    }

    private fun setupTurnoDropDown() {
        val turnos = resources.getStringArray(R.array.turnos_array)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, turnos)
        binding.autoCompleteTextViewTurno.setAdapter(adapter)
    }

    private fun loadTurmaData() {
        lifecycleScope.launch {
            turma = turmaDao.getById(args.turmaId)
            turma?.let {
                binding.editTextNomeTurma.setText(it.nome)
                binding.autoCompleteTextViewTurno.setText(it.turno, false)
            }
        }
    }

    private fun updateTurma() {
        val nomeTurma = binding.editTextNomeTurma.text.toString()
        val turnoSelecionado = binding.autoCompleteTextViewTurno.text.toString()

        if (nomeTurma.isNotEmpty() && turnoSelecionado.isNotEmpty()) {
            val updatedTurma = turma?.copy(
                nome = nomeTurma,
                turno = turnoSelecionado
            )

            if (updatedTurma != null) {
                lifecycleScope.launch {
                    turmaDao.update(updatedTurma)
                    Toast.makeText(requireContext(), "Turma atualizada com sucesso!", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
            } else {
                Toast.makeText(requireContext(), "Erro ao atualizar a turma.", Toast.LENGTH_SHORT).show()
            }

        } else {
            Toast.makeText(requireContext(), "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
