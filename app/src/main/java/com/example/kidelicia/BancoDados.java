package com.example.kidelicia;

import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class BancoDados {

    private static final String TAG = "BancoDadosFirebase";
    private static final String COLLECTION_CLIENTES = "clientes";

    private FirebaseFirestore firestore;

    public BancoDados() {
        firestore = FirebaseFirestore.getInstance();
    }

    public void cadastro(String email, String senha, String confSenha, String telefone, String cpf, FirebaseCallback callback) {
        CollectionReference clientesRef = firestore.collection(COLLECTION_CLIENTES);

        Map<String, Object> cliente = new HashMap<>();
        cliente.put("email", email);
        cliente.put("senha", senha);
        cliente.put("confSenha", confSenha);
        cliente.put("telefone", telefone);
        cliente.put("cpf", cpf);

        clientesRef.document(cpf)
                .set(cliente)
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "Cliente cadastrado com sucesso!");
                    callback.onSuccess(0); // Flag 0 para sucesso
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Erro ao cadastrar cliente", e);
                    callback.onFailure(1); // Flag 1 para erro
                });
    }

    // Método para consultar se um cliente já existe
    public void consultaClienteExistente(String email, String cpf, FirebaseCallback callback) {
        CollectionReference clientesRef = firestore.collection(COLLECTION_CLIENTES);

        clientesRef.document(cpf)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Map<String, Object> cliente = documentSnapshot.getData();
                        if (cliente != null && email.equals(cliente.get("email"))) {
                            Log.d(TAG, "Cliente encontrado");
                            callback.onSuccess(0); // Flag 0 se o cliente existe
                        } else {
                            Log.d(TAG, "Cliente não encontrado");
                            callback.onFailure(1); // Flag 1 se o cliente não existe
                        }
                    } else {
                        Log.d(TAG, "Cliente não encontrado");
                        callback.onFailure(1); // Flag 1 se o cliente não existe
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Erro ao consultar cliente", e);
                    callback.onFailure(1);
                });
    }

    // Interface de callback para operações assíncronas
    public interface FirebaseCallback {
        void onSuccess(int flag);
        void onFailure(int flag);
    }
}