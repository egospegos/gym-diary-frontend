package com.example.myapplication.entity;

public class Training {
    private Exercise exercise;
    private int weight;
    private int repeats;
    private int approaches;

    public Training(Exercise exercise, int weight, int repeats, int approaches){

        this.exercise = exercise;
        this.weight = weight;
        this.repeats = repeats;
        this.approaches = approaches;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getRepeats() {
        return repeats;
    }

    public void setRepeats(int repeats) {
        this.repeats = repeats;
    }

    public int getApproaches() {
        return approaches;
    }

    public void setApproaches(int approaches) {
        this.approaches = approaches;
    }
}
