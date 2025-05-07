package mohamad.hoseini.catapi.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import mohamad.hoseini.catapi.data.local.database.converter.DateConverter
import mohamad.hoseini.catapi.data.local.database.dao.CatBreedDao
import mohamad.hoseini.catapi.data.local.database.entity.CatBreedEntity

@Database(version = 1, entities = [CatBreedEntity::class])
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun catBreedDao(): CatBreedDao
}