package com.hajalibayram.copy_top10downloadapps14_09_1;

/**
 * Created by User on 9/15/2016.
 */
public class ClassInstance {

    private String name;// informasiyani hansi adla yazmaq isteyirsen, ve ya hansi informasiya lazimdi;
    // Tag

    //Getters and setters mutleq olmalidi
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return "Name: "+ getName();
    }
}
