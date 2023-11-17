package br.com.fiap.trashit.service.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.fiap.trashit.model.Usuario

@Dao
interface UsuarioDao {
    @Insert
    fun salvar(usuario: Usuario):Long

    @Update
    fun atualizar(usuario: Usuario): Int

    @Delete
    fun excluir(usuario: Usuario): Int

    @Query("SELECT * FROM tbl_usuario WHERE id_usuario = :id_usuario")
    fun buscarUsuarioPorId(id_usuario: Long): Usuario

    @Query("SELECT * FROM tbl_usuario WHERE email = :email AND :senha")
    fun buscarUsuarioPorEmailESenha(email: String, senha: String): Usuario

    @Query("Select * FROM tbl_usuario WHERE :id_endereco_usuario ORDER BY id_usuario ASC")
    fun listarUsuariosEndereco(id_endereco_usuario: Long): List<Usuario>

}