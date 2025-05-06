package mohamad.hoseini.catapi.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "cat_breeds")
data class CatBreedEntity(
    @PrimaryKey(autoGenerate = false) val id: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "temperament") val temperament: String,
    @ColumnInfo(name = "origin") val origin: String,
    @ColumnInfo(name = "weight") val weight: String,
    @ColumnInfo(name = "countryCode") val countryCode: String,
    @ColumnInfo(name = "lifeSpan") val lifeSpan: String,
    @ColumnInfo(name = "adaptability") val adaptability: Int,
    @ColumnInfo(name = "affectionLevel") val affectionLevel: Int,
    @ColumnInfo(name = "childFriendly") val childFriendly: Int,
    @ColumnInfo(name = "wikipediaUrl") val wikipediaUrl: String,
    @ColumnInfo(name = "imageId") val imageId: String,
    @ColumnInfo(name = "created_at") val createdAt: Date,

)

