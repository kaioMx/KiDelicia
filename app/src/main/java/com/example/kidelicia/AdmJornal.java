package com.example.kidelicia;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kidelicia.R;

import java.io.IOException;

public class AdmJornal  extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText editTextTituloJornal;
    private EditText editTextDataJornal;
    private TextView textViewImagemSelecionada;
    private Button buttonUploadImagem;
    private Button buttonPublicarJornal;
    private Uri imagemUri;
    private ImageView imageViewFolheto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admjornal);  // Usa o layout XML que criamos

        // Vinculando os componentes com seus respectivos IDs
        editTextTituloJornal = findViewById(R.id.inputTituloJornal);
        editTextDataJornal = findViewById(R.id.inputDataJornal);
        textViewImagemSelecionada = findViewById(R.id.labelImagemSelecionada);
        buttonUploadImagem = findViewById(R.id.buttonUploadImagem);
        buttonPublicarJornal = findViewById(R.id.buttonPublicarJornal);
        imageViewFolheto = findViewById(R.id.folheto);

        // Definindo o clique do botão de upload da imagem
        buttonUploadImagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirGaleria();
            }
        });

        // Definindo o clique do botão de publicar jornal
        buttonPublicarJornal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publicarJornal();
            }
        });
    }

    // Método para abrir a galeria de imagens
    private void abrirGaleria() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Selecionar Imagem"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            imagemUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagemUri);
                imageViewFolheto.setImageBitmap(bitmap);
                textViewImagemSelecionada.setText("Imagem selecionada!");
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Falha ao carregar imagem", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Método para simular a publicação do jornal
    private void publicarJornal() {
        String titulo = editTextTituloJornal.getText().toString().trim();
        String data = editTextDataJornal.getText().toString().trim();

        if (titulo.isEmpty() || data.isEmpty() || imagemUri == null) {
            Toast.makeText(this, "Preencha todos os campos e selecione uma imagem!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Lógica de publicação (exemplo de simulação)
        // Aqui você pode implementar a lógica para salvar o jornal no banco de dados, enviar para um servidor, etc.
        Toast.makeText(this, "Jornal publicado com sucesso!", Toast.LENGTH_LONG).show();

        // Reseta os campos após a publicação
        editTextTituloJornal.setText("");
        editTextDataJornal.setText("");
        textViewImagemSelecionada.setText("Nenhuma imagem selecionada");
        imageViewFolheto.setImageResource(0); // Limpa a visualização da imagem
    }
}
