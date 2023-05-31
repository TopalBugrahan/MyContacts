package com.example.mycontacts.ui.main

import android.app.Activity
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
import android.widget.EditText
import android.widget.ListView
import androidx.room.Room
import com.example.mycontacts.CardDetail
import com.example.mycontacts.R
import com.example.mycontacts.adapters.CardAdapter
import com.example.mycontacts.config.AppDatabase
import com.example.mycontacts.databinding.FragmentMainBinding
import com.example.mycontacts.models.Card
import com.example.mycontacts.ui.work.WorkFragment
import com.example.mycontacts.ui.work.WorkViewModel

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }
    lateinit var search_edit_text:EditText
    private var cardList= mutableListOf<Card>()
    private lateinit var viewModel: MainViewModel
    lateinit var db:AppDatabase
    lateinit var main_list_view:ListView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main,container,false)

        search_edit_text=root.findViewById(R.id.search_edit_text)
        main_list_view=root.findViewById(R.id.main_list_view)

        db = Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java,
            "appDataBase"
        ).build()

        main_list_view.setOnItemClickListener { adapterView, view, i, l ->
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
            cardList=db.noteDao().lastTenCard()
            activity?.runOnUiThread {
                val cardAdapter=CardAdapter(requireActivity(),cardList)
                main_list_view.adapter=cardAdapter
            }
        }
        Thread(run).start()

        search_edit_text.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                var searchData=s.toString()
                if(searchData.length<1){
                    var run = Runnable {
                        cardList=db.noteDao().lastTenCard()
                        activity?.runOnUiThread {
                            val cardAdapter=CardAdapter(requireActivity(),cardList)
                            main_list_view.adapter=cardAdapter
                        }
                    }
                    Thread(run).start()
                }
                else{
                    searchData="%"+searchData+"%"
                    var run = Runnable {
                        cardList=db.noteDao().searchName(searchData)
                        Log.d("data",cardList.toString())
                        activity?.runOnUiThread {
                            val cardAdapter=CardAdapter(requireActivity(),cardList)
                            main_list_view.adapter=cardAdapter
                        }
                    }
                    Thread(run).start()
                }
            }
        })


    }



}