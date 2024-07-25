package com.example.kidelicia;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;


public class TelaConta extends AppCompatActivity {
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conta);

        // Chamar o método Footer para configurar os botões do footer
        TelaHome.Footer(this, findViewById(R.id.Tconta));
    }

}
