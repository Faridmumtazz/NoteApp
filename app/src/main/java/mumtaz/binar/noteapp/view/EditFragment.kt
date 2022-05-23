package mumtaz.binar.noteapp.view

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.input_catatan
import kotlinx.android.synthetic.main.fragment_add.input_judul
import kotlinx.android.synthetic.main.fragment_edit.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import mumtaz.binar.noteapp.R
import mumtaz.binar.noteapp.room.Note
import mumtaz.binar.noteapp.room.NoteDatabase
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


class EditFragment : Fragment() {

    private lateinit var noteDatabase : NoteDatabase
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        noteDatabase = NoteDatabase.getInstance(requireContext())!!
        return inflater.inflate(R.layout.fragment_edit, container, false)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val noteData = arguments?.getParcelable<Note>("extra_edit") as Note

        input_judul.setText(noteData.judul)
        input_catatan.setText(noteData.catatan)

        btn_edit.setOnClickListener {
            val judul = input_judul.text.toString()
            val catatan = input_catatan.text.toString()
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
            val formatted = current.format(formatter)
            val note = Note(noteData.id, judul, catatan, noteData.email, formatted.toString())


            GlobalScope.async {
                val res = noteDatabase.noteDao().updateNote(note)

                requireActivity().runOnUiThread {
                    if (res != 0) {
                        Navigation.findNavController(view).navigate(R.id.action_editFragment_to_homeFragment)
                    }
                }
            }
        }
    }


}