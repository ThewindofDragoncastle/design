package com.dragon.patternframework.function.activity

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.dragon.patternframework.MyLog
import com.dragon.patternframework.R
import com.dragon.patternframework.commodity.activity.SettingActivity
import com.dragon.patternframework.function.enumclass.FragmentIdentify
import com.dragon.patternframework.function.fragment.Search
import com.dragon.patternframework.permission.RequestPermission
import kotlinx.android.synthetic.main.activity_function_main.*
import kotlinx.android.synthetic.main.main_toolbal.*

class FunctionMainActivity : AppCompatActivity() {
    private val TAG = javaClass.name
    /*识别码 如果为上新界面 退出将会询问*/
    private var IdentifyID = -1
    /*功能活动集合 而且这个活动是栈顶活动*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_function_main)
        val type = intent.getStringExtra("type")
        MyLog.i(TAG, "识别码：$type")
        /*现在虽然写起来麻烦 但是不易出问题 而且容易识别*/
        val search = fun(): FragmentIdentify? {
            FragmentIdentify.values().forEach {
                if (it.label == type)
                    return it
            }
            return null
        }
        val number = search()
        IdentifyID = number?.ordinal!!
        /*如果是上新页面 请求权限*/
        MyLog.i(TAG, "识别码：$number")

        if (IdentifyID == FragmentIdentify.NEW.ordinal&& RequestPermission.requestSd(this))
        {
             /*如果是版本小于23或者已经拥有权限返回false*/
        }
       else if (IdentifyID == FragmentIdentify.SYSTEMS_MANAGEMENT.ordinal)
        {
            /*如果是系统设置就进入设置界面*/
            this.finish()
            startActivity(Intent(this,SettingActivity::class.java))
        }
        else
        when (number) {
            null -> Toast.makeText(this, "出错了！！", Toast.LENGTH_SHORT).show()
            else -> {
                val f = number.fragment
                if (f != null) {
                    /*如果不是像上新的页面特殊页面 将标签页改名字*/
                    when (f) {
                        is Search -> fun_toolbar.visibility = View.GONE
                        else -> main_toolbar_text.text = number.label
                    }

                    replaceFragment(f)
                } else
                    Toast.makeText(this, "页面即将上新，敬请期待", Toast.LENGTH_SHORT).show()
            }
        }
    }
 fun replaceNew()
{
    val f=FragmentIdentify.NEW.fragment!!
    replaceFragment(f)
}
    private fun replaceFragment(fragment: Fragment) {
        val tran = supportFragmentManager.beginTransaction()
        if (fragment.view?.parent == null)
            tran.add(R.id.function_fragment, fragment)
        else
            tran.replace(R.id.function_fragment, fragment)
        tran.commit()
    }

    override fun onBackPressed() {
        if (IdentifyID == FragmentIdentify.NEW.ordinal) {
            val dialog = AlertDialog.Builder(this)
            dialog.setTitle("提示")
            dialog.setMessage("退出不保留已经填写的内容，你确定要退出吗？")
            dialog.setNegativeButton("取消", null)
            dialog.setPositiveButton("退出", DialogInterface.OnClickListener { dialog, which ->
                super.onBackPressed()
            })
            dialog.show()
        } else
            super.onBackPressed()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        RequestPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults)
    }
}
