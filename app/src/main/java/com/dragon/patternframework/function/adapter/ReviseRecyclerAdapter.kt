package com.dragon.patternframework.function.adapter

import android.app.AlertDialog
import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dragon.patternframework.R
import com.dragon.patternframework.function.enumclass.InfoIdentify
import com.dragon.patternframework.person.useinterface.InfoSupportInterface
import de.hdodenhof.circleimageview.CircleImageView

class ReviseRecyclerAdapter(val context: Context,val infoSupportInterface:InfoSupportInterface, val user: com.dragon.patternframework.javaBean.User) : RecyclerView.Adapter<ReviseRecyclerAdapter.ViewHolder>() {
    private val options = RequestOptions()
            .placeholder(R.drawable.loading)
            .error(R.drawable.noload)!!
    private val titles: Array<String> = context.resources.getStringArray(R.array.fun_revise_item)
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.function_info_amend_item, parent, false))
    }

    override fun getItemCount(): Int {
        return titles.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.run {
            var titleInfo = titles[position]
            portrait.visibility = View.INVISIBLE
            content.visibility = View.VISIBLE
            itemView.visibility = View.VISIBLE
            top.visibility = View.GONE
            while (titleInfo.isEmpty())
            {
                itemView.visibility = View.INVISIBLE
                titleInfo=titles[position+1]
            }
            Log.i("TAG信息对比",titleInfo)
             val contentInfo =when (titleInfo) {
                InfoIdentify.PORTRAIT.label -> {
                    top.visibility = View.VISIBLE
                    portrait.visibility = View.VISIBLE
                    content.visibility = View.GONE
                    if (user.image!=null)
                    {
                        Glide.with(context).load(user.image).apply(options).into( portrait)
                    }
                    ""/*头像加载*/
                }
                InfoIdentify.FALSE_NAME.label -> {
                    top.visibility = View.VISIBLE
                    if (user.nickname.isEmpty()) "点击添加呢称" else user.nickname
                }
                InfoIdentify.SIGN.label -> {
                    if (user.sign!=null)
                       if (user.sign.isEmpty())    "不存在" else user.sign
                    else
                        "不存在"
                }
                InfoIdentify.QQ.label -> {
                    if (user.qq!=null)
                    if (user.qq.isEmpty())  "不存在"  else user.qq
                    else
                        "不存在"
                }
                InfoIdentify.EMAIL.label -> {
                    if (user.email!=null)
                        if (user.email.isEmpty()) "不存在" else user.email
                    else
                        "不存在"

                }
                InfoIdentify.PHONE.label -> {
                    top.visibility = View.VISIBLE
                    if (user.phone!=null)
                        if (user.phone.isEmpty()) "不存在" else user.phone
                    else
                        "不存在"
                }
                InfoIdentify.ADDRESS.label -> {
                    top.visibility = View.VISIBLE
                    if (user.address!=null)
                        if (user.address.isEmpty()) "不存在" else user.address
                    else
                        "不存在"
                }
                InfoIdentify.SCHOOL.label -> {
                    if (user.school!=null)
                        if (user.school.isEmpty()) "不存在" else user.school + user.profession
                    else
                        "不存在"

                }
                InfoIdentify.TRUE_NAME.label -> {
                    if (user.trueName!=null)
                        if (user.trueName.isEmpty()) "不存在" else user.trueName
                    else
                        "不存在"
                }
                else ->/*说白了 就是空或者无*/ {
                    ""
                }
            }
                title.text = titleInfo
                content.text = contentInfo
            itemView.setOnClickListener {
                if (titleInfo != InfoIdentify.PORTRAIT.label)
                showDialog(titleInfo,content.text.toString(),content)
                else{
                    /*权限检查*/
                    if(!infoSupportInterface.checkPermission())
                       addPhoto(itemView)
                }
            }
        }
    }

    /*核实商品信息页面适配器*/
    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val portrait = item.findViewById<CircleImageView>(R.id.amend_portrait)
        val content = item.findViewById<TextView>(R.id.amend_content)
        val title = item.findViewById<TextView>(R.id.amend_title)
        val top = item.findViewById<View>(R.id.amend_top_view)

    }

    private fun showDialog(title:String, content:String, textView: TextView) {
        val  view =LayoutInflater.from(context).inflate(R.layout.revise_dialog,ConstraintLayout(context),false)
        val title1= view.findViewById<TextView>(R.id.revise_dialog_title)
        val edit= view.findViewById<TextView>(R.id.revise_dialog_edit)
        val save= view.findViewById<TextView>(R.id.revise_dialog_save)
        val cancel= view.findViewById<TextView>(R.id.revise_dialog_cancel)
        title1.text= "修改$title"
        edit.text=content
        val dialog=AlertDialog.Builder(context).setView(view).show()
        save.setOnClickListener {
            textView.text=edit.text.toString()
            dialog.cancel()
        }
        cancel.setOnClickListener {
            dialog.cancel()
        }
    }
    private fun addPhoto(view: View) {
        /*为悬浮键设置不同状态 弹出式菜单*/
        val menu = PopupMenu(context, view)
        val inflater = menu.menuInflater
        inflater.inflate(R.menu.picture_select, menu.menu)
        menu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.picture_select_1 -> {
                    infoSupportInterface.selectPicture()
                }
                R.id.picture_select_2 -> {

                        infoSupportInterface.takePhoto()
                }
                else -> {
                }
            }
            false
        }
        menu.show()
    }
}