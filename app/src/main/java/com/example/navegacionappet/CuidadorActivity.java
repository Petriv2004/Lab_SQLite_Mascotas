package com.example.navegacionappet;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CuidadorActivity extends AppCompatActivity {

    public static final String EXTRA_CUIDADORID = "cuidadorId";

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cuidador);
        int cuidadorId = (Integer)getIntent().getExtras().get(EXTRA_CUIDADORID);
        Cuidador cuidador = Cuidador.cuidadores[cuidadorId];

        TextView name = (TextView) findViewById(R.id.nombre);
        name.setText(cuidador.getNombre());

        TextView description = (TextView) findViewById(R.id.correo);
        description.setText(cuidador.getCorreo());

        ImageView photo = (ImageView) findViewById(R.id.photo);
        photo.setImageResource(cuidador.getFoto());
        photo.setContentDescription(cuidador.getNombre());

        TextView sexo = (TextView) findViewById(R.id.sexo);
        sexo.setText(cuidador.getSexo());

        TextView telefono = (TextView) findViewById(R.id.telefono);
        telefono.setText(cuidador.getTelefono());

        button = findViewById(R.id.button);
        button.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Confirmación")
                    .setMessage("¿Está seguro de contratar a esta persona?")
                    .setPositiveButton("Sí", (dialog, which) -> {
                        Intent intent = new Intent(CuidadorActivity.this, MainActivity.class);;
                        startActivity(intent);
                    })
                    .setNegativeButton("No", null)
                    .show();
        });

    }


}