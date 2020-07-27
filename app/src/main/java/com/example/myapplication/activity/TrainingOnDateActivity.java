package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.myapplication.adapter.TrainingOnDateAdapter;
import com.example.myapplication.R;
import com.example.myapplication.entity.Training;
import com.example.myapplication.container.TrainingScheduleContainer;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class TrainingOnDateActivity extends AppCompatActivity {

    TrainingOnDateAdapter adapter;
    ListView trainingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_on_date);

        Date date = new Date(getIntent().getLongExtra("date", -1));

        Map<Date, List<Training>> x = TrainingScheduleContainer.getExerciseSchedule();
        List<Training> trainingOnDate = TrainingScheduleContainer.getExerciseSchedule().get(date);
        // получаем элемент ListView
        trainingList = (ListView) findViewById(R.id.trainingOnDateList);
        // создаем адаптер
        adapter = new TrainingOnDateAdapter(this, R.layout.training_on_date_item, trainingOnDate);
        // устанавливаем адаптер
        trainingList.setAdapter(adapter);
    }
}
