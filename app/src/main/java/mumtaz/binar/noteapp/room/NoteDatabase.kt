package mumtaz.binar.noteapp.room

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao () : NoteDao

    companion object{
        private var INSTANCE : NoteDatabase? = null
        fun getInstance(context : Context):NoteDatabase? {
            if (INSTANCE == null){
                synchronized(NoteDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        NoteDatabase::class.java,"Note.db").build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance(){
            INSTANCE = null
        }
    }
}