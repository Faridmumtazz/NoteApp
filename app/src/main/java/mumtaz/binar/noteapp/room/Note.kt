package mumtaz.binar.noteapp.room

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Note(
    @PrimaryKey(autoGenerate = true)
    var id : Int?,

    @ColumnInfo(name = "nama")
    var nama : String,

    @ColumnInfo(name = "note")
    var note:String
) : Parcelable
