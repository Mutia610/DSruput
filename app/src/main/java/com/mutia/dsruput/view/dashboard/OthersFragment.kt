package com.mutia.dsruput.view.dashboard

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mutia.dsruput.R
import com.mutia.dsruput.adapter.OthersAdapter
import com.mutia.dsruput.preferences.SessionManager
import com.mutia.dsruput.model.others.DataOthers
import com.mutia.dsruput.preferences.PrefManager
import com.mutia.dsruput.view.MainActivity
import kotlinx.android.synthetic.main.fragment_others.*


class OthersFragment : Fragment() {

    lateinit var navController: NavController
    lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_others, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataKolomSatu = ArrayList<DataOthers>()
        dataKolomSatu.add(DataOthers(R.drawable.ic_profil_outline, "Profile"))
        dataKolomSatu.add(DataOthers(R.drawable.ic_membership_card_outline, "Membership"))
        dataKolomSatu.add(DataOthers(R.drawable.ic_newspaper_outline, "Berita"))
        dataKolomSatu.add(DataOthers(R.drawable.ic_location_favorite_outline, "Lokasi Favorite"))

        val adapter1 = OthersAdapter(dataKolomSatu,object : OthersAdapter.OnClickListener{
            override fun detail(keterangan: String?) {
                if (keterangan.equals("Profile")){
                    startActivity(Intent(context, UserProfileActivity::class.java))
                }else if (keterangan.equals("Berita")){
                    startActivity(Intent(context, BeritaActivity::class.java))
                }else if (keterangan.equals("Membership")){
                   // startActivity(Intent(context, BeritaActivity::class.java))
                }else{

                }
            }
        })

        recyclerKolom1.setLayoutManager(LinearLayoutManager(context))
        recyclerKolom1.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        recyclerKolom1.adapter = adapter1

        val dataKolomDua = ArrayList<DataOthers>()
        dataKolomDua.add(DataOthers(R.drawable.ic_about_ouline, "Tentang"))
        dataKolomDua.add(DataOthers(R.drawable.ic_faq_outline_in_circle, "FAQ"))
        dataKolomDua.add(DataOthers(R.drawable.ic_syarat_outline, "Syarat dan Ketentuan"))
        dataKolomDua.add(DataOthers(R.drawable.ic_phone_outline, "Hubungi Kami"))

        val adapter2 = OthersAdapter(dataKolomDua,object : OthersAdapter.OnClickListener{
            override fun detail(keterangan: String?) {
                if (keterangan.equals("Tentang")){
                    startActivity(Intent(context, AboutActivity::class.java))
                }else if (keterangan.equals("FAQ")){
                    startActivity(Intent(context, faqActivity::class.java))
                }else if (keterangan.equals("Syarat dan Ketentuan")){
//                    startActivity(Intent(context, AboutActivity::class.java))
                }else{
                    startActivity(Intent(context, ContactActivity::class.java))
                }
            }
        })

        recyclerKolom2.setLayoutManager(LinearLayoutManager(context))
        recyclerKolom2.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        recyclerKolom2.adapter = adapter2

        navController = Navigation.findNavController(view)

        btnSignOut.setOnClickListener {
            val session = SessionManager(requireContext())
            AlertDialog.Builder(context).apply {
                setTitle("Sign Out")
                setMessage("Yakin ingin keluar ?")
                setCancelable(false)
                setPositiveButton("Yakin") { dialogInterface, i ->
                    session.logout()
                    val intent = Intent(context, MainActivity::class.java)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                    activity?.finish()
                }
                setNegativeButton("Batal") { dialogInterface, i ->
                    dialogInterface.dismiss()
                }
            }.show()
        }
    }
}