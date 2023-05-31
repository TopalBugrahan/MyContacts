package com.example.mycontacts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.room.Room
import com.example.mycontacts.config.AppDatabase
import com.example.mycontacts.models.Card
import com.google.android.material.snackbar.Snackbar

class CreateCard : AppCompatActivity() {
    lateinit var addName: EditText
    lateinit var addSurname:EditText
    lateinit var addPhoneNumber:EditText
    lateinit var addSex:Spinner
    lateinit var addGroup:Spinner
    lateinit var btnAddProfile:Button
    var globalGroupsArray= mutableListOf<String>("School","Family","Friend","Agalar","Work")
    var globalSexArray= mutableListOf<String>("Male","Female")
    private var selectedGroup="School"
    private var selectedSex="Male"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_card)

        addName=findViewById(R.id.addName)
        addSurname=findViewById(R.id.addSurname)
        addPhoneNumber=findViewById(R.id.addPhoneNumber)
        addSex=findViewById(R.id.addSex)
        addGroup=findViewById(R.id.addGroup)
        btnAddProfile=findViewById(R.id.btnAddProfile)

        val db = Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "appDataBase"
        ).build()

        val spinnerGroupAdapter=ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,globalGroupsArray)
        val spinnerSexAdapter=ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,globalSexArray)

        addGroup.adapter=spinnerGroupAdapter
        addSex.adapter=spinnerSexAdapter

        addGroup.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedGroup=globalGroupsArray.get(p2)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(applicationContext,"not select",Toast.LENGTH_LONG).show()
            }
        }

        addSex.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedSex=globalSexArray.get(p2)

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(applicationContext,"not select",Toast.LENGTH_LONG).show()
            }
        }

        btnAddProfile.setOnClickListener {
            val name=addName.text.toString()
            val surname=addSurname.text.toString()
            val phoneNumber=addPhoneNumber.text.toString()
            if(name!=""&&surname!=""&&phoneNumber!=""){
                var run = Runnable {
                    val card= Card(null,name,surname,selectedGroup,phoneNumber,selectedSex)
                    val status=db.noteDao().insert(card)
                    Snackbar.make(it, "Account added successfly", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
                }
                Thread(run).start()
                //finish()
            }
            else{
                Toast.makeText(applicationContext,"Please fill in the blanks ",Toast.LENGTH_LONG).show()
            }

        }

    }
}