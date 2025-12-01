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
import com.example.roteiro.R
import com.example.roteiro.databinding.FragmentLoginBinding
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = AppDatabase.getInstance(requireContext())
        val usuarioDao = db.usuarioDao()

        binding.buttonLogin.setOnClickListener {
            val login = binding.editTextLogin.text.toString()
            val senha = binding.editTextSenha.text.toString()

            if (login.isNotEmpty() && senha.isNotEmpty()) {
                lifecycleScope.launch {
                    val usuario = usuarioDao.getUser(login, senha)
                    if (usuario != null) {
                        // Navegar para a tela principal
                        findNavController().navigate(R.id.action_loginFragment_to_nav_home)
                    } else {
                        Toast.makeText(requireContext(), "Login ou senha inválidos!", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.buttonRegistrar.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
