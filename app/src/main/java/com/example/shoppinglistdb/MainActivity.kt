package com.example.shoppinglistdb

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

// ...

class MainActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var etItemName: EditText
    private lateinit var etItemQuantity: EditText
    private lateinit var btnAddItem: Button
    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etItemName = findViewById(R.id.etItemName)
        etItemQuantity = findViewById(R.id.etItemQuantity)
        btnAddItem = findViewById(R.id.btnAddItem)
        listView = findViewById(R.id.listView)

        databaseHelper = DatabaseHelper(this)

        // Elem hozzáadása gomb eseménykezelő
        btnAddItem.setOnClickListener {
            val name = etItemName.text.toString()
            val quantity = etItemQuantity.text.toString().toInt()

            val newItem = ShoppingItem(0, name, quantity)
            val itemId = databaseHelper.addShoppingItem(newItem)

            if (itemId > -1) {
                updateShoppingList()
            }

            // Tisztítás az új elem hozzáadása után
            etItemName.text.clear()
            etItemQuantity.text.clear()
        }

        // Lista frissítése
        updateShoppingList()
    }

    private fun updateShoppingList() {
        val items = databaseHelper.getAllShoppingItems()
        val adapter = ShoppingListAdapter(this, items)
        listView.adapter = adapter

        // Elem törlése gomb eseménykezelő
        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedItem = items[position]
            databaseHelper.deleteShoppingItem(selectedItem.id)
            updateShoppingList()
        }
    }
}

