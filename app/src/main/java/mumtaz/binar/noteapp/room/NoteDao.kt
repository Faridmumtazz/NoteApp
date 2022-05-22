package mumtaz.binar.noteapp.room

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

interface NoteDao {

    @Insert
    fun insertStatus(note: Note) : Long

    @Query("SELECT * FROM Note")
    fun getAllStatus(): List<Note>

    @Delete
    fun deleteStatus(note: Note) : Int

    @Update
    fun updateStatus(note: Note) : Int
}