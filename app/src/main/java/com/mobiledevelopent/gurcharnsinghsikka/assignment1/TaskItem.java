package com.mobiledevelopent.gurcharnsinghsikka.assignment1;

public class TaskItem {

    private int id;
    private String name;
    private String place;
    private String message;

    public TaskItem(){
    }

    public TaskItem(int id, String name, String place, String message) {
        this.id = id;
        this.name = name;
        this.place = place;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPlace() {
        return place;
    }

    public String getMessage() {
        return message;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
