package mohamad.hoseini.catapi.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import mohamad.hoseini.catapi.data.local.database.dao.CatBreedDao
import mohamad.hoseini.catapi.data.local.database.entity.CatBreedEntity

@Database(version = 1, entities = [CatBreedEntity::class])

abstract class AppDatabase : RoomDatabase() {
    abstract fun catBreedDao(): CatBreedDao
}