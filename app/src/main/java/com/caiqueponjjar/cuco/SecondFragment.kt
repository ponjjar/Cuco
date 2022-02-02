package com.caiqueponjjar.cuco;

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.caiqueponjjar.cuco.helper.usuario





public class SecondFragment : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var myView = inflater!!.inflate(R.layout.activity_secondfragment
            , container, false)

        val titulo = myView.findViewById<EditText>(R.id.EdtTitulo).text
            val subtitulo = myView.findViewById<EditText>(R.id.EdtSubtitulo).text
            val buttonShare = myView.findViewById<Button>(R.id.ShareBtn)
        val buttonAdicionar = myView.findViewById<Button>(R.id.AddBtn)
        buttonAdicionar.setOnClickListener{
            usuario().commitNewData(requireActivity(), titulo.toString(),subtitulo.toString())
       // val fragment = FirstFragment();
      //val bundle = Bundle().apply { putString("Titulo", titulo.toString())
        //    putString("Subtitulo", subtitulo.toString())
       // }
        //fragment.arguments = bundle
     //   parentFragmentManager.beginTransaction() .replace(R.id.fragment_container_view, fragment).addToBackStack(null).commit()
            dismiss()
        }

        buttonShare.setOnClickListener {
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "" + titulo + ": " + subtitulo)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

        return myView


    }

    override fun onStart() {
        super.onStart()
        getDialog()!!.getWindow()!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }
}
