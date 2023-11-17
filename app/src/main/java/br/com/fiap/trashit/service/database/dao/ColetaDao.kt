package br.com.fiap.trashit.service.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.fiap.trashit.model.Coleta

@Dao
interface ColetaDao {
    @Insert
    fun salvar(coleta: Coleta):Long

    @Update
    fun atualizar(coleta: Coleta): Int

    @Delete
    fun excluir(coleta: Coleta): Int

    @Query("SELECT * FROM tbl_coleta WHERE id_coleta = :id")
    fun buscarColetaPorId(id: Long): Coleta

    @Query("Select * FROM tbl_coleta WHERE id_endereco_dono = :idResidencia ORDER BY data_coleta DESC")
    fun listarColetas(idResidencia: Long): List<Coleta>
}