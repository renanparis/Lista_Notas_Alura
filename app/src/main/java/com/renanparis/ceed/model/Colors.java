package com.renanparis.ceed.model;

import android.graphics.Color;

import java.util.Arrays;
import java.util.List;

public class Colors {


    private int blue = Color.parseColor("#408EC9");
    private int white = Color.parseColor("#FFFFFF");
    private int red = Color.parseColor("#EC2F4B");
    private int green = Color.parseColor("#9ACD32");
    private int yellow = Color.parseColor("#F9F256");
    private int lilac = Color.parseColor("#F1CBFF");
    private int Grey = Color.parseColor("#D2D4DC");
    private int brown = Color.parseColor("#A47C48");
    private int purple = Color.parseColor("#BE29EC");

    public List<Integer> allColors() {

        return Arrays.asList(blue, white, red, green, yellow, lilac, Grey, brown, purple);

    }


    public int getWhite() {
        return white;
    }


}
