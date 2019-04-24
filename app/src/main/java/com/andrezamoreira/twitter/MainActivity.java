package com.andrezamoreira.twitter;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database;

    private String uid;
    private String usuario;

    private ArrayList<String> seguindo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        seguindo = new ArrayList<>();
    }

    // TODO: ao iniciar
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user == null) finish();

        getUserInfo();
    }

    private void getUserInfo(){
        uid = firebaseAuth.getCurrentUser().getUid();

        DatabaseReference userRef = database.getReference("user/" + uid);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // pegar nossos dados
                usuario = dataSnapshot.child("usuario").getValue(String.class);

                seguindo.clear();
                // para cada um dos filhos de seguindo
                for(DataSnapshot s:dataSnapshot.child("seguindo").getChildren()){
                    // armazenar
                    seguindo.add(s.getValue(String.class));
                }

                Log.d("usuario", usuario);
                Log.d("lista", seguindo.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
