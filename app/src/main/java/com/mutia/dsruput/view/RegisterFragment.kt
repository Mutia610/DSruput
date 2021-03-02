package com.mutia.dsruput.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.easywaylocation.Listener
import com.mutia.dsruput.R
import com.mutia.dsruput.config.Network
import com.mutia.dsruput.model.action.ResponseAction
import kotlinx.android.synthetic.main.fragment_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterFragment : Fragment(), View.OnClickListener {

    lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        btnSave.setOnClickListener(this)
        txtBack.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.txtBack -> activity?.onBackPressed()
            R.id.btnSave -> {
                registrasi(etUsername.text.toString(), etEmail.text.toString(), etPassword.text.toString(), etPasswordKonfirmasi.text.toString())
            }
        }
    }

    private fun registrasi(username: String, email: String, password: String, passKonf: String){
        if(username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && passKonf.isNotEmpty()){
            if(password != passKonf){
                etPasswordKonfirmasi.error = "Password tidak sama"
            }else{
                val input = Network.service().register(username ?: "", email ?: "", password ?:"")
                input.enqueue(object : Callback<ResponseAction> {

                    override fun onResponse(
                        call: Call<ResponseAction>,
                        response: Response<ResponseAction>
                    ) {
                        Toast.makeText(context, "Registrasi Berhasil", Toast.LENGTH_SHORT).show()
                        //startActivity(Intent(context, MainActivity::class.java))
                        navController.navigate(R.id.action_registerFragment_to_loginFragment)
                    }

                    override fun onFailure(call: Call<ResponseAction>, t: Throwable) {
                        Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }else{
            if (username.isEmpty()){
                etUsername.error = "Username harus di isi"
            }

            if (email.isEmpty()){
                etEmail.error = "Email harus di isi"
            }

            if (password.isEmpty()){
                etPassword.error = "Password harus di isi"
            }

            if (passKonf.isEmpty()){
                etPasswordKonfirmasi.error = "Konfirmasi Password harus di isi"
            }
        }

    }
}