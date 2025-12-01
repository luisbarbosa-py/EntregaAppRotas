package com.example.roteiro.ui.escola

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.roteiro.database.AppDatabase
import com.example.roteiro.model.Escola
import kotlinx.coroutines.launch

class EscolaViewModel(application: Application) : AndroidViewModel(application) {

    private val _escolas = MutableLiveData<List<Escola>>()
    val escolas: LiveData<List<Escola>> = _escolas

    private val escolaDao = AppDatabase.getDatabase(application).escolaDao()

    fun carregarEscolas() {
        viewModelScope.launch {
            _escolas.value = escolaDao.getAll()
        }
    }

    fun deleteEscola(escola: Escola) {
        viewModelScope.launch {
            escolaDao.delete(escola.id)
            carregarEscolas() // Recarrega a lista após a exclusão
        }
    }
}
