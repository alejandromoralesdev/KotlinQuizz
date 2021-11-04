package com.example.kotlinquizz.views

import android.icu.util.LocaleData
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinquizz.DataHolder
import com.example.kotlinquizz.R
import com.example.kotlinquizz.adapters.MainAdapter
import com.example.kotlinquizz.databinding.FragmentReviewBinding
import com.example.kotlinquizz.databinding.FragmentScoresBinding
import com.example.kotlinquizz.models.Game
import io.paperdb.Paper

class ScoresFragment : Fragment() {

    private var _binding: FragmentScoresBinding? = null
    private val b get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScoresBinding.inflate(inflater, container, false)
        val view = b.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val games = Paper.book().read<ArrayList<Game>>(DataHolder.KEY_GAMES, arrayListOf())

        val mAdapter = MainAdapter(games) { valor ->
            Log.v("Prueba", "Probando que se haya guardado bien el game: $valor")
        }
        _binding?.scoreRecyclerView?.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        _binding?.scoreRecyclerView?.adapter = mAdapter
    }
}