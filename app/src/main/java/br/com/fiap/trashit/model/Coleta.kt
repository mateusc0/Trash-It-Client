package br.com.fiap.trashit.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp
import java.time.Instant
import java.util.Date

@Entity(tableName = "tbl_coleta")
data class Coleta (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id_coleta") val id: Long,
    @ColumnInfo(name = "id_endereco_dono") val idEnderecoDono: Long,
    @ColumnInfo(name = "data_coleta") val dtColeta: Date = Timestamp.from(Instant.now()),
    @Embedded val lixeira: Lixeira
)