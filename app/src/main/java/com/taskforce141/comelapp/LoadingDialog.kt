package com.taskforce141.comelapp

import android.app.Activity
import android.app.AlertDialog
import com.taskforce141.comelapp.databinding.LoadingItemBinding

class LoadingDialog(val activity: Activity){
    private lateinit var isDialog: AlertDialog
    private lateinit var binding: LoadingItemBinding
    fun startLoading(){
        //set view
        val infalter = activity.layoutInflater
        val dialogView = infalter.inflate(R.layout.loading_item,null)
        //set dialog
        val builder = AlertDialog.Builder(activity)
        builder.setView(dialogView)
        builder.setCancelable(false)
        isDialog = builder.create()
        isDialog.setCanceledOnTouchOutside(false)
        isDialog.show()
    }
    fun isDismiss(){
        isDialog.dismiss()
    }
}