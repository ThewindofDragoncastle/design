package com.dragon.patternframework.internet.intention

import com.dragon.patternframework.commoditydetail.useinterface.ComIdPresenterIm2
import com.dragon.patternframework.internet.CommodityUpload.Model
import com.dragon.patternframework.internet.CommodityUpload.comIdIntention2

class PostBigInPresenter : comIdIntention2 {
    override fun connectInternet(ip: String, map: Map<String, String>) {
        Model(ip, this).upload(true, map)
    }

    private lateinit var comIdPresenter: ComIdPresenterIm2
    override fun setPicUpload(comId: ComIdPresenterIm2) {
        this.comIdPresenter=comId
    }

    /*正在销售的查询*/
    override fun info(msg: String) {
          comIdPresenter.success()
    }

    override fun error(e: String) {
        comIdPresenter.fail(e)
    }

    companion object {
        private val ourInstance = PostBigInPresenter()
        fun getInstance(): PostBigInPresenter{
            return ourInstance
        }
    }
}