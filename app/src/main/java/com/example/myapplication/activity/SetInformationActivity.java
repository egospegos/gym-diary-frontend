package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;

public class SetInformationActivity extends AppCompatActivity {

    private TextView name;
    private TextView description;
    private TextView muscles;
    private ImageView image;
    private EditText weight;
    private EditText repeats;
    private EditText approaches;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_information);

        name = (TextView) findViewById(R.id.nametxt2);
        description = (TextView) findViewById(R.id.descriptiontxt2);
        muscles = (TextView) findViewById(R.id.musclestxt2);
        image = (ImageView) findViewById(R.id.image3);
        // Принимаем название, описание и картинку
        String txtName = getIntent().getStringExtra("name");
        String txtDescription = getIntent().getStringExtra("description");
        String txtMuscles = getIntent().getStringExtra("muscles");
        int intImage = getIntent().getIntExtra("image", 0);
        String imageBase64 = getIntent().getStringExtra("imageBase64");
        position = getIntent().getIntExtra("position", 0);

        // выводим принятые параметры
        name.setText(name.getText().toString() + " " + txtName);
        description.setText(description.getText().toString() + " " + txtDescription);
        muscles.setText(muscles.getText().toString() + " " + txtMuscles);
        //image.setImageResource(intImage);
        if (imageBase64 != null && imageBase64.length()>0){
            byte[] decodedString = Base64.decode(imageBase64, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            image.setImageBitmap(decodedByte);
        }

    }


    public void applyInformation(View view){
        weight = (EditText) findViewById(R.id.weight);
        repeats = (EditText) findViewById(R.id.repeats);
        approaches = (EditText) findViewById(R.id.approaches);

        int w = Integer.parseInt(weight.getText().toString());
        int r = Integer.parseInt(repeats.getText().toString());
        int a = Integer.parseInt(approaches.getText().toString());

        Intent intent = new Intent(SetInformationActivity.this, TrainingActivity.class);

        // указываем первым параметром ключ, а второе значение
        // по ключу мы будем получать значение с Intent
        intent.putExtra("weight", w);
        intent.putExtra("repeats", r);
        intent.putExtra("approaches", a);
        intent.putExtra("position", position);

        // показываем новое Activity
        startActivity(intent);
    }


}
