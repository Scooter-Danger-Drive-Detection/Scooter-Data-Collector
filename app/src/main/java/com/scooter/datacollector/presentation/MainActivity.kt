package com.scooter.datacollector.presentation

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.scooter.datacollector.R
import com.scooter.datacollector.domain.models.Frame
import com.scooter.datacollector.domain.usecases.CheckSessionStateUsecase
import org.koin.java.KoinJavaComponent.get

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainActivityViewModel

    private lateinit var sessionIdText: TextView
    private lateinit var frameIdText: TextView
    private lateinit var previousFrameIdText: TextView
    private lateinit var timeText: TextView
    private lateinit var speedText: TextView
    private lateinit var longitudeText: TextView
    private lateinit var latitudeText: TextView
    private lateinit var fullAccelerationXText: TextView
    private lateinit var fullAccelerationYText: TextView
    private lateinit var fullAccelerationZText: TextView
    private lateinit var gravityAccelerationXText: TextView
    private lateinit var gravityAccelerationYText: TextView
    private lateinit var gravityAccelerationZText: TextView
    private lateinit var rotationXText: TextView
    private lateinit var rotationYText: TextView
    private lateinit var rotationZText: TextView
    private lateinit var angleSpeedXText: TextView
    private lateinit var angleSpeedYText: TextView
    private lateinit var angleSpeedZText: TextView

    private lateinit var startSessionButton: Button
    private lateinit var stopSessionButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sessionIdText = findViewById(R.id.session_id_value)
        frameIdText = findViewById(R.id.frame_id_value)
        previousFrameIdText = findViewById(R.id.previous_frame_id_value)
        timeText = findViewById(R.id.time_value)
        speedText = findViewById(R.id.speed_value)
        longitudeText = findViewById(R.id.longitude_value)
        latitudeText = findViewById(R.id.latitude_value)
        fullAccelerationXText = findViewById(R.id.full_acceleration_x_value)
        fullAccelerationYText = findViewById(R.id.full_acceleration_y_value)
        fullAccelerationZText = findViewById(R.id.full_acceleration_z_value)
        gravityAccelerationXText = findViewById(R.id.gravity_acceleration_x_value)
        gravityAccelerationYText = findViewById(R.id.gravity_acceleration_y_value)
        gravityAccelerationZText = findViewById(R.id.gravity_acceleration_z_value)
        rotationXText = findViewById(R.id.rotation_x_value)
        rotationYText = findViewById(R.id.rotation_y_value)
        rotationZText = findViewById(R.id.rotation_z_value)
        angleSpeedXText = findViewById(R.id.angle_speed_x_value)
        angleSpeedYText = findViewById(R.id.angle_speed_y_value)
        angleSpeedZText = findViewById(R.id.angle_speed_z_value)

        startSessionButton = findViewById(R.id.start_session_button)
        stopSessionButton = findViewById(R.id.stop_session_button)


        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]

        viewModel.currentFrame.observe(this){
            frame -> updateShowingFrame(frame)
        }

        updateButtonsState()
    }

    private fun updateButtonsState(){
        val checkSession: CheckSessionStateUsecase = get(CheckSessionStateUsecase::class.java)
        val isSessionGoing = checkSession.execute()
        startSessionButton.isEnabled = !isSessionGoing
        stopSessionButton.isEnabled = isSessionGoing
    }

    private fun updateShowingFrame(frame: Frame){
        sessionIdText.text = frame.sessionId.toString()
        frameIdText.text = frame.frameId.toString()
        previousFrameIdText.text = frame.lastFrameId.toString()
        timeText.text = frame.time.toString()
        speedText.text = frame.gps.speed.toString()
        longitudeText.text = frame.gps.longitude.toString()
        latitudeText.text = frame.gps.latitude.toString()
        fullAccelerationXText.text = frame.accelerometer.fullAccelerationX.toString()
        fullAccelerationYText.text = frame.accelerometer.fullAccelerationY.toString()
        fullAccelerationZText.text = frame.accelerometer.fullAccelerationZ.toString()
        gravityAccelerationXText.text = frame.accelerometer.gravityX.toString()
        gravityAccelerationYText.text = frame.accelerometer.gravityY.toString()
        gravityAccelerationZText.text = frame.accelerometer.gravityZ.toString()
        rotationXText.text = frame.gyroscopeData.rotationDeltaX.toString()
        rotationYText.text = frame.gyroscopeData.rotationDeltaY.toString()
        rotationYText.text = frame.gyroscopeData.rotationDeltaZ.toString()
        // TODO добавить отображение угловой скорости
    }

}