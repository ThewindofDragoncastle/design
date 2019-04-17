package com.dragon.patternframework.login.fragment

interface SignControlInterface {
    fun showFail(msg:String)
    fun showWait()
    fun signSuccess()
}