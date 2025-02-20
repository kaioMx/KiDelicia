package com.example.kidelicia;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TelaJornal extends AppCompatActivity {
    private ImageView folheto;
    private TextView tituloJornal;

    // Conexão com o banco de dados
    BancoDados bancoDados;
    Connection con;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jornal);

        folheto = findViewById(R.id.folheto);
        tituloJornal = findViewById(R.id.tituloJornal);

        bancoDados = new BancoDados();
        buscarUltimoJornal();
        footer.Footer(this, findViewById(R.id.Tjornal));
    }

    private void buscarUltimoJornal() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                con = bancoDados.COON();
                if (con == null) {
                    Log.e("TelaJornal", "Erro na conexão com o banco.");
                    return;
                }

                String query = "SELECT titulo, imagemUri FROM Jornal ORDER BY id DESC LIMIT 1";
                PreparedStatement stmt = con.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    String titulo = rs.getString("titulo");
                    String imagemUriString = rs.getString("imagemUri");

                    runOnUiThread(() -> {
                        tituloJornal.setText(titulo);

                        if (imagemUriString != null && !imagemUriString.isEmpty()) {
                            File arquivoImagem = new File(Uri.parse(imagemUriString).getPath());
                            if (arquivoImagem.exists()) {
                                folheto.setImageURI(Uri.fromFile(arquivoImagem));
                                Log.d("TelaJornal", "Imagem carregada: " + arquivoImagem.getAbsolutePath());
                            } else {
                                Log.e("TelaJornal", "Arquivo de imagem não encontrado: " + arquivoImagem.getAbsolutePath());
                                Toast.makeText(this, "Erro: Imagem não encontrada!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            } catch (SQLException | java.sql.SQLException e) {
                Log.e("TelaJornal", "Erro ao buscar jornal no banco", e);
                runOnUiThread(() -> Toast.makeText(this, "Erro ao carregar o jornal!", Toast.LENGTH_SHORT).show());
            }
        });
    }



}
