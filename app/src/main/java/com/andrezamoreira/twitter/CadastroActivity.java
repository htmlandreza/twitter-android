package com.andrezamoreira.twitter;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class CadastroActivity extends AppCompatActivity {
    // declaração
    private FirebaseAuth firebaseAuth;

    private EditText editUsuario;
    private EditText editEmail;
    private EditText editSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        // instanciando
        firebaseAuth = FirebaseAuth.getInstance();

        editUsuario = findViewById(R.id.cadastroUsuarioEditText);
        editEmail = findViewById(R.id.cadastroEmailEditText);
        editSenha = findViewById(R.id.cadastroSenhaEditText);
    }

    // TODO: botão salvar
    public void salvar(View view){
        String usuario = editUsuario.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String senha = editSenha.getText().toString().trim();

        // possiveis erros
        if (usuario.equals("")){
            editUsuario.setError("Preencha esse campo!");
            editUsuario.requestFocus();
            return;
        }
        if (email.equals("")){
            editEmail.setError("Preencha esse campo!");
            editEmail.requestFocus();
            return;
        }
        if (senha.equals("")){
            editSenha.setError("Preencha esse campo!");
            editSenha.requestFocus();
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(email,senha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                        } else {
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException e){
                                editSenha.setError("Senha fraca!");
                                editSenha.requestFocus();
                            } catch (FirebaseAuthInvalidCredentialsException e){
                                editEmail.setError("E-mail inválido!");
                                editEmail.requestFocus();
                            } catch (FirebaseAuthUserCollisionException e){
                                editEmail.setError("E-mail já cadastrado!");
                                editEmail.requestFocus();
                            } catch (Exception e){
                                Log.e("Cadastro", e.getMessage());
;                            }
                        }
                    }
                });
    }
}
