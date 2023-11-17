package br.com.fiap.trashit.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import br.com.fiap.trashit.model.Coleta
import br.com.fiap.trashit.service.database.repository.ColetaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ColetasViewModel(context: Context): ViewModel() {
    private val coletaRepository = ColetaRepository(context)

    private val _listaColetas = MutableStateFlow<List<Coleta>>(
        coletaRepository
        .listarColetas(idResidencia = 1)
    )
    val listaColetas: StateFlow<List<Coleta>>
        get() = _listaColetas
}