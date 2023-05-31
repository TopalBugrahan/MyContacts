package com.example.mycontacts.ui.besti

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.room.Room
import com.example.mycontacts.CardDetail
import com.example.mycontacts.R
import com.example.mycontacts.adapters.CardAdapter
import com.example.mycontacts.config.AppDatabase
import com.example.mycontacts.models.Card
import com.example.mycontacts.ui.main.MainFragment

class Besti : Fragment() {

    lateinit var besti_list_view:ListView
    lateinit var db: AppDatabase
    companion object {
        fun newInstance() = Besti()
    }
    private var cardList= mutableListOf<Card>()
    private lateinit var viewModel: BestiViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_besti,container,false)

        besti_list_view=root.findViewById(R.id.besti_list_view)

        db = Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java,
            "appDataBase"
        ).build()

        besti_list_view.setOnItemClickListener { adapterView, view, i, l ->
            val data = cardList.get(i)
            val intent=Intent(requireContext(),CardDetail::class.java)
            intent.putExtra("name",data.name)
            intent.putExtra("surname",data.surname)
            intent.putExtra("phoneNumber",data.phoneNumber)
            intent.putExtra("sex",data.sex)
            intent.putExtra("group",data.grop)
            intent.putExtra("cid",data.cid)
            startActivity(intent)
        }
        return root
    }

    override fun onStart() {
        super.onStart()
        var run = Runnable {
            cardList=db.noteDao().findByGroup("Agalar")
            activity?.runOnUiThread {
                val cardAdapter=CardAdapter(requireActivity(),cardList)
                besti_list_view.adapter=cardAdapter
            }
        }
        Thread(run).start()

    }


}