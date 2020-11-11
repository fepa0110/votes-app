package com.example.votesapp.maps

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.TypedValue
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.votesapp.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions


class MapsActivity : AppCompatActivity(), OnMapReadyCallback,
    GoogleMap.OnMapLongClickListener, GoogleMap.OnCameraIdleListener{

    private lateinit var map: GoogleMap
    private lateinit var tapTextView: TextView
    private lateinit var cameraTextView: TextView

    private lateinit var seleccionarButton : Button

    /**
     * Keeps track of the last selected marker (though it may no longer be selected).  This is
     * useful for refreshing the info window.
     *
     * Must be nullable as it is null when no marker has been selected
     */
    private var lastSelectedPoint: LatLng? = null

    private val places = mapOf(
        "PUERTO MADRYN" to LatLng(-42.6859231, 65.3040953)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps_events)

        disableButtonSeleccionar()

        tapTextView = findViewById(R.id.tap_text)
//        cameraTextView = findViewById(R.id.camera_text)

        // create bounds that encompass every location we reference
        val boundsBuilder = LatLngBounds.Builder()
        places.keys.map { place -> boundsBuilder.include(places.getValue(place)) }
        val bounds = boundsBuilder.build()

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        with(map.uiSettings){
            map.uiSettings.isZoomControlsEnabled = true
            map.uiSettings.isZoomGesturesEnabled = true
        }

        val puertoMadryn = LatLng(-42.6859231, -65.3040953)
        map.moveCamera(CameraUpdateFactory.newLatLng(puertoMadryn))

        map.setOnMapLongClickListener(this)

        seleccionarButton.setOnClickListener { view ->
            val intent = Intent()

            intent.putExtra("Latitud",lastSelectedPoint!!.latitude)
            intent.putExtra("Longitud",lastSelectedPoint!!.longitude)

            setResult(2, intent);
            finish()
        }
    }

    override fun onBackPressed() {
        setResult(0,null)
        finish()
    }

    override fun onMapLongClick(point: LatLng) {
        map.clear()
        enableButtonSeleccionar()
        tapTextView.text = point.toString()
        lastSelectedPoint = point
        tapTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16F)
        map.addMarker(MarkerOptions().position(point).title("Tu selección"))
    }

    override fun onCameraIdle() {
        if(!::map.isInitialized) return
        cameraTextView.text = map.cameraPosition.toString()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun enableButtonSeleccionar(){
        seleccionarButton = findViewById(R.id.seleccionar_ubicacion_button_map)
        seleccionarButton.isEnabled = true
        seleccionarButton.background = resources.getDrawable(R.color.colorPrimary, null)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun disableButtonSeleccionar(){
        seleccionarButton = findViewById(R.id.seleccionar_ubicacion_button_map)
        seleccionarButton.isEnabled = false
        seleccionarButton.background = resources.getDrawable(R.color.gris, null)
    }
}