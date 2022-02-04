package com.maksimyurau.android.qrcodescanner

import android.app.AlertDialog
import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val qrButton = findViewById<ImageButton>(R.id.qrcode_scanner_ib)
        qrButton.setOnClickListener {
            val intentIntegrator = IntentIntegrator(this)
            intentIntegrator.setDesiredBarcodeFormats(listOf(IntentIntegrator.QR_CODE))
            intentIntegrator.initiateScan()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val result = IntentIntegrator.parseActivityResult(resultCode, data)
        if (result != null) {
            AlertDialog.Builder(this)
                .setMessage("Would you like to go to ${result.contents}?")
                .setPositiveButton(resources.getString(R.string.yes)) { _, _ ->
                    val intent = Intent(Intent.ACTION_WEB_SEARCH)
                    intent.putExtra(SearchManager.QUERY, result.contents)
                    startActivity(intent)
                }
                .setNegativeButton(resources.getString(R.string.no)) { _, _ -> }
                .create()
                .show()
        }
    }
}