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
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import mumtaz.binar.noteapp.R
import mumtaz.binar.noteapp.datastore.UserManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginFragment : Fragment() {

    lateinit var email: String
    lateinit var password: String
    lateinit var toast : String
    lateinit var userManager: UserManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        userManager = UserManager(requireContext())

        val daftar = view.findViewById<TextView>(R.id.registrasi)
        val login = view.findViewById<Button>(R.id.btnlogin)

        daftar.setOnClickListener {
            view.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        login.setOnClickListener {
            if (loginemail.text.isNotEmpty() && loginpassword.text.isNotEmpty()){
                email = loginemail.text.toString()
                password = loginpassword.text.toString()
            }
            else{
                toast = "Harap Isi Semua Data"
                custom()
            }
        }
        return view
    }

    fun custom(){
        val text = toast
        val toast = Toast.makeText(
            requireActivity()?.getApplicationContext(),
            text,
            Toast.LENGTH_LONG
        )
        val text1 =
            toast.getView()?.findViewById(android.R.id.message) as TextView
        val toastView: View? = toast.getView()
        toastView?.setBackgroundColor(Color.TRANSPARENT)
        text1.setTextColor(Color.RED);
        text1.setTextSize(15F)
        toast.show()
        toast.setGravity(Gravity.CENTER or Gravity.TOP, 0, 960)
    }



}