package com.example.navegacionappet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class VerInformacion extends AppCompatActivity {

    private TextView tvNombre, tvUsuario, tvCorreo, tvDireccion, tvNacimiento, tvGenero;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_informacion);

        tvNombre = findViewById(R.id.tv_nombre);
        tvUsuario = findViewById(R.id.tv_usuario);
        tvCorreo = findViewById(R.id.tv_correo);
        tvDireccion = findViewById(R.id.tv_direccion);
        tvNacimiento = findViewById(R.id.tv_nacimiento);
        tvGenero = findViewById(R.id.tv_genero);

        dbHelper = new DatabaseHelper(this);

        SharedPreferences sharedPreferences = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("idUsuario", -1);

        if (userId != -1) {
            mostrarDatosUsuario(userId);
        }
    }

    private void mostrarDatosUsuario(int userId) {
        Usuario usuario = dbHelper.obtenerUsuarioPorId(userId);
        if (usuario != null) {
            tvNombre.setText(usuario.getNombreCompleto());
            tvUsuario.setText(usuario.getNombre());
            tvCorreo.setText(usuario.getCorreo());
            tvDireccion.setText(usuario.getDireccion());
            tvNacimiento.setText(usuario.getFechaNacimiento());
            tvGenero.setText(usuario.getSexo());
        }
    }

    public void onClickEditar(View view){
        Intent intent = new Intent(VerInformacion.this, EditarInformacion.class);
        startActivity(intent);
    }
}