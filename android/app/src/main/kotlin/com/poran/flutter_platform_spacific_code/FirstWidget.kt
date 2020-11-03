package com.poran.flutter_platform_spacific_code

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.platform.PlatformView

class FirstWidget internal  constructor(private val context: Context?, id:Int, creationParams: Map<String?, Any?>?, messenger:BinaryMessenger):PlatformView,MethodChannel.MethodCallHandler{
    private  val view:View = LayoutInflater.from(context).inflate(R.layout.first_layout,null)
    private val methodChannel:MethodChannel = MethodChannel(messenger,"platform_view/first_widgets$id")
    private val textView: TextView
    private  val  btn:Button
    private  var count:Int=0
    override fun getView(): View {
        return  view
    }

    init {
        textView=view.findViewById(R.id.text_view)
        btn=view.findViewById(R.id.btn)
        methodChannel.setMethodCallHandler(this)

        btn.setOnClickListener {
            count+=1
            textView.text=count.toString()
            sendFromNative(count)

        }
    }
    private fun sendFromNative(text: Int) {
        methodChannel.invokeMethod("sendFromNative", text)
    }
    private  fun ping(methodCall: MethodCall,result: MethodChannel.Result){
        result.success(
                hashMapOf<String,Int>(
                        "count" to count
                )
        )


    }

    private  fun showToast(methodCall: MethodCall,result: MethodChannel.Result){
        Toast.makeText(context,methodCall.arguments.toString(),Toast.LENGTH_SHORT).show();


    }
    override fun dispose() {
    }

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        when(call.method){
            "ping"->ping(call,result)
            "showToast"->showToast(call,result)
            else-> result.notImplemented()
        }
    }
}