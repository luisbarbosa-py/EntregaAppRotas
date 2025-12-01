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
import com.example.roteiro.database.AppDatabase
import com.example.roteiro.databinding.FragmentCadastrarResponsavelBinding
import com.example.roteiro.model.Responsavel
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

// ---------- MODELO DE RESPOSTA ----------
data class ViaCepResponse(
    val logradouro: String?,
    val bairro: String?,
    val localidade: String?, // Cidade
    val uf: String?, // Estado
    val erro: Boolean? = false
)

// ---------- INTERFACE DO RETROFIT ----------
interface ViaCepService {
    @GET("{cep}/json/")
    suspend fun buscarCep(@Path("cep") cep: String): ViaCepResponse
}

class CadastrarResponsavelFragment : Fragment() {

    private var _binding: FragmentCadastrarResponsavelBinding? = null
    private val binding get() = _binding!!

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://viacep.com.br/ws/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val api by lazy { retrofit.create(ViaCepService::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCadastrarResponsavelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {

        // Máscara automática do CEP
        binding.editTextCep.addTextChangedListener(object : TextWatcher {
            private var isUpdating = false

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (isUpdating) return
                isUpdating = true

                val cep = s.toString().replace("-", "")
                if (cep.length >= 5) {
                    val formatted = cep.substring(0, 5) + "-" + cep.substring(5, cep.length.coerceAtMost(8))
                    binding.editTextCep.setText(formatted)
                    binding.editTextCep.setSelection(formatted.length)
                }

                isUpdating = false
            }
        })

        // Perdeu o foco → busca o CEP
        binding.editTextCep.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val cep = binding.editTextCep.text.toString().replace("-", "")
                if (cep.length == 8) {
                    buscarCep(cep)
                }
            }
        }

        // Botão salvar
        binding.buttonSalvar.setOnClickListener { salvarResponsavel() }
    }

    // ---------- BUSCA REAL DO VIA CEP ----------
    private fun buscarCep(cep: String) {
        mostrarLoading(true)

        lifecycleScope.launch {
            try {
                val resposta = api.buscarCep(cep)

                if (resposta.erro == true) {
                    onApiFailure("CEP não encontrado.")
                } else {
                    onApiSuccess(resposta)
                }

            } catch (e: Exception) {
                onApiFailure("Erro ao conectar à API.")
            }
        }
    }

    private fun onApiSuccess(response: ViaCepResponse) {
        mostrarLoading(false)

        preencherEndereco(response)
        Toast.makeText(requireContext(), "Endereço preenchido!", Toast.LENGTH_SHORT).show()

        binding.editTextNumero.requestFocus()
    }

    private fun onApiFailure(mensagem: String) {
        mostrarLoading(false)
        Toast.makeText(requireContext(), mensagem, Toast.LENGTH_LONG).show()
    }

    private fun preencherEndereco(endereco: ViaCepResponse) {
        binding.editTextEndereco.setText(endereco.logradouro ?: "")
        binding.editTextBairro.setText(endereco.bairro ?: "")
        binding.editTextCidade.setText(endereco.localidade ?: "")
        binding.editTextEstado.setText(endereco.uf ?: "")
    }

    private fun mostrarLoading(mostrar: Boolean) {
        binding.progressBar.isVisible = mostrar
        binding.editTextCep.isEnabled = !mostrar
    }

    // ---------- SALVAR NO BANCO ----------
    private fun salvarResponsavel() {
        val nome = binding.editTextNomeResponsavel.text.toString()
        val cep = binding.editTextCep.text.toString().replace("-", "")
        val endereco = binding.editTextEndereco.text.toString()
        val numero = binding.editTextNumero.text.toString()
        val complemento = binding.editTextComplemento.text.toString()
        val bairro = binding.editTextBairro.text.toString()
        val cidade = binding.editTextCidade.text.toString()
        val estado = binding.editTextEstado.text.toString()
        val telefone = binding.editTextTelefone.text.toString()

        if (nome.isNotEmpty() && cep.length == 8 && endereco.isNotEmpty() &&
            numero.isNotEmpty() && bairro.isNotEmpty() &&
            cidade.isNotEmpty() && estado.isNotEmpty() &&
            telefone.isNotEmpty()
        ) {

            val responsavel = Responsavel(
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

            lifecycleScope.launch {
                AppDatabase.getDatabase(requireContext()).responsavelDao().insert(responsavel)
                Toast.makeText(requireContext(), "Responsável salvo com sucesso!", Toast.LENGTH_SHORT).show()
                clearFields()
            }

        } else {
            Toast.makeText(requireContext(), "Preencha todos os campos obrigatórios!", Toast.LENGTH_LONG).show()
        }
    }

    private fun clearFields() {
        binding.editTextNomeResponsavel.text?.clear()
        binding.editTextCep.text?.clear()
        binding.editTextEndereco.text?.clear()
        binding.editTextNumero.text?.clear()
        binding.editTextComplemento.text?.clear()
        binding.editTextBairro.text?.clear()
        binding.editTextCidade.text?.clear()
        binding.editTextEstado.text?.clear()
        binding.editTextTelefone.text?.clear()
        binding.editTextNomeResponsavel.requestFocus()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
