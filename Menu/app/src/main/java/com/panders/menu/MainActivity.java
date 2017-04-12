package com.panders.menu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void printToLogs(View view)
    {
        TextView text = (TextView) findViewById(R.id.menu_item_1);
        logit(text);
        text = (TextView) findViewById(R.id.menu_item_2);
        logit(text);
        text = (TextView) findViewById(R.id.menu_item_3);
        logit(text);
    }

    private void logit(TextView text)
    {
        Log.i("MainActivity.java",text.getText().toString());
    }
}
