package com.example.customview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ben_custom_view.databinding.ViewholderTestLayoutBinding
import com.example.wanandroid.ui.home.view.protocol.SlidingComponent
import com.example.wanandroid.ui.home.view.protocol.SlidingComponentManager

class MySlidingAdapter : RecyclerView.Adapter<MySlidingAdapter.MyViewHolder>(),
    SlidingComponentManager {

    private var mSlidingComponent: SlidingComponent? = null

    var data: List<String> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ViewholderTestLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyViewHolder(binding = binding, slidingComponentManager = this@MySlidingAdapter)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun closeOpenedComponent() {
        if (mSlidingComponent != null && mSlidingComponent?.isOpened == true) {
            mSlidingComponent?.close()
            mSlidingComponent = null
        }
    }

    override fun setOpenedComponent(slidingComponent: SlidingComponent) {
        mSlidingComponent = slidingComponent
    }

    class MyViewHolder(
        binding: ViewholderTestLayoutBinding,
        slidingComponentManager: SlidingComponentManager
    ) : RecyclerView.ViewHolder(binding.root) {

        private val slidingComponent = binding.root

        init {
            // 注入左滑组件管理器
            slidingComponent.slidingComponentManager = slidingComponentManager
        }

    }

}
