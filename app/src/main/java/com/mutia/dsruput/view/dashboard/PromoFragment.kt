package com.mutia.dsruput.view.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.mutia.dsruput.R
import com.mutia.dsruput.adapter.ViewPagerPromoAdapter
import kotlinx.android.synthetic.main.fragment_promo.*

class PromoFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_promo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter= fragmentManager?.let { ViewPagerPromoAdapter(it, lifecycle) }

        viewPagerPromo.adapter = adapter

        TabLayoutMediator(tabLayout,viewPagerPromo){tab,position->
            when(position){
                0->{
                    tab.text="Subscription"
                }
                1->{
                    tab.text="Deals"
                }
                2->{
                    tab.text="Voucher Saya"
                }
            }
        }.attach()


    }
}