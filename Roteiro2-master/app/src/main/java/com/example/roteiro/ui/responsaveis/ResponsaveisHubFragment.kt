package com.example.roteiro.ui.responsaveis

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.roteiro.R
import com.example.roteiro.databinding.FragmentResponsaveisHubBinding

class ResponsaveisHubFragment : Fragment() {

    private var _binding: FragmentResponsaveisHubBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResponsaveisHubBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonCadastrarResponsavel.setOnClickListener {
            findNavController().navigate(R.id.action_responsaveisHubFragment_to_cadastrarResponsavelFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
