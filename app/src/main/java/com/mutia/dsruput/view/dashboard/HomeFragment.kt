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
import com.mutia.dsruput.adapter.MenuFavoriteAdapter
import com.mutia.dsruput.config.Network
import com.mutia.dsruput.model.getMenu.DataMenu
import com.mutia.dsruput.model.getMenu.ResponseGetMenu
import com.mutia.dsruput.preferences.PrefManager
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {

    lateinit var prefManager: PrefManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        showData()
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

        prefManager = PrefManager(requireContext())

        var jmlBagShop = prefManager.getValueInt("jmlBagShop")

        jmlBeliKeranjang.text = jmlBagShop.toString()

        icKeranjang.setOnClickListener {
            startActivity(Intent(context, KeranjangActivity::class.java))
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
}


