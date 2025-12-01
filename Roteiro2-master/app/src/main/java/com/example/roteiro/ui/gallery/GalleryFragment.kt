package com.example.roteiro.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.roteiro.R
import com.example.roteiro.databinding.FragmentGalleryBinding

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imageButtonAlunos.setOnClickListener{
            findNavController().navigate(R.id.action_nav_gallery_to_alunosHubFragment)
        }
        binding.imageButtonResponsaveis.setOnClickListener{
            findNavController().navigate(R.id.action_nav_gallery_to_responsaveisHubFragment)
        }
        binding.imageButtonTurma.setOnClickListener{
            findNavController().navigate(R.id.action_nav_gallery_to_cadastrarTurmaFragment)
        }
        // Corrigido para a nova tela de cadastro de escola
        binding.imageButtonEscola.setOnClickListener{
            findNavController().navigate(R.id.action_nav_gallery_to_cadastrarEscolaFragment)
        }
        // Correção alternativa: Navegando diretamente para o destino
        binding.imageButtonCondutor.setOnClickListener{
            findNavController().navigate(R.id.cadastrarCondutorFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}