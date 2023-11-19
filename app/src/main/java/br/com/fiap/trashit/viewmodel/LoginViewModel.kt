package br.com.fiap.trashit.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import br.com.fiap.trashit.model.Usuario
import br.com.fiap.trashit.service.trashItService.RetrofitFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(context: Context): ViewModel() {
    private val _usuario = MutableStateFlow<Usuario>(Usuario())
    val usuario: StateFlow<Usuario>
        get() = _usuario

    fun refreshView(){
        val callUsuario: Call<Usuario> = RetrofitFactory()
            .getTrashItService()
            .getUsuarioById(1)

        callUsuario.enqueue(object : Callback<Usuario> {
            override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                if (response.body() != null){
                    _usuario.update { response.body()!! }
                }
            }

            override fun onFailure(call: Call<Usuario>, t: Throwable) {
                Log.d("TRASHIT - ERROR", "Menssagem: Verifique se o serviço foi" +
                        " iniciado ou está rodando adequadamente")
                System.exit(0)
            }

        })
    }
}