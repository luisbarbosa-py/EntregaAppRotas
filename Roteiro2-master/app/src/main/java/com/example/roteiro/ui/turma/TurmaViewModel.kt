package com.example.roteiro.ui.turma

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.roteiro.database.AppDatabase
import com.example.roteiro.model.Turma
import kotlinx.coroutines.launch

class TurmaViewModel(application: Application) : AndroidViewModel(application) {

    private val _turmas = MutableLiveData<List<Turma>>()
    val turmas: LiveData<List<Turma>> = _turmas

    private val turmaDao = AppDatabase.getDatabase(application).turmaDao()

    fun carregarTurmas() {
        viewModelScope.launch {
            _turmas.value = turmaDao.getAll()
        }
    }

    fun deleteTurma(turma: Turma) {
        viewModelScope.launch {
            turmaDao.delete(turma.id)
            // Recarrega a lista após a exclusão
            carregarTurmas()
        }
    }
}
