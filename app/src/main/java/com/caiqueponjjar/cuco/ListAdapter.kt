package com.caiqueponjjar.cuco

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings.System.putString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.caiqueponjjar.cuco.helper.usuario
import org.w3c.dom.Text
import java.util.ArrayList

class ListAdapter(val itemList: ArrayList<Item>, val activity: Activity) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {


    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
    //    val titleTextView = p0.findViewById<TextView>(R.id.title_textview)
     //   val authorTextView = findViewById<TextView>(R.id.subtitle_textview)
        p0.titleTextView.text = itemList[p1].itemTitle
        p0.subtitleTextView.text = itemList[p1].itemSubtitle
        p0.itemView.setOnClickListener {
            System.out.println("Clicado:" + itemList[p1].itemTitle)
            val builder = AlertDialog.Builder(activity)
            builder.setTitle(itemList[p1].itemTitle)
            builder.setMessage(itemList[p1].itemSubtitle)
            builder.setPositiveButton("voltar") { dialog, which ->
                //Toast.makeText(applicationContext,"continuar",Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            builder.setNegativeButton("deletar") { dialog, which ->
                //Toast.makeText(applicationContext,"continuar",Toast.LENGTH_SHORT).show()
               // usuario().deleteData(context, titulo.toString(),subtitulo.toString())
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
           /*  val fragment = details();
            val bundle = Bundle().apply {
                putString("Titulo", itemList[p1].itemTitle)
               putString("Subtitulo", itemList[p1].itemSubtitle)
             }
            fragment.arguments = bundle
            parentFragmentManager.beginTransaction().add(R.id.fragment_container_view, fragment).addToBackStack(null).commit()*/
        }
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
         var cardView : CardView = itemView.findViewById(R.id.card_pertanyaan)

        //titleTextView.text = itemList[position].itemTitle
        //authorTextView.text = itemList[position].itemSubtitle
    }
}