package com.example.kidelicia;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TelaHome extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        TextView alfredo = findViewById(R.id.TextAlfredo);


        // Ajustar padding com as barras do sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Thome), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        footer.Footer(this, findViewById(R.id.Thome));
        alfredo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TelaHome", "TextView foi clicado!");

                Uri gmmIntentUri = Uri.parse("https://www.google.com/maps/search/?api=1&query=R.+José+Gonçalves+Borges,+407+-+Conj.+Alfredo+Freire,+Uberaba+-+MG,+38056-110");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                startActivity(mapIntent);

                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                } else {
                    Log.e("TelaHome", "Google Maps não está instalado.");
                }
            }
        });
    }
}