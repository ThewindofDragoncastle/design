package com.dragon.patternframework.internet.PictureUpload

import com.dragon.patternframework.MyLog
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import java.io.*
import java.net.Socket
import java.net.SocketException

/**
 * Created by WYL on 2018/2/26.
 */
class Upload private constructor() {
    /*上传图片*/
    /* 将接收到的数据rxjava上传至主线程*/
    /*是否取消上传*/
    private var isCancel = false
    private val control = WaitControl()
    private var pro=0
    private var proAble: ObservableEmitter<Int>? = null
    /*两个rxJava接收器分别接收字符串和进度信息*/
    private val progressOb = object : Observer<Int> {
        override fun onComplete() {
            MyLog.d(javaClass.name,"onNext:"+Thread.currentThread().name)
            presenterCallBack.complete()
        }
        override fun onSubscribe(d: Disposable) {}
        override fun onNext(t: Int) {
            MyLog.d(javaClass.name,Thread.currentThread().name)
            if (t!=-1)
                presenterCallBack.progressChange(currentPage + 1,t)
            else
                presenterCallBack.cancel()
        }

        override fun onError(e: Throwable) {
            presenterCallBack.fail()
        }
    }
    /*上传图片控制*/
    private var currentPage = 0
    /*上传图片数组*/
    private var arg: Array<String>?=null
    private lateinit var socket: Socket

    companion object {
        private lateinit var presenterCallBack: PresenterCallBack
        fun getInstance(presenterCallBack: PresenterCallBack): Upload {
            this.presenterCallBack = presenterCallBack
            return Upload()
        }
    }

    fun start(array: Array<String>?,ip:String) {
        isCancel = false
        Observable.create<Int> { e -> proAble
            MyLog.d(javaClass.name,"onNext:"+Thread.currentThread().name)
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(progressOb)
        arg = array
        /*网络运行在子线程中*/
        MyLog.i(javaClass.name, "正在准备连接到服务器")
            try {
                /*Genymotion：10.0.3.2
                安卓模拟器：10.0.2.2*/
                socket = Socket(ip, 12378)
            } catch (e: SocketException) {
                proAble?.onError(Throwable("服务器异常"))
                MyLog.e(javaClass.name, "服务器异常！${e.toString()}")
                return
            }
            MyLog.i(javaClass.name, "服务器连接成功！")
            val input = DataInputStream(socket.getInputStream())
            val output = DataOutputStream(socket.getOutputStream())
            Thread(Runnable {
                /*开启一个线程接收数据*/
                try {
                    receiveSysData(input, output)
                } catch (e: SocketException) {
                    /*无论是主动断开 还是被动断开同属于断开 不需要弹出提示*/
//                    infoAble?.onNext("与服务器连接断开。")
                }
            }).start()
            sendData(output, arg!![currentPage])
    }

    private fun sendData(output: DataOutputStream, path: String?) {
        val file = File(path)
    Thread.sleep(100)
    val input = BufferedInputStream(FileInputStream(file))
    val b = ByteArray(1024)
    var length = 0
    while (input.read(b) != -1) {
        /*计算长度*/
        length += b.size
    }
    val ob = JSONObject()
    ob.put("name", file.name)
    ob.put("size", length)
    ob.put("account", "龙城飞絮")
    /*发送json数组就行了 不一定数组*/
    MyLog.i(javaClass.name, "$ob")
    output.writeUTF("$ob")
    input.close()
    val input1 = BufferedInputStream(FileInputStream(file))
    while (input1.read(b) != -1) {
        /*不限制上传速度会让rxjava来不及转发 发生端会丢失数据（原因 不明）*/
        /*改法：必须等对方响应并且返回数据再进行上传*/
        if (!isCancel) {
            output.write(b)
            control.shouldWait()
        }
        /*下载取消通知客户端删除*/
        if (isCancel)
            break
    }
    /*关闭socket*/
    if (isCancel) {
        socket.close()
        proAble?.onNext(-1)
        MyLog.i(javaClass.name, "关闭socket成功！")
    }
    input1.close()
}

    private fun receiveSysData(input: DataInputStream, output: DataOutputStream) {
        var content: String? = null
        while (input.readUTF().apply {
                    content = this
                } != null) {
            MyLog.i(javaClass.name, "状态信息：" + content)
            val info = Gson().fromJson(content, Info::class.java)
            val over = when (info.status) {
                0 -> {
                    val infoPro=info.progress
                    if (pro!=infoPro)
                    {
                        proAble?.onNext(infoPro)
                        pro=infoPro
                    }
                    control.shouldWake()
                    MyLog.i(javaClass.name, "图片上传中，上传进度：${info.progress}")
                    false
                }
                1 -> {
                    MyLog.i(javaClass.name, "图片上传成功！")
                    currentPage++
                    /*只要还有图片未上传就不跳出循环*/
                    if (currentPage < arg!!.size) {
                        Thread(Runnable {
                            /*这里必须开启子线程 因为这里本来是接收线程的地盘*/
                            sendData(output, arg!![currentPage])
                        }).start()
                        false
                    } else {
                        /*同时关闭socket*/
                        proAble?.onComplete()
                        MyLog.i(javaClass.name, "关闭连接！")
                        progressOb.onComplete()
                        socket.close()
                        true
                    }
                }
                2 -> {
                    MyLog.i(javaClass.name, "图书上传失败！失败原因：${info.reason}")
                    proAble?.onError(Throwable("失败"))
                    socket.close()
                    true
                }
                else -> false
            }
            /*上传完成跳出循环*/
            if (over)
                break
        }
    }

    fun cancel() {
        isCancel = true
    }
    data class Info(val status: Int, val info: String = "",
                    val reason: String = "", var progress: Int = 0)
/*如果状态为0 代表还在上传 1 上传成功 2 上传失败*/
}