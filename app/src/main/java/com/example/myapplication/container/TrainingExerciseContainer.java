package com.example.myapplication.container;

import com.example.myapplication.entity.Training;

import java.util.ArrayList;
import java.util.List;

public class TrainingExerciseContainer {
    private static List<Training> trainings = new ArrayList<>();


    public static List<Training> getTrainings(){
        return trainings;
    }
}
