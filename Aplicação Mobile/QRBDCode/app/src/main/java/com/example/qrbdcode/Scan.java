package com.example.qrbdcode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Scan extends AppCompatActivity {

    private SurfaceView surfaceView;
    private CameraSource cameraSource;
    private TextView textView;
    private BarcodeDetector barcodeDetector;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private TextView Name;
    private TextView Telephone;
    private TextView Email;
    private Button Add;

     private String EmailR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);


        surfaceView = findViewById(R.id.camera);
        textView = findViewById(R.id.text);

        Name = findViewById(R.id.text_name);
        Telephone = findViewById(R.id.text_nr);
        Email = findViewById(R.id.text_email);

        Add = findViewById(R.id.adicionar);

       EmailR = getIntent().getStringExtra("EmailR");

        barcodeDetector = new BarcodeDetector.Builder(getApplicationContext())
                .setBarcodeFormats(Barcode.QR_CODE).build();

        cameraSource = new CameraSource.Builder(getApplicationContext(), barcodeDetector)
                .setRequestedPreviewSize(640, 480).build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder holder) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){

                    return;
                }
                try {
                    cameraSource.start(holder);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrcode = detections.getDetectedItems();
                if(qrcode.size()!=0){
                    textView.post(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(qrcode.valueAt(0).displayValue);
                        }





                    });
                }
                DocumentReference noteRef = db.collection("users").document(textView.getText().toString());
                noteRef.get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if(documentSnapshot.exists()){
                                    String name = documentSnapshot.getString("Nome");
                                    String phone = documentSnapshot.getString("Telefone");
                                    String email = documentSnapshot.getString("Email");

                                    Name.setText(name);
                                    Telephone.setText(phone);
                                    Email.setText(email);
                                }else{
                                    Toast.makeText(Scan.this, "Dados Não existentes", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Scan.this, "Erro!!", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> map = new HashMap<>();
                map.put("Nome",Name.getText().toString());
                map.put("Telefone", Telephone.getText().toString());
                map.put("Email", Email.getText().toString());

                db.collection(EmailR)
                        .document(textView.getText().toString())
                        .set(map)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(Scan.this, "Adicionado", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Scan.this, "Erro!!", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}