package com.example.roteiro.ui.condutor

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.roteiro.database.AppDatabase
import com.example.roteiro.model.Condutor
import kotlinx.coroutines.launch

class CondutorViewModel(application: Application) : AndroidViewModel(application) {

    private val _condutores = MutableLiveData<List<Condutor>>()
    val condutores: LiveData<List<Condutor>> = _condutores

    private val condutorDao = AppDatabase.getDatabase(application).condutorDao()

    fun carregarCondutores() {
        viewModelScope.launch {
            _condutores.value = condutorDao.getAll()
        }
    }

    fun deleteCondutor(condutor: Condutor) {
        viewModelScope.launch {
            condutorDao.delete(condutor.id)
            carregarCondutores() // Recarrega a lista após a exclusão
        }
    }
}
