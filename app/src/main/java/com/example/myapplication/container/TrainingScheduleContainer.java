package com.example.myapplication.container;

import com.example.myapplication.entity.Training;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrainingScheduleContainer {
    private static Map<Date, List<Training>> exerciseSchedule = new HashMap<>();

    public static Map<Date, List<Training>> getExerciseSchedule() {
        return exerciseSchedule;
    }
}
