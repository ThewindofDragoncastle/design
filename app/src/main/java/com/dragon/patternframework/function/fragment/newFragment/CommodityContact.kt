package com.dragon.patternframework.function.fragment.newFragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dragon.patternframework.R
import com.dragon.patternframework.function.useinterface.DataCallback
import com.dragon.patternframework.function.useinterface.DataManager
import kotlinx.android.synthetic.main.function_new_contact.*
import java.util.regex.Pattern

/**
 * Created by WYL on 2018/2/9.
 */
class CommodityContact : Fragment(), DataManager {
    private lateinit var pager: ViewPager
    private var pageNumber = 0
    private lateinit var dataCallback: DataCallback
    override fun setPager(pager: ViewPager, pageNumber: Int, dataCallback: DataCallback) {
        this.pager = pager
        this.pageNumber = pageNumber
        this.dataCallback = dataCallback
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.function_new_contact, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setGoto()
        setInput()
    }

    private fun setInput() {
        qq_input.setOnFocusChangeListener { view, focus ->
            if (!focus)
            /*如果用户删除数据那么也是允许qq号为空的*/
                if (checkQ() || qq_input.text.isNullOrEmpty())
                    dataCallback.commodityContactQQ(qq_input.text.toString())
                else
                    dataCallback.commodityContactQQ("error")
        }

        phone_input.setOnFocusChangeListener { view, focus ->
            if (!focus)
            /*不允许为空的,空就是错误*/
                if (checkPhone())
                    dataCallback.commodityContactPhone(phone_input.text.toString())
                else
                    dataCallback.commodityContactPhone("")
        }

        email_input.setOnFocusChangeListener { view, focus ->
            if (checkEmail() || email_input.text.isNullOrEmpty())
                dataCallback.commodityContactEmail(email_input.text.toString())
            else
                dataCallback.commodityContactEmail("error")
        }
    }

    private fun setGoto() {
        finish_input.setOnClickListener {
            /*如果goto为false就不进行跳转*/
            val goto: Boolean = checkQ() && checkEmail() && checkPhone()
            if (goto) {
                dataCallback.commodityContactQQ(qq_input.text.toString())
                dataCallback.commodityContactPhone(phone_input.text.toString())
                dataCallback.commodityContactEmail(email_input.text.toString())
                pager.currentItem = pageNumber + 1
            }
        }
    }

    private fun isMobileNO(mobiles: String): Boolean {
        val p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$")
        val m = p.matcher(mobiles)
        return m.matches()
    }

    private fun isEmail(email: String?): Boolean {
/*可以为空*/
        if (null == email || "" == email) return true
        //Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
        //   val p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*")//复杂匹配
        val p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}")
        val m = p.matcher(email)
        return m.matches()
    }

    private fun checkQ(): Boolean {
        return if (qq_input.length() <= 5 && qq_input.length() != 0) {
            /*可以为空*/
            qq_input.error = "你输入的位数过少"
            false
        } else
            true
    }

    private fun checkEmail(): Boolean {
        return if (!isEmail(email_input.text.toString())) {
            email_input.error = "你的邮箱格式有误"
            return false
        } else true
    }

    private fun checkPhone(): Boolean {
        return if (!isMobileNO(phone_input.text.toString())) {
            phone_input.error = "请输入合法电话号码"
            return false
        } else
            true
    }

}