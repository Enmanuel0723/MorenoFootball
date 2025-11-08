package edu.ucne.morenofootball.data.entities.usuarios.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface UsuarioDao {
    @Upsert
    suspend fun save(user: UsuarioEntity)

    @Query("delete from usuarios where usuarioId = :id")
    suspend fun deleteById(id: Int): Boolean

    @Query("select * from usuarios limit 1")
    suspend fun getUser()
}