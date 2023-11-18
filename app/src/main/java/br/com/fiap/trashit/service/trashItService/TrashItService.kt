package br.com.fiap.trashit.service.trashItService

import br.com.fiap.trashit.model.ColetaAPI
import br.com.fiap.trashit.model.EnderecoAPI
import br.com.fiap.trashit.model.UsuarioAPI
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
    fun getUsuarioById(@Path("id") id: Long): Call<UsuarioAPI>
    @GET("/endereco/{id}")
    fun getEnderecoById(@Path("id") id: Long): Call<EnderecoAPI>
    @GET("/coleta/{id_endereco}")
    fun getColetasByEnderecoId(@Path("id_endereco") id: Long): Call<List<ColetaAPI>>

    @GET("/usuario")
    fun getUsuarioByEmailESenha(@Query("email") email: String, @Query("senha") senha: String): Call<UsuarioAPI>
    @POST("/coleta/{id_endereco}")
    fun saveColeta(@Path("id_endereco") idEndereco: Long, @Body coleta: ColetaAPI): Call<ColetaAPI>
    @PUT("/usuario/{id}")
    fun updateUsuario(@Path("id") id: Long, @Body usuario: UsuarioAPI): Call<UsuarioAPI>
    @PUT("/endereco/{id}")
    fun updateEndereco(@Path("id") id: Long, @Body endereco: EnderecoAPI): Call<EnderecoAPI>
}