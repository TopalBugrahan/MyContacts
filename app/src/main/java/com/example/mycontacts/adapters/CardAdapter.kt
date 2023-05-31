package com.example.mycontacts.adapters

import android.app.Activity

import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity

import com.example.mycontacts.R
import com.example.mycontacts.models.Card


class CardAdapter(private val context: Activity, private val cards: List<Card>):ArrayAdapter<Card>(
    context,
    R.layout.main_costum_list_view,
    cards
) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val rootView=context.layoutInflater.inflate(R.layout.main_costum_list_view,null)
        val profileName=rootView.findViewById<TextView>(R.id.profileName)
        val profileGroup=rootView.findViewById<TextView>(R.id.profileGroup)
        val profileNumber=rootView.findViewById<TextView>(R.id.profileNumber)
        val profileImage=rootView.findViewById<ImageView>(R.id.profileImage)

        val data = cards.get(position)

        if(data.sex=="Male"){
            profileImage.setImageResource(R.drawable.male);
        }
        else{
            profileImage.setImageResource(R.drawable.female);
        }
        profileName.text=data.name.toString()+" "+ data.surname.toString()
        profileGroup.text=data.grop.toString()
        profileNumber.text=data.phoneNumber.toString()
        return rootView
    }
}