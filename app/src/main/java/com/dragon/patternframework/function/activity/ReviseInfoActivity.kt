package com.dragon.patternframework.function.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.dragon.patternframework.R
import com.dragon.patternframework.function.adapter.AmendRecyclerAdapter
import com.dragon.patternframework.function.data.User
import kotlinx.android.synthetic.main.activity_revise_info.*

class ReviseInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_revise_info)
        val info = intent.getStringArrayExtra("array_info")
        val label = intent.getStringArrayExtra("array_label")
        val user = User()
        label.mapIndexed { index, it ->
            user.run {
                when (it) {
                    "头像" -> portrait = info[index]
                    "呢称" -> falseName = info[index]
                    "邮箱" -> email = info[index]
                    "签名" -> sign = info[index]
                    "电话" -> phone = info[index]
                    "QQ" -> qq = info[index]
                    "地址" -> address = info[index]
                    "班级" -> whichClass = info[index]
                    "真实姓名" -> trueName = info[index]
                    "学校" -> school = info[index]
                }
            }
        }
        fun_revise_info.adapter = AmendRecyclerAdapter(this, user, label)
        fun_revise_info.layoutManager = LinearLayoutManager(this)
    }
}
