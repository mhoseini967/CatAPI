package mohamad.hoseini.catapi.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "favorite_breeds")
data class FavoriteBreedEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "breed_id") val breedId: String,
)

