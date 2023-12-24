package com.scooter.datacollector.presentation

import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.scooter.datacollector.R
import com.scooter.datacollector.domain.models.Frame
import com.scooter.datacollector.domain.models.RideMode
import com.scooter.datacollector.domain.usecases.CheckSessionStateUsecase
import com.scooter.datacollector.domain.usecases.EndSessionUsecase
import com.scooter.datacollector.domain.usecases.StartSessionUsecase
import com.scooter.datacollector.domain.usecases.SynchronizeAllUsecase
import org.koin.android.ext.android.get

class MainActivity : AppCompatActivity() {
    companion object{
        private const val MAIN_ACTIVITY_PERMISSION_REQUEST_CODE = 1
    }

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
    private lateinit var rotationDeltaArray: Array<TextView>
    private lateinit var angleSpeedXText: TextView
    private lateinit var angleSpeedYText: TextView
    private lateinit var angleSpeedZText: TextView

    private lateinit var safetyRideText: TextView

    private lateinit var testRideRadioButton: RadioButton
    private lateinit var safeRideRadioButton: RadioButton
    private lateinit var unsafeRideAloneRadioButton: RadioButton
    private lateinit var unsafeRideManyPeopleRadioButton: RadioButton

    private lateinit var serverActionButton: Button
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
        rotationDeltaArray = listOf<TextView>(
            findViewById(R.id.rotation_delta_value_0),
            findViewById(R.id.rotation_delta_value_1),
            findViewById(R.id.rotation_delta_value_2),
            findViewById(R.id.rotation_delta_value_3),
            findViewById(R.id.rotation_delta_value_4),
            findViewById(R.id.rotation_delta_value_5),
            findViewById(R.id.rotation_delta_value_6),
            findViewById(R.id.rotation_delta_value_7),
            findViewById(R.id.rotation_delta_value_8)
        ).toTypedArray()
        angleSpeedXText = findViewById(R.id.angle_speed_x_value)
        angleSpeedYText = findViewById(R.id.angle_speed_y_value)
        angleSpeedZText = findViewById(R.id.angle_speed_z_value)

        safetyRideText = findViewById(R.id.ride_safety_value)

        testRideRadioButton = findViewById(R.id.test_ride_radio_button)
        safeRideRadioButton = findViewById(R.id.safe_ride_radio_button)
        unsafeRideAloneRadioButton = findViewById(R.id.unsafe_alone_ride_radio_button)
        unsafeRideManyPeopleRadioButton = findViewById(R.id.unsafe_many_people_ride_radio_button)
        testRideRadioButton.isChecked = true

        startSessionButton = findViewById(R.id.start_session_button)
        startSessionButton.setOnClickListener {
            val startSessionUsecase: StartSessionUsecase = get()
            startSessionUsecase.execute(getRideMode())
            updateButtonsState()
            viewModel.sessionStarted()
        }

        stopSessionButton = findViewById(R.id.stop_session_button)
        stopSessionButton.setOnClickListener {
            val endSessionUsecase: EndSessionUsecase = get()
            endSessionUsecase.execute()
            updateButtonsState()
            viewModel.sessionEnded()
        }

        serverActionButton = findViewById(R.id.server_action_button)
        serverActionButton.setOnClickListener {
            serverActionButton.isEnabled = false
            val syncAll: SynchronizeAllUsecase = get()
            syncAll.execute({
                Handler(mainLooper).post{
                    serverActionButton.isEnabled = true
                }
            }, {})

        }

        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
        viewModel.attachActivity(this)
        viewModel.currentFrame.observe(this){
            frame -> updateShowingFrame(frame)
        }
        viewModel.rideSafety.observe(this){
            safetyRideText.text = it.toString()
        }
    }

    private fun updateButtonsState(){
        val checkSession: CheckSessionStateUsecase = get()
        val isSessionGoing = checkSession.execute()
        startSessionButton.isEnabled = !isSessionGoing
        stopSessionButton.isEnabled = isSessionGoing
        safeRideRadioButton.isEnabled = !isSessionGoing
        unsafeRideAloneRadioButton.isEnabled = !isSessionGoing
        unsafeRideManyPeopleRadioButton.isEnabled = !isSessionGoing
        serverActionButton.isEnabled = !isSessionGoing
    }

    private fun updateShowingFrame(frame: Frame){
        sessionIdText.text = frame.sessionId.toString()
        frameIdText.text = frame.frameId.toString()
        previousFrameIdText.text = frame.lastFrameId.toString()
        timeText.text = frame.time.toString()
        speedText.text = frame.gps.speed.toString()
        longitudeText.text = frame.gps.longitude.toString()
        latitudeText.text = frame.gps.latitude.toString()
        fullAccelerationXText.text = frame.accelerometer.linearAccelerationX.toString()
        fullAccelerationYText.text = frame.accelerometer.linearAccelerationY.toString()
        fullAccelerationZText.text = frame.accelerometer.linearAccelerationZ.toString()
        gravityAccelerationXText.text = frame.accelerometer.gravityAccelerationX.toString()
        gravityAccelerationYText.text = frame.accelerometer.gravityAccelerationY.toString()
        gravityAccelerationZText.text = frame.accelerometer.gravityAccelerationZ.toString()

        for (i in rotationDeltaArray.indices){
            rotationDeltaArray[i].text = frame.gyroscopeData.rotationDelta[i].toString()
        }
        angleSpeedXText.text = frame.gyroscopeData.angleSpeedX.toString()
        angleSpeedYText.text = frame.gyroscopeData.angleSpeedY.toString()
        angleSpeedZText.text = frame.gyroscopeData.angleSpeedZ.toString()
    }

    private fun getRideMode() : RideMode{
        if(safeRideRadioButton.isChecked)
            return RideMode.SAFE
        if(unsafeRideAloneRadioButton.isChecked)
            return RideMode.UNSAFE_ALONE
        if(unsafeRideManyPeopleRadioButton.isChecked)
            return RideMode.UNSAFE_MANY_PEOPLE
        if(testRideRadioButton.isChecked)
            return RideMode.TEST_RIDE
        throw Exception("Undefined RideMode!!")
    }
}