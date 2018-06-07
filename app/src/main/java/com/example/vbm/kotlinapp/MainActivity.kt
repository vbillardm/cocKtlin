package com.example.vbm.kotlinapp;

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.*
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.TextView
import java.util.*
import kotlin.concurrent.timer
import kotlin.math.log


class MainActivity() : AppCompatActivity(), Parcelable {

    var timeStamp: Long = Date().getTime();
    var currentTimeStamp = 0L
    var currentTimeStamp2 = 0L
    var oldSensorValue: Float = 0.0f;
    internal lateinit var ProximitySensor: TextView
    internal lateinit var data: TextView
    internal lateinit var mySensorManager: SensorManager
    internal var myProximitySensor: Sensor? = null

    private var currentRunnable: Runnable? = null

    internal var proximitySensorEventListener: SensorEventListener = object : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
            // TODO Auto-generated method stub
        }


        override fun onSensorChanged(event: SensorEvent) {
            if (event.sensor.type == Sensor.TYPE_PROXIMITY) {

                if (event.values[0] <= 10 && event.values[0] != oldSensorValue.toFloat()) {

                    if (event.values[0] != 0.0f) {
                        oldSensorValue = event.values[0]
                    }

                    if (currentTimeStamp != 0L) {
                        currentTimeStamp2 = Date().time
                    }

                 //   Log.d("timer", currentTimeStamp2.toString() + " :: :: :: ::" + currentTimeStamp.toString())


                    currentTimeStamp = Date().time


                    if(currentRunnable ===  null) {
                        currentRunnable = Runnable {
                            runOnUiThread {
                                // ce code sera exécuté sur le thread principal
                                // donc safe pour modifier label

                                //Log.d("timer", currentTimeStamp2.toString() + " :: :: :: ::" + currentTimeStamp.toString())
                                if ((currentTimeStamp2 - currentTimeStamp) <= 1000 && currentTimeStamp2 != 0L) {
                                    data.text = "Précédent"
                                } else {
                                    data.text = "Suivant"
                                }

                                currentRunnable = null
                                currentTimeStamp = 0L
                                currentTimeStamp2 = 0L
                            }
                        }
                        Handler().postDelayed(currentRunnable, 2000)
                    }






                    //  if( (currentTimeStamp- timeStamp ) <= 1000){
                    //      data.text = "Précédent"
                    //  }else {
                    //      data.text = "Suivant"
                    //    }
                    //timeStamp = currentTimeStamp;
                }

            }
        }
    }

    constructor(parcel: Parcel) : this() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ProximitySensor = findViewById(R.id.proximitySensor) as TextView
        data = findViewById(R.id.data) as TextView
        mySensorManager = getSystemService(
                Context.SENSOR_SERVICE) as SensorManager
        myProximitySensor = mySensorManager.getDefaultSensor(
                Sensor.TYPE_PROXIMITY)
        if (myProximitySensor == null) {
            ProximitySensor.text = "No Proximity Sensor!"
        } else {

            mySensorManager.registerListener(proximitySensorEventListener,
                    myProximitySensor,
                    SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MainActivity> {
        override fun createFromParcel(parcel: Parcel): MainActivity {
            return MainActivity(parcel)
        }

        override fun newArray(size: Int): Array<MainActivity?> {
            return arrayOfNulls(size)
        }
    }
}
