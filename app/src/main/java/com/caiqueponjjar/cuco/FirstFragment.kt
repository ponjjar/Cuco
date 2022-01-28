package com.caiqueponjjar.cuco;

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.caiqueponjjar.cuco.helper.usuario
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FirstFragment : Fragment(R.layout.activity_firstfragment){
    private lateinit var itemList : ArrayList<Item>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

          //  val textInfo = arguments?.getString("key")
          //  val textView = view.findViewById<TextView>(R.id.textView)
          //  textView.text = textInfo
        var welcomeText = view.findViewById<TextView>(R.id.welcomeText)
        welcomeText.text = "Olá, " + usuario().getUsername()

        val FloatButton = view.findViewById<FloatingActionButton>(R.id.floatingActionButton)
        FloatButton.setOnClickListener {
            val nextFrag = SecondFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, nextFrag, "findThisFragment")
                .addToBackStack(null)
                .commit()
        }


        val listview = view.findViewById<RecyclerView>(R.id.list_item)
        //Configurando array adapter para criação de lista...

        itemList = ArrayList<Item>()
        itemList.add(Item("Comprar macarrão", "Para fazer yakisoba"))

        if(! arguments?.getString("Titulo").equals(null)){
            itemList.add(Item(arguments?.getString("Titulo").toString(),
                arguments?.getString("Subtitulo").toString()
            ))
            listview.adapter =ListAdapter( itemList)
        }

        listview.adapter = ListAdapter( itemList)

        listview.layoutManager =  LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
    }

        //System.out.println(adapter.toString());
        //listview.setAdapter(adapter)
        //getting string arrays from resource
    }