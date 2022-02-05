package com.caiqueponjjar.cuco;

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.caiqueponjjar.cuco.helper.usuario
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class FirstFragment : Fragment(R.layout.activity_firstfragment){
    private lateinit var itemList : ArrayList<Item>
    private lateinit var listview : RecyclerView
    private lateinit var loadingList : ProgressBar
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
          //  val textInfo = arguments?.getString("key")
          //  val textView = view.findViewById<TextView>(R.id.textView)
          //  textView.text = textInfo
        var welcomeText = view.findViewById<TextView>(R.id.welcomeText)
        loadingList = view.findViewById<ProgressBar>(R.id.LoadingList)
        welcomeText.text = "Olá, " + usuario().getUsername(requireActivity())

        listview = view.findViewById<RecyclerView>(R.id.list_item)
        val FloatButton = view.findViewById<FloatingActionButton>(R.id.floatingActionButton)
        FloatButton.setOnClickListener {
         /*   val nextFrag = SecondFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, nextFrag, "findThisFragment")
                .addToBackStack(null)
                .commit()*/

         //val editNameDialogFragment: EditNameDialogFragment = EditNameDialogFragment.newInstance("Some Title")
            val pop = SecondFragment()
            val fm = requireActivity().supportFragmentManager

            if (fm != null) {
                pop.show(fm, "name")
             //   fm.setStyle(DialogFragment.STYLE_NO_FRAME, 0);
                pop.dialog?.getWindow()?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            }
            }




        /*if(! arguments?.getString("Titulo").equals(null)){
            itemList.add(Item(arguments?.getString("Titulo").toString(),
                arguments?.getString("Subtitulo").toString()
            ))
            listview.adapter =ListAdapter( itemList)
        }

        listview.adapter = ListAdapter( itemList)*/

        var mLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,true)
        mLayoutManager.reverseLayout = true;
        mLayoutManager.stackFromEnd = true;

        listview.layoutManager =mLayoutManager

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Configurando array adapter para criação de lista...
        var userId = activity?.let { usuario().getUniqueId(it) }
        val rootRef = Firebase.database.reference.child("users").child(userId!!)

        itemList = ArrayList<Item>()

        rootRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                println(error!!.message)
            }
            var adapter = ListAdapter( itemList, requireActivity());
            override fun onDataChange(snapshot: DataSnapshot) {
                itemList.clear()
                for (postSnapshot in snapshot.children) {
                    //Carregando lista de dados
                    var subtitle = postSnapshot.child("subtitle").getValue(String::class.java)
                    var title = postSnapshot.child("title").getValue(String::class.java)
                    var key = postSnapshot.child("key").getValue(String::class.java)

                                itemList.add(
                                    Item(
                                        title.toString(),
                                        subtitle.toString(),
                                        key.toString()
                                    )
                                )

                }
                if(itemList.count() == 0){

                    itemList.add(
                        Item(
                           "Tente anotar algo legal",
                            "Clique no botão laranja",
                            "CucoMessage"
                        )
                    )
                    itemList.add(
                        Item(
                            "Esse lugar está vazio",
                            "Vamos animar isso!",
                            "CucoMessage"
                        )
                    )
                }

                loadingList.visibility = View.GONE
                listview.adapter = adapter
            }
        })
    }

    override fun onStart() {
        super.onStart()
    }
}
