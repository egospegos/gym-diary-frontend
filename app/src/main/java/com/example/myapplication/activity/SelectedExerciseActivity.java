package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.container.AvailableExerciseContainer;
import com.example.myapplication.entity.Exercise;
import com.example.myapplication.R;
import com.example.myapplication.container.UserContainer;
import com.example.myapplication.data.model.LoggedInUser;

import java.util.List;

public class SelectedExerciseActivity extends AppCompatActivity {

    private TextView name;
    private TextView description;
    private TextView muscles;
    private ImageView image;
    private Button buttonDelete;
    List<Exercise> exercises = AvailableExerciseContainer.getExercises();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_exercise);

        buttonDelete = (Button) findViewById(R.id.deleteButton);

        LoggedInUser admin = UserContainer.getAdmin();
        if(admin.isLogged()) buttonDelete.setVisibility(View.VISIBLE);
        else buttonDelete.setVisibility(View.INVISIBLE);

        name = (TextView) findViewById(R.id.nametxt);
        description = (TextView) findViewById(R.id.descriptiontxt);
        muscles = (TextView) findViewById(R.id.musclestxt);
        image = (ImageView) findViewById(R.id.image2);
        // Принимаем название, описание и картинку
        String txtName = getIntent().getStringExtra("name");
        String txtDescription = getIntent().getStringExtra("description");
        String txtMuscles = getIntent().getStringExtra("muscles");
        int intImage = getIntent().getIntExtra("image", 0);
        String imageBase64 = getIntent().getStringExtra("imageBase64");

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

    public void deleteExercise(View view) {

        Exercise deleteEx = null;
        String name = getIntent().getStringExtra("name");
        for(int i=0; i < exercises.size(); i++)
        {
            if (exercises.get(i).getName().equals(name))
                deleteEx = exercises.get(i);
        }
        exercises.remove(deleteEx);

        finish();


    }
}
