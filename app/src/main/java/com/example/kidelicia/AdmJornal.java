package com.example.kidelicia;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AdmJornal extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText editTextTituloJornal;
    private TextView textViewImagemSelecionada;
    private Button buttonUploadImagem, buttonPublicarJornal;
    private ImageView imageViewFolheto;
    private Uri imagemUri;

    // Conexão com banco de dados
    BancoDados bancoDados;
    Connection con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admjornal);

        bancoDados = new BancoDados();
        connect(); // Conecta ao banco

        inicializarComponentes();
        footer.Footer(this, findViewById(R.id.admjornal));
    }

    private void inicializarComponentes() {
        editTextTituloJornal = findViewById(R.id.inputTituloJornal);
        textViewImagemSelecionada = findViewById(R.id.labelImagemSelecionada);
        buttonUploadImagem = findViewById(R.id.buttonUploadImagem);
        buttonPublicarJornal = findViewById(R.id.buttonPublicarJornal);
        imageViewFolheto = findViewById(R.id.folheto);

        buttonUploadImagem.setOnClickListener(v -> abrirGaleria());
        buttonPublicarJornal.setOnClickListener(v -> salvarJornalNoBanco());
    }

    private void abrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Selecionar Imagem"), PICK_IMAGE_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri imagemSelecionada = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagemSelecionada);

                // Salvar a imagem internamente
                String nomeArquivo = "jornal_" + System.currentTimeMillis() + ".jpg";
                File arquivoImagem = new File(getFilesDir(), nomeArquivo);
                FileOutputStream fos = new FileOutputStream(arquivoImagem);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.close();

                // Atualizar a URI para o caminho local salvo
                imagemUri = Uri.fromFile(arquivoImagem);

                imageViewFolheto.setImageBitmap(bitmap);
                textViewImagemSelecionada.setText("Imagem salva!");

            } catch (IOException e) {
                Log.e("AdmJornal", "Erro ao salvar a imagem", e);
                Toast.makeText(this, "Erro ao salvar a imagem", Toast.LENGTH_SHORT).show();
            }
        }
    }



    // Método para salvar o jornal no banco de dados
    private void salvarJornalNoBanco() {
        String titulo = editTextTituloJornal.getText().toString().trim();

        if (titulo.isEmpty() || imagemUri == null) {
            Toast.makeText(this, "Preencha todos os campos e selecione uma imagem!", Toast.LENGTH_SHORT).show();
            return;
        }

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                con = bancoDados.COON();
                if (con == null) {
                    Log.e("AdmJornal", "Erro na conexão com o banco.");
                    runOnUiThread(() -> Toast.makeText(this, "Erro ao conectar ao banco!", Toast.LENGTH_SHORT).show());
                    return;
                }

                String query = "INSERT INTO Jornal (titulo, imagemUri) VALUES (?, ?)";
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setString(1, titulo);
                stmt.setString(2, imagemUri.toString()); // Salvar URL pública da imagem

                int rowsInserted = stmt.executeUpdate();
                Log.d("AdmJornal", "Linhas inseridas: " + rowsInserted);

                runOnUiThread(() -> {
                    if (rowsInserted > 0) {
                        Toast.makeText(this, "Jornal salvo com sucesso!", Toast.LENGTH_SHORT).show();
                        editTextTituloJornal.setText("");
                        textViewImagemSelecionada.setText("Nenhuma imagem selecionada");
                        imageViewFolheto.setImageDrawable(null);
                        imagemUri = null;
                    } else {
                        Toast.makeText(this, "Erro ao salvar no banco!", Toast.LENGTH_SHORT).show();
                    }
                });

            } catch (SQLException | java.sql.SQLException e) {
                Log.e("AdmJornal", "Erro ao salvar no banco", e);
                runOnUiThread(() -> Toast.makeText(this, "Erro ao salvar o jornal!", Toast.LENGTH_SHORT).show());
            }
        });
    }



    // Conexão com o banco de dados
    public void connect() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                con = bancoDados.COON();
                String status = (con == null) ? "Erro na conexão" : "Conexão estabelecida";
                runOnUiThread(() -> Toast.makeText(this, status, Toast.LENGTH_SHORT).show());
            } catch (Exception e) {
                Log.e("AdmJornal", "Erro na conexão com o banco", e);
            }
        });
    }
}
