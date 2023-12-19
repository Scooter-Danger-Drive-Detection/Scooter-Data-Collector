package com.scooter.datacollector.presentation

import android.Manifest
import android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.ACCESS_NETWORK_STATE
import android.Manifest.permission.INTERNET
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.scooter.datacollector.R

class RequestPermissionsActivity : AppCompatActivity() {
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()){
                _: Boolean ->
            updateGrantedStatuses()
        }

    private lateinit var accessCoarseLocationGrantedText: TextView
    private lateinit var accessFineLocationGrantedText: TextView
    private lateinit var accessBackgroundGrantedLocation: TextView
    private lateinit var internetGrantedText: TextView
    private lateinit var accessNetworkStateGrantedText: TextView

    private lateinit var requestPermissionsButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_permissions)

        accessCoarseLocationGrantedText = findViewById(R.id.access_coarse_location_granted)
        accessFineLocationGrantedText = findViewById(R.id.access_fine_location_granted)
        accessBackgroundGrantedLocation = findViewById(R.id.access_background_location_granted)
        internetGrantedText = findViewById(R.id.internet_granted)
        accessNetworkStateGrantedText = findViewById(R.id.access_network_state_granted)

        requestPermissionsButton = findViewById(R.id.request_permissions_button)
        requestPermissionsButton.setOnClickListener {
            requestPermissions()
            updateGrantedStatuses()
            tryStartMainActivity()
        }

        updateGrantedStatuses()
        tryStartMainActivity()
    }

    private fun tryStartMainActivity(){
        if(getNotAllowedPermissions().isEmpty()){
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
    private fun getNotAllowedPermissions(): List<String>{
        val notAllowedPermissions = mutableListOf<String>()
        if(checkSelfPermission(ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            notAllowedPermissions.add(ACCESS_COARSE_LOCATION)
        }
        if(checkSelfPermission(ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            notAllowedPermissions.add(ACCESS_FINE_LOCATION)
        }
        if(checkSelfPermission(ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED && Build.VERSION.SDK_INT >= 29){
            notAllowedPermissions.add(ACCESS_BACKGROUND_LOCATION)
        }
        if (checkSelfPermission(INTERNET) != PackageManager.PERMISSION_GRANTED){
            notAllowedPermissions.add(INTERNET)
        }
        if(checkSelfPermission(ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED){
            notAllowedPermissions.add(ACCESS_NETWORK_STATE)
        }
        return notAllowedPermissions
    }
    private fun requestPermissions(){
        val notAllowedPermissions = getNotAllowedPermissions()
        for(index in notAllowedPermissions.indices)
            requestPermissionLauncher.launch(notAllowedPermissions[index])
    }

    private fun updateGrantedStatuses(){
        val notAllowedPermissions = getNotAllowedPermissions()
        val granted = getString(R.string.permissions_activity_permission_granted)
        val notGranted = getString(R.string.permissions_activity_permission_notGranted)
        accessCoarseLocationGrantedText.text = if(notAllowedPermissions.contains(ACCESS_COARSE_LOCATION)) notGranted else granted
        accessFineLocationGrantedText.text = if(notAllowedPermissions.contains(ACCESS_FINE_LOCATION)) notGranted else granted
        accessBackgroundGrantedLocation.text = if(notAllowedPermissions.contains(ACCESS_BACKGROUND_LOCATION)) notGranted else granted
        internetGrantedText.text = if(notAllowedPermissions.contains(INTERNET)) notGranted else granted
        accessNetworkStateGrantedText.text = if(notAllowedPermissions.contains(ACCESS_NETWORK_STATE)) notGranted else granted
    }
}