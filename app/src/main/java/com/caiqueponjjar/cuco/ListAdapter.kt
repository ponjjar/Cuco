package com.caiqueponjjar.cuco

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import java.util.ArrayList

class ListAdapter(private val context : Activity, private val itemList: ArrayList<Item>) : ArrayAdapter<Item>(context,R.layout.activity_listitem,itemList){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        //val phraseIndex = position
        convertView = LayoutInflater.from(context).inflate(
            R.layout.activity_listitem,
            null
        )
        val titleTextView = convertView.findViewById<TextView>(R.id.title_textview)
        val authorTextView = convertView.findViewById<TextView>(R.id.subtitle_textview)

        titleTextView.text = itemList[position].itemTitle
        authorTextView.text = itemList[position].itemSubtitle
        return convertView
    }
}