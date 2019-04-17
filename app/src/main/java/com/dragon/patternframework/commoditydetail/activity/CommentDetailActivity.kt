package com.dragon.patternframework.commoditydetail.activity

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.dragon.patternframework.R
import com.dragon.patternframework.commoditydetail.adapter.CDCitemRCAdapater
import com.dragon.patternframework.commoditydetail.data.CommentItemUser
import com.dragon.patternframework.commoditydetail.data.CommentUser
import com.dragon.patternframework.commoditydetail.useinterface.InputVisible
import com.dragon.patternframework.loadingview.SuspendingView
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_comment_detail.*

class CommentDetailActivity : AppCompatActivity(), InputVisible {
    /*评论详情的布局
    * 可能需要反转一下链表 但是我将数据放在第一个故此不一定*/
    private val lm = LinearLayoutManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment_detail)
        initUI()
        setInput()
    }

    private fun initUI() {
        val item = LayoutInflater.from(this).inflate(R.layout.cd_commodity_comment_item, cdc_item_view, false)
        val headSculpture = item.findViewById<CircleImageView>(R.id.head_sculpture)
        val nickname = item.findViewById<TextView>(R.id.nickname)
        val content = item.findViewById<TextView>(R.id.content)
        val fr = item.findViewById<FrameLayout>(R.id.cd_suspendingView_fl)
        val comment = item.findViewById<ImageView>(R.id.cd_comment_comment)
        val likeAmountButton = item.findViewById<TextView>(R.id.cd_comment_want_amount)
        val commentAmountbu = item.findViewById<TextView>(R.id.cd_comment_comment_amount)
        val like = item.findViewById<TextView>(R.id.cd_comment_wanaask)
        val view = item.findViewById<View>(R.id.view)
//        把轮廓线隐藏
        view.visibility = View.GONE
        val holder = intent.getSerializableExtra("comment_user") as CommentUser
        nickname.text = holder.nickname
        content.text = holder.content
        /*制式化日期 格式例如1-1 9:10*/
        if (holder.isSure) {
            if (holder.timeYYD!="0:0")
            fr.addView(SuspendingView(this, "用心做", holder.timeYYD))
        }
        comment.visibility = View.GONE
        likeAmountButton.text = "(${holder.likeAmount})"
        commentAmountbu.text = "评论(${holder.commentAmount})"
        cdc_item_view.addView(item)
        /*item 也要设置监听 一旦点击 文本框隐藏*/
        item.setOnClickListener { inputInvisible() }
    }

    @SuppressLint("SimpleDateFormat")
    private fun setInput() {
        val ls = mutableListOf<CommentItemUser>()
        for (it in 0..20) {
            val ciu = CommentItemUser()
            if (it % 2 == 0) {
                ciu.nickname = "侠客行"
                ciu.content = resources.getString(R.string.test2)

            } else {
                ciu.nickname = "三跃击衣"
                ciu.content = resources.getString(R.string.yurang)
            }
            ls.add(ciu)
        }
        val adapter = CDCitemRCAdapater(this, this, ls)
        cdc_recyclerView.adapter = adapter
        cdc_recyclerView.layoutManager = lm
        cd_comment.setOnClickListener {
            inputVisible()
        }
        val conSend = constraint_include.findViewById<Button>(R.id.cd_send)
        val send = include.findViewById<Button>(R.id.cd_send)
        val conEdit = constraint_include.findViewById<EditText>(R.id.cd_edit)
        val edit = include.findViewById<EditText>(R.id.cd_edit)
        val sendClick = fun(isCon: Boolean) {
            /*点击发送 链表添加数据 清空文本框 刷新适配器*/
            inputInvisible()
            /*关闭输入框*/
            hideKeyboard()
            /*获得对应的输入框*/
            val s = if (isCon) conEdit.text.toString() else edit.text.toString()
            if (!s.isEmpty()) {
                val ed = if (isCon) conEdit else edit
                ed.text = null
                val ciu = CommentItemUser()
                ciu.nickname = "侠客行"
                ciu.content = s
                ls.add(0, ciu)
                /*反转*/
                adapter.notifyItemInserted(0)
                cdc_recyclerView.smoothScrollToPosition(0)
            }
        }
        send.setOnClickListener { sendClick(false) }
        conSend.setOnClickListener { sendClick(true) }
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm.isActive && currentFocus != null) {
            if (currentFocus.windowToken != null) {
                imm.hideSoftInputFromWindow(currentFocus.windowToken, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    override fun inputVisible() {
        /*评论不可见，输入框可见*/
        /*我得记一笔 这种方法很巧妙 避开了很多运算
        * 用两个输入框替代一个输入框 而在用户眼里其实感觉都是一样的
        * 开发时就不一样了
        * 会出现点击输入框item显示不完全 或者不上移动的现象
        * 我们巧妙的用了第一个完全显示的位置来判断显示哪一个文本框*/
        val position = lm.findFirstCompletelyVisibleItemPosition()
        Log.d("CommentDetailAC", "第一个完整可见位置：$position")
        if (position == 0) {
            cd_comment.visibility = View.GONE
            include.visibility = View.VISIBLE
        } else {
            cd_comment.visibility = View.GONE
            constraint_include.visibility = View.VISIBLE
        }
    }

    override fun inputInvisible() {
        /*评论可见，输入框不可见*/
        cd_comment.visibility = View.VISIBLE
        include.visibility = View.GONE
        constraint_include.visibility = View.GONE
    }

    override fun onBackPressed() {
        /*按下退出键时 如果input在就隐藏 如果不可见就退出*/
        if (include.visibility == View.VISIBLE)
            inputInvisible()
        else
            super.onBackPressed()
    }
}
