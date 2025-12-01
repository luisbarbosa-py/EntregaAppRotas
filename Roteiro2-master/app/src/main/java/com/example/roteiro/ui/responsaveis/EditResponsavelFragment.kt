package com.example.roteiro.ui.responsaveis

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.roteiro.database.AppDatabase
import com.example.roteiro.dao.ResponsavelDao
import com.example.roteiro.databinding.FragmentEditResponsavelBinding
import com.example.roteiro.model.Responsavel
import kotlinx.coroutines.launch

class EditResponsavelFragment : Fragment() {

    private var _binding: FragmentEditResponsavelBinding? = null
    private val binding get() = _binding!!
    private val args: EditResponsavelFragmentArgs by navArgs()
    private lateinit var responsavelDao: ResponsavelDao
    private var responsavel: Responsavel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditResponsavelBinding.inflate(inflater, container, false)
        responsavelDao = AppDatabase.getDatabase(requireContext()).responsavelDao()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        loadResponsavel()
    }

    private fun setupListeners() {
        // Listener para formatação do CEP
        binding.editTextCep.addTextChangedListener(object : TextWatcher {
            private var isUpdating = false
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (isUpdating) return
                isUpdating = true
                val cep = s.toString()
                if (cep.length == 5 && !cep.contains("-")) {
                    s?.insert(5, "-")
                }
                isUpdating = false
            }
        })

        // Listener para quando o usuário sair do campo CEP
        binding.editTextCep.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val cep = binding.editTextCep.text.toString().replace("-", "")
                if (cep.length == 8) {
                    buscarCep(cep)
                }
            }
        }

        // Listener do botão Atualizar
        binding.buttonAtualizar.setOnClickListener { updateResponsavel() }
    }

    private fun buscarCep(cep: String) {
        mostrarLoading(true)

        // ###################################################################
        // TODO: IMPLEMENTAR A CHAMADA À API AQUI (igual ao fragment de cadastro)
        // ###################################################################
        
        // **ATENÇÃO**: Remova o código de exemplo abaixo após implementar sua chamada real.
        lifecycleScope.launch {
            kotlinx.coroutines.delay(1500) // Simula o tempo de espera da rede
            val simulacaoSucesso = true
            if (simulacaoSucesso) {
                val respostaSimulada = ViaCepResponse(
                    logradouro = "Avenida Paulista",
                    bairro = "Bela Vista",
                    localidade = "São Paulo",
                    uf = "SP"
                )
                onApiSuccess(respostaSimulada)
            } else {
                onApiFailure("CEP não encontrado.")
            }
        }
    }

    private fun onApiSuccess(response: ViaCepResponse) {
        mostrarLoading(false)
        if (response.erro == true) {
            onApiFailure("CEP não encontrado ou inválido.")
            return
        }
        preencherEndereco(response)
        Toast.makeText(requireContext(), "Endereço preenchido!", Toast.LENGTH_SHORT).show()
        binding.editTextNumero.requestFocus()
    }

    private fun onApiFailure(mensagem: String) {
        mostrarLoading(false)
        Toast.makeText(requireContext(), "Erro ao buscar CEP: $mensagem", Toast.LENGTH_LONG).show()
    }

    private fun preencherEndereco(endereco: ViaCepResponse) {
        binding.editTextEndereco.setText(endereco.logradouro)
        binding.editTextBairro.setText(endereco.bairro)
        binding.editTextCidade.setText(endereco.localidade)
        binding.editTextEstado.setText(endereco.uf)
    }

    private fun mostrarLoading(mostrar: Boolean) {
        binding.progressBar.isVisible = mostrar
        binding.editTextCep.isEnabled = !mostrar
    }

    private fun loadResponsavel() {
        lifecycleScope.launch {
            responsavel = responsavelDao.getById(args.responsavelId)
            responsavel?.let {
                binding.editTextNomeResponsavel.setText(it.nome)
                val cepFormatado = it.cep.let { if (it.length == 8) it.substring(0, 5) + "-" + it.substring(5) else it }
                binding.editTextCep.setText(cepFormatado)
                binding.editTextEndereco.setText(it.endereco)
                binding.editTextNumero.setText(it.numero)
                binding.editTextComplemento.setText(it.complemento)
                binding.editTextBairro.setText(it.bairro)
                binding.editTextCidade.setText(it.cidade)
                binding.editTextEstado.setText(it.estado)
                binding.editTextTelefone.setText(it.telefone)
            }
        }
    }

    private fun updateResponsavel() {
        val nome = binding.editTextNomeResponsavel.text.toString()
        val cep = binding.editTextCep.text.toString().replace("-", "")
        val endereco = binding.editTextEndereco.text.toString()
        val numero = binding.editTextNumero.text.toString()
        val complemento = binding.editTextComplemento.text.toString()
        val bairro = binding.editTextBairro.text.toString()
        val cidade = binding.editTextCidade.text.toString()
        val estado = binding.editTextEstado.text.toString()
        val telefone = binding.editTextTelefone.text.toString()

        if (nome.isNotEmpty() && cep.length == 8 && endereco.isNotEmpty() && numero.isNotEmpty() && bairro.isNotEmpty() && cidade.isNotEmpty() && estado.isNotEmpty() && telefone.isNotEmpty()) {
            val updatedResponsavel = responsavel?.copy(
                nome = nome,
                cep = cep,
                endereco = endereco,
                numero = numero,
                complemento = complemento.ifEmpty { null },
                bairro = bairro,
                cidade = cidade,
                estado = estado,
                telefone = telefone
            )

            if (updatedResponsavel != null) {
                lifecycleScope.launch {
                    responsavelDao.update(updatedResponsavel)
                    Toast.makeText(requireContext(), "Responsável atualizado com sucesso!", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
            } else {
                Toast.makeText(requireContext(), "Erro ao preparar atualização do responsável!", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(requireContext(), "Preencha todos os campos obrigatórios!", Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
