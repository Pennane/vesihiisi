package com.example.vesihiisi;

import java.util.Date;

/**
 * Water consumption data class with target consumption computing
 */
public class DayData {
    private Date date;
    private double consumption;
    private double targetConsumption;
    private int age;
    private double weight;

    /**
     * Constructor for DayData objects.
     * Calculates targetConsumption amount using local method getTargetConsumption
     * @param age in years
     * @param weight in kilograms
     * @param gender as "male", "female" or "other"
     */
    public DayData(int age, double weight, String gender) {
        this.date = new Date();
        this.age = age;
        this.weight = weight;
        this.targetConsumption = getTargetConsumption(age, weight, gender);
    }

    /**
     * Calculate how much water should person drink per day in millilitres
     *
     * The computed amount is loosely based on various sources from the internet.
     *
     * @param age in years
     * @param weight in kilograms
     * @param gender as "male", "female" or "other"
     * @return
     */
    private static double getTargetConsumption(int age, double weight, String gender) {
        double computedConsumption;
        if (age < 15) {
            computedConsumption = (0.05*weight + 0.7) * 1000;
        } else if (age > 40) {
            computedConsumption = (0.319 * weight + 0.0473) * 1000;
        } else {
            computedConsumption = age * weight;
        }

        if (gender.equals("male")) {
            computedConsumption *= 1.03;
        } else if (gender.equals("female")) {
          //
        } else {
            computedConsumption *= 1.015;
        }

        return computedConsumption;
    }

    /**
     * Adds given amount to the daily consumption
     * @param amount in millilitres
     *
     */
    public void consume(int amount) {
        this.consumption += amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    /**
     *
     * @return target water consumption in millilitres
     */
    public double getTargetConsumption() {
        return targetConsumption;
    }

    /**
     *
     * @param targetConsumption in millilitres
     */
    public void setTargetConsumption(double targetConsumption) {
        this.targetConsumption = targetConsumption;
    }

    /**
     *
     * @return age in years
     */
    public int getAge() {
        return age;
    }

    /**
     *
     * @param age in years
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     *
     * @return weight in kilograms
     */
    public double getWeight() {
        return weight;
    }

    /**
     *
     * @param weight in kilograms
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     *
     * @return current daily consumption in millilitres
     */
    public double getConsumption() {
        return this.consumption;
    }

    /**
     *
     * @param amount in millilitres
     */
    public void setConsumption(int amount) {
        this.consumption = amount;
    }

    @Override
    public String toString() {
        return this.date.toString();
    }
}
