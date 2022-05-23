package mumtaz.binar.noteapp.view

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import mumtaz.binar.noteapp.R
import mumtaz.binar.noteapp.datastore.UserManager
import mumtaz.binar.noteapp.room.NoteDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginFragment : Fragment() {

    private var noteDatabase : NoteDatabase? = null

    private lateinit var email: String
    private lateinit var password: String

    private lateinit var userManager: UserManager
    private var cek: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        noteDatabase = NoteDatabase.getInstance(requireContext())
        userManager = UserManager(requireContext())
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userManager.email.asLiveData().observe(viewLifecycleOwner){
           if (it != ""){
               Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_homeFragment)
           }
        }

        registrasi.setOnClickListener {
            it.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        btnlogin.setOnClickListener {
            login()
        }

    }

    private fun login(){
        email = loginemail.text.toString()
        password = loginpassword.text.toString()
        loginUser(email, password)
    }

    private fun loginUser(email: String, password: String){
        GlobalScope.async {
            val user = noteDatabase?.noteDao()?.getUserRegistered(email)
            requireActivity().runOnUiThread {
                if (user != null) {
                    if (email == user.email && password == user.password){
                        GlobalScope.launch {
                            userManager.saveData(email)
                        }
                        Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_homeFragment)
                    } else {
                        Toast.makeText(requireContext(), "Password yang anda masukkan salah",Toast.LENGTH_LONG).show()

                    }
                }else{
                    Toast.makeText(requireContext(), "Akun dengan email ${email} belum terdaftar", Toast.LENGTH_LONG).show()
                }
            }
        }
    }



}