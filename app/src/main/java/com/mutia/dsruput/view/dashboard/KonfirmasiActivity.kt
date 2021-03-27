package com.mutia.dsruput.view.dashboard

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mutia.dsruput.R
import com.mutia.dsruput.config.Network
import com.mutia.dsruput.model.ResponseActionKonfirmasi
import kotlinx.android.synthetic.main.activity_konfirmasi.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException

class KonfirmasiActivity : AppCompatActivity() {

    var decoded: Bitmap? = null
    var bitmap_size = 60
    private val GALLERY = 1

    companion object{
        private val PERMISSION_CODE = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_konfirmasi)

        val idKonfimasi = intent.getIntExtra("Metode", 0)
        val totalBayar = intent.getStringExtra("totalBayar")
        val idOrder = intent.getStringExtra("idOrder").toString()

        totBayarKonf.text = "Rp. " + totalBayar.toString()

        if (idKonfimasi == 1){
            layoutTransfer.visibility = View.VISIBLE
            layoutCod.visibility = View.GONE

            imgBuktiTransfer.setOnClickListener {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                        requestPermissions(permissions, PERMISSION_CODE)
                    } else {
                        pickImageFromGallery()
                    }
                } else {
                    pickImageFromGallery()
                }
            }

            if (imgBuktiTransfer.drawable.equals(R.drawable.bukti_tranfer)){
                AlertDialog.Builder(this)
                    .setMessage("Masukan Bukti transfer !")
                    .setCancelable(false)
                    .setNegativeButton("Ok", null)
                    .show()
            }else{
                btnSelesai.setOnClickListener {
                    insertKonfirmasi(idOrder, getStringImage(decoded)!!, totalBayar.toString())
                }
            }
        }else{
            layoutTransfer.visibility = View.GONE
            layoutCod.visibility = View.VISIBLE

            btnSelesai.setOnClickListener {
                insertKonfirmasi(idOrder, idOrder, totalBayar.toString())
            }
        }
    }

    private fun insertKonfirmasi(id_order: String?, bukti_bayar: String?, total_bayar: String?) {
        val insert = Network.service().konfirmasi(
            id_order ?: "",
            bukti_bayar ?: "",
            total_bayar ?: ""
        )
        insert.enqueue(object : Callback<ResponseActionKonfirmasi> {
            override fun onResponse(
                call: Call<ResponseActionKonfirmasi>,
                response: Response<ResponseActionKonfirmasi>
            ) {
                if (response.isSuccessful) {
                    val intent = Intent(this@KonfirmasiActivity, NavActivity::class.java)
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                    finish()
                    Toast.makeText(applicationContext, response.message(), Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<ResponseActionKonfirmasi>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun pickImageFromGallery(){
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryIntent.type = "image/*"
        startActivityForResult(galleryIntent, GALLERY)
    }

    fun getStringImage(bmp: Bitmap?): String? {
        val baos = ByteArrayOutputStream()
        bmp?.compress(Bitmap.CompressFormat.JPEG, bitmap_size, baos)
        val imageBytes: ByteArray = baos.toByteArray()
        return Base64.encodeToString(imageBytes, Base64.DEFAULT)
    }

    private fun setToImageView(bmp: Bitmap?) {
        //compress image
        val bytes = ByteArrayOutputStream()
        bmp?.compress(Bitmap.CompressFormat.JPEG, bitmap_size, bytes)
        decoded = BitmapFactory.decodeStream(ByteArrayInputStream(bytes.toByteArray()))
        //menampilkan gambar yang dipilih dari camera/gallery ke ImageView
        imgBuktiTransfer.setImageBitmap(decoded)
    }

    // fungsi resize image
    fun getResizedBitmap(image: Bitmap, maxSize: Int): Bitmap? {
        var width = image.width
        var height = image.height
        val bitmapRatio = width.toFloat() / height.toFloat()
        if (bitmapRatio > 1) {
            width = maxSize
            height = (width / bitmapRatio).toInt()
        } else {
            height = maxSize
            width = (height * bitmapRatio).toInt()
        }
        return Bitmap.createScaledBitmap(image, width, height, true)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        // super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGallery()
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode === GALLERY && resultCode === RESULT_OK && android.R.attr.data != null && data?.data != null)  {
            var filePath = data!!.data
            try {
                //mengambil fambar dari Gallery
                var bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)

                // 512 adalah resolusi tertinggi setelah image di resize, bisa di ganti.
//                inImgPlant.setImageBitmap(bitmap)
                setToImageView(getResizedBitmap(bitmap, 512));
            }
            catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}