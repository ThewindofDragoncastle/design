package com.dragon.patternframework.person.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.constraint.ConstraintLayout
import android.support.v4.content.FileProvider
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dragon.patternframework.GetIP
import com.dragon.patternframework.MyLog
import com.dragon.patternframework.R
import com.dragon.patternframework.function.adapter.ReviseRecyclerAdapter
import com.dragon.patternframework.function.support.UploadPictureServiceHelper
import com.dragon.patternframework.function.useinterface.NewCallback
import com.dragon.patternframework.internet.user.pictureUpload.PictureUploadPresenter
import com.dragon.patternframework.internet.user.query.UserQueryPre
import com.dragon.patternframework.javaBean.User
import com.dragon.patternframework.permission.RequestPermission
import com.dragon.patternframework.person.useinterface.ImageUploadInterface
import com.dragon.patternframework.person.useinterface.InfoSupportInterface
import com.dragon.patternframework.person.useinterface.UserQueryInterface
import kotlinx.android.synthetic.main.function_info_revise.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class InfoActivity : AppCompatActivity() , UserQueryInterface, InfoSupportInterface , ImageUploadInterface, NewCallback {
    
    private val presenter= PictureUploadPresenter.getInstance()
    private var filename:String=""
    private lateinit var user: User
    override fun success() {
        user.image="${Environment.getExternalStorageDirectory()}"+File.separator+"跳蚤市场"+File.separator+filename
        revise_recycler.adapter.notifyDataSetChanged()
    }

    override fun complete() {
        presenter.connectInternet(GetIP.getIp(this,"portraitRevise"),filename)
    }

    private lateinit var photoFile:File
    private val options = RequestOptions()
            .placeholder(R.drawable.loading)
            .error(R.drawable.noload)!!
    override fun checkPermission(): Boolean {
       return RequestPermission.requestSd(this)
    }

    private fun loadImage(path: String, imageView: ImageView) {
        this.runOnUiThread(Runnable {
            Glide.with(this).load(path).apply(options).into(imageView)
        })
    }

    override fun takePhoto() {
        val file = File(
                Environment.getExternalStorageDirectory().toString() + File.separator
                        + "跳蚤市场")
        photoFile = File(
                "${Environment.getExternalStorageDirectory()}" + File.separator
                        + "跳蚤市场" + File.separator + "照片" + System.currentTimeMillis() + ".jpg")
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
            FileProvider.getUriForFile(this, "com.dragon.patternframework.fileprovider", photoFile)
        }
        val intent = Intent("android.media.action.IMAGE_CAPTURE")
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)//输出路径
        startActivityForResult(intent,1)
    }

    override fun selectPicture() {
        val intent = Intent("android.intent.action.GET_CONTENT")
        intent.type = "image/*"
        startActivityForResult(intent,0)
    }

    private val userQueryPre=UserQueryPre.getInstance()
    override fun fail(e: String) {
       Toast.makeText(this,"加载失败！$e",Toast.LENGTH_SHORT).show()
    }

    override fun success(user: com.dragon.patternframework.javaBean.User) {
        this.user=user
        revise_recycler.adapter = ReviseRecyclerAdapter(this,this ,this.user)
        revise_recycler.layoutManager = LinearLayoutManager(this)
        revice_loadingView.visibility=View.GONE
        info_edit.setOnClickListener {
            if (info_edit.text == "编辑")
            {
                info_edit.text="保存"
            }
            else
            {
                info_edit.text="编辑"
            }
        }
    }
    @SuppressLint("SetTextI18n")
    private fun showImageDialog(title:String,path: String) {
        val  view = LayoutInflater.from(this).inflate(R.layout.revise_image_dialog, ConstraintLayout(this),false)
        val title1= view.findViewById<TextView>(R.id.revise_dialog_title)
        val image= view.findViewById<ImageView>(R.id.revise_protrait)
        val save= view.findViewById<TextView>(R.id.revise_dialog_save)
        val cancel= view.findViewById<TextView>(R.id.revise_dialog_cancel)
        title1.text= "修改$title"
        loadImage(path,image)
        val dialog= AlertDialog.Builder(this).setView(view).show()
        save.setOnClickListener {
            dialog.cancel()
            Toast.makeText(this,"上传开始，请在通知查看进度。",Toast.LENGTH_SHORT).show()
            UploadPictureServiceHelper.getInstance().start(listOf(imageFile(image)).toTypedArray(), this, this)
        }
        cancel.setOnClickListener {
            dialog.cancel()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.function_info_revise)
        presenter.setPicUpload(this)
        userQueryPre.setUserQuery(this)
        userQueryPre.connectInternet(GetIP.getIp(this,"userQuery"))
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        /*活动结果*/
        when (requestCode) {
            1 -> {
                if (resultCode == Activity.RESULT_OK) {
                    showImageDialog("头像",photoFile.absolutePath)
                }
            }
            0 -> {
                if (resultCode == Activity.RESULT_OK) {
                    val uri = data?.data
                    if (uri != null) {
                        showImageDialog("头像", uri.toString())
                    }
                }
            }
        }
        }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        RequestPermission.onRequestPermissionsResult(this,requestCode,permissions,grantResults)
    }
  private fun imageFile(image: ImageView): String {
      val dir=File(
              "${Environment.getExternalStorageDirectory()}" + File.separator
                      + "跳蚤市场")
      if (!dir.exists())
        dir.mkdirs()
        /*文件复制到sd卡*/
        val imageFile = File(
                "${Environment.getExternalStorageDirectory()}" + File.separator
                        + "跳蚤市场" + File.separator + "照片" + System.currentTimeMillis() + ".jpg"
        )
        if (imageFile.exists())
            imageFile.delete()
        imageFile.createNewFile()
        val outStream = FileOutputStream(imageFile)
        /*如果图片过大就会报异常 所以把视图转到view*/
        val bitmap = Bitmap.createBitmap(image.width, image.height, Bitmap.Config.RGB_565)
        val canvas = Canvas(bitmap)
        image.draw(canvas)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream)
        outStream.flush()
        outStream.close()
        filename=imageFile.name
        return imageFile.absolutePath
    }
}
