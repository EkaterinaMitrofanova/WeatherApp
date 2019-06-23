package com.cathy.weatherapp.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.cathy.weatherapp.model.search.City
import android.R
import android.view.LayoutInflater
import android.widget.*


class CityAutoCompleteAdapter(mContext: Context, private val mResults: MutableList<City>)
    : ArrayAdapter<City>(mContext, R.layout.simple_dropdown_item_1line, mResults) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        view = if (convertView == null) {
            val inflater = LayoutInflater.from(context)
            inflater.inflate(R.layout.simple_dropdown_item_1line, parent, false)
        } else{
            convertView
        }
        val city = getItem(position)
        (view.findViewById(R.id.text1) as TextView).text = String.format("%s, %s", city?.name, city?.address)

        return view
    }

    override fun getItem(position: Int): City? {
        return mResults[position]
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = mResults.size ?: 0

}