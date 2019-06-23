package com.cathy.weatherapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cathy.weatherapp.R
import com.cathy.weatherapp.listeners.EventListener
import com.cathy.weatherapp.model.search.City
import kotlinx.android.synthetic.main.list_item_city.view.*

class CityAdapter(private val listener: EventListener,
                  private var data: List<City>?)
    : RecyclerView.Adapter<CityAdapter.Holder>(){

    fun updateData(data: List<City>?){
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): Holder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return Holder(layoutInflater.inflate(R.layout.list_item_city, parent, false))
    }

    override fun getItemCount(): Int = data?.size ?: 0

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val weatherData = data?.get(position)
        holder.itemView.name.text = weatherData?.name
        holder.itemView.country.text = weatherData?.address
    }


    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.remove.setOnClickListener {
                data?.get(adapterPosition)?.let {
                        it1 -> listener.onClick(it1) }
            }
        }
    }
}