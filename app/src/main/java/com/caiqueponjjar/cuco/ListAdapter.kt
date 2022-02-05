package com.caiqueponjjar.cuco

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.caiqueponjjar.cuco.helper.usuario


class ListAdapter(val itemList: ArrayList<Item>, val activity: Activity) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    val itensListed = ArrayList<String>()
    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        //    val titleTextView = p0.findViewById<TextView>(R.id.title_textview)
        //   val authorTextView = findViewById<TextView>(R.id.subtitle_textview)
        p0.titleTextView.text = itemList[p1].itemTitle
        p0.subtitleTextView.text = itemList[p1].itemSubtitle
        if (itemList[p1].itemKey == "CucoMessage") {
            var anim = AnimationUtils.loadAnimation(
                activity, R.anim.fadein
            )

            anim.duration = 500

            p0.itemView.startAnimation(anim)

        } else {
            if (!itensListed.contains(itemList[p1].itemKey)) {
                itensListed.add(itemList[p1].itemKey)
                var anim: Animation = AnimationUtils.loadAnimation(
                    activity, android.R.anim.slide_in_left
                )
                if (itemList.size - p1 > 1) {
                    if (itemList.size - p1 > 7) {
                        anim = AnimationUtils.loadAnimation(
                            activity, R.anim.toptodown
                        )
                        anim.duration = 500
                        anim.startOffset = 200;
                    } else {

                        anim.duration = ((itemList.size - p1) * 100).toLong().toLong()
                        anim.startOffset = (((itemList.size - p1) * 100) - 50).toLong();
                    }
                } else {
                    anim.duration = 400
                    anim.startOffset = 0
                }
                p0.itemView.startAnimation(anim)
            }

        var x1: Float? =null
        var x2: Float? =null
        p0.itemView.setOnTouchListener { e, motionEvent ->



            val min_distance: Int  = 40
            var deltaX = 0.0f
            when (motionEvent.action) {

                MotionEvent.ACTION_DOWN -> {
                    //when user touch down
                    x1 = motionEvent.getX();
                }
                MotionEvent.ACTION_MOVE -> {
                    x2 = motionEvent.getX()
                    if(Math.abs(deltaX) <= min_distance  && deltaX <= 0) {
                        deltaX = x2!! - x1!!
                    }
                    if (Math.abs(deltaX) > min_distance && deltaX > 0) {

                        println(deltaX)
                        var anim: Animation = AnimationUtils.loadAnimation(
                            activity, android.R.anim.slide_out_right
                        )
                        anim.duration = 400
                        if(anim.hasStarted() == false) {
                            p0.itemView.startAnimation(anim)
                        }
                        anim.setAnimationListener(object : Animation.AnimationListener {
                            override fun onAnimationStart(arg0: Animation) {}
                            override fun onAnimationRepeat(arg0: Animation) {}
                            override fun onAnimationEnd(arg0: Animation) {
                                usuario().deleteData(activity, itemList[p1].itemKey)
                            }
                        })
                        //  Toast.makeText(this, "left2right swipe", Toast.LENGTH_SHORT).show()
                    }
                }
                MotionEvent.ACTION_UP -> {
                    //when user touch release
                    x2 = motionEvent.getX()
                    var deltaX = x2!! - x1!!
                    if (Math.abs(deltaX) > min_distance && deltaX > 0) {

                        println(deltaX)
                        var anim: Animation = AnimationUtils.loadAnimation(
                            activity, android.R.anim.slide_out_right
                        )
                        anim.duration =  500
                        p0.itemView.startAnimation(anim)
                        anim.setAnimationListener(object : Animation.AnimationListener {
                            override fun onAnimationStart(arg0: Animation) {}
                            override fun onAnimationRepeat(arg0: Animation) {}
                            override fun onAnimationEnd(arg0: Animation) {
                                usuario().deleteData(activity, itemList[p1].itemKey)
                            }
                        })
                      //  Toast.makeText(this, "left2right swipe", Toast.LENGTH_SHORT).show()
                    } else {
                        // consider as something else - a screen tap for example
                        p0.itemView.callOnClick()
                    }
                }
            }
            true
        }

        p0.itemView.setOnClickListener {
            println("Clicado:" + itemList[p1].itemTitle)
            val builder = AlertDialog.Builder(activity)
            builder.setTitle(itemList[p1].itemTitle)
            builder.setMessage(itemList[p1].itemSubtitle)

            builder.setPositiveButton("voltar") { dialog, which ->
                //Toast.makeText(applicationContext,"continuar",Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            builder.setNegativeButton("deletar") { dialog, which ->
                //Toast.makeText(applicationContext,"continuar",Toast.LENGTH_SHORT).show()
                var anim: Animation = AnimationUtils.loadAnimation(
                    activity, android.R.anim.slide_out_right
                )
                anim.duration =  500
                p0.itemView.startAnimation(anim)
                anim.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(arg0: Animation) {}
                    override fun onAnimationRepeat(arg0: Animation) {}
                    override fun onAnimationEnd(arg0: Animation) {
                        usuario().deleteData(activity, itemList[p1].itemKey)
                    }
                })


            }
            builder.setNeutralButton(
                "compartilhar"
            ) {  dialog, which ->
                val sendIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(
                        Intent.EXTRA_TEXT,
                        "" + itemList[p1].itemTitle + ": " + itemList[p1].itemSubtitle
                    )
                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, null)
                activity.startActivity(shareIntent)
            }
            val dialog: AlertDialog = builder.create()

            dialog.show()
            val buttonNegative: Button = dialog.getButton(DialogInterface.BUTTON_NEGATIVE)
            buttonNegative.setTextColor(Color.parseColor("#960909"))
            val buttonNeutral: Button = dialog.getButton(DialogInterface.BUTTON_NEUTRAL)
            buttonNeutral.setTextColor(Color.parseColor("#009c3a"))
            val buttonPositive: Button = dialog.getButton(DialogInterface.BUTTON_POSITIVE)
            buttonPositive.setTextColor(Color.parseColor("#00707a"))
        }
            //buttonPositive.setTextColor(BLUE)
          //  buttonbackground1.setBackgroundDrawable( activity.resources.getDrawable(R.drawable.roundedconers))
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