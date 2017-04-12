package de.panders.markus.justjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    public int pricePerCup = 5;
    public int quantity = 0;
    public int sum = 0;
    public String orderText;
    public TextView textViewOrderSummary;
    public TextView textViewQuantity;
    public CheckBox checkBoxTopChoc;
    public CheckBox checkBoxTopWhip;
    public EditText editTextName;
    public String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewOrderSummary = (TextView) findViewById(R.id.order_summary_view);
        textViewQuantity = (TextView) findViewById(R.id.quantity_text_view);
        checkBoxTopWhip = (CheckBox) findViewById(R.id.chk_whippd);
        checkBoxTopChoc = (CheckBox) findViewById(R.id.chk_chock);
        orderText = getString(R.string.order_fail);
        editTextName = (EditText) findViewById(R.id.txt_usrName);
        displayQuantity();
    }

    public void calcOrder(View view) {

        this.sum = calculatePrice();
        this.name = this.editTextName.getText().toString();

        if (this.sum > 0) {
            orderText = getString(R.string.order_success);
            displayMessage(createOrderSummary());
        } else {
            orderText = getString(R.string.order_fail);
            showToast(view, getString(R.string.order_fail));

        }
    }

    public void submitOrder(View view) {
        showToast(view, orderText);
    }

    private int calculatePrice() {
        return quantity * pricePerCup;
    }

    private String formatPrice() {
        return (NumberFormat.getCurrencyInstance().format(sum));
    }

    private String createOrderSummary() {
        String value = "Name: " + name + "\n";
        if (checkBoxTopWhip.isChecked()) {
            value += "Topping: " + checkBoxTopWhip.getText()+"\n";
        }
        if (checkBoxTopChoc.isChecked()) {
            value += "Topping: " + checkBoxTopChoc.getText()+"\n";
        }
        value += "Quantity: " + quantity + "\n";
        value += "Total: " + formatPrice() + "\n";
        value += "Thank you!";
        return value;
    }

    private void displayMessage(String message) {
        textViewOrderSummary.setText(message);
    }

    private void displayQuantity() {
        textViewQuantity.setText(String.valueOf(quantity));
    }

    public void increment(View view) {
        quantity++;
        displayQuantity();
    }

    public void decrement(View view) {
        if (quantity - 1 >= 0) {
            quantity--;
        }
        displayQuantity();
    }

    public void showToast(View view, String text) {
        Toast t = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        t.show();
    }
}
