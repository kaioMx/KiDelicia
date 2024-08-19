package com.example.kidelicia;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;

public class footer {
    public static void Footer(Context context, View layout) {
        // Referenciar os botões
        ImageButton Bjornal = layout.findViewById(R.id.jornalButton);
        ImageButton Bhome = layout.findViewById(R.id.Tadm);
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
