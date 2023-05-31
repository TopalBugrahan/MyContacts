package com.example.mycontacts.ui.school

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.mycontacts.CardDetail
import com.example.mycontacts.adapters.CardAdapter
import com.example.mycontacts.config.AppDatabase
import com.example.mycontacts.databinding.FragmentHomeBinding
import com.example.mycontacts.models.Card

class SchoolFragment : Fragment() {
    lateinit var school_list_view: ListView
    lateinit var db: AppDatabase
    private var cardList= mutableListOf<Card>()
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(SchoolViewModel::class.java)

        db = Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java,
            "appDataBase"
        ).build()

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        school_list_view=binding.schoolListView
        val textView: TextView = binding.textHome

        school_list_view.setOnItemClickListener { adapterView, view, i, l ->
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
            cardList=db.noteDao().findByGroup("School")
            activity?.runOnUiThread {
                val cardAdapter= CardAdapter(requireActivity(),cardList)
                school_list_view.adapter=cardAdapter
            }
        }
        Thread(run).start()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}