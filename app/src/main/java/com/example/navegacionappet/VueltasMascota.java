package com.example.navegacionappet;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.navegacionappet.R;

import java.util.ArrayList;
import java.util.Locale;

public class VueltasMascota extends AppCompatActivity {

    private int seconds = 0;
    private boolean running;
    private int vueltas_maximas = 5;
    private ArrayList<String> vueltas;
    private TextView recuentro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vueltas_mascota);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        vueltas = new ArrayList<>();
        recuentro = findViewById(R.id.registro);
        runTimer();
    }

    public void onClickStart(View view) {
        running = true;
    }

    public void onClickStop(View view) {
        if (vueltas_maximas > 0) {
            guardarVuelta();
            vueltas_maximas--;
            seconds = 0;
        }
    }

    public void onClickReset(View view){
        running = false;
        seconds = 0;
        vueltas_maximas = 5;
        vueltas.clear();
        recuentro.setText("");
    }

    public void onClickResetVuelta(View view){
        seconds = 0;
    }

    public void onClickResetPausar(View view){
        running = false;
    }
    private void guardarVuelta(){
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int secs = seconds % 60;
        String time = "Vuelta " + (vueltas.size() + 1) + " demoró: " +
                String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);
        vueltas.add(time);
        actualizarRegistro();
    }

    private void actualizarRegistro() {
        String salida = "";
        for (String v : vueltas) {
            salida += v +"\n";
        }
        recuentro.setText(salida);
    }

    private void runTimer() {
        final TextView timeView = findViewById(R.id.time_view);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;
                String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);
                timeView.setText(time);

                if (running) {
                    seconds++;
                }
                if (vueltas_maximas == 0) {
                    running = false;
                    seconds = 0;
                }

                handler.postDelayed(this, 1000);
            }
        });
    }
}
