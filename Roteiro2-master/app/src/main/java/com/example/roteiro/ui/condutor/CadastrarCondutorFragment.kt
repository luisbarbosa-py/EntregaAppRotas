package com.example.roteiro.ui.condutor

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
import com.example.roteiro.databinding.FragmentCadastrarCondutorBinding
import com.example.roteiro.model.Condutor
import kotlinx.coroutines.launch

class CadastrarCondutorFragment : Fragment() {

    private var _binding: FragmentCadastrarCondutorBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCadastrarCondutorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupTurnoDropDown()

        binding.buttonSalvarCondutor.setOnClickListener {
            salvarCondutor()
        }
    }

    private fun setupTurnoDropDown() {
        val turnos = resources.getStringArray(R.array.turnos_array)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, turnos)
        binding.autoCompleteTextViewTurno.setAdapter(adapter)
    }

    private fun salvarCondutor() {
        val nome = binding.editTextNomeCondutor.text.toString()
        val cpf = binding.editTextCpfCondutor.text.toString()
        val turno = binding.autoCompleteTextViewTurno.text.toString()

        if (nome.isNotEmpty() && cpf.isNotEmpty() && turno.isNotEmpty()) {
            lifecycleScope.launch {
                val condutor = Condutor(nome = nome, cpf = cpf, turno = turno)
                val db = AppDatabase.getDatabase(requireContext())
                db.condutorDao().insert(condutor)

                Toast.makeText(context, "Condutor '$nome' salvo com sucesso!", Toast.LENGTH_SHORT).show()
                clearFields()
            }
        } else {
            Toast.makeText(context, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun clearFields() {
        binding.editTextNomeCondutor.text?.clear()
        binding.editTextCpfCondutor.text?.clear()
        binding.autoCompleteTextViewTurno.text?.clear()
        binding.editTextNomeCondutor.requestFocus()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
