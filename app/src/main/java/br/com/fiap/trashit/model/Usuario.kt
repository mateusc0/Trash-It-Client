package br.com.fiap.trashit.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity( tableName = "tbl_usuario")
data class Usuario(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id_usuario") val id: Long,
    @ColumnInfo(name = "id_endereco_usuario") val idEndereco: Long,
    @ColumnInfo(name = "nome_completo")val nomeCompleto: String,
    val cpf: String,
    val email: String,
    val celular: String,
    val senha: String,
    @ColumnInfo(name = "status_login")val isLogged: Boolean
)