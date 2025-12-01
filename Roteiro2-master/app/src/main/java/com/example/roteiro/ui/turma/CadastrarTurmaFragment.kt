package com.example.roteiro.ui.turma

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
import com.example.roteiro.databinding.FragmentCadastrarTurmaBinding
import com.example.roteiro.model.Turma
import kotlinx.coroutines.launch

class CadastrarTurmaFragment : Fragment() {

    private var _binding: FragmentCadastrarTurmaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCadastrarTurmaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configura o seletor de turnos
        setupTurnoDropDown()

        // Configura o clique do botão salvar
        binding.buttonSalvarTurma.setOnClickListener {
            salvarTurma()
        }
    }

    private fun setupTurnoDropDown() {
        val turnos = resources.getStringArray(R.array.turnos_array)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, turnos)
        binding.autoCompleteTextViewTurno.setAdapter(adapter)
    }

    private fun salvarTurma() {
        val nomeTurma = binding.editTextNomeTurma.text.toString()
        val turnoSelecionado = binding.autoCompleteTextViewTurno.text.toString()

        if (nomeTurma.isNotEmpty() && turnoSelecionado.isNotEmpty()) {
            lifecycleScope.launch {
                val turma = Turma(nome = nomeTurma, turno = turnoSelecionado)
                val db = AppDatabase.getDatabase(requireContext())
                db.turmaDao().insert(turma)

                Toast.makeText(context, "Turma '$nomeTurma' ('$turnoSelecionado') salva!", Toast.LENGTH_SHORT).show()
                clearFields()
            }
        } else {
            Toast.makeText(context, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun clearFields() {
        binding.editTextNomeTurma.text?.clear()
        binding.autoCompleteTextViewTurno.text?.clear()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
