package com.caiqueponjjar.cuco;

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment


public class SecondFragment : Fragment(R.layout.activity_secondfragment) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


            val titulo = view.findViewById<EditText>(R.id.EdtTitulo).text
            val subtitulo = view.findViewById<EditText>(R.id.EdtSubtitulo).text
            val buttonShare = view.findViewById<Button>(R.id.ShareBtn)
        val buttonAdicionar = view.findViewById<Button>(R.id.AddBtn)
        buttonAdicionar.setOnClickListener{
        val fragment = FirstFragment();
        val bundle = Bundle().apply { putString("Titulo", titulo.toString())
            putString("Subtitulo", subtitulo.toString())
        }
        fragment.arguments = bundle
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, fragment).addToBackStack(null).commit()
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
    }
}
