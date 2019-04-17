package com.dragon.patternframework.login.fragment

interface LoginControlInterface {
    fun showFail(msg:String)
    fun showWait()
    fun loginSuccess()
    fun savePassword(account:String,password:String)
}