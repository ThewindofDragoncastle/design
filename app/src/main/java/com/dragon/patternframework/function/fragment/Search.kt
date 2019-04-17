package com.dragon.patternframework.function.fragment

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.dragon.patternframework.GetIP
import com.dragon.patternframework.MyLog
import com.dragon.patternframework.R
import com.dragon.patternframework.commodity.adapter.CommodityAdapter
import com.dragon.patternframework.commoditydetail.useinterface.SearchCom
import com.dragon.patternframework.internet.commodity.OnSearchPresenter
import com.dragon.patternframework.javaBean.Commodity
import kotlinx.android.synthetic.main.function_search.*

/**
 * Created by WYL on 2018/2/9.
 */
class Search : Fragment(), SearchCom {
    private  val OnSeachPresenter= OnSearchPresenter.getInstance()
    override fun Im3Sucess(com: List<Commodity>) {
        if (com.isEmpty())
        Toast.makeText(context,"无数据",Toast.LENGTH_SHORT).show()
        list.clear()
        list.reverse()
        list.addAll(com)
        adapter.notifyDataSetChanged()
    }

    override fun fail(s: String) {
        Toast.makeText(context,"fail $s",Toast.LENGTH_SHORT).show()
    }

    /*问题：不会去解决的问题：屏幕过小不能显示三个记录*/
    private val list = mutableListOf<Commodity>()
    private lateinit var pre: SharedPreferences
    private lateinit var adapter: CommodityAdapter
    /*商品搜索*/
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.function_search, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        pre = activity.getSharedPreferences("cm_record", MODE_PRIVATE)
        OnSeachPresenter.setMainPageSettingInterface(this)
        adapter = CommodityAdapter(context, list)
        Log.d("kfk", Thread.currentThread().name + this.list.toString())
        search_recyclerView.adapter = adapter
        search_recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        /*预先加载搜索记录*/
        loadingData()
/*为历史记录设置监听*/
        record_1.setOnClickListener {
            /*最简单的思路 直接将搜索字符换成参数*/
            loadSearch(record_1.text.toString())
        }
        record_2.setOnClickListener {
            /*最简单的思路 直接将搜索字符换成参数*/
            loadSearch(record_2.text.toString())
        }
        record_3.setOnClickListener {
            /*最简单的思路 直接将搜索字符换成参数*/
            loadSearch(record_3.text.toString())
        }
        search_button.setOnClickListener { loadSearch(search_edit.text.toString()) }
    }

    private fun search(key: String) {
        MyLog.i("${javaClass.name}搜索内容:", key)
        OnSeachPresenter.connectInternet(GetIP.getIp(context,"search"),key)
    }

    private fun saveData(t1: String, t2: String, t3: String) {
        val preEdit = pre.edit()
        preEdit.putString("cm_record_1", t1)
        preEdit.putString("cm_record_2", t2)
        preEdit.putString("cm_record_3", t3)
        preEdit.apply()
    }

    private fun loadingData() {
        val cm_record_1 = pre.getString("cm_record_1", "")
        val cm_record_2 = pre.getString("cm_record_2", "")
        val cm_record_3 = pre.getString("cm_record_3", "")
        MyLog.i("${javaClass.name}获取搜索数据：", "$cm_record_1,$cm_record_2,$cm_record_3")
        if (cm_record_1.isNotEmpty()) {
            record_1.text = cm_record_1
            record_1.visibility = View.VISIBLE
        }
        if (cm_record_2.isNotEmpty()) {
            record_2.text = cm_record_2
            record_2.visibility = View.VISIBLE
        }
        if (cm_record_3.isNotEmpty()) {
            record_3.text = cm_record_3
            record_3.visibility = View.VISIBLE
        }
    }

    private fun loadSearch(data: String) {
        var key = data
        if (data != record_1.text.toString()) {
            /*保存搜索记录 而且第一个如果与data一样就不进行记录 先加载第三个再第二个再第一个*/
            if (record_2.text.isNotEmpty()) {
                record_3.text = record_2.text
                record_3.visibility = View.VISIBLE
            }
            if (record_1.text.isNotEmpty()) {
                record_2.text = record_1.text
                record_2.visibility = View.VISIBLE
            }
            if (key.isEmpty())
                key = search_edit.hint.toString()
            record_1.text = key
            record_1.visibility = View.VISIBLE
            /*保存数据*/
            saveData(record_1.text.toString(), record_2.text.toString(), record_3.text.toString())
        }
        val removeLength = this.list.size
        this.list.clear()
        /*通知范围删除数据*/
        adapter.notifyItemRangeRemoved(0, removeLength)
        search(key)
    }
}