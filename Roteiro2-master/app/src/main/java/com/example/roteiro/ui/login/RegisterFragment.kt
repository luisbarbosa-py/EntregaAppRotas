package com.example.roteiro.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.roteiro.AppDatabase
import com.example.roteiro.databinding.FragmentRegisterBinding
import com.example.roteiro.model.Usuario
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = AppDatabase.getInstance(requireContext())
        val usuarioDao = db.usuarioDao()

        binding.buttonRegistrar.setOnClickListener {
            val login = binding.editTextLogin.text.toString()
            val senha = binding.editTextSenha.text.toString()

            if (login.isNotEmpty() && senha.isNotEmpty()) {
                lifecycleScope.launch {
                    val existingUser = usuarioDao.getUserByLogin(login)
                    if (existingUser == null) {
                        val newUser = Usuario(login = login, senha = senha)
                        usuarioDao.insert(newUser)
                        Toast.makeText(requireContext(), "Usuário registrado com sucesso!", Toast.LENGTH_SHORT).show()
                        findNavController().popBackStack()
                    } else {
                        Toast.makeText(requireContext(), "Este nome de usuário já existe!", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
