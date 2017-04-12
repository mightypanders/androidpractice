package com.panders.example.cookiemonster;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public int state = 1; // 1 cookie not eaten, 2 cookie eaten
    ImageView image;
    TextView test;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image = (ImageView) findViewById(R.id.cookie_view);
        test = (TextView) findViewById(R.id.status_text_view);
        btn = (Button) findViewById(R.id.btn_eat);
    }

    public void eatCookie(View view) {
        if (state == 1) {
            image.setImageResource(R.drawable.after_cookie);
            test.setText(R.string.so_full);
            btn.setText(R.string.new_cookie);
            state = 2;
        } else {
            if (state == 2) {
                image.setImageResource(R.drawable.before_cookie);
                test.setText(R.string.so_hungry);
                btn.setText(R.string.eat_cookie);
                state = 1;
            }
        }
    }
}
