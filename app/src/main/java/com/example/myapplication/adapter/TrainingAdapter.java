package com.example.myapplication.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.entity.Training;

import java.util.List;

public class TrainingAdapter extends ArrayAdapter<Training> {

    private LayoutInflater inflater;
    private int layout;
    private List<Training> trainings;

    public TrainingAdapter(Context context, int resource, List<Training> trainings) {
        super(context, resource, trainings);
        this.trainings = trainings;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }
    public View getView(int position, View convertView, ViewGroup parent) {

        View view=inflater.inflate(this.layout, parent, false);

        ImageView imageView = (ImageView) view.findViewById(R.id.trImage);
        TextView nameView = (TextView) view.findViewById(R.id.trName);
        TextView musclesView = (TextView) view.findViewById(R.id.trMuscles);
        TextView weightView = (TextView) view.findViewById(R.id.txtweight);
        TextView repeatsView = (TextView) view.findViewById(R.id.txtrepeats);
        TextView approachesView = (TextView) view.findViewById(R.id.txtapproaches);


        Training training = trainings.get(trainings.size()-1-position); // чтобы вверху списка были новые добавленные


        //imageView.setImageResource(training.getExercise().getImage());
        String strBase64 = training.getExercise().getImageBase64();
        if (strBase64 != null && strBase64.length()>0){
            byte[] decodedString = Base64.decode(strBase64, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            imageView.setImageBitmap(decodedByte);
        }
        nameView.setText(training.getExercise().getName());
        musclesView.setText(training.getExercise().getMuscles());
        weightView.setText(weightView.getText().toString()+ " " + training.getWeight());
        repeatsView.setText(repeatsView.getText().toString()+ " " + training.getRepeats());
        approachesView.setText(approachesView.getText().toString()+ " " + training.getApproaches());


        return view;
    }
}
