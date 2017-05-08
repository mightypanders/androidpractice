package com.panders.shoppingsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by markus on 07.05.17.
 */

public class ShoppingMemoDataSource {
    private static final String LOG_TAG = ShoppingMemoDataSource.class.getSimpleName();
    private SQLiteDatabase db;
    private ShoppingMemoDbHelper helper;
    private String[] columns = {
            ShoppingMemoDbHelper.COLUMN_ID,
            ShoppingMemoDbHelper.COLUMN_PRODUCT,
            ShoppingMemoDbHelper.COLUMN_QUANTITY
    };

    public ShoppingMemoDataSource(Context context) {
        Log.d(LOG_TAG, "DataSource erzeugt den dbHelper");
        helper = new ShoppingMemoDbHelper(context);
    }

    public void open() {
        Log.d(LOG_TAG, "Anfragen einer Referenz auf DB");
        db = helper.getWritableDatabase();
        Log.d(LOG_TAG, "DB Referenz erhalten. Pfad: " + db.getPath());
    }

    public void close() {
        helper.close();
        Log.d(LOG_TAG, "DB mit helper geschlossen.");
    }

    public ShoppingMemo createShoppingMemo(String product, int quantity) {
        ContentValues values = new ContentValues();
        values.put(ShoppingMemoDbHelper.COLUMN_PRODUCT, product);
        values.put(ShoppingMemoDbHelper.COLUMN_QUANTITY, quantity);

        long insertID = db.insert(ShoppingMemoDbHelper.TABLE_SHOPPING_LIST, null, values);
        Cursor cursor = db.query(ShoppingMemoDbHelper.TABLE_SHOPPING_LIST, columns, ShoppingMemoDbHelper.COLUMN_ID + "=" + insertID, null, null, null, null);
        cursor.moveToFirst();
        ShoppingMemo shoppingMemo = cursorToShoppingMemo(cursor);
        cursor.close();

        return shoppingMemo;
    }

    private ShoppingMemo cursorToShoppingMemo(Cursor cursor) {
        long id = cursor.getLong(cursor.getColumnIndex(ShoppingMemoDbHelper.COLUMN_ID));
        String product = cursor.getString(cursor.getColumnIndex(ShoppingMemoDbHelper.COLUMN_PRODUCT));
        int quantity = cursor.getInt(cursor.getColumnIndex(ShoppingMemoDbHelper.COLUMN_QUANTITY));
        ShoppingMemo memo = new ShoppingMemo(product, quantity, id);
        return memo;
    }

    public List<ShoppingMemo> getAllShoppingMemos(){
        List<ShoppingMemo> shoppingMemoList = new ArrayList<>();
        Cursor cursor = db.query(ShoppingMemoDbHelper.TABLE_SHOPPING_LIST,columns,null,null,null,null,null);
        cursor.moveToFirst();
        ShoppingMemo memo;
        while(!cursor.isAfterLast()){
            memo = cursorToShoppingMemo(cursor);
            shoppingMemoList.add(memo);
            Log.d(LOG_TAG,"ID: "+memo.getId()+", Inhalt: "+memo.toString());
            cursor.moveToNext();
        }
        cursor.close();
        return shoppingMemoList;
    }
}
