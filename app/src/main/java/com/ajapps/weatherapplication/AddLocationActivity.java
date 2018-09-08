package com.ajapps.weatherapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddLocationActivity extends AppCompatActivity {

    private EditText locationText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);

        locationText = findViewById(R.id.locationName);
        Button saveBtn = findViewById(R.id.saveButton);
        Button cancelBtn = findViewById(R.id.cancelButton);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = locationText.getText().toString();
                if(!text.equals("")) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("newLocation", text);
                    setResult(Activity.RESULT_OK,intent);
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(),"Empty text entered",Toast.LENGTH_LONG).show();
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   locationText.setText("");
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                setResult(Activity.RESULT_CANCELED,intent);
                finish();
            }
        });
    }
}
