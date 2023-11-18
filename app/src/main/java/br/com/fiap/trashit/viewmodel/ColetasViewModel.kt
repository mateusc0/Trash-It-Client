package br.com.fiap.trashit.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import br.com.fiap.trashit.model.ColetaAPI
import br.com.fiap.trashit.service.database.repository.ColetaRepository
import br.com.fiap.trashit.service.trashItService.RetrofitFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ColetasViewModel(context: Context): ViewModel() {
    private val coletaRepository = ColetaRepository(context)
    var refreshed: Boolean = false;

    private var _listaColetas = MutableStateFlow<List<ColetaAPI>>(
        listOf()
    )
    val listaColetas: StateFlow<List<ColetaAPI>>
        get() = _listaColetas

    fun refreshView(){
        val callColetas: Call<List<ColetaAPI>> = RetrofitFactory().getTrashItService().getColetasByEnderecoId(1)
        callColetas.enqueue(object : Callback<List<ColetaAPI>> {
            override fun onResponse(
                call: Call<List<ColetaAPI>>,
                response: Response<List<ColetaAPI>>,
            ) {
                Log.d("TESTE API", "onResponse: ${response.body()}")
                _listaColetas.update { response.body()!! }
                refreshed = true
            }

            override fun onFailure(call: Call<List<ColetaAPI>>, t: Throwable) {
                Log.d("TESTE API", "onResponse: ${t.message}")
            }

        })
    }
}