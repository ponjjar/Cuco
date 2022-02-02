package com.caiqueponjjar.cuco;

import android.animation.ArgbEvaluator
import android.animation.TimeAnimator
import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.caiqueponjjar.cuco.helper.usuario
import com.google.android.gms.common.ErrorDialogFragment.newInstance
import com.google.android.gms.common.SupportErrorDialogFragment.newInstance
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.FirebaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import java.lang.reflect.Array.newInstance

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

        listview.layoutManager =  LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
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

            override fun onDataChange(snapshot: DataSnapshot) {

                itemList.clear()
                for (postSnapshot in snapshot.children) {
                    //Carregando lista de dados
                    var subtitle = postSnapshot.child("subtitle").getValue(String::class.java)
                    var title = postSnapshot.child("title").getValue(String::class.java)
                    System.out.println("Email:" + userId + "| title:" + title)
                    itemList.add(Item(title.toString(), subtitle.toString()))
                }
                loadingList.visibility = View.GONE

                listview.adapter = parentFragmentManager?.let { ListAdapter( itemList, it) }
            }
        })
    }

    override fun onStart() {
        super.onStart()
    }
}
