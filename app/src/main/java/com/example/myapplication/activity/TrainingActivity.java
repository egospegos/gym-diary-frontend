package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.adapter.TrainingAdapter;
import com.example.myapplication.entity.Exercise;
import com.example.myapplication.R;
import com.example.myapplication.entity.Training;
import com.example.myapplication.container.TrainingExerciseContainer;
import com.example.myapplication.container.TrainingScheduleContainer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class TrainingActivity extends AppCompatActivity {

    TextView currentDate;
    Calendar dateAndTime=Calendar.getInstance();

    TrainingAdapter adapter;
    ListView trainingsList;
    Button btnFinish;
    List<Training> trainings = TrainingExerciseContainer.getTrainings();
    Map<Date, List<Training>> trainingSchedule = TrainingScheduleContainer.getExerciseSchedule();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);
        currentDate=(TextView)findViewById(R.id.currentDate);
        setInitialDateTime();

        setInitialData();
        btnFinish = (Button) findViewById(R.id.finishButton);
        if (trainings.size() == 0){
            btnFinish.setEnabled(false);
        }
        else btnFinish.setEnabled(true);


        // слушатель выбора в списке
        AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                // получаем выбранный пункт
                Training selectedTraining = (Training) parent.getItemAtPosition(position);

                // Говорим между какими Activity будет происходить связь
                Intent intent = new Intent(TrainingActivity.this, SetInformationActivity.class);

                // указываем первым параметром ключ, а второе значение
                // по ключу мы будем получать значение с Intent
                intent.putExtra("name", selectedTraining.getExercise().getName());
                intent.putExtra("image", selectedTraining.getExercise().getImage());
                intent.putExtra("description", selectedTraining.getExercise().getDescription());
                intent.putExtra("muscles", selectedTraining.getExercise().getMuscles());
                intent.putExtra("imageBase64", selectedTraining.getExercise().getImageBase64());
                intent.putExtra("position", position);

                // показываем новое Activity
                startActivity(intent);

            }
        };
        trainingsList.setOnItemClickListener(itemListener);

        /*trainingsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> list, View v, int pos, long id) {
                trainings.remove(pos);
            }
        });*/

    }

    public void addExercise(View view) {

        Intent myIntent = new Intent(TrainingActivity.this, ExerciseForTrainingActivity.class);

        TrainingActivity.this.startActivity(myIntent);

    }

    public void finishTraining (View view){

        dateAndTime.set(Calendar.MILLISECOND, 0);
        dateAndTime.set(Calendar.SECOND, 0);
        dateAndTime.set(Calendar.MINUTE, 0);
        dateAndTime.set(Calendar.HOUR_OF_DAY, 0);

        List<Training> trainingsToCalendar = new ArrayList<>();
        Exercise addEx;
        int addApproaches;
        int addWeight;
        int addRepeats;
        for(int i=0; i<trainings.size(); i++)
        {
            addEx= trainings.get(i).getExercise();
            addApproaches = trainings.get(i).getApproaches();
            addWeight = trainings.get(i).getWeight();
            addRepeats = trainings.get(i).getRepeats();
            trainingsToCalendar.add(new Training(addEx, addWeight, addRepeats, addApproaches));
        }

        trainingSchedule.put(dateAndTime.getTime(), trainingsToCalendar);

        trainings.clear();


        Intent myIntent = new Intent(TrainingActivity.this, MainActivity.class);
        TrainingActivity.this.startActivity(myIntent);
    }

    public void deleteExercise(View view){
        System.out.println("dsfs");
        this.trainingsList.getSelectedItemPosition();
    }

    // отображаем диалоговое окно для выбора даты
    public void setDate(View v) {
        new DatePickerDialog(TrainingActivity.this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }


    // установка начальных даты и времени
    private void setInitialDateTime() {
        currentDate.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));

    }

    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();
        }
    };

    private void setInitialData(){

        // Принимаем
        if (getIntent().getStringExtra("name") != null)
        {
            String txtName = getIntent().getStringExtra("name");
            String txtMuscles = getIntent().getStringExtra("muscles");
            String txtDescription = getIntent().getStringExtra("description");
            int intImage = getIntent().getIntExtra("image", 0);
            String imageBase64 = getIntent().getStringExtra("imageBase64");
            Exercise ex = new Exercise(txtName, txtMuscles, intImage, txtDescription, imageBase64);
            trainings.add(new Training(ex, 0,0,0));
        }

        if(getIntent().getIntExtra("position", 100) != 100)
        {
            int position = getIntent().getIntExtra("position", 100);
            int weight = getIntent().getIntExtra("weight", 0);
            int repeats = getIntent().getIntExtra("repeats", 0);
            int approaches = getIntent().getIntExtra("approaches", 0);
            trainings.get(trainings.size()-1-position).setWeight(weight);
            trainings.get(trainings.size()-1-position).setRepeats(repeats);
            trainings.get(trainings.size()-1-position).setApproaches(approaches);
        }
        // получаем элемент ListView
        trainingsList = (ListView) findViewById(R.id.trainingList);
        // создаем адаптер
        adapter = new TrainingAdapter(this, R.layout.training_item, trainings);
        // устанавливаем адаптер
        trainingsList.setAdapter(adapter);
    }
}
