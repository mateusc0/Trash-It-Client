package br.com.fiap.trashit.service.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.fiap.trashit.model.Endereco

@Dao
interface EnderecoDao {

    @Insert
    fun salvar(endereco: Endereco):Long

    @Update
    fun atualizar(endereco: Endereco): Int

    @Delete
    fun excluir(endereco: Endereco): Int

    @Query("SELECT * FROM tbl_endereco WHERE id_endereco = :id_endereco")
    fun buscarEnderecoPorId(id_endereco: Long): Endereco

    @Query("Select * FROM tbl_endereco ORDER BY id_endereco ASC")
    fun listarEnderecos(): List<Endereco>
}