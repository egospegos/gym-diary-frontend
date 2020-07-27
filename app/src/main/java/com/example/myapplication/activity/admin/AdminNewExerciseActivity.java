package com.example.myapplication.activity.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.myapplication.container.AvailableExerciseContainer;
import com.example.myapplication.entity.Exercise;
import com.example.myapplication.R;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AdminNewExerciseActivity extends AppCompatActivity {

    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    private static final int PICK_IMAGE = 14;
    private String imageBase64FromGallery;
    private static OkHttpClient client = new OkHttpClient();
    private ObjectMapper objectMapper = new ObjectMapper();
    private EditText name;
    private Spinner muscles;
    private EditText description;
    List<Exercise> exercises = AvailableExerciseContainer.getExercises();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_exercise);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //get the spinner from the xml.
        Spinner dropdown = findViewById(R.id.spinner1);
//create a list of items for the spinner.
        String[] items = new String[]{"Спина", "Ноги", "Грудь", "Бицепс", "Трицепс", "Плечи", "Пресс"};
//create an adapter to describe how the items are displayed, adapters are used in several places in android.
//There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
//set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);
    }

    public void addImage(View view){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                //Display an error
                return;
            }

            InputStream imageStream = null;
            try {
                imageStream = getContentResolver().openInputStream(data.getData());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            selectedImage.compress(Bitmap.CompressFormat.JPEG,100,baos);
            byte[] b = baos.toByteArray();
            String encImage = Base64.encodeToString(b, Base64.DEFAULT);
            imageBase64FromGallery = encImage;
            //InputStream inputStream = context.getContentResolver().openInputStream(data.getData());
            //Now you can do whatever you want with your inpustream, save it as file, upload to a server, decode a bitmap...
        }
    }

    public void applyInformation(View view){
        name = (EditText) findViewById(R.id.name);
        muscles = (Spinner) findViewById(R.id.spinner1);
        description = (EditText) findViewById(R.id.description);

        String n = name.getText().toString();
        String m = muscles.getSelectedItem().toString();
        String d = description.getText().toString();
        int image = R.drawable.default_back;


        if (m.equals("Ноги")) image = R.drawable.default_legs;
        if (m.equals("Грудь")) image = R.drawable.default_chest;
        if (m.equals("Бицепс")) image = R.drawable.default_bice;
        if (m.equals("Трицепс")) image = R.drawable.default_trice;
        if (m.equals("Пресс")) image = R.drawable.default_press;

        Exercise newExercise = new Exercise(n, m, image, d, imageBase64FromGallery);

        try {
            String json = objectMapper.writeValueAsString(newExercise);
            post("http://10.0.2.2:8081/exercise",json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        exercises.add(newExercise);

        finish();
    }


    private void post(String url, String json) {
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        finally {
            response.close();
        }
    }
}
