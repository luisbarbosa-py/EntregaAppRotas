package com.example.roteiro.ui.responsaveis

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.roteiro.database.AppDatabase
import com.example.roteiro.model.Responsavel
import kotlinx.coroutines.launch

class ResponsavelViewModel(application: Application) : AndroidViewModel(application) {

    private val responsavelDao = AppDatabase.getDatabase(application).responsavelDao()

    private val _responsaveis = MutableLiveData<List<Responsavel>>()
    val responsaveis: LiveData<List<Responsavel>> = _responsaveis

    fun carregarResponsaveis() {
        viewModelScope.launch {
            _responsaveis.value = responsavelDao.getAll()
        }
    }
}
