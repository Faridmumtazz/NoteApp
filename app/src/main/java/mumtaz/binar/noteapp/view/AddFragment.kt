package mumtaz.binar.noteapp.view

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.asLiveData
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import mumtaz.binar.noteapp.R
import mumtaz.binar.noteapp.datastore.UserManager
import mumtaz.binar.noteapp.room.Note
import mumtaz.binar.noteapp.room.NoteDatabase
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*


class AddFragment : Fragment() {
    private lateinit var userManager: UserManager
    private lateinit var noteDatabase: NoteDatabase

    private val c = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        noteDatabase = NoteDatabase.getInstance(requireContext())!!
        userManager = UserManager(requireContext())
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_add.setOnClickListener {
            val judul = input_judul.text.toString()
            val note = input_catatan.text.toString()
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
            val formatted = current.format(formatter)

            userManager.email.asLiveData().observe(viewLifecycleOwner) {
                if (checkInput(judul, note)) {
                    val note = Note(null, judul, note, it, formatted.toString())
                    addNote(note)
                }
            }

        }


    }

    private fun addNote(note: Note) {
        GlobalScope.async {
            val result = noteDatabase.noteDao().addNote(note)
            requireActivity().runOnUiThread {
                if (result != 0.toLong()) {
                    Toast.makeText(requireContext(), "Note Ditambahkan", Toast.LENGTH_SHORT).show()
                    Navigation.findNavController(requireView()).navigate(R.id.action_addFragment_to_homeFragment)
                } else {
                    Toast.makeText(requireContext(), "Gagal Menambahkan", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun checkInput(judul: String, note: String): Boolean {
        if (judul.isEmpty() || note.isEmpty()) {
            if (note.isEmpty()) {
                input_catatan.setError("Catatan tidak boleh kosong")
                input_catatan.requestFocus()
            }
            if (judul.isEmpty()) {
                input_judul.setError("Judul tidak boleh kosong")
                input_judul.requestFocus()
            }
            return false
        } else {
            return true
        }
    }

}