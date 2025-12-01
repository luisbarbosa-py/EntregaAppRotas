package com.example.roteiro.ui.alunos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.roteiro.R
import com.example.roteiro.databinding.FragmentAlunosHubBinding

class AlunosHubFragment : Fragment() {

    private var _binding: FragmentAlunosHubBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlunosHubBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonCadastrarAluno.setOnClickListener {
            findNavController().navigate(R.id.action_alunosHubFragment_to_alunosFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
