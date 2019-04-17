package com.dragon.patternframework.function.fragment.newFragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import com.dragon.patternframework.MyLog
import com.dragon.patternframework.R
import com.dragon.patternframework.commodity.adapter.FragmentAdapter
import com.dragon.patternframework.function.activity.FunctionMainActivity
import com.dragon.patternframework.function.enumclass.Name
import com.dragon.patternframework.function.useinterface.DataCallback
import com.dragon.patternframework.javaBean.Commodity
import kotlinx.android.synthetic.main.function_new.*
import kotlinx.android.synthetic.main.main_toolbal.*

/**
 * Created by WYL on 2018/2/9.
 */
class New : Fragment(), DataCallback {

    override fun commodityImage(content: String) {
        product.images=content
    }


    /*商品上新页面 由于某些原因 悬浮键位置记录取消*/
    private lateinit var toolbar_text: TextView
    private val product = Commodity()
    private val ga = Gather()
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.function_new, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // createAlert()
        val activity=activity
       if ( activity is FunctionMainActivity)
       {
           toolbar_text=activity.main_toolbar_text
       }
//        pre = context.getSharedPreferences("fab", MODE_PRIVATE)
        val number = listOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9")
        /*和枚举类一一对应*/
        val cn = CommodityName()
        val ca = CommodityAmount()
        val cp = CommodityPrice()
        val cnp = CommodityNegotiablePrice()
        val cd = CommodityDelivery()
        val ps = PictureSelected()
        val cds = CommodityDescribe()
        val sn = SellerNotice()
        val st = SellerTrading()
        val cc = CommodityContact()

        /*这里不直接填写序号的原因 是增加单一元素的依耐性 方便修改
        * 跳转的页面标识*/
        cn.setPager(fun_news_viewPager, Name.NAME.ordinal, this)
        ca.setPager(fun_news_viewPager, Name.AMOUNT.ordinal, this)
        cp.setPager(fun_news_viewPager, Name.PRICE.ordinal, this)
        cnp.setPager(fun_news_viewPager, Name.NEGOTIABLE.ordinal, this)
        cd.setPager(fun_news_viewPager, Name.DELIVERY.ordinal, this)
        ps.setPager(fun_news_viewPager, Name.PIC.ordinal, this)
        cds.setPager(fun_news_viewPager, Name.DESCRIBE.ordinal, this)
        sn.setPager(fun_news_viewPager, Name.NOTICE.ordinal, this)
        st.setPager(fun_news_viewPager, Name.ITEM.ordinal, this)
        cc.setPager(fun_news_viewPager, Name.INFO.ordinal, this)
        ga.setPager(fun_news_viewPager, Name.GATHER.ordinal, this)
        val fragments = listOf<Fragment>(cn, ca, cp, cnp, cd, ps, cds, sn, st, cc, ga)
        fun_news_viewPager.adapter = FragmentAdapter(fragmentManager
                , fragments, number
        )
        fun_news_viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                /*标签页名称*/
                toolbar_text.text = Name.values()[position].id
                /*页面跳转监听 如果是切换到汇总页面 那么重新获取数据*/
                /*启示：做事情应该往简单的地方想*/
                if (position == Name.GATHER.ordinal)
                    ga.updateData()
            }
        })
        toolbar_text.text = "设置商品名称"
        /*设置悬浮键可以随手指移动*/
        setFAB()

    }

    private fun setFAB() {
        /*设置悬浮键初始位置*/
//        val t=pre.getInt("new_fab_top",new_goto.top)
//        val l=pre.getInt("new_fab_left",new_goto.left)
//        val r=pre.getInt("new_fab_right",new_goto.right)
//        val b=pre.getInt("new_fab_bottom",new_goto.bottom)
//        MyLog.i(javaClass.name," 数据获取：t=$t l=$l r=$r b=$b ")
//        Thread(Runnable {
//            while (true)
//            {
//                Thread.sleep(2000)
//                break
//            }
//            activity.runOnUiThread(Runnable {
//                new_goto.layout(l,t,r,b)
//            })
//        }).start()

        var lastX = 0f
        var lastY = 0f
        new_goto.setOnClickListener {
            /*为悬浮键设置不同状态 弹出式菜单*/
            val menu = PopupMenu(context, it)
            val inflater = menu.menuInflater
            inflater.inflate(R.menu.select_page, menu.menu)
            menu.setOnMenuItemClickListener {
                val position = when (it.itemId) {
                    R.id.page_select_1 -> 0
                    R.id.page_select_2 -> 1
                    R.id.page_select_3 -> 2
                    R.id.page_select_4 -> 3
                    R.id.page_select_5 -> 4
                    R.id.page_select_6 -> 5
                    R.id.page_select_7 -> 6
                    R.id.page_select_8 -> 7
                    R.id.page_select_9 -> 8
                    R.id.page_select_10 -> 9
                    R.id.page_select_11 -> 10
                    else -> 0
                }
                /*页面跳转监听 如果是切换到汇总页面 那么重新获取数据*/
                fun_news_viewPager.currentItem = position
                false
            }
            menu.show()
        }
        var isReturn: Boolean
        var moveX: Int = 0
        var moveY: Int = 0

        new_goto.setOnTouchListener { view, event ->
            /*设置按下判断*/
            isReturn = when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    lastX = event.x
                    lastY = event.y
                    false
                }
                MotionEvent.ACTION_UP -> {
                    /*当你抬起键时 判断是否进行了移动
                    * 如果没有清空 不阻止按钮事件传递
                    * 有也清空  阻止按钮事件传递 并且将按键抬起
                    * */
                    MyLog.i(javaClass.name, "moveX=$moveX moveY=$moveY")
                    if (moveX == 0 && moveY == 0) {
                        moveX = 0
                        moveY = 0
                        false
                    } else {
                        /*数据保存局部变量*/
//                        val preEdit = pre.edit()
                        /*有移动 将数据保存
                        * 其实这里性能可以优化例如放在OnResume但是没有必要*/
//                        preEdit.putInt("new_fab_top",new_goto.top)
//                        preEdit.putInt("new_fab_left",new_goto.left)
//                        preEdit.putInt("new_fab_right",new_goto.right)
//                        preEdit.putInt("new_fab_bottom",new_goto.bottom)
//                        MyLog.i(javaClass.name,"数据保存：t=${new_goto.top} l=${new_goto.left} r=${new_goto.right} b=${new_goto.bottom}")
//                        preEdit.apply()
                        new_goto.isPressed = false
                        moveX = 0
                        moveY = 0
                        true
                    }
                }
                MotionEvent.ACTION_MOVE -> {
                    /*移动时取消按钮按下状态*/
                    new_goto.isPressed = false
                    moveX = (event.x - lastX).toInt()
                    moveY = (event.y - lastY).toInt()
                    new_goto.layout(new_goto.left + moveX
                            , new_goto.top + moveY
                            , new_goto.right + moveX
                            , new_goto.bottom + moveY)
                    false
                }
                else -> false
            }
            isReturn
        }
    }

    override fun commodityName(name: String) {
        product.comLabel = name

    }

    override fun commodityPrice(price: Float) {
        product.price = price.toDouble()

    }

    override fun commodityDescribe(describe: String) {
        product.note = describe

    }

    override fun commodityDelivery(isSellerDelivery: Boolean) {
        product.isDispatch=if (isSellerDelivery) 1 else 0
    }

    override fun sellerTrading(content: String) {
        product.deal = content
    }

    override fun sellerNotice(content: String) {
        product.notice = content
    }

    override fun commodityAmount(amount: Int) {
        product.amount = amount.toLong()

    }

    override fun commodityNegotiablePrice(can: Boolean) {
        product.isBargain = if (can) 1 else 0

    }

    override fun getNewProduct(): Commodity {
        /*返回商品信息*/
        return product
    }

    override fun modifyInfo() {
        new_goto.performClick()
    }

    override fun commodityContactQQ(qq: String) {
        product.qq = qq

    }

    override fun commodityContactEmail(email: String) {
        product.email = email
    }

    override fun commodityContactPhone(phone: String) {
        product.phone = phone
    }

}