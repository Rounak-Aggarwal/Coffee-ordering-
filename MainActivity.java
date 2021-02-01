/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the + button is clicked.
     */
    public void incrementValue(View view) {
        if (quantity < 100) {
            display(++quantity);
        } else if (quantity >= 100) {
            Toast toast = Toast.makeText(this, "Sorry, can not order more than 100 cups of coffee", Toast.LENGTH_SHORT);
            toast.show();
        }
    }


    /**
     * This method is called when the - button is clicked.
     */
    public void decrementValue(View view) {
        if (quantity >= 2) {
            display(--quantity);
        } else if (quantity <= 1) {
            Toast toast = Toast.makeText(this, "Order at least 1 cup of coffee", Toast.LENGTH_SHORT);
            toast.show();
        }
    }


    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox checkBox1 = (CheckBox) findViewById(R.id.toppings_whipped_cream);
        CheckBox checkBox2 = (CheckBox) findViewById(R.id.toppings_chocolate);
        boolean whippedCream = checkBox1.isChecked();
        boolean chocolate = checkBox2.isChecked();
        EditText nameField = (EditText) findViewById(R.id.text_name);
        String name = nameField.getText().toString();
        String orderSummary = createOrderSummary(calculatePrice(whippedCream, chocolate), whippedCream, chocolate, quantity, name);
        composeEmail(orderSummary, name);
    }


    /**
     * This method calculates the price for given quantity value on the screen.
     */
    private int calculatePrice(boolean isWhippedCream, boolean isChocolate) {
        int basePrice = 5;
        if (isWhippedCream) {
            basePrice += 1;
        }
        if (isChocolate) {
            basePrice += 2;
        }
        return quantity * basePrice;
    }

    /**
     * Create summary of the order.
     *
     * @param name            of the order
     * @param addWhippedCream is whether or not the user wants whipped cream topping
     * @param addChocolate    is whether or not the user wants chocolate topping
     * @param totalPrice      of the order
     * @param quantity        of the order
     * @return text summary
     */
    private String createOrderSummary(int totalPrice, boolean addWhippedCream, boolean addChocolate, int quantity, String name) {
        String toppingsAdded = "\nToppings added ";
        if (addWhippedCream) {
            toppingsAdded += "\n              ‣ Whipped Cream";
        }

        if (addChocolate) {
            toppingsAdded += "\n              ‣ Chocolate";
        }
        String priceMessage = "Name: " + name + toppingsAdded +
                "\nQuantity: " + quantity +
                "\nTotal $" + totalPrice +
                "\n\nThank you!";
        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


    public void composeEmail(String orderSummary, String name) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee Order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, orderSummary);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}