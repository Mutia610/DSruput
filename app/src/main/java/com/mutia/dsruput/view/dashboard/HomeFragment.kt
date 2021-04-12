package com.mutia.dsruput.view.dashboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.denzcoskun.imageslider.models.SlideModel
import com.mutia.dsruput.R
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.mutia.dsruput.adapter.KeranjangAdapter
import com.mutia.dsruput.adapter.MenuFavoriteAdapter
import com.mutia.dsruput.config.Network
import com.mutia.dsruput.model.getDataKeranjang.DataItemKeranjang
import com.mutia.dsruput.model.getDataKeranjang.ResponseGetDataKeranjang
import com.mutia.dsruput.model.getMenu.DataMenu
import com.mutia.dsruput.model.getMenu.ResponseGetMenu
import com.mutia.dsruput.preferences.PrefManager
import kotlinx.android.synthetic.main.activity_keranjang.*
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {

    lateinit var prefManager: PrefManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        showData()
        showDataKeranjang()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageList = ArrayList<SlideModel>()
       // imageList.add(SlideModel(R.drawable.img1))
        imageList.add(SlideModel(R.drawable.img3))
        imageList.add(SlideModel(R.drawable.img9))
        imageList.add(SlideModel(R.drawable.img10))
        imageList.add(SlideModel(R.drawable.img11))
;
        val imageSlider = image_slider
        imageSlider.setImageList(imageList, ScaleTypes.FIT)

        icKeranjang.setOnClickListener {
            startActivity(Intent(context, KeranjangActivity::class.java))
        }

        icPesan.setOnClickListener {
            startActivity(Intent(context, MessageActivity::class.java))
        }

        txtLebihBanyak.setOnClickListener {
            startActivity(Intent(context, BeritaActivity::class.java))
        }
    }

    private fun showData() {
        val listMenu = Network.service().getMenu("1")
        listMenu.enqueue(object : Callback<ResponseGetMenu> {

            override fun onResponse(
                call: Call<ResponseGetMenu>,
                response: Response<ResponseGetMenu>
            ) {
                if (response.isSuccessful){
                    val item = response.body()?.data

                    val adapter = MenuFavoriteAdapter(item, object : MenuFavoriteAdapter.OnClickListener{
                        override fun detailMenuFav(data: DataMenu?) {
//                            val intent = Intent(context, DetailMenu::class.java)
//                            startActivity(intent)
                        }
                    })
                    recyclerFavorite.adapter = adapter
                }
            }

            override fun onFailure(call: Call<ResponseGetMenu>, t: Throwable) {
                Log.d("eror", "eror : " + t + " cal :" + call);
                Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun showDataKeranjang() {
        prefManager = PrefManager(requireContext())
        val id_user = prefManager.getValueInt("id").toString()

        val show = Network.service().getDataKeranjang(id_user)
        show.enqueue(object : Callback<ResponseGetDataKeranjang> {
            override fun onResponse(
                call: Call<ResponseGetDataKeranjang>,
                response: Response<ResponseGetDataKeranjang>
            ) {
                if (response.isSuccessful) {
                    val item = response.body()?.data

                    val banyak = item?.size!!.toInt()
                    if (banyak != 0){
                        jmlBeliKeranjang.text = banyak.toString()
                    }else{
                        jmlBeliKeranjang.visibility = View.GONE
                    }
                } else {
                    Toast.makeText(context, "Gagal mendapatkan jumlah item", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseGetDataKeranjang>, t: Throwable) {
                Toast.makeText(context, "Gagal Get Data", Toast.LENGTH_SHORT).show()
            }

        })
    }
}


