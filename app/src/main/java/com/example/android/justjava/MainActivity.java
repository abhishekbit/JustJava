package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends ActionBarActivity {
    CheckBox b;
    CheckBox c2;
    EditText t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    int qty = 2;
    int cp=5,whp=1,cf=2;


    public void increment(View view) {
        qty = qty + 1;
        display(qty);
    }

    public void decrement(View view) {
        if(qty==1) {
            Toast.makeText(getApplicationContext(),"You cant have less than 1 cups",Toast.LENGTH_SHORT).show();
            return;
        }
        qty = qty - 1;
        display(qty);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
//        displayPrice(qty * 5);
        t=(EditText)findViewById(R.id.editText);
        String text=t.getText().toString();
        b=(CheckBox)findViewById(R.id.c);
        c2=(CheckBox)findViewById(R.id.c1);
        boolean haswhipped=b.isChecked();
        boolean haschoco=c2.isChecked();
        int price=  calculatePrice(haswhipped,haschoco);
        String priceMessage=createSummary(price,haswhipped,haschoco,text);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for" + text);
        intent.putExtra(Intent.EXTRA_TEXT,priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        displayMessage(priceMessage);

    }
    private String createSummary(int price,boolean haswhipped,boolean haschoco,String text){
        String priceMessage="Name : " + text;
        priceMessage=priceMessage + "\nQuantity :" + qty;
        priceMessage=priceMessage + "\nAdd Whipped Cream? " + haswhipped;
 /*       if(b.isChecked())
            priceMessage=priceMessage + "\nAdd Whipped Cream? true";
        else
            priceMessage=priceMessage + "\nAdd Whipped Cream? false";*/
        priceMessage=priceMessage + "\nAdd Chocolate? " + haschoco;
        priceMessage=priceMessage + "\nTotal :$" + price;
        priceMessage=priceMessage + "\nThank you";
        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
    public int calculatePrice(boolean addwhip,boolean addchoco){
        int baseprice=5;
        if(addwhip){
            baseprice=baseprice+1;
        }
        if(addchoco){
            baseprice=baseprice+2;
        }
        return baseprice*qty;
    }

    /**
     * This method displays the given price on the screen.
     */
 /*   private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }*/
    public void displayMessage(String message) {
        TextView ordersummary= (TextView) findViewById(R.id.order_summary_text_view);
        ordersummary.setText(message);
    }
}