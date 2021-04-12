package com.mutia.dsruput.view.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.viewpager.widget.ViewPager
import com.mutia.dsruput.R
import kotlinx.android.synthetic.main.fragment_voucher_saya.*

class VoucherSayaFragment : Fragment() {

//    lateinit var navController: NavController
//    lateinit  var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_voucher_saya, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        navController = Navigation.findNavController(view)

//        btnBeliPaket.setOnClickListener {
//            navController.navigate(R.id.action_voucherSayaFragment_to_subscriptionFragment)
//            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1, true)
//        }
//
//        btnDapatkanVoucher.setOnClickListener {
//            navController.navigate(R.id.action_voucherSayaFragment_to_dealsFragment)
//        }
    }

}