package com.example.hamburgueriaz;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private int quantity = 0;
    private final int BASE_PRICE = 20;
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

        buttonMinus.setOnClickListener(v -> subtrair());
        buttonPlus.setOnClickListener(v -> somar());
        buttonOrder.setOnClickListener(v -> enviarPedido());
    }

    private void somar() {
        quantity++;
        updateUI();
    }

    private void subtrair() {
        if (quantity > 0) {
            quantity--;
            updateUI();
        }
    }

    private void updateUI() {
        textQuantity.setText(String.valueOf(quantity));
        textTotal.setText("Resumo do pedido: R$" + calcularPreco());
    }

    private int calcularPreco() {
        int preco = BASE_PRICE;
        if (checkBacon.isChecked()) preco += 2;
        if (checkQueijo.isChecked()) preco += 2;
        if (checkOnion.isChecked()) preco += 3;
        return preco * quantity;
    }

    private void enviarPedido() {
        String nome = editTextName.getText().toString();
        boolean temBacon = checkBacon.isChecked();
        boolean temQueijo = checkQueijo.isChecked();
        boolean temOnion = checkOnion.isChecked();

        int precoFinal = calcularPreco();

        String resumo = "" + nome + "\n" +
                "Tem Bacon? " + (temBacon ? "Sim" : "Não") + "\n" +
                "Tem Queijo? " + (temQueijo ? "Sim" : "Não") + "\n" +
                "Tem Onion Rings? " + (temOnion ? "Sim" : "Não") + "\n" +
                "Quantidade: " + quantity + "\n" +
                "Preço final: R$ " + precoFinal;

        textTotal.setText(resumo);

        new AlertDialog.Builder(this)
                .setTitle("Resumo do Pedido")
                .setMessage(resumo)
                .setPositiveButton("Enviar por E-mail", (dialog, which) -> {
                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setData(Uri.parse("mailto:muriloeduardo1997@gmail.com"));
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Pedido de " + nome);
                    intent.putExtra(Intent.EXTRA_TEXT, resumo);

                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    } else {
                        Toast.makeText(this, "Nenhum app de e-mail disponível", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
}