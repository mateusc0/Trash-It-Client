package br.com.fiap.trashit.service.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.fiap.trashit.model.Coleta
import br.com.fiap.trashit.model.Endereco
import br.com.fiap.trashit.model.Usuario
import br.com.fiap.trashit.service.database.dao.ColetaDao
import br.com.fiap.trashit.service.database.dao.EnderecoDao
import br.com.fiap.trashit.service.database.dao.UsuarioDao

@Database(entities = [Endereco::class, Coleta::class, Usuario::class], version = 5)
@TypeConverters(Converter::class)
abstract class TrashItDb: RoomDatabase() {

    abstract fun enderecoDao(): EnderecoDao
    abstract fun coletaDao(): ColetaDao
    abstract fun usuarioDao(): UsuarioDao

    companion object{
        private lateinit var instance: TrashItDb

        fun getDatabase(context: Context): TrashItDb {
            if (!::instance.isInitialized) {
                instance = Room
                    .databaseBuilder(
                        context,
                        TrashItDb::class.java,
                        "trashIt_db"
                    )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance
        }
    }
}