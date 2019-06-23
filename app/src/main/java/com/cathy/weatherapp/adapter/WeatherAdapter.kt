package com.cathy.weatherapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cathy.weatherapp.R
import com.cathy.weatherapp.listeners.EventListener
import com.cathy.weatherapp.model.search.City
import kotlinx.android.synthetic.main.list_item_weather.view.*
import java.util.*
import java.text.SimpleDateFormat


class WeatherAdapter(private var data: List<City>?, private var edit: Boolean, val listener: EventListener)
    : RecyclerView.Adapter<WeatherAdapter.Holder>(){

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): Holder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return Holder(layoutInflater.inflate(R.layout.list_item_weather, parent, false))
    }

    override fun getItemCount(): Int = data?.size ?: 0

    override fun onBindViewHolder(holder: Holder, position: Int) {
        if (edit){
            holder.itemView.delete.visibility = View.VISIBLE
            holder.itemView.temp.visibility = View.INVISIBLE
        } else{
            holder.itemView.delete.visibility = View.GONE
            holder.itemView.temp.visibility = View.VISIBLE
        }
        data?.let {
            val city = it[position]
            holder.itemView.name.text = city.name
            holder.itemView.country.text = city.address
            holder.itemView.temp.text = String.format("%d \u2103", city.main?.temp?.toInt())
            holder.itemView.wind.text =
                String.format("Ветер %.1f км/ч, %s", city.wind?.speed, city.wind?.toTextualDescription())
            if (city.dt != null){
                val date = GregorianCalendar()
                date.timeInMillis = city.dt!! * 1000

                val yesterday = GregorianCalendar()
                yesterday.set(Calendar.HOUR, 0)
                yesterday.set(Calendar.MINUTE, 0)
                yesterday.set(Calendar.SECOND, 0)

                if (date.after(yesterday)) {
                    val df = SimpleDateFormat("HH:mm", Locale.getDefault())
                    holder.itemView.date.text = df.format(date.time)
                } else{
                    val df = SimpleDateFormat("d MMM", Locale.getDefault())
                    holder.itemView.date.text = df.format(date.time)
                }
            }
        }
    }

    fun setEdit(isEdit: Boolean){
        edit = isEdit
        notifyDataSetChanged()
    }

    fun setData(data: List<City>?){
        this.data = data
        notifyDataSetChanged()
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.delete.setOnClickListener {
                itemView.delete.setOnClickListener {
                    data?.get(adapterPosition)?.let {
                            it1 -> listener.onClick(it1) }
                }
            }
        }
    }
}