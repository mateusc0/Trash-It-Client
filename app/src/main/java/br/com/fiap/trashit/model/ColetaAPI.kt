package br.com.fiap.trashit.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp
import java.time.Instant
import java.util.Date

@Entity(tableName = "tbl_coleta")
data class ColetaAPI (
    val id: Long,
    val dtColeta: Date = Timestamp.from(Instant.now()),
    val lixeira: Lixeira
)