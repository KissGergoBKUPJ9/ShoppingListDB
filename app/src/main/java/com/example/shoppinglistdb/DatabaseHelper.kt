package com.example.shoppinglistdb

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "shopping_list.db"
        private const val TABLE_NAME = "shopping_items"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_QUANTITY = "quantity"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable =
            "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_NAME TEXT, $COLUMN_QUANTITY INTEGER)"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addShoppingItem(item: ShoppingItem): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_NAME, item.name)
        values.put(COLUMN_QUANTITY, item.quantity)

        val id = db.insert(TABLE_NAME, null, values)
        db.close()

        return id
    }

    fun getAllShoppingItems(): List<ShoppingItem> {
        val items = mutableListOf<ShoppingItem>()
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID))
                val name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                val quantity = cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY))
                items.add(ShoppingItem(id, name, quantity))
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return items
    }

    fun deleteShoppingItem(itemId: Long) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_ID=?", arrayOf(itemId.toString()))
        db.close()
    }
}
