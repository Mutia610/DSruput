package com.mutia.dsruput.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mutia.dsruput.view.dashboard.DealsFragment
import com.mutia.dsruput.view.dashboard.SubscriptionFragment
import com.mutia.dsruput.view.dashboard.VoucherSayaFragment

class ViewPagerPromoAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return   when(position){
            0->{
                SubscriptionFragment()
            }
            1->{
                DealsFragment()
            }
            2->{
                VoucherSayaFragment()
            }
            else->{
                Fragment()
            }

        }
    }
}