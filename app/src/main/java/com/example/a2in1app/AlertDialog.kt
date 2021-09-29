package com.example.a2in1app

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface

class AlertDialog (activity: Activity, msg:String) {
    init {
        val dialogBuilder = AlertDialog.Builder(activity)
        dialogBuilder.setMessage(msg)
            .setCancelable(false)
            .setPositiveButton("Yes", DialogInterface.OnClickListener(){
                dialog,id-> activity.recreate()
            } )
            .setNegativeButton("No", DialogInterface.OnClickListener(){
                    dialog,id-> dialog.cancel()
            })

        // create dialog box
        val alert = dialogBuilder.create()
        // set title for alert dialog box
        alert.setTitle("Game Over")
        // show alert dialog
        alert.show()
    }
}