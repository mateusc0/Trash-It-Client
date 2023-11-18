package br.com.fiap.trashit.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import br.com.fiap.trashit.model.Endereco
import br.com.fiap.trashit.model.Lixeira
import br.com.fiap.trashit.model.Usuario
import br.com.fiap.trashit.model.UsuarioAPI
import br.com.fiap.trashit.service.database.repository.EnderecoRepository
import br.com.fiap.trashit.service.database.repository.UsuarioRepository
import br.com.fiap.trashit.service.trashItService.RetrofitFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(context: Context): ViewModel() {
    private val enderecoRepository = EnderecoRepository(context)
    private val usuarioRepository = UsuarioRepository(context)
    private val _usuario = MutableStateFlow<UsuarioAPI>(UsuarioAPI())
    val usuario: StateFlow<UsuarioAPI>
        get() = _usuario

    fun refreshView(){
        val callUsuario: Call<UsuarioAPI> = RetrofitFactory()
            .getTrashItService()
            .getUsuarioById(1)

        callUsuario.enqueue(object : Callback<UsuarioAPI> {
            override fun onResponse(call: Call<UsuarioAPI>, response: Response<UsuarioAPI>) {
                if (response.body() != null){
                    _usuario.update { response.body()!! }
                }
            }

            override fun onFailure(call: Call<UsuarioAPI>, t: Throwable) {
                Log.d("TESTE API LOGIN", "onResponse: ${t.message}")            }

        })
    }
}