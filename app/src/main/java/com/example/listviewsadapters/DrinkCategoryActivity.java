package com.example.listviewsadapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class DrinkCategoryActivity extends AppCompatActivity {

    private StarbuzzDataBaseHelper dbHelper;
    private ArrayList<String> drinkNames;

    private ArrayList<Integer> drinkIds;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_drink_category);

        dbHelper = new StarbuzzDataBaseHelper(this);
        drinkNames = new ArrayList<>();
        loadDrinksFromDatabase();

        ArrayAdapter<String> listAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                drinkNames);

        ListView listDrinks = findViewById(R.id.list_drinks);
        listDrinks.setAdapter(listAdapter);

        listDrinks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int realId = drinkIds.get(position);
                Intent intent = new Intent(DrinkCategoryActivity.this, DrinkActivity.class);
                intent.putExtra(DrinkActivity.EXTRA_DRINKID, realId);
                startActivity(intent);
            }
        });

    }

    private void loadDrinksFromDatabase() {
        Cursor cursor = dbHelper.getAllDrinks();
        drinkNames = new ArrayList<>();
        drinkIds = new ArrayList<>();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(StarbuzzDataBaseHelper.COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(StarbuzzDataBaseHelper.COLUMN_NAME));

                drinkNames.add(name);
                drinkIds.add(id);  // Guarda el ID real de la base de datos
            }
            cursor.close();
        }
    }
}
