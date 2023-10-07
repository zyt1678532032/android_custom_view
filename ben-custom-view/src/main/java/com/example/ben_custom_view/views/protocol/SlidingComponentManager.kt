package com.example.wanandroid.ui.home.view.protocol

/**
 * SlidingComponent的管理器
 */
interface SlidingComponentManager {

    /**
     * 关闭已开启的组件
     */
    fun closeOpenedComponent()

    /**
     * 设置开启的组件
     */
    fun setOpenedComponent(slidingComponent: SlidingComponent)

}