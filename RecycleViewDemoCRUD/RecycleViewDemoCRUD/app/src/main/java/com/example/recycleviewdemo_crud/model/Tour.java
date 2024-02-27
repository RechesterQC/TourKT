package com.example.recycleviewdemo_crud.model;

public class Tour {
    private int img;
    private String name,describe;

    public Tour() {
    }

    public Tour(int img, String name, String describe) {
        this.img = img;
        this.name = name;
        this.describe = describe;
    }
    public boolean checkExist(Tour tourToCheck) {
        if (this.name.equals(tourToCheck.getName()) &&
                this.describe.equals(tourToCheck.getDescribe()) &&
                this.img == tourToCheck.getImg()) {
            return true;
        } else {
            return false;
        }
    }
    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

}
