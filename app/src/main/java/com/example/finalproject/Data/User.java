package com.example.finalproject.Data;


import com.example.finalproject.Fragment.FoodListFrag.Food;

import java.util.ArrayList;
import java.util.List;

public class User {

    //1 2注册和登录用
    private String password;
    private String name;
    //
    private float weight;
    private float progress;
    private float weight_record;
    private float weightTarget;
    private int targetCal;
    private int breakTarget;
    private int lunchTarget;
    private int dinnerTarget;

    //在4 7里面要用到的数据
    private List<Food> BreakfastList = new ArrayList<Food>();
    private List<Food> LunchList = new ArrayList<Food>();
    private List<Food> DinnerList = new ArrayList<Food>();

    //4 7早中晚饭的总卡数
    private int BreakfastCal;
    private int LunchCal;
    private int DinnerCal;

    public float getProgress() {
        return progress;
    }
    public void setProgress(float progress) {
        this.progress = progress;
    }

    public float getWeight_record() {
        return weight_record;
    }

    public void setWeight_record(float weight_record) {
        this.weight_record = weight_record;
    }

    public float getWeightTarget() {
        return weightTarget;
    }

    public void setWeightTarget(float weightTarget) {
        this.weightTarget = weightTarget;
    }


    public void setBreakfastCal(int breakfastCal) {
        BreakfastCal = breakfastCal;
    }

    public void setLunchCal(int lunchCal) {
        LunchCal = lunchCal;
    }

    public void setDinnerCal(int dinnerCal) {
        DinnerCal = dinnerCal;
    }

    public int getBreakTarget() {
        return breakTarget;
    }

    public void setBreakTarget(int breakTarget) {
        this.breakTarget = breakTarget;
    }

    public int getDinnerTarget() {
        return dinnerTarget;
    }

    public void setDinnerTarget(int dinnerTarget) {
        this.dinnerTarget = dinnerTarget;
    }



    public int getLunchTarget() {
        return lunchTarget;
    }

    public void setLunchTarget(int lunchTarget) {
        this.lunchTarget = lunchTarget;
    }



    public int getTargetCal() { return targetCal; }
    public void setTargetCal(int targetCal) { this.targetCal = targetCal; }

    public List<Food> getBreakfastList() {
        return BreakfastList;
    }

    public List<Food> getLunchList() {
        return LunchList;
    }

    public List<Food> getDinnerList() {
        return DinnerList;
    }



    //用底部popup设置体重
    public float getWeight() { return weight; }
    public void setWeight(float weight) { this.weight = weight; }
    //1 2 设置用户名密码
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getBreakfastCal() {
        return BreakfastCal;
    }
    public int getLunchCal() {
        return LunchCal;
    }
    public int getDinnerCal() {
        return DinnerCal;
    }

    //4添加时要用到的
    public void addBreakfast(Food food,int amt) {
        this.BreakfastList.add(food);
        this.BreakfastCal += food.getCalorie()*amt/100;
    }
    public void addLunch(Food food,int amt) {
        this.LunchList.add(food);
        this.LunchCal += food.getCalorie()*amt/100;
    }
    public void addDinner(Food food,int amt)
    {
        this.DinnerList.add(food);
        this.DinnerCal += food.getCalorie()*amt/100;
    }
}
