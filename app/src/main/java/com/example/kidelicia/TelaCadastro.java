package com.example.kidelicia;

import android.annotation.SuppressLint;
import android.app.backup.RestoreObserver;
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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TelaCadastro extends AppCompatActivity {

    BancoDados bancoDados;
    Connection con;
    String str;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro);

        bancoDados = new BancoDados();
        connect();
    }

    public void btnConfirmar(View view) {

        EditText txtemail = findViewById(R.id.txtEmailCadastro);
        EditText txtsenha = findViewById(R.id.txtSenha);
        EditText txtconfSenha = findViewById(R.id.txtConfirmaSenha);
        EditText txtelefone = findViewById(R.id.txtTelefone);
        EditText txtcpf = findViewById(R.id.txtCpf);

        String email = txtemail.getText().toString();
        String senha = txtsenha.getText().toString();
        String confSenha = txtconfSenha.getText().toString();
        String telefone = txtelefone.getText().toString();
        String cpf = txtcpf.getText().toString();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(()->{
            try {
                con = bancoDados.COON();
                String query = "INSERT INTO cadastro (email, senha, confSenha, telefone, cpf) " +
                        "VALUES (?, ?, ?, ?, ?)";
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setString(1, email);
                stmt.setString(2, senha);
                stmt.setString(3, confSenha);
                stmt.setString(4, telefone);
                stmt.setString(5, cpf);
                stmt.executeUpdate();

            } catch (SQLException e){
                throw new RuntimeException(e);
            }

            runOnUiThread(()->{
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
            });

        });
    }

    public void connect(){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(()->{
            try {
                con = bancoDados.COON();
                if(con == null){
                    str = "Erro na conexão";
                } else {
                    str = "Conexão estabelecida";
                }
            } catch (Exception e){
                throw new RuntimeException(e);
            }

            runOnUiThread(()->{
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
            });

        });
    }

}