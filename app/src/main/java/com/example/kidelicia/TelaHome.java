package com.example.kidelicia;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TelaHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        // Ajustar padding com as barras do sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Tconta), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Chamar o método Footer para configurar os botões
        Footer(this, findViewById(R.id.Tconta));
    }

    public static void Footer(Context context, View layout) {
        // Referenciar os botões
        ImageButton Bjornal = layout.findViewById(R.id.jornalButton);
        ImageButton Bhome = layout.findViewById(R.id.homeButton);
        ImageButton Boferta = layout.findViewById(R.id.ofertaButton);
        ImageButton Bconta = layout.findViewById(R.id.contaButton);

        // Definir listener de clique para o botão jornal
        Bjornal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TelaJornal.class);
                context.startActivity(intent);
            }
        });


       Bhome.setOnClickListener(new View.OnClickListener() {
           @Override
       public void onClick(View view) {
          Intent intent = new Intent(context, TelaHome.class);
               context.startActivity(intent);
            }
        });



        // Definir listener de clique para o botão conta
        Bconta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TelaConta.class);
                context.startActivity(intent);
            }
        });
    }
}
