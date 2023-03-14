package com.luminate.room_database

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.luminate.room_database.data.AppDatabase
import com.luminate.room_database.data.entity.User

class CreateActivity : AppCompatActivity() {

    private lateinit var save_bt : Button
    private lateinit var etTitle : EditText
    private lateinit var etDetail : EditText
    private lateinit var database : AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        etTitle = findViewById(R.id.tittle_et)
        etDetail = findViewById(R.id.detail_et)
        save_bt = findViewById(R.id.bt_save)
        database = AppDatabase.getInstance(applicationContext)

        save_bt.setOnClickListener {
            if (etTitle.text.isNotEmpty() && etDetail.text.isNotEmpty() )
            {
                database.userDao().insertAll(User(null, etTitle.text.toString(), etDetail.text.toString()))
                Toast.makeText(applicationContext, "Your data has been saved", Toast.LENGTH_SHORT).show()
                finish()
            }
            else
            {
                Toast.makeText(applicationContext, "Please fill all the data", Toast.LENGTH_SHORT).show()
            }
        }
    }
}