package com.example.roteiro.ui.escola

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
import com.example.roteiro.databinding.FragmentCadastrarEscolaBinding
import com.example.roteiro.model.Escola
import kotlinx.coroutines.launch

class CadastrarEscolaFragment : Fragment() {

    private var _binding: FragmentCadastrarEscolaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCadastrarEscolaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupPeriodoDropDown()

        binding.buttonSalvarEscola.setOnClickListener {
            salvarEscola()
        }
    }

    private fun setupPeriodoDropDown() {
        val periodos = resources.getStringArray(R.array.periodos_escola_array)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, periodos)
        binding.autoCompleteTextViewPeriodo.setAdapter(adapter)
    }

    private fun salvarEscola() {
        val nome = binding.editTextNomeEscola.text.toString()
        val endereco = binding.editTextEnderecoEscola.text.toString()
        val numero = binding.editTextNumeroEscola.text.toString()
        val periodo = binding.autoCompleteTextViewPeriodo.text.toString()

        if (nome.isNotEmpty() && endereco.isNotEmpty() && numero.isNotEmpty() && periodo.isNotEmpty()) {
            lifecycleScope.launch {
                val escola = Escola(nome = nome, endereco = endereco, numero = numero, periodo = periodo)
                val db = AppDatabase.getDatabase(requireContext())
                db.escolaDao().insert(escola)

                Toast.makeText(context, "Escola '$nome' salva com sucesso!", Toast.LENGTH_SHORT).show()
                clearFields()
            }
        } else {
            Toast.makeText(context, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun clearFields() {
        binding.editTextNomeEscola.text?.clear()
        binding.editTextEnderecoEscola.text?.clear()
        binding.editTextNumeroEscola.text?.clear()
        binding.autoCompleteTextViewPeriodo.text?.clear()
        binding.editTextNomeEscola.requestFocus()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
