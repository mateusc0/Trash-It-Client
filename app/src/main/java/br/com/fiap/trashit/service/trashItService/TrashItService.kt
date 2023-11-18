package br.com.fiap.trashit.service.trashItService

import br.com.fiap.trashit.model.UsuarioAPI
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface TrashItService {
    //http://10.0.2.2:8080/usuario/1
    @GET("/usuario/{id}")
    fun getUsuarioById(@Path("id") id: Long): Call<UsuarioAPI>
}