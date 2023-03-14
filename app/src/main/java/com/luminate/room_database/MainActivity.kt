package com.luminate.room_database

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.luminate.room_database.data.AppDatabase
import com.luminate.room_database.data.entity.User

class MainActivity : AppCompatActivity() {


    private lateinit var fab: FloatingActionButton
    private lateinit var rv : RecyclerView
    private var list = mutableListOf<User>()
    private lateinit var adapter: Adapter
    private lateinit var database: AppDatabase
    private lateinit var etSearch: EditText
    private lateinit var btnSearch: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = Adapter(list)
        rv = findViewById(R.id.recycler_view)
        fab = findViewById(R.id.fab)
        database = AppDatabase.getInstance(applicationContext)
        etSearch = findViewById(R.id.et_search)
        btnSearch = findViewById(R.id.bt_search)

        adapter.setDialog(object : Adapter.Dialog{
            override fun onClick(position: Int) {
                val dialog = AlertDialog.Builder(this@MainActivity)
                dialog.setTitle(list[position].title)
                dialog.setItems(R.array.dialog_option, DialogInterface.OnClickListener{dialog, which->
                    if (which==0)
                    {
                        val intent  = Intent(this@MainActivity, UpdateActivity:: class.java)
                        intent.putExtra("id", list[position].uid)
                        startActivity(intent)
                    }
                    else if (which == 1)
                    {
                        //to delete data uding DAO
                        database.userDao().delete(list[position])
                        //to refresh the list after you delete the chosen data or the list will not change
                        getData()
                    }
                    else
                    {
                        //to close the dialog
                        dialog.dismiss()
                    }
                })
                val  dialogView = dialog.create()
                dialogView.show()
            }

        })
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(applicationContext, VERTICAL, false)
        rv.addItemDecoration(DividerItemDecoration(applicationContext, VERTICAL))

        fab.setOnClickListener {
            val i = Intent(this, CreateActivity::class.java)
            startActivity(i)
        }

        btnSearch.setOnClickListener {
            if (etSearch.text.isNotEmpty())
            {
                searchTitle(etSearch.text.toString())
            }
            else
            {
                getData()
                Toast.makeText(applicationContext, "Please type something that you want to search", Toast.LENGTH_SHORT).show()
            }
        }

        etSearch.addTextChangedListener {
            searchTitle(etSearch.text.toString())
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun getData()
    {
        list.clear()
        list.addAll(database.userDao().getAll())
        adapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun searchTitle(search: String)
    {
        list.clear()
        list.addAll(database.userDao().searchByTitle(search))
        adapter.notifyDataSetChanged()
    }
}