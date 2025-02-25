package com.example.practica1android

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var giroscopio: Sensor? = null
    private var acelerometro: Sensor? = null

    private lateinit var esfera: Esfera
    private lateinit var esfera2: Esfera
    private lateinit var textPostion: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        esfera = findViewById(R.id.Esfera)
        esfera2 = findViewById(R.id.Esfera2)
        esfera2.setColor(android.graphics.Color.RED)

        textPostion = findViewById(R.id.textPostion)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        giroscopio = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        if (giroscopio == null) {
            acelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
            if (acelerometro == null) {
                textPostion.text = "Este dispositivo no tiene giroscopio ni acelerómetro"
            } else {
                sensorManager.registerListener(this, acelerometro, SensorManager.SENSOR_DELAY_UI)
                textPostion.text = "Acelerómetro"
            }
        } else {
            sensorManager.registerListener(this, giroscopio, SensorManager.SENSOR_DELAY_UI)
            textPostion.text = "Giroscopio"
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event == null) return

        if (event.sensor.type == Sensor.TYPE_GYROSCOPE) {
            actualizarPosicionEsferas(event.values[0], event.values[1])
        } else if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            actualizarPosicionEsferas(event.values[0], event.values[1])
        }
    }

    private fun actualizarPosicionEsferas(newX: Float, newY: Float) {
        val x = newX * 50
        val y = newY * 50
        runOnUiThread {
            textPostion.text = "X: $x, Y: $y"
            esfera.updatePosition(x, y)
            esfera2.updatePosition(-x  , -y )
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) { }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }
}
