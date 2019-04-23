package com.andrezamoreira.twitter;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    // declaração
    private FirebaseAuth firebaseAuth;

    private EditText editEmail;
    private EditText editSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // inicializando
        firebaseAuth = FirebaseAuth.getInstance();

        editEmail = findViewById(R.id.emailEditText);
        editSenha = findViewById(R.id.senhaEditText);
    }

    // TODO: Verificar se já está logado
    @Override
    protected void onStart() {
        super.onStart();

        // usuário atual
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        // interface do usuário se comportar como usuário atual
        updateUI(currentUser);
    }

    // TODO: interface do usuário
    private void updateUI(FirebaseUser user){
        if (user != null){
            // passa pra tela principal
        }
    }

    // TODO: botão de login
    public void login(View view){
        // pega valores da tela
        String email = editEmail.getText().toString().trim();
        String senha = editSenha.getText().toString().trim();

        // tratando possíveis erros
        if (email.equals("")){
            editEmail.setError("Preencha esse campo!");
            return;
        }
        if (senha.equals("")){
            editSenha.setError("Preencha este campo");
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(email,senha).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    updateUI(firebaseAuth.getCurrentUser());
                } else {
                    Toast.makeText(LoginActivity.this, "Usuário ou senha incorreta", Toast.LENGTH_SHORT).show();
                    // sem usuário logado
                    updateUI(null);
                }
            }
        });
    }

    public void cadastro (View view){
        // ir para tela de cadastro

    }
}
