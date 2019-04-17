package com.dragon.patternframework.function.fragment.newFragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.dragon.patternframework.R
import com.dragon.patternframework.function.adapter.GatherAdapter
import com.dragon.patternframework.function.enumclass.Name
import com.dragon.patternframework.function.useinterface.DataCallback
import com.dragon.patternframework.function.useinterface.DataManager
import com.dragon.patternframework.javaBean.Commodity
import kotlinx.android.synthetic.main.function_new_gather.*

/**
 * Created by WYL on 2018/2/9.
 */
class Gather : Fragment(), DataManager {
    private lateinit var pager: ViewPager
    private var pageNumber = 0
    private lateinit var dataCallback: DataCallback
    private lateinit var pro: Commodity
    /*错误页面记录*/
    private val errorList = mutableListOf<Int>()
    private var isExists = false
    private lateinit var adapter: GatherAdapter
    override fun setPager(pager: ViewPager, pageNumber: Int, dataCallback: DataCallback) {
        this.pager = pager
        this.pageNumber = pageNumber
        this.dataCallback = dataCallback
    }

    /*卖家de话*/
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.function_new_gather, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isExists = true
        pro = dataCallback.getNewProduct()
        adapter = GatherAdapter(context, pro)
        fun_new_gather_recycler.adapter = adapter
        fun_new_gather_recycler.layoutManager = LinearLayoutManager(context)
        sure.setOnClickListener {
            /*为确定键设置监听*/
            checkAllError()
            if (errorList.size != 0)
                dialogErrorShow()
            else if (pro.images.isEmpty())
            {
                Toast.makeText(context,"请等待图片上传完成",Toast.LENGTH_SHORT).show()
            }
            else dialogMultItemShow()
        }
        modify.setOnClickListener {
            /*为修改键设置监听*/
            dataCallback.modifyInfo()
        }
    }

    private fun dialogSelectShow() {
        val dialog = AlertDialog.Builder(context)
        dialog.setTitle("提示")
        dialog.setMessage("即将提交至服务器，你的商品信息确认清楚了吗？")
        dialog.setNegativeButton("我再看看", DialogInterface.OnClickListener { dialog, which ->
            Toast.makeText(context, "现在你可以重新查看你的商品信息", Toast.LENGTH_SHORT).show()
        })
        dialog.setPositiveButton("确认", DialogInterface.OnClickListener { dialog, which ->
            Toast.makeText(context, "你的商品信息正在上传至服务器", Toast.LENGTH_SHORT).show()
            dialog.cancel()
            com.dragon.patternframework.internet.CommodityUpload.View(this).start(pro)
        })
        dialog.show()
    }
    private fun dialogMultItemShow() {
        val dialog = AlertDialog.Builder(context)
        dialog.setTitle("还需要最后一步")
        val a = context.resources.getStringArray(R.array.mutCheck)
        val bb= BooleanArray(a.size)
        dialog.setMultiChoiceItems(a,bb,DialogInterface.OnMultiChoiceClickListener{
           dialogInterface: DialogInterface, i: Int, b: Boolean ->
                bb[i]=true
        }).setPositiveButton("确定", DialogInterface.OnClickListener{ dialogInterface: DialogInterface, i: Int ->
            val sb=StringBuilder()
            bb.mapIndexed { index, b ->
                if (bb[index])
                    sb.append("$index;")
            }
            pro.tab=sb.toString()
            dialogSelectShow()
        }).setNegativeButton("取消",null)
        dialog.show()
    }

    private fun dialogErrorShow() {
        val dialog = AlertDialog.Builder(context)
        dialog.setTitle("商品信息需要完善")
        val a = arrayOfNulls<String>(errorList.size)
        a.mapIndexed { index, s ->
            /*展示标签名字*/
            a[index] = Name.values()[errorList[index]].id
        }
        dialog.setItems(a, DialogInterface.OnClickListener { dialog, which ->
            if (which >= 0)
                pager.currentItem = errorList[which]
        })
        dialog.show()
    }

    private fun checkAllError() {
        errorList.clear()
        /*按顺序添加*/
        if (pro.comLabel.isNullOrEmpty())
            errorList.add(Name.NAME.ordinal)
        if (pro.note.isEmpty())
            errorList.add(Name.DESCRIBE.ordinal)
        if (pro.phone.isEmpty() || pro.email == "error" || pro.qq == "error") {
            errorList.add(Name.INFO.ordinal)
        }
    }

    fun updateData() {
        if (isExists) {
            /*重新接收数据 如果碎片还未创建就不需要OnResume*/
            pro = dataCallback.getNewProduct()
            adapter.notifyDataSetChanged()
            if (pro.price != 0.0)
                price.text = "￥${pro.price}"
            else
                price.text = "面议"
            name.text = if (pro.comLabel.isNullOrEmpty()) "请填写商品名称" else pro.comLabel
            amount.text = "${pro.amount}"
            describe.text = if (pro.note.isEmpty()) "请填写商品描述" else pro.note
        }
    }
}