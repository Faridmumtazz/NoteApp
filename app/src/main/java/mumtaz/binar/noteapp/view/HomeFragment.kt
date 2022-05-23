package mumtaz.binar.noteapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.asLiveData
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import mumtaz.binar.noteapp.R
import mumtaz.binar.noteapp.adapter.NoteAdapter
import mumtaz.binar.noteapp.datastore.UserManager
import mumtaz.binar.noteapp.room.NoteDatabase


class HomeFragment : Fragment() {

    private lateinit var noteDatabase : NoteDatabase
    private lateinit var userManager: UserManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        noteDatabase = NoteDatabase.getInstance(requireContext())!!
        userManager = UserManager(requireContext())
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab_add.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_addFragment)
        }

        fab_profile.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
        }

        getList()
    }

    private fun getList() {
        userManager.email.asLiveData().observe(viewLifecycleOwner){
            GlobalScope.launch {
                val res = noteDatabase.noteDao().getNote(it)

                requireActivity().runOnUiThread {
                    if (res.isNotEmpty()) {
                        rv_note.layoutManager = LinearLayoutManager(requireContext())
                        rv_note.adapter = NoteAdapter(res){
                            val mBundle = bundleOf("extra_data" to it)
                            Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_detailFragment, mBundle)
                        }
                    }
                }

            }
        }
    }


}