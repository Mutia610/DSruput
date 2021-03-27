package com.mutia.dsruput.view.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.mutia.dsruput.R
import com.mutia.dsruput.adapter.RiwayatAdapter
import com.mutia.dsruput.config.Network
import com.mutia.dsruput.model.action.ResponseAction
import com.mutia.dsruput.model.riwayat.ResponseDataRiwayat
import com.mutia.dsruput.preferences.PrefManager
import kotlinx.android.synthetic.main.fragment_riwayat.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RiwayatFragment : Fragment() {

    lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        showDataRiwayat()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_riwayat, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefManager = PrefManager(requireContext())

    }

    private fun showDataRiwayat() {
//        var id_costumer = prefManager.getValueInt("id").toString()
        val show = Network.service().getOrders("1")
        show.enqueue(object : Callback<ResponseDataRiwayat>{
            override fun onResponse(
                call: Call<ResponseDataRiwayat>,
                response: Response<ResponseDataRiwayat>
            ) {
                val data = response.body()?.data

                val adapter = RiwayatAdapter(data)
                rvRiwayat.adapter = adapter
            }

            override fun onFailure(call: Call<ResponseDataRiwayat>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }
}
