package br.com.fiap.trashit.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity( tableName = "tbl_usuario")
data class UsuarioAPI(
    val id: Long = 0,
    val nomeCompleto: String = "",
    val cpf: String = "",
    val email: String = "",
    val celular: String = "",
    val senha: String = "",
)