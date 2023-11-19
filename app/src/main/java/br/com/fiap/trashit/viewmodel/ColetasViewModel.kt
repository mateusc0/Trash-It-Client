package br.com.fiap.trashit.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import br.com.fiap.trashit.model.Coleta
import br.com.fiap.trashit.service.trashItService.RetrofitFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ColetasViewModel(context: Context): ViewModel() {
    var refreshed: Boolean = false;

    private var _listaColetas = MutableStateFlow<List<Coleta>>(
        listOf()
    )
    val listaColetas: StateFlow<List<Coleta>>
        get() = _listaColetas

    fun refreshView(){
        val callColetas: Call<List<Coleta>> = RetrofitFactory().getTrashItService().getColetasByEnderecoId(1)
        callColetas.enqueue(object : Callback<List<Coleta>> {
            override fun onResponse(
                call: Call<List<Coleta>>,
                response: Response<List<Coleta>>,
            ) {
                _listaColetas.update { response.body()!! }
                refreshed = true
            }

            override fun onFailure(call: Call<List<Coleta>>, t: Throwable) {
                Log.d("TRASHIT - ERROR", "Menssagem: Verifique se o serviço foi" +
                        " iniciado ou está rodando adequadamente")
            }

        })
    }
}