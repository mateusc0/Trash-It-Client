package br.com.fiap.trashit.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_endereco")
data class Endereco (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id_endereco") val id: Long,
    val cep: String,
    var numero: String,
    val rua: String,
    var complemento: String,
    val bairro: String,
    val cidade: String,
    val uf: String,
    @Embedded val lixeira: Lixeira
)
