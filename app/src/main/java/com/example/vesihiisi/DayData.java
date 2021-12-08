package com.example.vesihiisi;

import java.util.Date;

public class DayData {
    private Date date;
    private double consumption;
    private double targetConsumption;
    private int age;
    private double weight;

    public DayData(int age, double weight) {
        this.date = new Date();
        this.age = age;
        this.weight = weight;
        this.targetConsumption = getTargetConsumption(age, weight);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setConsumption(double consumption) {
        this.consumption = consumption;
    }

    public double getTargetConsumption() {
        return targetConsumption;
    }

    public void setTargetConsumption(double targetConsumption) {
        this.targetConsumption = targetConsumption;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    private static double getTargetConsumption(int age, double weight) {
        return age * weight;
    }


    public void consume(int amount) {
        this.consumption += amount;
    }

    public double getConsumption() {
        return this.consumption;
    }

    public void setConsumption(int amount) {
        this.consumption = amount;
    }
}
