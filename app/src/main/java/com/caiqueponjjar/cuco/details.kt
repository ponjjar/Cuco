package com.caiqueponjjar.cuco

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [details.newInstance] factory method to
 * create an instance of this fragment.
 */
class details : Fragment(R.layout.activity_firstfragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if( arguments?.getString("Titulo") != null) {
            //view.findViewById<TextView>(R.id.titleText).text = arguments?.getString("Titulo").toString()
            //view.findViewById<TextView>(R.id.subtitleText).text = arguments?.getString("Subtitulo").toString()
        }
    }
}