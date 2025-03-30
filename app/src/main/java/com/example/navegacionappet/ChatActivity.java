package com.example.navegacionappet;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat);

        SharedPreferences sharedPreferences = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("idUsuario", -1);

        if (userId != -1) {
            mostrarMensajes(userId);
        }
    }
    public void onSendMessageC(View view) {
        EditText etMensaje = findViewById(R.id.messageC);
        String mensaje = etMensaje.getText().toString().trim();

        if (mensaje.isEmpty()) {
            Toast.makeText(this, "Escribe un mensaje", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences sharedPreferences = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("idUsuario", -1);

        if (userId == -1) {
            Toast.makeText(this, "Error: Usuario no encontrado", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.guardarMensaje(userId, mensaje);

        mostrarMensajes(userId);

        etMensaje.setText("");
    }

    private void mostrarMensajes(int userId) {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        List<String> mensajes = dbHelper.obtenerMensajes(userId);

        TextView tvConversacion = findViewById(R.id.converC);
        StringBuilder historial = new StringBuilder();

        for (String msg : mensajes) {
            historial.append(msg).append("\n");
        }

        tvConversacion.setText(historial.toString());
    }
}