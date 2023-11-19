package br.com.fiap.trashit.service.trashItService

import br.com.fiap.trashit.model.Coleta
import br.com.fiap.trashit.model.Endereco
import br.com.fiap.trashit.model.Usuario
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface TrashItService {
    //http://10.0.2.2:8080/usuario/1
    @GET("/usuario/{id}")
    fun getUsuarioById(@Path("id") id: Long): Call<Usuario>
    @GET("/endereco/{id}")
    fun getEnderecoById(@Path("id") id: Long): Call<Endereco>
    @GET("/coleta/{id_endereco}")
    fun getColetasByEnderecoId(@Path("id_endereco") id: Long): Call<List<Coleta>>

    @GET("/usuario")
    fun getUsuarioByEmailESenha(@Query("email") email: String, @Query("senha") senha: String): Call<Usuario>
    @POST("/coleta/{id_endereco}")
    fun saveColeta(@Path("id_endereco") idEndereco: Long, @Body coleta: Coleta): Call<Coleta>
    @PUT("/usuario/{id}")
    fun updateUsuario(@Path("id") id: Long, @Body usuario: Usuario): Call<Usuario>
    @PUT("/endereco/{id}")
    fun updateEndereco(@Path("id") id: Long, @Body endereco: Endereco): Call<Endereco>
}