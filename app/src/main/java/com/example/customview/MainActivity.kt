package com.example.customview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.example.customview.databinding.ActivityMainBinding
import com.example.customview.views.AvatarView

class MainActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        _binding.avatarView.run {
            setLabelType(AvatarView.LabelType.TYPE_OWNER)
            avatarDrawable =
                ResourcesCompat.getDrawable(resources, R.drawable.core_demo_cat, null)
            isShowBorder = true
        }
    }
}