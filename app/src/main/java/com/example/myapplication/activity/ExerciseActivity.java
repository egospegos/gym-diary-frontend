package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.myapplication.adapter.ExerciseAdapter;
import com.example.myapplication.container.AvailableExerciseContainer;
import com.example.myapplication.entity.Exercise;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class ExerciseActivity extends AppCompatActivity {

    EditText editText;
    ExerciseAdapter adapter;
    ListView exercisesList;
    List<String> names = new ArrayList<>();
    List<Exercise> exercises2 = new ArrayList<>();

    List<Exercise> exercises = AvailableExerciseContainer.getExercises();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        editText=(EditText)findViewById(R.id.txtsearch);
        // начальная инициализация списка
        setInitialData();

        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    // reset listview
                    setInitialData();
                } else {
                    // perform search
                    searchItem(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        // слушатель выбора в списке
        AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                // получаем выбранный пункт
                Exercise selectedExercise = (Exercise) parent.getItemAtPosition(position);

                // Говорим между какими Activity будет происходить связь
                Intent intent = new Intent(ExerciseActivity.this, SelectedExerciseActivity.class);

                // указываем первым параметром ключ, а второе значение
                // по ключу мы будем получать значение с Intent
                intent.putExtra("name", selectedExercise.getName());
                intent.putExtra("image", selectedExercise.getImage());
                intent.putExtra("description", selectedExercise.getDescription());
                intent.putExtra("muscles", selectedExercise.getMuscles());
                intent.putExtra("imageBase64", selectedExercise.getImageBase64());

                // показываем новое Activity
                startActivity(intent);

                //Toast.makeText(getApplicationContext(), "Было выбрано упражнение: " + selectedExercise.getName(),
                //        Toast.LENGTH_SHORT).show();
            }
        };
        exercisesList.setOnItemClickListener(itemListener);
    }

    public void searchItem(String textToSearch){
        String textToSearch1 = textToSearch.toLowerCase(); //в случае чего к нижнему регистру приводим строку поиска
        for(String item:names){
            if(!item.contains(textToSearch1)){
                for(int i=0; i<exercises2.size(); i++){
                    if(exercises2.get(i).getName().toLowerCase().equals(item))
                        exercises2.remove(exercises2.get(i));
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void setInitialData(){

        names.clear();
        exercises2.clear();
        for(int i=0; i<exercises.size();i++)
            exercises2.add(new Exercise(exercises.get(i).getName(),exercises.get(i).getMuscles(), exercises.get(i).getImage(), exercises.get(i).getDescription(), exercises.get(i).getImageBase64()));

        for(int i=0; i<exercises.size();i++)
            names.add(exercises.get(i).getName().toLowerCase());

        // получаем элемент ListView
        exercisesList = (ListView) findViewById(R.id.exercisesList);
        // создаем адаптер
        adapter = new ExerciseAdapter(this, R.layout.exercise_item, exercises2);
        // устанавливаем адаптер
        exercisesList.setAdapter(adapter);
    }
}
