package com.neversaydie.andreii.mysimplewidget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import android.widget.Toast

class Widget : AppWidgetProvider() {

    val TAG: String = "MyLog"
    val ACTION_WIDGET_RECIVE: String = "ActionReciveWidget"

    val blueToothAdapter: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()


    override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)

        var remoteViews: RemoteViews = RemoteViews(context?.getPackageName(), R.layout.widget)

        var intent: Intent = Intent(context, Widget::class.java)
        var intent1: Intent = Intent(context, MainActivity::class.java)

        intent.setAction(ACTION_WIDGET_RECIVE)
        intent.putExtra("msg", "On")

        val actionPandingIntent: PendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
        remoteViews.setOnClickPendingIntent(R.id.button_ON_Bluetooth, actionPandingIntent)

        val actionPandingIntent1: PendingIntent = PendingIntent.getActivity(context, 0, intent1, 0)
        remoteViews.setOnClickPendingIntent(R.id.button_ON_Activity, actionPandingIntent1)

        appWidgetManager?.updateAppWidget(appWidgetIds, remoteViews)



        Log.d(TAG, "onUpdate")
    }

    override fun onEnabled(context: Context?) {
        super.onEnabled(context)
        Log.d(TAG, "onEnabled")
    }

    override fun onDisabled(context: Context?) {
        super.onDisabled(context)
        Log.d(TAG, "onDisabled")
    }

    override fun onDeleted(context: Context?, appWidgetIds: IntArray?) {
        super.onDeleted(context, appWidgetIds)
        Log.d(TAG, "onDeleted")
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        val intentR: String = intent!!.action
        if (ACTION_WIDGET_RECIVE.equals(intentR)) {
            Log.d(TAG, "onReceive")
            Toast.makeText(context, "intentR  " + intentR, Toast.LENGTH_SHORT).show()
            val on: String = intent.getStringExtra("msg")
            Log.d(TAG, "onReceive  =" + on)
            if (on.equals("On")) {
                Toast.makeText(context, "on" + on, Toast.LENGTH_SHORT).show()
                if (true) {
                    if (blueToothAdapter.getState() == BluetoothAdapter.STATE_ON) {
                        blueToothAdapter.disable()
                    } else if (blueToothAdapter.getState() == BluetoothAdapter.STATE_OFF) {
                        blueToothAdapter.enable()
                    }
                }
            }
        }


    }
}