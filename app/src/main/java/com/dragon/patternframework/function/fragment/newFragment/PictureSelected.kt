package com.dragon.patternframework.function.fragment.newFragment

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v4.content.FileProvider
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dragon.patternframework.MyLog
import com.dragon.patternframework.R
import com.dragon.patternframework.function.adapter.PictureSelect
import com.dragon.patternframework.function.support.UploadPictureServiceHelper
import com.dragon.patternframework.function.useinterface.DataCallback
import com.dragon.patternframework.function.useinterface.DataManager
import com.dragon.patternframework.function.useinterface.NewCallback
import com.dragon.patternframework.function.useinterface.PictureSelectCallBack
import kotlinx.android.synthetic.main.function_new_picture_select.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class PictureSelected : Fragment(), PictureSelectCallBack, DataManager, NewCallback {
    override fun complete() {
        val sb = StringBuilder()
        filePaths.map {
            sb.append("$it;")
        }
        dataCallback.commodityImage(sb.toString())
    }

    private lateinit var adapter: PictureSelect
    private val images = mutableListOf<String>()
    /*保存真实截屏图片所在的绝对路径*/
    private val filePaths = mutableListOf<String>()
    private val take_photo = 0
    private val select_picture = 1
    private lateinit var photoFile: File
    val options = RequestOptions()
            .placeholder(R.drawable.loading)
            .error(R.drawable.noload)
    private lateinit var pager: ViewPager
    private var pageNumber = 0
    private lateinit var dataCallback: DataCallback
    override fun setPager(pager: ViewPager, pageNumber: Int, dataCallback: DataCallback) {
        this.pager = pager
        this.pageNumber = pageNumber
        this.dataCallback = dataCallback
    }

    override fun addPhoto(view: View) {
        /*为悬浮键设置不同状态 弹出式菜单*/
        val menu = PopupMenu(context, view)
        val inflater = menu.menuInflater
        inflater.inflate(R.menu.picture_select, menu.menu)
        menu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.picture_select_1 -> {
                    val intent = Intent("android.intent.action.GET_CONTENT")
                    intent.type = "image/*"
                    startActivityForResult(intent, select_picture)
                }
                R.id.picture_select_2 -> {
                    takePhoto()
                }
                else -> {
                }
            }
            false
        }
        menu.show()
    }

    override fun loadImage(path: String, imageView: ImageView, isCallback: Boolean) {
        if (!isCallback) {
            imageView.scaleType = ImageView.ScaleType.CENTER
        }
        activity.runOnUiThread(Runnable {
            Glide.with(context).load(path).apply(options).into(imageView)
        })
    }

    override fun takePhoto() {
        val file = File(
                Environment.getExternalStorageDirectory().toString() + File.separator
                        + "跳蚤市场"
        )
        photoFile = File(
                "${Environment.getExternalStorageDirectory()}" + File.separator
                        + "跳蚤市场" + File.separator + "照片" + System.currentTimeMillis() + ".jpg"
        )
        if (!file.exists())
            file.mkdirs()
        try {
            if (!photoFile.exists())
                photoFile.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        val imageUri = if (Build.VERSION.SDK_INT < 24) {
            MyLog.d("TakeSelectPhoto:", "当前版本低于7.0")
            Uri.fromFile(photoFile)
        }
        else {
            MyLog.d("TakeSelectPhoto:", "当前版本高于7.0")
            FileProvider.getUriForFile(context, "com.dragon.patternframework.fileprovider", photoFile)
        }
        val intent = Intent("android.media.action.IMAGE_CAPTURE")
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)//输出路径
        startActivityForResult(intent, take_photo)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        /*活动结果*/
        when (requestCode) {
            take_photo -> {
                if (resultCode == RESULT_OK) {
//                    filePaths.add(photoFile.absolutePath)
                    images.add(photoFile.absolutePath)
                    adapter.notifyDataSetChanged()
                }
            }
            select_picture -> {
                if (resultCode == RESULT_OK) {
                    val uri = data?.data
                    MyLog.d(javaClass.name, "uri=$uri")
                    images.add(uri.toString())
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.function_new_picture_select, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        complete.setOnClickListener { upLoad() }
        adapter = PictureSelect(context, picture_delete, images, this)
        picture_select_recycler.adapter = adapter
        picture_select_recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        picture_select.setOnClickListener {
            if (images.size == 0) {
                /*如果没有图片 弹出选择框*/
                addPhoto(picture_select)
            }
        }
    }

    override fun delete(position: Int, lastHolder: PictureSelect.ViewHolder) {
        AlertDialog.Builder(this.context).setTitle("删除照片")
                .setMessage("你确定要删除这张照片吗？")
                .setPositiveButton("是的", DialogInterface.OnClickListener { dialog, which ->
                    /*这个算的是当前所处的*/
                    if (position >= 0) {
                        /*此时还应该将移除位置的图片 色彩变为半透明 移除位置一定会有的*/
                        /*逻辑：删除当前位置的图片 然后将上一个位置的图片赋给大图 ，再将当前位置减一*/
                        /*忘了位置移除了 但是这个位置还是存在12456*/
                        //images.removeAt(currentSelect)
                        this.images.removeAt(position)
//                移除数组中的位置
                        filePaths.removeAt(position)
                        if (position != 0) {
                            loadImage(images[position - 1], picture_select, false)
                            /*改变当前位置*/
                            adapter.setCurrentPosition(position - 1)
                        }
                        else {
                            /*如果照片列表已经是空的那么显示添加 否则显示下一个*/
                            if (images.size == 0)
                                picture_select.setImageResource(R.drawable.add_photo)
                            else
                                loadImage(images[position], picture_select, false)
                            /*当前位置已经清除position了 所以当前位置就是position*/
                            adapter.setCurrentPosition(0)
                        }
                    }
                    else
                        picture_select.setImageResource(R.drawable.add_photo)
                    lastHolder.image.alpha = 0.5f
                    adapter.notifyDataSetChanged()
                }).setNegativeButton("取消", null).show()
    }

    override fun imageSet(pos: Int) {
        /*如果位置大小同总图片大小相等就设置添加*/
        if (pos != images.size)
            loadImage(images[pos], picture_select, false)
        else
            picture_select.setImageResource(R.drawable.add_photo)
    }

    private fun upLoad() {
        Toast.makeText(context, "上传开始，通知栏中查看进度，如不能请开启通知权限。", Toast.LENGTH_SHORT).show()
        if (images.isNotEmpty()) {
            Thread(Runnable {
                images.mapIndexed { index, s ->
                    adapter.setCurrentPosition(index)
                    imageSet(index)
                    /*为了方便加载完毕*/
                    Thread.sleep(1500)
                    imageFile(picture_select)
                }
                UploadPictureServiceHelper.getInstance().start(filePaths.toTypedArray(), activity, this)
            }).start()
        }
        else
            Toast.makeText(context, "未有图片。", Toast.LENGTH_SHORT).show()
    }

    private fun imageFile(image: ImageView): String {
        /*文件复制到sd卡*/
        val dir = File(
                "${Environment.getExternalStorageDirectory()}" + File.separator
                        + "跳蚤市场"
        )
        if (!dir.exists())
            dir.mkdirs()
        val imageFile = File(
                "${Environment.getExternalStorageDirectory()}" + File.separator
                        + "跳蚤市场" + File.separator + "照片" + System.currentTimeMillis() + ".jpg"
        )
        filePaths.add(imageFile.absolutePath)
        if (imageFile.exists())
            imageFile.delete()
        imageFile.createNewFile()
        val outStream = FileOutputStream(imageFile)
        /*如果图片过大就会报异常 所以把视图转到view*/
        val bitmap = Bitmap.createBitmap(picture_select.width, picture_select.height, Bitmap.Config.RGB_565)
        val canvas = Canvas(bitmap)
        image.draw(canvas)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream)
        outStream.flush()
        outStream.close()
        return imageFile.absolutePath
    }
}