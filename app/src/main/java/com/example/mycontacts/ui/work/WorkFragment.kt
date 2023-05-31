package com.example.mycontacts.ui.work

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
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

class WorkFragment : Fragment() {
    lateinit var work_list_view: ListView
    lateinit var db: AppDatabase
    private var cardList= mutableListOf<Card>()
    companion object {
        fun newInstance() = WorkFragment()
    }

    private lateinit var viewModel: WorkViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_work,container,false)

        work_list_view=root.findViewById(R.id.work_list_view)

        db = Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java,
            "appDataBase"
        ).build()

        work_list_view.setOnItemClickListener { adapterView, view, i, l ->
            val data = cardList.get(i)
            val intent= Intent(requireContext(), CardDetail::class.java)
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
            cardList=db.noteDao().findByGroup("Work")
            activity?.runOnUiThread {
                val cardAdapter= CardAdapter(requireActivity(),cardList)
                work_list_view.adapter=cardAdapter
            }
        }
        Thread(run).start()

    }


}