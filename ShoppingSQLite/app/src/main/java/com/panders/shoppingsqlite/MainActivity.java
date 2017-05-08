package com.panders.shoppingsqlite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

/**
 * Created by markus on 07.05.17.
 */

public class MainActivity extends AppCompatActivity {
    public final static String LOG_TAG = MainActivity.class.getSimpleName();
    private ShoppingMemoDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(LOG_TAG, "Anlegen von DB Objekt");
        dataSource = new ShoppingMemoDataSource(this);
        activateAddButton();
    }

    private void activateAddButton() {
        Button buttonAddProduct = (Button) findViewById(R.id.button_add_product);
        final EditText editTextQuantity = (EditText) findViewById(R.id.editText_quantity);
        final EditText editTextProduct = (EditText) findViewById(R.id.editText_product);
        buttonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quantString = editTextQuantity.getText().toString();
                String prodString = editTextProduct.getText().toString();

                if (TextUtils.isEmpty(quantString)) {
                    editTextQuantity.setError(getString(R.string.editText_error_message));
                    return;
                }
                if (TextUtils.isEmpty(prodString)) {
                    editTextProduct.setError(getString(R.string.editText_error_message));
                    return;
                }
                int quantitiy = Integer.parseInt(quantString);
                editTextProduct.setText("");
                editTextQuantity.setText("");
                dataSource.createShoppingMemo(prodString,quantitiy);
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if(getCurrentFocus()!=null){
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
                }
                showAllListEntries();
            }
        });
    }

    private void showAllListEntries() {
        List<ShoppingMemo> shoppingMemoList = dataSource.getAllShoppingMemos();
        ArrayAdapter<ShoppingMemo> shoppingMemoArrayAdapter = new ArrayAdapter<ShoppingMemo>(
                this,
                android.R.layout.simple_list_item_multiple_choice,
                shoppingMemoList);
        ListView shoppingMemoListView = (ListView) findViewById(R.id.listview_shopping_memos);
        shoppingMemoListView.setAdapter(shoppingMemoArrayAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "Datenquelle wird geöffnet.");
        dataSource.open();
        Log.d(LOG_TAG, "Folgende Einträge sind vorhanden");
        showAllListEntries();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "Datenquelle schließen");
        dataSource.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
