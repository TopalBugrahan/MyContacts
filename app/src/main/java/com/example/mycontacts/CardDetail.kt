package com.example.mycontacts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.room.Room
import com.example.mycontacts.adapters.CardAdapter
import com.example.mycontacts.config.AppDatabase

class CardDetail : AppCompatActivity() {
    lateinit var profileImage:ImageView
    lateinit var updateName:EditText
    lateinit var updateSurname:EditText
    lateinit var updatePhoneNumber:EditText
    lateinit var updateGroup:Spinner
    lateinit var updateSex:Spinner
    lateinit var btnDelete:Button
    lateinit var btnUpdate:Button
    lateinit var db:AppDatabase

    var globalGroupsArray= mutableListOf<String>("School","Family","Friend","Agalar","Work")
    var globalSexArray= mutableListOf<String>("Male","Female")

    lateinit var selectedGroup:String
    lateinit var  selectedSex:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_detail)

        profileImage=findViewById(R.id.profileImage)
        updateName=findViewById(R.id.updateName)
        updateSurname=findViewById(R.id.updateSurname)
        updatePhoneNumber=findViewById(R.id.updatePhoneNumber)
        updateGroup=findViewById(R.id.updateGroup)
        updateSex=findViewById(R.id.updateSex)
        btnDelete=findViewById(R.id.btnDelete)
        btnUpdate=findViewById(R.id.btnUpdate)

       db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "appDataBase"
        ).build()

        val cid=intent.getIntExtra("cid",1000000)
        val name=intent.getStringExtra("name").toString()
        val surname=intent.getStringExtra("surname").toString()
        val phoneNumber=intent.getStringExtra("phoneNumber").toString()
        val group=intent.getStringExtra("group").toString()
        val sex=intent.getStringExtra("sex").toString()

        selectedGroup=group
        selectedSex=sex

        if (globalGroupsArray.contains(group)) {
            globalGroupsArray.remove(group)
            if (globalGroupsArray.isNotEmpty()) {
                val firstElement = globalGroupsArray[0]
                globalGroupsArray.removeAt(0)
                globalGroupsArray.add(0, group)
                globalGroupsArray.add(1, firstElement)
            }
        }
        if (globalSexArray.contains(sex)) {
            globalSexArray.remove(sex)
            if (globalSexArray.isNotEmpty()) {
                val firstElement = globalSexArray[0]
                globalSexArray.removeAt(0)
                globalSexArray.add(0, sex)
                globalSexArray.add(1, firstElement)
            }
        }

        updateName.setText(name)
        updateSurname.setText(surname)
        updatePhoneNumber.setText(phoneNumber)

        val spinnerGroupAdapter= ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,globalGroupsArray)
        val spinnerSexAdapter= ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,globalSexArray)
        updateGroup.adapter=spinnerGroupAdapter
        updateSex.adapter=spinnerSexAdapter

        if(sex=="Male"){
            profileImage.setImageResource(R.drawable.male);
        }
        else{
            profileImage.setImageResource(R.drawable.female);
        }
        updateGroup.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedGroup=globalGroupsArray.get(p2)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(applicationContext,"not select",Toast.LENGTH_LONG).show()
            }
        }

        updateSex.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedSex=globalSexArray.get(p2)

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(applicationContext,"not select",Toast.LENGTH_LONG).show()
            }
        }


        btnDelete.setOnClickListener {
            var run = Runnable {
                db.noteDao().deleteByCid(cid)
                finish()
            }
            Thread(run).start()
        }

        btnUpdate.setOnClickListener {
            val newName=updateName.text.toString()
            val newSurname=updateSurname.text.toString()
            val newphoneNumber=updatePhoneNumber.text.toString()
            var run = Runnable {
                db.noteDao().update(newName,newSurname,newphoneNumber,selectedGroup,selectedSex,cid)
                finish()
            }
            Thread(run).start()
        }

    }
}