package com.example.kidelicia;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TelaCadastro extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro);

        EditText txtemail = findViewById(R.id.txtEmailCadastro);
        EditText txtsenha = findViewById(R.id.txtSenha);
        EditText txtconfSenha = findViewById(R.id.txtConfirmaSenha);
        EditText txtelefone = findViewById(R.id.txtTelefone);
        EditText txtcpf = findViewById(R.id.txtCpf);
        Button bConfirmar= findViewById(R.id.bConfirmar);

        BancoDados bd = new BancoDados();

        bConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = txtemail.getText().toString();
                String senha = txtsenha.getText().toString();
                String confSenha = txtconfSenha.getText().toString();
                String telefone = txtelefone.getText().toString();
                String cpf = txtcpf.getText().toString();

                bd.cadastro(email, senha, confSenha, telefone, cpf, new BancoDados.FirebaseCallback() {
                    @Override
                    public void onSuccess(int flag) {
                        System.out.println("Sucesso: " + flag);
                    }

                    @Override
                    public void onFailure(int flag) {
                        System.err.println("Erro: " + flag);
                    }
                });

            }
        });

    }

}