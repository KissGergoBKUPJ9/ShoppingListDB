package com.example.shoppinglistdb

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class ShoppingListAdapter(private val context: Context, private val items: List<ShoppingItem>) :
    BaseAdapter() {

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): Any {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)

        val item = getItem(position) as ShoppingItem

        val itemNameTextView: TextView = view.findViewById(R.id.tvItemName)
        val itemQuantityTextView: TextView = view.findViewById(R.id.tvItemQuantity)

        itemNameTextView.text = item.name
        itemQuantityTextView.text = item.quantity.toString()

        return view
    }

}
