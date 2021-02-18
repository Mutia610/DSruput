package com.mutia.dsruput.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import com.mutia.dsruput.R

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

    override fun onClick(v: View?) {
        when (v?.id) {

            //R.id.back -> activity?.onBackPressed()
            R.id.txtBack -> navController.navigate(R.id.loginFragment)

        }
    }

}