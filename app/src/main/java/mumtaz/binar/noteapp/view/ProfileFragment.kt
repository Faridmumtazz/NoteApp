package mumtaz.binar.noteapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import mumtaz.binar.noteapp.R
import mumtaz.binar.noteapp.datastore.UserManager
import mumtaz.binar.noteapp.room.NoteDatabase


class ProfileFragment : Fragment() {
    private lateinit var noteDatabase : NoteDatabase
    private lateinit var userManager: UserManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        noteDatabase = NoteDatabase.getInstance(requireContext())!!
        userManager = UserManager(requireContext())
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userManager.email.asLiveData().observe(viewLifecycleOwner){
            GlobalScope.async {
                val user = noteDatabase.noteDao().getUserRegistered(it)
                requireActivity().runOnUiThread {
                    tv_username.text = user.nama
                    tv_email.text = user.email
                }
            }
        }

        btn_logout.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.action_profileFragment_to_loginFragment)
            GlobalScope.launch {
                userManager.saveData("")
            }
        }
    }
}