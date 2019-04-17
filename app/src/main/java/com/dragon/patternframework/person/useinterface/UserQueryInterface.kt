package com.dragon.patternframework.person.useinterface

import com.dragon.patternframework.javaBean.User

interface UserQueryInterface {
    fun fail(e:String)
    fun success(user: User)
}
interface ImageUploadInterface {
    fun fail(e:String)
    fun success()
}