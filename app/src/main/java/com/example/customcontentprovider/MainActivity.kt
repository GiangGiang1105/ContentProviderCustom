package com.example.customcontentprovider

import android.annotation.SuppressLint
import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        eventClickButton()
    }

    private fun eventClickButton() {
        add.setOnClickListener {
            insertData()
        }
        show.setOnClickListener {
            showDataToListView()
        }
    }

    @SuppressLint("Recycle")
    private fun getData(): MutableList<String> {
        val rs = contentResolver.query(
            AcronymProvider.CONTENT_URI,
            arrayOf(AcronymProvider._ID, AcronymProvider.NAME, AcronymProvider.MEANING),
            null,
            null,
            null
        )
        val list: MutableList<String> = mutableListOf()
        while (rs?.moveToNext()!!) {
            list.add(rs.getString(1) + "   " + rs.getString(2))
        }
        return list
    }

    private fun showDataToListView() {
        val list = getData()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)
        list_view.adapter = adapter
    }

    private fun insertData() {
        val name = txt_name.text.toString()
        val meaning = txt_meaning.text.toString()
        val values = ContentValues()
        values.put(AcronymProvider.NAME, name)
        values.put(AcronymProvider.MEANING, meaning)
        contentResolver.insert(AcronymProvider.CONTENT_URI, values)
    }
}