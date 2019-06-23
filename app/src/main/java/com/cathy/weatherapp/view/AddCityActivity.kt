package com.cathy.weatherapp.view

import android.Manifest
import android.app.AlertDialog
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import com.cathy.weatherapp.R
import com.cathy.weatherapp.adapter.CityAdapter
import com.cathy.weatherapp.adapter.CityAutoCompleteAdapter
import com.cathy.weatherapp.viewmodel.CityVM
import android.widget.AdapterView
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cathy.weatherapp.listeners.EventListener
import com.cathy.weatherapp.model.search.City
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_add_city.*
import kotlinx.android.synthetic.main.activity_add_city.iv_back
import kotlinx.android.synthetic.main.activity_add_city.progress_bar
import kotlinx.android.synthetic.main.activity_add_city.toolbar
import kotlinx.android.synthetic.main.activity_main.*


class AddCityActivity : AppCompatActivity(), EventListener{

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val REQUEST_CODE_PERMISSION = 1

    var adapter: CityAdapter = CityAdapter(this, emptyList())

    private lateinit var cityVM: CityVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_city)

        setViewModel()
        setViews()
    }

    private fun setViewModel(){
        cityVM = ViewModelProviders.of(this).get(CityVM::class.java)

        cityVM.getAddedCities().observe(this, Observer {
            adapter.updateData(it)
        })

        cityVM.getData().observe(this, Observer {
            if (it != null) {
                et_search.setAdapter(CityAutoCompleteAdapter(this, it.toMutableList()))
                et_search.showDropDown()
            }
        })

        cityVM.getProgress().observe(this, Observer {
            it?.let {progress ->
                progress_bar.visibility = progress
            }
        })

        cityVM.getMessage().observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }

    private fun setViews(){
        setActionBar(toolbar)

        rv_cities.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?
        rv_cities.adapter = adapter

        et_search.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val city = parent.getItemAtPosition(position) as City
            et_search.text.clear()
            cityVM.addCity(city)
        }

        et_search.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (v.text.toString().trim().isEmpty()) {
                    return@OnEditorActionListener false
                }
                cityVM.search(v.text.toString())
                true
            } else {
                false
            }
        })

        btn_location.setOnClickListener {
            getLocation()
        }

        btn_apply.setOnClickListener {
            finish()
        }

        Picasso.get()
            .load(R.drawable.morning)
            .placeholder(R.color.colorBack)
            .noFade()
            .fit()
            .centerCrop()
            .into(iv_back)
    }

    override fun onClick(city: City) {
        val builder = AlertDialog.Builder(this)
        builder
            .setTitle("Удалить город?")
            .setCancelable(true)
            .setPositiveButton("Удалить"){ _, _ ->
                cityVM.deleteCity(city)
            }
            .setNegativeButton("Отмена") { dialog, _ ->
                dialog.cancel() }
        val alert = builder.create()
        alert.show()
    }

    private fun getLocation(){
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val permissionStatus = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location : Location? ->
                    if (location == null){
                        turnOnGps()
                        return@addOnSuccessListener
                    } else{
                        val pair = Pair(location.latitude, location.longitude)
                        cityVM.search(pair)
                    }
                }
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE_PERMISSION)
        }
    }

    private fun turnOnGps(){
        val builder = AlertDialog.Builder(this)
        builder
            .setMessage("Для определения местоположения необходимо включить GPS")
            .setCancelable(true)
            .setPositiveButton("Включить"){ _, _ ->
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent);
            }
            .setNegativeButton("Отмена") { dialog, _ ->
                dialog.cancel() }
        val alert = builder.create()
        alert.show()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation()
            } else {
                Log.w("MainActivity", "Permission was denied")
            }
        }
    }

}
