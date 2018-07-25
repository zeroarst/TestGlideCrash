package com.zeorarst.testglidecrash

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.ViewTarget
import com.bumptech.glide.request.transition.Transition
import com.bumptech.glide.signature.ObjectKey
import kotlinx.android.synthetic.main.activity_1st.*

class FirstActivity : AppCompatActivity() {

    var second = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_1st)

        bt.setOnClickListener {
            second = true
            startActivity(Intent(this, SecondActivity::class.java))
        }

        if (PermissionUtils.verifyStoragePermissions(this, true))
            loadImg(false)
    }

    override fun onResume() {
        super.onResume()
        if (second)
            loadImg(true)
    }

    private fun loadImg(second: Boolean) {
        Glide.with(this).asBitmap()
            .apply(RequestOptions.signatureOf(ObjectKey(System.nanoTime().toString())))
            .load("https://lh3.googleusercontent.com/_QBUrbWexlXjySCSegm4fPoh7CdB2J2_cX9gTnH-NZRsqegGIyoWYyppnzzxzyNhqvAd8CG3nTUVXDm0OdSCTw3hfr1AdqpR1XLawuX_4l8hXLRLp-1T8DWPHlgSOBXSH6b8o5IIqtud_DiLBTvbvWIwXqBbEUbQ84-lIEwiVlCBi6LLCR31e5ymKtgazv2hYGi1LMoHpHEmh0sZ17QCkN3Qn1qwW_wxEKDT5k5nwt4QhJv-R8dLhROjt_h1_bGTKk5sOP3NIakGGVqsgmLpAkNK2ZCPaiVEpeeGyQR3Z1-unVHdW1_TEQRpMycZfbsucT_4_p74e-egoosfJ883K3QJNTSjkfHTmWufLPqtSxCoFCvFY44IL6_PwVD9ZOOqLEKXwmqIzVfNv3b13AcXiasG8bQGl3A__Z8bTHEwHHs-Hjzs1Sapu4Bg4LAkC-K3M_yaswNC1Dio8jVmn_CnjzusPCKX92nRtyNzmO5CTpWYsEvVaReYRukSxAkqWqCgpFQOaeGt8cA1K9gqvADmmg_vDtq4kSTOkmlE7c4TOTQQHDe7J9pYCJXoTaoZvXRLztjhlp3yYDZvBz03P3hDUDCPAEAiEAHMkARuM9J2=w1073-h744-no")
            .into(object : ViewTarget<View, Bitmap>(iv) {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {

                    /* For debug. use normal ImageView to test */
                    iv.apply {
                        BitmapDrawable(resources, resource).apply {
                            if (second) {
                                alpha = 255 - 150
                            }
                            setImageDrawable(this)
                        }


                    }


                }
            })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == PermissionUtils.REQUEST_EXTERNAL_STORAGE) {
            if (PermissionUtils.verifyStoragePermissions(this, false)) {
                loadImg(false)
            } else
                finish()
        }
    }

}
