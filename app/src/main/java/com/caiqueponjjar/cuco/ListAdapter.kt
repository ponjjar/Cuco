package com.caiqueponjjar.cuco

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import org.w3c.dom.Text
import java.util.ArrayList

class ListAdapter(val itemList: ArrayList<Item>) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {


    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
    //    val titleTextView = p0.findViewById<TextView>(R.id.title_textview)
     //   val authorTextView = findViewById<TextView>(R.id.subtitle_textview)
        p0.titleTextView.text = itemList[p1].itemTitle
        p0.subtitleTextView.text = itemList[p1].itemSubtitle
        //p0?.txtTitle?.text = androidVersionList[p1].codeName
        //p0?.txtContent?.text = "Version : ${androidVersionList[p1].versionName}, Api Name : ${androidVersionList[p1].apiLevel}"
        //p0?.image.setImageResource(androidVersionList[p1].imgResId!!)
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.activity_listitem, p0, false)
        return ViewHolder(v);
    }
    /*override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
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
    }*/
    override fun getItemCount(): Int {
        return itemList.size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         var titleTextView : TextView = itemView.findViewById<TextView>(R.id.title_textview)
         var subtitleTextView : TextView = itemView.findViewById<TextView>(R.id.subtitle_textview)

        //titleTextView.text = itemList[position].itemTitle
        //authorTextView.text = itemList[position].itemSubtitle
    }
}