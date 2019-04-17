package com.dragon.patternframework.internet.CommodityUpload

import com.dragon.patternframework.commodity.setting.MainPage_Setting
import com.dragon.patternframework.commodity.useinterface.ChangeUrgent
import com.dragon.patternframework.commodity.useinterface.MessageAmount
import com.dragon.patternframework.commodity.useinterface.MessageQuery
import com.dragon.patternframework.commodity.useinterface.MessageResult
import com.dragon.patternframework.commoditydetail.useinterface.*
import com.dragon.patternframework.function.useinterface.MyComment
import com.dragon.patternframework.function.useinterface.RemoveSup
import com.dragon.patternframework.javaBean.Commodity
import com.dragon.patternframework.login.fragment.LoginControlInterface
import com.dragon.patternframework.login.fragment.SignControlInterface
import com.dragon.patternframework.person.useinterface.AllOrderInterface
import com.dragon.patternframework.person.useinterface.ImageUploadInterface
import com.dragon.patternframework.person.useinterface.UserQueryInterface

/**
 * Created by WYL on 2018/2/26.
 */
interface PresenterCallBack {
    fun info(msg: String)
    fun error(e:String)
}
interface ComUpload :PresenterCallBack{
    /*连接*/
    fun connectInternet(com: Commodity)
}
interface OnSellingQuery :PresenterCallBack{
    /*连接*/
    fun connectInternet(ip:String)
    fun setRemoveSupInterface(removeSup: RemoveSup)
}
interface RemoveInterface :PresenterCallBack{
    /*连接*/
    fun connectInternet(ip:String,comId:Int)
    fun setRemoveSupInterface(imageUploadInterface: ImageUploadInterface)
}
interface ComQuery :PresenterCallBack{
    /*连接*/
    fun returnProducts(products: MutableList<Commodity>)
    fun connectInternet(ip:String)
    fun setMainPageSettingInterface(settingInterface: MainPage_Setting)
}
interface GlideAdQuery :PresenterCallBack{
    /*连接*/
    fun returnADProducts(products: MutableList<Commodity>)
    fun connectInternet(ip:String)
    fun setMainPageSettingInterface(settingInterface: MainPage_Setting)
}
interface UrgentQuery :PresenterCallBack{
    /*连接*/
    fun connectInternet(ip:String)
    fun setChangeUrgentInterface(changeUrgent: ChangeUrgent)
}
interface Login:PresenterCallBack{
    /*连接*/
    fun connectInternet(ip:String,account:String,password:String)
    fun setLoginControllInterface(loginControllInterface: LoginControlInterface)
}
interface SignIn :PresenterCallBack{
    /*连接*/
    fun connectInternet(ip:String,account:String,password:String)
    fun setSignControllInterface(changeUrgent: SignControlInterface)
}
interface OrderPost :PresenterCallBack{
    /*连接*/
    fun connectInternet(ip:String,map:Map<String,String>)
    fun setSignControllInterface(needPost: NeedPost)
}
interface AllOrderQuery :PresenterCallBack{
    /*连接*/
    fun connectInternet(ip:String)
    fun setAllOrder(allOrder: AllOrderInterface)
}
interface UserQuery :PresenterCallBack{
    /*连接*/
    fun connectInternet(ip:String)
    fun setUserQuery(userQueryInterface: UserQueryInterface)
}
interface PictureUploadInterface :PresenterCallBack{
    /*连接*/
    fun connectInternet(ip:String,filename:String)
    fun setPicUpload(imageUploadInterface: ImageUploadInterface)
}
interface comIdIntention :PresenterCallBack{
    /*连接*/
    fun connectInternet(ip:String,comId:Int)
    fun setPicUpload(comId: ComIdPresenterIm)
}
interface comIdIntention2 :PresenterCallBack{
    /*连接*/
    fun connectInternet(ip:String,map: Map<String,String>)
    fun setPicUpload(comId: ComIdPresenterIm2)
}
interface comIdIntention11 :PresenterCallBack{
    /*连接*/
    fun connectInternet(ip:String)
    fun setPicUpload(comId: ComIdPresenterIm)
}
interface MyComment :PresenterCallBack{
    /*连接*/
    fun connectInternet(ip:String,map: Map<String,String>)
    fun setBack(myComment: MyComment)
}
interface comIdIntention22 :PresenterCallBack{
    /*连接*/
    fun connectInternet(ip:String,map: Map<String,String>)
    fun setPicUpload(comId: ComIdPresenterIm2)
}
interface search :PresenterCallBack{
    /*连接*/
    fun connectInternet(ip:String,key:String)
    fun setMainPageSettingInterface(settingInterface: SearchCom)
}
interface returnIDCom :PresenterCallBack{
    /*连接*/
    fun connectInternet(ip:String,int: Int)
    fun setMainPageSettingInterface(settingInterface: ComIdPresenterIm3)
}
interface MessageAmount :PresenterCallBack{
    /*连接*/
    fun connectInternet(ip:String,use_id:String)
    fun setMessageQuery(needPost:MessageAmount)
}
interface MessageQuery :PresenterCallBack{
    /*连接*/
    fun connectInternet(ip:String,use_id:String)
    fun setMessageQuery(needPost: MessageQuery)
}
interface MessageResult :PresenterCallBack{
    /*连接*/
    fun connectInternet(ip:String,map:Map<String,String>)
    fun setMessageQuery(needPost: MessageResult)
}