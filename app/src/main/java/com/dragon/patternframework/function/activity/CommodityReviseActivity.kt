package com.dragon.patternframework.function.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.dragon.patternframework.R
import com.dragon.patternframework.commoditydetail.adapter.ReviseAcAdapter
import com.dragon.patternframework.javaBean.Commodity
import kotlinx.android.synthetic.main.activity_commodity_revise.*

class CommodityReviseActivity : AppCompatActivity() {
    lateinit var commodity: Commodity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_commodity_revise)
        this.commodity= intent.getSerializableExtra("commodity") as Commodity
        acr_recycler.adapter=ReviseAcAdapter(this,commodity,0)
        acr_recycler.layoutManager=LinearLayoutManager(this)
        revice_save.setOnClickListener {
            Toast.makeText(this,commodity.comLabel,Toast.LENGTH_SHORT).show()
        }
    }
}
