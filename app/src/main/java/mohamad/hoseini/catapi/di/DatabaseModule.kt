package mohamad.hoseini.catapi.di
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import mohamad.hoseini.catapi.data.local.database.AppDatabase
import mohamad.hoseini.catapi.data.local.database.dao.CatBreedDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideCatBreedDao(appDatabase: AppDatabase): CatBreedDao = appDatabase.catBreedDao()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context, AppDatabase::class.java, "database"
        ).build()

}