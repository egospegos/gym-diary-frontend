package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.entity.Training;
import com.example.myapplication.container.TrainingScheduleContainer;
import com.squareup.timessquare.CalendarPickerView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class CalendarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar);

        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);

        CalendarPickerView calendar = (CalendarPickerView) findViewById(R.id.calendar_view);
        Date today = new Date();

        Calendar calYesterday = Calendar.getInstance();
        calYesterday.set(Calendar.MILLISECOND, 0);
        calYesterday.set(Calendar.SECOND, 0);
        calYesterday.set(Calendar.MINUTE, 0);
        calYesterday.set(Calendar.HOUR_OF_DAY, 0);
        calYesterday.set(Calendar.YEAR, 2020);
        calYesterday.set(Calendar.MONTH, 4);
        calYesterday.set(Calendar.DAY_OF_MONTH, 1);
        Date yesterday = calYesterday.getTime();


        calendar.init(yesterday, nextYear.getTime())
                .withSelectedDate(today);

        calendar.highlightDates(TrainingScheduleContainer.getExerciseSchedule().keySet());

        calendar.setOnDateSelectedListener(new com.squareup.timessquare.CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                getTrainingOnDate(date);
            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });

    }


    public void getTrainingOnDate(Date selected_date){
         List<Training> trainingOnDate = TrainingScheduleContainer.getExerciseSchedule().get( selected_date );
         if (trainingOnDate != null)
         {
             Intent intent = new Intent(CalendarActivity.this, TrainingOnDateActivity.class);

             intent.putExtra("date", selected_date.getTime());

             startActivity(intent);
         }
         Map<Date, List<Training>> trainingSchedule = TrainingScheduleContainer.getExerciseSchedule();
    }
}
