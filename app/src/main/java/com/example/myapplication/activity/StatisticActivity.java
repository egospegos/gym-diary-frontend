package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.example.myapplication.entity.Exercise;
import com.example.myapplication.R;
import com.example.myapplication.entity.Training;
import com.example.myapplication.container.TrainingScheduleContainer;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Calendar;
import java.util.Date;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StatisticActivity extends AppCompatActivity {

    TextView countTraining;
    TextView lastMonth;
    TextView favoriteExercise;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        countTraining = (TextView) findViewById(R.id.count);
        lastMonth = (TextView) findViewById(R.id.lastMonth);
        favoriteExercise = (TextView) findViewById(R.id.favorite);

        Map<Date, List<Training>> x = TrainingScheduleContainer.getExerciseSchedule();
        long count = x.entrySet()
                .stream()
                .filter(e -> e.getKey().compareTo(new Date()) < 0)
                .count();

        Date monthAgo = new Date();
        monthAgo.setMonth(monthAgo.getMonth()-1);
        long countMonth = x.entrySet()
                .stream()
                .filter(e -> e.getKey().compareTo(new Date()) < 0)
                .filter(e -> e.getKey().compareTo(monthAgo) >0)
                .count();

        String favorite = x.entrySet()
                .stream()
                .filter(e -> e.getKey().compareTo(new Date()) < 0)
                .map(Map.Entry::getValue)
                .flatMap(List::stream)
                .map(Training::getExercise)
                .map(Exercise::getName)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow(EmptyStackException::new);

        countTraining.setText(countTraining.getText().toString() + " " + count);
        lastMonth.setText(lastMonth.getText().toString() + " " + countMonth);
        favoriteExercise.setText(favoriteExercise.getText().toString() + " " + favorite);




        // generate Dates
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.YEAR, 2020);
        calendar.set(Calendar.MONTH, 4);
        calendar.set(Calendar.DAY_OF_MONTH, 27);
        Date d1 = calendar.getTime();

        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.YEAR, 2020);
        calendar.set(Calendar.MONTH, 4);
        calendar.set(Calendar.DAY_OF_MONTH, 29);
        Date d2 = calendar.getTime();

        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.YEAR, 2020);
        calendar.set(Calendar.MONTH, 5);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date d3 = calendar.getTime();

        GraphView graph = (GraphView) findViewById(R.id.graph);

// you can directly pass Date objects to DataPoint-Constructor
// this will convert the Date to double via Date#getTime()
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(d1, 50),
                new DataPoint(d2, 65),
                new DataPoint(d3, 55)

        });
        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(d1, 15),
                new DataPoint(d2, 6),
                new DataPoint(d3, 12)

        });

        LineGraphSeries<DataPoint> series3 = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(d1, 3),
                new DataPoint(d2, 3),
                new DataPoint(d3, 3)

        });

        series.setColor(Color.BLUE);
        series2.setColor(Color.RED);
        series3.setColor(Color.GREEN);


        graph.addSeries(series);
        graph.addSeries(series2);
        graph.addSeries(series3);

// set date label formatter
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(StatisticActivity.this));
        graph.getGridLabelRenderer().setNumHorizontalLabels(3); // only 4 because of the space

// set manual x bounds to have nice steps
        graph.getViewport().setMinX(d1.getTime());
        graph.getViewport().setMaxX(d3.getTime());
        graph.getViewport().setXAxisBoundsManual(true);



// as we use dates as labels, the human rounding to nice readable numbers
// is not necessary
        graph.getGridLabelRenderer().setHumanRounding(false);

    }
}
