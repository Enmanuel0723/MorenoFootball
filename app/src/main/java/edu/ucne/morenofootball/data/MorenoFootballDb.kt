package edu.ucne.morenofootball.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import edu.ucne.morenofootball.data.usuarios.local.UsuarioDao
import edu.ucne.morenofootball.data.usuarios.local.UsuarioEntity
import edu.ucne.morenofootball.utils.DateAdapter

@Database(
    entities = [
        UsuarioEntity::class,
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(DateAdapter::class)
abstract class MorenoFootballDb : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao
}