package com.example.listviewsadapters;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class DrinkActivity extends AppCompatActivity {

    public static final String EXTRA_DRINKID = "drinkId";
    private StarbuzzDataBaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_drink);
        int drinkId = getIntent().getIntExtra(EXTRA_DRINKID, -1);
        dbHelper = new StarbuzzDataBaseHelper(this);

        Log.d("DrinkActivity", "Drink ID recibido: " + drinkId);

        if (drinkId != -1) {
            Cursor cursor = dbHelper.getDrinkById(drinkId);
            if (cursor != null && cursor.moveToFirst()) {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(StarbuzzDataBaseHelper.COLUMN_NAME));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(StarbuzzDataBaseHelper.COLUMN_DESCRIPTION));
                int imageResourceId = cursor.getInt(cursor.getColumnIndexOrThrow(StarbuzzDataBaseHelper.COLUMN_IMAGE_RESOURCE_ID));
                cursor.close();

                TextView nameTextView = findViewById(R.id.name);
                nameTextView.setText(name);

                TextView descriptionTextView = findViewById(R.id.description);
                descriptionTextView.setText(description);

                ImageView photo = findViewById(R.id.photo);
                photo.setImageResource(imageResourceId);
                photo.setContentDescription(name);
            }
        }

    }
}
