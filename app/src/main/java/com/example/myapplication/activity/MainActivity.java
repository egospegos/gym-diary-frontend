package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.activity.admin.AdminMainActivity;
import com.example.myapplication.R;
import com.example.myapplication.container.UserContainer;
import com.example.myapplication.data.model.LoggedInUser;
import com.example.myapplication.ui.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    Button buttonTraining;
    Button buttonCalendar;
    Button buttonLogIn;
    Button buttonLogOut;
    Button buttonStatistic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkLogin();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        checkLogin();

    }


    public void checkLogin(){
        buttonTraining = (Button) findViewById(R.id.button11);
        buttonCalendar = (Button) findViewById(R.id.button12);
        buttonLogIn =  (Button) findViewById(R.id.button14);
        buttonLogOut =  (Button) findViewById(R.id.button15);
        buttonStatistic = (Button) findViewById(R.id.button16);
        LoggedInUser user = UserContainer.getUser();
        LoggedInUser admin = UserContainer.getAdmin();
        if(user.isLogged()){
            buttonTraining.setEnabled(true);
            buttonCalendar.setEnabled(true);
            buttonStatistic.setEnabled(true);
            buttonLogIn.setVisibility(View.INVISIBLE);
            buttonLogOut.setVisibility(View.VISIBLE);
        }

        else if (admin.isLogged()){
            Intent myIntent = new Intent(MainActivity.this, AdminMainActivity.class);

            MainActivity.this.startActivity(myIntent);

        }
        else{
            buttonTraining.setEnabled(false);
            buttonCalendar.setEnabled(false);
            buttonStatistic.setEnabled(false);
            buttonLogIn.setVisibility(View.VISIBLE);
            buttonLogOut.setVisibility(View.INVISIBLE);
        }

    }

    public void openLogin(View view) {
        Intent myIntent = new Intent(MainActivity.this, LoginActivity.class);


        MainActivity.this.startActivityForResult(myIntent,1);

    }

    public void openLogOut(View view) {
        LoggedInUser user = UserContainer.getUser();
        user.setLogged(false);
        recreate();
    }

    public void openCalendar(View view) {

        Intent myIntent = new Intent(MainActivity.this, CalendarActivity.class);

        MainActivity.this.startActivity(myIntent);

    }



    public void openExercises(View view) {

        Intent myIntent = new Intent(MainActivity.this, ExerciseActivity.class);

        MainActivity.this.startActivity(myIntent);

    }

    public void openStatistic(View view){
        Intent myIntent = new Intent(MainActivity.this, StatisticActivity.class);

        MainActivity.this.startActivity(myIntent);
    }

    public void openTraining(View view) {

        Intent myIntent = new Intent(MainActivity.this, TrainingActivity.class);

        MainActivity.this.startActivity(myIntent);

    }


}
