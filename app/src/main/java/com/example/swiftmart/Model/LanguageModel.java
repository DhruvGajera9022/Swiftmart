package com.example.swiftmart.Model;

public class LanguageModel {
    int image;
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }



    public LanguageModel(int image, String name) {
        this.image = image;
        this.name = name;
    }
}
