package com.example.vkcase



import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.vkcase.databinding.ActivityMainBinding
import com.google.android.material.imageview.ShapeableImageView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var cameraStatus = false
        var microStatus = true
        var greedFlag = true

        val firstGrid = findViewById<FrameLayout>(R.id.firstTile)
        val secondGrid = findViewById<FrameLayout>(R.id.secondTile)
        val linearLayout = findViewById<LinearLayout>(R.id.linear)

        getImage(binding.image)


        binding.camera.setOnClickListener {
            changeIconCamera(binding.camera, cameraStatus)
            cameraStatus = !cameraStatus
        }

        binding.micro.setOnClickListener {
            changeIconMicro(binding.micro, binding.you, microStatus)
            microStatus = !microStatus
        }

        binding.group.setOnClickListener {
            openApplication("content://contacts/people/")
        }

        binding.chat.setOnClickListener {
            openApplication("sms:")
        }

        binding.hand.setOnClickListener {
            openDialog()
        }

        binding.grid.setOnClickListener {
            changeGreed(greedFlag, linearLayout, firstGrid, secondGrid)
            greedFlag = !greedFlag
        }

        binding.phone.setOnClickListener {
            finish()
        }
    }

    private fun getImage(image: ShapeableImageView) {
        binding.progressBar.visibility = View.VISIBLE
        Glide.with(this)
            .load(resources.getString(R.string.link))
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .fitCenter()
            .into(image)


    }


    private fun changeGreed(
        greedFlag: Boolean,
        linearLayout: LinearLayout,
        firstGrid: FrameLayout,
        secondGrid: FrameLayout
    ) {
        linearLayout.removeViewAt(0)
        linearLayout.removeViewAt(0)
        if (greedFlag) {
            linearLayout.addView(secondGrid)
            linearLayout.addView(firstGrid)
        } else {
            linearLayout.addView(firstGrid)
            linearLayout.addView(secondGrid)
        }
    }

    private fun openDialog() {
        val dialog = AlertDialog.Builder(this)
            .setTitle(resources.getString(R.string.alertTitle))
            .setMessage(resources.getString(R.string.alertMessage))
            .setPositiveButton(resources.getString(R.string.positiveButton)) { _, _ ->
                getImage(binding.image)
                Toast.makeText(this, resources.getString(R.string.positiveToast), Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton(resources.getString(R.string.negativeButton)) { _, _ ->
                Toast.makeText(this, resources.getString(R.string.negativeToast), Toast.LENGTH_SHORT).show()
            }
            .create()
        dialog.show()
    }


    private fun openApplication(app: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(app)
        startActivity(intent)
    }


    private fun changeIconCamera(button: ImageButton, cameraStatus: Boolean) {
        if (cameraStatus) {
            button.setImageResource(R.drawable.videocam_off_fill0_wght400_grad0_opsz48)
            button.setBackgroundResource(R.drawable.circle_white)
            binding.youImage.setImageResource(R.drawable.no_photography_fill0_wght400_grad0_opsz48)
        } else {
            button.setImageResource(R.drawable.videocam_fill0_wght400_grad0_opsz48)
            button.setBackgroundResource(R.drawable.circle_gray)
            binding.youImage.setImageResource(R.drawable.avatar)
        }
    }


    private fun changeIconMicro(button: ImageButton, text: TextView, microStatus: Boolean) {
        if (microStatus) {
            button.setImageResource(R.drawable.mic_off_fill0_wght400_grad0_opsz48)
            button.setBackgroundResource(R.drawable.circle_white)
            text.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.micro_off, 0)
        } else {
            button.setImageResource(R.drawable.mic_fill0_wght400_grad0_opsz48)
            button.setBackgroundResource(R.drawable.circle_gray)
            text.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.micro, 0)
        }
    }
}

