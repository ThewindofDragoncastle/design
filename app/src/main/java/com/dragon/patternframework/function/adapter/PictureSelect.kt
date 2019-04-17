package com.dragon.patternframework.function.adapter


import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import com.dragon.patternframework.MyLog
import com.dragon.patternframework.R
import com.dragon.patternframework.function.useinterface.PictureSelectCallBack


class PictureSelect(var context: Context, var delete: ImageButton,var images: MutableList<String>
                    , var callback: PictureSelectCallBack) : RecyclerView.Adapter<PictureSelect.ViewHolder>() {

    /*评论细节的适配器*/
    private lateinit var lastHolder: ViewHolder
    private var currentSelect = 0

    init {
          delete.setOnClickListener {
            Log.i("按钮", "点击有效")
            /*删除就将当前位置减去1 但是如果 当前位置已经是0就不再减去1了*/
            if (images.size != 0)
                callback.delete(currentSelect, lastHolder)
            else
                Toast.makeText(context, "你的操作无法继续，添加图片后再进行尝试。", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return images.size + 1
    }

    fun setCurrentPosition(pos: Int) {
        currentSelect = pos
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.run {
            /*设置选择*/
            /*删除就将当前位置减去1 但是如果 当前位置已经是0*/
            if (currentSelect == position) {
                /*将上一个照片变暗*/
                image.alpha = 1f
                callback.imageSet(currentSelect)
                lastHolder = this
            }

            if (position == images.size) {
                /*当有图片时 最后一个会自动添加*/
                image.setImageResource(R.drawable.add_photo)
                image.setOnClickListener {
                  callback.addPhoto(it)
                }
            } else {
                callback.loadImage(images[position],  image ,true)
                image.setOnClickListener {
                    /*将上一个照片变暗*/
                    MyLog.i(javaClass.name, "当前位置：$position 前一个位置：$currentSelect" +
                            " 图片集合：${images.size}")
                    lastHolder.image.alpha = 0.5f
                    lastHolder = this
                    image.alpha = 1f
                    currentSelect = position
                    callback.imageSet(position)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.picture_select_item, parent, false))
    }

    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val image = item.findViewById<ImageView>(R.id.select_image_item)

        init {
            /*将照片变暗*/
            image.alpha = 0.5f
        }
    }
}
