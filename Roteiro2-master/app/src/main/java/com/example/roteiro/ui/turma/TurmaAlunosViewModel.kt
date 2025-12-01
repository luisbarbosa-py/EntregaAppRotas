package com.example.roteiro.ui.turma

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.roteiro.database.AppDatabase
import com.example.roteiro.model.Aluno
import com.example.roteiro.model.Turma
import com.example.roteiro.model.TurmaAlunoCrossRef
import com.example.roteiro.model.TurmaComAlunos
import kotlinx.coroutines.launch

class TurmaAlunosViewModel(application: Application) : AndroidViewModel(application) {

    private val turmaDao = AppDatabase.getDatabase(application).turmaDao()
    private val alunoDao = AppDatabase.getDatabase(application).alunoDao()

    private val _turmaComAlunos = MutableLiveData<TurmaComAlunos>()
    val turmaComAlunos: LiveData<TurmaComAlunos> = _turmaComAlunos

    private val _todosOsAlunos = MutableLiveData<List<Aluno>>()
    val todosOsAlunos: LiveData<List<Aluno>> = _todosOsAlunos

    fun carregarTurmaEAlunos(turmaId: Long) {
        viewModelScope.launch {
            _turmaComAlunos.value = turmaDao.getTurmaComAlunos(turmaId)
        }
    }

    fun carregarTodosOsAlunos() {
        viewModelScope.launch {
            _todosOsAlunos.value = alunoDao.getAll()
        }
    }

    fun adicionarAlunoNaTurma(turmaId: Long, alunoId: Long) {
        viewModelScope.launch {
            val crossRef = TurmaAlunoCrossRef(turmaId = turmaId, alunoId = alunoId)
            turmaDao.addAlunoToTurma(crossRef)
            // Recarrega os dados para atualizar a UI
            carregarTurmaEAlunos(turmaId)
        }
    }

    fun removerAlunoDaTurma(turmaId: Long, alunoId: Long) {
        viewModelScope.launch {
            val crossRef = TurmaAlunoCrossRef(turmaId = turmaId, alunoId = alunoId)
            turmaDao.removeAlunoFromTurma(crossRef)
            // Recarrega os dados para atualizar a UI
            carregarTurmaEAlunos(turmaId)
        }
    }
}
