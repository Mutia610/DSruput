package com.mutia.dsruput.view

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.mutia.dsruput.R
import com.mutia.dsruput.config.Network
import com.mutia.dsruput.model.login.ResponseLogin
import com.mutia.dsruput.preferences.PrefManager
import kotlinx.android.synthetic.main.fragment_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment : Fragment(), View.OnClickListener {

    lateinit var navController: NavController
    lateinit var prefManager: PrefManager
 //   val login: List<DataItemKeranjang?>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        btnLogin.setOnClickListener(this)
        btnRegister.setOnClickListener(this)
        chkShowPass.setOnClickListener(this)
        chkShowPass2.setOnClickListener(this)

        prefManager = PrefManager(requireContext())

        // langsung login
       // Log.d("tes","tes 1 :"+prefManager.getValueInt("id"))
        if (prefManager.getValueInt("id")!=0){
            navController.navigate(R.id.action_loginFragment_to_navActivity)
            Log.d("tes","tes  :"+prefManager.getValueInt("id"))
        }
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.btnLogin -> {
                login(emailLogin.text.toString(), passwordLogin.text.toString())
            }
            R.id.btnRegister -> navController.navigate(R.id.action_loginFragment_to_registerFragment)
            R.id.chkShowPass -> {
                    if (chkShowPass.isChecked) {
                        passwordLogin.setTransformationMethod(HideReturnsTransformationMethod.getInstance())
                        chkShowPass2.visibility = View.VISIBLE
                        chkShowPass.visibility = View.GONE
                    }
            }
            R.id.chkShowPass2 -> {
                if (chkShowPass2.isChecked){
                    passwordLogin.setTransformationMethod(PasswordTransformationMethod.getInstance())
                    chkShowPass.visibility = View.VISIBLE
                    chkShowPass2.visibility = View.GONE
                }
            }
        }
    }

    private fun login (email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
                val loginUser = Network.service().login( email ?: "", password ?: "")
                loginUser.enqueue(object : Callback<ResponseLogin> {

                    override fun onResponse(
                        call: Call<ResponseLogin>,
                        response: Response<ResponseLogin>
                    ) {
                        if (response.isSuccessful){
                            val status = response.body()?.isSuccess
                            val message = response.body()?.message

                            if (status ?: true){
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

                                prefManager.save("id",1)

                                navController.navigate(R.id.action_loginFragment_to_navActivity)
                            }else
                            {
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                        Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                    }
                })
        } else {
            if (email.isEmpty()) {
                emailLogin.error = "Email harus di isi"
            }

            if (password.isEmpty()) {
                passwordLogin.error = "Password harus di isi"
            }
        }
    }
}

