package com.example.qrbdcode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {


     FirebaseAuth fAuth;


    com.google.android.material.textfield.TextInputEditText txtEmailL;
    com.google.android.material.textfield.TextInputEditText txtPassL;
    Button btnGoToRegister;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fAuth           = FirebaseAuth.getInstance();
        txtEmailL       = (com.google.android.material.textfield.TextInputEditText)findViewById(R.id.txtEmailL);
        txtPassL        = (com.google.android.material.textfield.TextInputEditText)findViewById(R.id.txtPasswordL);
        btnGoToRegister = (Button)findViewById(R.id.btnToRegisto);
        btnLogin        = (Button)findViewById(R.id.btnLogin);


        btnGoToRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), registo.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtEmailL.getText().toString();
                String pass = txtPassL.getText().toString();

                if (TextUtils.isEmpty(email)){
                    txtEmailL.setError("É necessário Email");
                    return;
                }
                if (TextUtils.isEmpty((pass))){
                    txtPassL.setError("É necessário Password");
                    return;
                }else{
                    fAuth.signInWithEmailAndPassword(email, pass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(getApplicationContext(),"Sucesso", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), Scan.class));
                                    }else{
                                        Toast.makeText(getApplicationContext(),"Erro ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                }



            }
        });






    }
}