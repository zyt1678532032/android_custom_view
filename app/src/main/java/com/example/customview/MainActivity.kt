package com.example.customview

import android.annotation.SuppressLint
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View.LAYER_TYPE_HARDWARE
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.customview.databinding.ActivityMainBinding
import com.example.customview.databinding.ArticleHolderLayoutBinding
import com.example.customview.views.AvatarView
import com.example.customview.views.FollowFingerView
import com.example.customview.views.TimeLineView2

class MainActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        // grayWindow()

        _binding.avatarView.run {
            setLabelType(AvatarView.LabelType.TYPE_OWNER)
            avatarDrawable =
                ResourcesCompat.getDrawable(resources, R.drawable.cat, null)
            isShowBorder = true
        }
        setAvatarGray()

        val moveViewByTouch = FollowFingerView(this@MainActivity)
        _binding.container.addView(moveViewByTouch)

        _binding.container.addView(TimeLineView2(this))

        bindRecycleView()
    }

    private fun setAvatarGray() {
        val colorMatrix = ColorMatrix()
        colorMatrix.setSaturation(0f)
        val colorMatrixColorFilter = ColorMatrixColorFilter(colorMatrix)
        val paint = Paint()
        paint.colorFilter = colorMatrixColorFilter
        _binding.avatarView.setLayerType(LAYER_TYPE_HARDWARE, paint)
    }

    // 页面全局置灰
    private fun grayWindow() {
        val grayPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)
        ColorMatrix().apply {
            setSaturation(0F)
            val colorFilter = ColorMatrixColorFilter(this)
            grayPaint.colorFilter = colorFilter
        }
        this.window.decorView.setLayerType(LAYER_TYPE_HARDWARE, grayPaint)
    }

    private fun bindRecycleView() {
        _binding.recycleView.layoutManager = LinearLayoutManager(this)
        val adapter = ArticleAdapter()
        adapter.data = listOf(
            Article(),
            Article(),
            Article(),
            Article()
        )
        _binding.recycleView.adapter = adapter
    }

}

class ArticleAdapter : RecyclerView.Adapter<ArticleHolder>() {

    var data: List<Article> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleHolder {
        val binding =
            ArticleHolderLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ArticleHolder, position: Int) {
        holder.titleView.text = data[position].title
        holder.authorView.text = data[position].author
        holder.rootView.setOnClickListener {
            // TODO: 拉起Activity
        }
    }

}

class ArticleHolder(binding: ArticleHolderLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val rootView = binding.root
    val titleView = binding.title
    val authorView = binding.author
}

data class Article(
    val title: String = "title",
    val author: String = "author"
)