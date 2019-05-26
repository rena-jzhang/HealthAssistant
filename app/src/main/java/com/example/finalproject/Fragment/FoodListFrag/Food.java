package com.example.finalproject.Fragment.FoodListFrag;

public class Food {

    //食物信息
    private int calorie;
    private String name;
    private int picId;
    private int amt;

    //构造函数
    public Food(int Cal, String Name, int pic,int amt) {
        this.calorie = Cal;
        this.name = Name;
        this.picId = pic;
        this.amt=amt;
    }

    //getter setter
    public void setAmt(int amt) {
        this.amt = amt;
    }
    public int getAmt() {
        return amt;
    }
    public String getName() {
        return name;
    }
    public int getCalorie() {
        return calorie;
    }
    public int getPicId() {
        return picId;
    }
}
