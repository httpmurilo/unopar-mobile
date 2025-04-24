package com.example.hamburgueriaz;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private int quantity = 0;
    private final int BASE_PRICE = 10;
    private EditText editTextName;
    private CheckBox checkBacon, checkQueijo, checkOnion;
    private TextView textQuantity, textTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = findViewById(R.id.editTextName);
        checkBacon = findViewById(R.id.checkBacon);
        checkQueijo = findViewById(R.id.checkQueijo);
        checkOnion = findViewById(R.id.checkOnion);
        textQuantity = findViewById(R.id.textQuantity);
        textTotal = findViewById(R.id.textTotal);

        Button buttonMinus = findViewById(R.id.buttonMinus);
        Button buttonPlus = findViewById(R.id.buttonPlus);
        Button buttonOrder = findViewById(R.id.buttonOrder);

        buttonMinus.setOnClickListener(v -> {
            if (quantity > 0) quantity--;
            updateUI();
        });

        buttonPlus.setOnClickListener(v -> {
            quantity++;
            updateUI();
        });

        buttonOrder.setOnClickListener(v -> {
            String name = editTextName.getText().toString();
            String orderSummary = "Cliente: " + name + "\nTotal: R$" + calculatePrice();
            Toast.makeText(this, orderSummary, Toast.LENGTH_LONG).show();
        });
    }

    private void updateUI() {
        textQuantity.setText(String.valueOf(quantity));
        textTotal.setText("Resumo do pedido: R$" + calculatePrice());
    }

    private int calculatePrice() {
        int price = BASE_PRICE;
        if (checkBacon.isChecked()) price += 2;
        if (checkQueijo.isChecked()) price += 1;
        if (checkOnion.isChecked()) price += 3;
        return price * quantity;
    }
}