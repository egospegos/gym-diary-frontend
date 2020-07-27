package com.example.myapplication.activity.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.activity.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.container.UserContainer;
import com.example.myapplication.data.model.LoggedInUser;

public class AdminMainActivity extends AppCompatActivity {


    Button buttonExercise;
    Button buttonLogIn;
    Button buttonLogOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);
        buttonLogIn =  (Button) findViewById(R.id.button14);
        buttonLogOut =  (Button) findViewById(R.id.button15);
        buttonLogIn.setVisibility(View.INVISIBLE);
        buttonLogOut.setVisibility(View.VISIBLE);
    }

    public void openLogOut(View view) {
        LoggedInUser admin = UserContainer.getAdmin();
        admin.setLogged(false);

        Intent myIntent = new Intent(AdminMainActivity.this, MainActivity.class);
        AdminMainActivity.this.startActivity(myIntent);

    }

    public void openExercises(View view) {

        Intent myIntent = new Intent(AdminMainActivity.this, AdminExerciseActivity.class);

        AdminMainActivity.this.startActivity(myIntent);

    }

    public void addExercise(View view){
        Intent myIntent = new Intent(AdminMainActivity.this, AdminNewExerciseActivity.class);

        AdminMainActivity.this.startActivity(myIntent);
    }

}
