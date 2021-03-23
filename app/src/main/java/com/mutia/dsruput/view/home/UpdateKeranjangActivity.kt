package com.mutia.dsruput.view.home

import android.app.AlertDialog
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mutia.dsruput.R
import com.mutia.dsruput.model.getDataKeranjang.DataItemKeranjang
import kotlinx.android.synthetic.main.activity_update_keranjang.*

class UpdateKeranjangActivity : AppCompatActivity() {

    private var dialogView : Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_keranjang)

        val data = intent.getParcelableExtra<DataItemKeranjang>("data")

        txtRasaMenuKrj.text = data?.rasa
        txtVarianMenuKrj.text = data?.varian
        txtTambahanKrj.text = data?.tambahan
        hargaSatuanKrj.text = data?.harga
        txtHargaMenuKrj.text = data?.total_harga
        txtJmlUpdateKrj.text = data?.jumlah

        val dialog = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.activity_update_keranjang, null)
        dialog.setView(view)

//        view.back.setOnClickListener {
//            dialogView?.dismiss()
//        }

        dialogView = dialog.create()
        dialogView?.show()
    }
}