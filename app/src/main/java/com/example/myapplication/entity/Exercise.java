package com.example.myapplication.entity;

import com.example.myapplication.R;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Exercise {
    @JsonProperty
    private String name;
    @JsonProperty
    private String muscles;
    @JsonProperty
    private String description;
    @JsonProperty
    private String imageBase64;
    private int image = R.drawable.default_bice;

    public Exercise(String name, String muscles, int image, String description, String imageBase64){

        this.name=name;
        this.muscles = muscles;
        this.image = image;
        this.description = description;
        this.imageBase64 = imageBase64;
    }

    public Exercise() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMuscles() {
        return this.muscles;
    }

    public void setMuscles(String muscles) {
        this.muscles = muscles;
    }

    public int getImage() {
        return this.image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }
}
