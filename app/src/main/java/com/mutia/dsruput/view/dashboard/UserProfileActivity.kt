package com.mutia.dsruput.view.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.VISIBLE
import android.widget.Toast
import com.mutia.dsruput.R
import com.mutia.dsruput.config.Network
import com.mutia.dsruput.model.ResponseGetUsers
import com.mutia.dsruput.model.action.ResponseAction
import com.mutia.dsruput.preferences.PrefManager
import kotlinx.android.synthetic.main.activity_user_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserProfileActivity : AppCompatActivity() {

    lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        prefManager = PrefManager(this)

        txtBackProfile.setOnClickListener {
            onBackPressed()
        }

        btnEditProfile.setOnClickListener {
            relativeShow.visibility = View.GONE
            relativeEdit.visibility = View.VISIBLE

        }

        btnUpdateProfile.setOnClickListener {
            var jenis_kelamin = ""
            val id = prefManager.getValueInt("id")

            if (rbLaki2.isChecked){
                jenis_kelamin = "Laki - Laki"
            }else{
                jenis_kelamin = "Perempuan"
            }

            if (etPasswordProfile.text.toString().isNotEmpty()){
                editProfilePass(id.toString(), etUsernameProfile.text.toString(), etEmailProfile.text.toString(), etPasswordProfile.text.toString(), etNoHpProfile.text.toString(), jenis_kelamin)
            }else{
                editProfile(id.toString(), etUsernameProfile.text.toString(), etEmailProfile.text.toString(), etNoHpProfile.text.toString(), jenis_kelamin)
            }
        }

        showProfileUser()

    }

    private fun showProfileUser() {
        val id_user = prefManager.getValueInt("id")
        val showProfile = Network.service().getUsers(id_user.toString())
        showProfile.enqueue(object : Callback<ResponseGetUsers> {
            override fun onResponse(
                call: Call<ResponseGetUsers>,
                response: Response<ResponseGetUsers>
            ) {
                val data = response.body()?.data
                val x = data?.size!!.toInt() - 1

                for (i in 0..x) {
                    val username = data?.get(i)?.username.toString()
                    val email = data?.get(i)?.email.toString()
                    val telp = data?.get(i)?.noTelp.toString()
                    val jenisKelamin = data?.get(i)?.jenisKelamin.toString()
                    val alamat = prefManager.getValueString("AlamatUser")

                    namaUserProfile.text = username
                    textUsernameProfile.text = username
                    textEmailProfile.text = email
                    textNoHpProfile.text = telp
                    textAlamatProfile.text = alamat.toString()

                    etUsernameProfile.setText(username)
                    etEmailProfile.setText(email)
                    etNoHpProfile.setText(telp)
                    etAlamatProfile.setText(alamat)

                    if (jenisKelamin.equals("Perempuan")) {
                        rbPerempuan1.isChecked = true
                        rbPerempuan2.isChecked = true
                        rbLaki1.isChecked = false
                        rbLaki2.isChecked = false
                    } else {
                        rbPerempuan1.isChecked = false
                        rbPerempuan2.isChecked = false
                        rbLaki1.isChecked = true
                        rbLaki2.isChecked = true
                    }
                }
            }

            override fun onFailure(call: Call<ResponseGetUsers>, t: Throwable) {
                Toast.makeText(this@UserProfileActivity, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun editProfile(id: String, username: String, email: String, no_telp: String, jenis_kelamin: String) {
        val editProfile = Network.service().updateUsers(id ?: "", username ?: "", email ?: "", no_telp ?: "", jenis_kelamin ?: "")
        editProfile.enqueue(object : Callback<ResponseAction>{
            override fun onResponse(
                call: Call<ResponseAction>,
                response: Response<ResponseAction>
            ) {
                Toast.makeText(this@UserProfileActivity, "Data berhasil di update", Toast.LENGTH_SHORT).show()
                relativeShow.visibility = View.VISIBLE
                relativeEdit.visibility = View.GONE
                showProfileUser()
            }

            override fun onFailure(call: Call<ResponseAction>, t: Throwable) {
                Toast.makeText(this@UserProfileActivity, "Gagal Update Data", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun editProfilePass(id: String, username: String, email: String, password: String, no_telp: String, jenis_kelamin: String) {
        val editProfile = Network.service().updateUsersPass(id ?: "", username ?: "", email ?: "", password ?: "", no_telp ?: "", jenis_kelamin ?: "")
        editProfile.enqueue(object : Callback<ResponseAction>{
            override fun onResponse(
                call: Call<ResponseAction>,
                response: Response<ResponseAction>
            ) {
                Toast.makeText(this@UserProfileActivity, "Data berhasil di update", Toast.LENGTH_SHORT).show()
                relativeShow.visibility = View.VISIBLE
                relativeEdit.visibility = View.GONE
                showProfileUser()
            }

            override fun onFailure(call: Call<ResponseAction>, t: Throwable) {
                Toast.makeText(this@UserProfileActivity, "Gagal Update Data", Toast.LENGTH_SHORT).show()
            }
        })
    }
}