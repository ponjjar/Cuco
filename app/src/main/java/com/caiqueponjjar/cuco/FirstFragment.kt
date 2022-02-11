package com.caiqueponjjar.cuco;

import android.graphics.Color
import android.graphics.PointF
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
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
    private lateinit var loadingList : ConstraintLayout
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
          //  val textInfo = arguments?.getString("key")
          //  val textView = view.findViewById<TextView>(R.id.textView)
          //  textView.text = textInfo
        var welcomeText = view.findViewById<TextView>(R.id.welcomeText)
        loadingList = view.findViewById<ConstraintLayout>(R.id.LoadingConstraint)
        var loadingImage = view.findViewById<ImageView>(R.id.LoadingImage)
        var anim: Animation = AnimationUtils.loadAnimation(
            activity, R.anim.floating
        )
        anim.repeatCount = Animation.INFINITE
        loadingImage.startAnimation(anim)
        var EyesBird = view.findViewById<TextView>(R.id.BirdEyes)
        var animEyes: Animation = AnimationUtils.loadAnimation(
            activity, R.anim.eyes
        )
        animEyes.repeatCount = Animation.INFINITE
        EyesBird.startAnimation(animEyes)


        welcomeText.text = "Olá, " + usuario().getUsername(requireActivity())

        val pullToRefresh: SwipeRefreshLayout = view.findViewById(R.id.pullToRefresh)
        pullToRefresh.setOnRefreshListener {
             // your code
            pullToRefresh.isRefreshing = false
        }
        listview = view.findViewById<RecyclerView>(R.id.list_item)
        listview.setItemViewCacheSize(7);
        listview.setHasFixedSize(true);
        listview.setDrawingCacheEnabled(true);
        listview.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_AUTO);

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

        var scrollonfinish: Boolean  = false;
        var adapter = ListAdapter( itemList, requireActivity());
        rootRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                println(error!!.message)
            }

            override fun onDataChange(snapshot: DataSnapshot) {

                var itemcountBefore = itemList.count()
                itemList.clear()
                for (postSnapshot in snapshot.children) {
                    //Carregando lista de dados
                    var subtitle = postSnapshot.child("subtitle").getValue(String::class.java)
                    var title = postSnapshot.child("title").getValue(String::class.java)
                    var itemColor = postSnapshot.child("color").getValue(Int::class.java)
                    var key = postSnapshot.child("key").getValue(String::class.java)
                    var category = postSnapshot.child("category").getValue(String::class.java)
                    if(category == "null"){
                        category = "-1";
                    }
                                itemList.add(
                                    Item(
                                        title.toString(),
                                        subtitle.toString(),
                                        itemColor?.toInt() ?: Color.parseColor("#E05F22"),
                                        key.toString(),
                                        getResources().getIdentifier(usuario().getIcons(category?.toInt() ?: 0) , "drawable", requireActivity().packageName)?: R.drawable.roundedconers,
                                    )
                                )

                }
                scrollonfinish = false
                System.out.println("Scroll on finish: " +itemList.count() +", " + itemcountBefore)
                if(itemList.count() > itemcountBefore){

                    scrollonfinish=true
                }
                if(itemList.count() == 0){

                    itemList.add(
                        Item(
                           "Tente anotar algo legal",
                            "Clique no botão laranja",
                            Color.parseColor("#E05F22"),
                            "CucoMessage",
                            R.drawable.logo4
                        )
                    )

                    listview.adapter = adapter
                }
                adapter = ListAdapter( itemList, requireActivity());
                loadingList.visibility = View.GONE
                if(listview.adapter == null) {
                    listview.adapter = adapter
                }else {
                    listview.adapter?.notifyDataSetChanged();
                    if(scrollonfinish == true){
                        listview.smoothScrollToPosition(itemList.count())
                    }
                }
                //listview.adapter = adapter
            }
        })
    }

    fun RecyclerView.smoothScrollToPosition(
        recyclerView: RecyclerView?,
        state: RecyclerView.State?, position: Int
    ) {
        val smoothScroller: LinearSmoothScroller = object : LinearSmoothScroller(context) {
            //This controls the direction in which smoothScroll looks
            //for your view
            override fun computeScrollVectorForPosition(targetPosition: Int): PointF? {

                    println("posicação:"+targetPosition)
                    return this
                        .computeScrollVectorForPosition(targetPosition)
            }

            //This returns the milliseconds it takes to
            //scroll one pixel.
            override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
                return 990f / displayMetrics.densityDpi
            }
        }
        smoothScroller.targetPosition = position
        this.layoutManager?.startSmoothScroll(smoothScroller)
    }
    override fun onStart() {
        super.onStart()
    }
}
