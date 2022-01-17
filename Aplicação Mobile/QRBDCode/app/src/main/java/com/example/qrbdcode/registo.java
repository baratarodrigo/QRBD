package com.example.qrbdcode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;


public class registo extends AppCompatActivity {
    public com.google.android.material.textfield.TextInputEditText txtPassR;
    public com.google.android.material.textfield.TextInputEditText txtEmailR;
    Button btnGoToLogin, btnRegister;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registo);

        txtEmailR = findViewById(R.id.txtEmailL);
        txtPassR =  findViewById(R.id.txtPasswordL);
        btnGoToLogin = findViewById(R.id.btnToLogin);
        btnRegister = findViewById(R.id.btnRegisto);

        fAuth = FirebaseAuth.getInstance();



        btnGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emaill = txtEmailR.getText().toString();
                String passl = txtPassR.getText().toString();

                if (TextUtils.isEmpty(emaill)){
                    txtEmailR.setError("É necessário Email");
                    return;
                }
                if (TextUtils.isEmpty((passl))){
                    txtPassR.setError("É necessário Password");
                    return;
                }
                if (passl.length() < 9){
                    txtPassR.setError("Password fraca");
                    return;
                }else{
                    fAuth.createUserWithEmailAndPassword(txtEmailR.getText().toString(), txtPassR.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                       Toast.makeText(getApplicationContext(),"User Criado", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(registo.this, Scan.class);
                                        intent.putExtra("EmailR", emaill);
                                        startActivity(intent);

                                    }else{
                                        Toast.makeText(getApplicationContext(),"Erro ! " + task.getException().getCause(), Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                }






            }
        });
    }
}