package com.cathy.weatherapp.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.cathy.weatherapp.R
import com.cathy.weatherapp.adapter.WeatherAdapter
import com.cathy.weatherapp.viewmodel.WeatherVM
import kotlinx.android.synthetic.main.activity_main.*
import com.cathy.weatherapp.listeners.EventListener
import com.cathy.weatherapp.model.search.City
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso


class MainActivity : AppCompatActivity() , EventListener{

    private lateinit var weatherVM: WeatherVM
    private val adapter = WeatherAdapter(emptyList(), false, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setViewModel()
        setViews()
    }

    private fun setViewModel(){
        weatherVM = ViewModelProviders.of(this).get(WeatherVM::class.java)

        weatherVM.getMessage().observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        weatherVM.getProgress().observe(this, Observer {
            it?.let {progress ->
                swipe_layout.isRefreshing = progress
            }
        })

        weatherVM.getWeather().observe(this, Observer {
            adapter.setData(it)
            if (it.isEmpty()){
                closeEditMode()
            }
        })
    }

    private fun setViews(){
        rv_weather.layoutManager = LinearLayoutManager(this)
        rv_weather.adapter = adapter

        swipe_layout.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorAccent))

        btn_add.setOnClickListener {
            startActivity(Intent(this, AddCityActivity::class.java))
        }

        edit.setOnClickListener {view ->
            if (view.tag == "edit") {
                adapter.setEdit(true)
                view.background = resources.getDrawable(R.drawable.ic_done, null)
                view.tag = "done"
            } else{
                adapter.setEdit(false)
                view.background = resources.getDrawable(R.drawable.ic_edit, null)
                view.tag = "edit"
            }

        }

        swipe_layout.setOnRefreshListener {
            weatherVM.update()
        }

        Picasso.get()
            .load(R.drawable.morning)
            .noFade()
            .placeholder(R.color.colorBack)
            .fit()
            .centerCrop()
            .into(iv_back)
    }

    override fun onClick(city: City) {
        weatherVM.deleteCity(city)
    }

    override fun onBackPressed() {
        if (closeEditMode()){
            return
        }
        super.onBackPressed()
    }

    private fun closeEditMode(): Boolean{
        if (edit.tag == "done") {
            adapter.setEdit(false)
            edit.background = resources.getDrawable(R.drawable.ic_edit, null)
            edit.tag = "edit"
            return  true
        }
        return false
    }
}
