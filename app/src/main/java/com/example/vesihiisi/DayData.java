package com.example.vesihiisi;

import static com.example.vesihiisi.Utilities.dateToFinnishLocaleString;

import java.util.Date;

/**
 * Water consumption data class with target consumption computing
 *
 * @author Adnan Avni
 * @author Nafisul Nazrul
 * @author Arttu Pennanen
 */
public class DayData {
    private Date date;
    private int consumption;
    private int targetConsumption;
    private int age;
    private int weight;
    private int maxConsumption;
    private String gender;


    /**
     * Constructor for DayData objects.
     * Calculates targetConsumption amount using local method getTargetConsumption
     *
     * @param age    in years
     * @param weight in kilograms
     * @param gender as "male", "female" or "other"
     */
    public DayData(int age, int weight, String gender) {
        this.date = new Date();
        this.age = age;
        this.weight = weight;
        this.gender = gender;
        this.targetConsumption = calculateTargetConsumption(age, weight, gender);
        this.maxConsumption = 5000;
    }

    /**
     * Calculate how much water should person drink per day in millilitres that is
     * clamped between 1000 and 4000 ml.
     * <p>
     * The computed amount is loosely based on various sources from the internet.
     *
     * @param age    in years
     * @param weight in kilograms
     * @param gender as "male", "female" or "other"
     * @return target consumption in millilitres
     */
    public static int calculateTargetConsumption(int age, int weight, String gender) {
        double computedConsumption;
        if (age < 15) {
            computedConsumption = (0.05 * weight + 0.7) * 1000;
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

        int clampedComputedConsumption = (int) Math.max(Math.min(computedConsumption, 4000), 1000);

        return clampedComputedConsumption;
    }

    /**
     * Adds given amount to the daily consumption.
     * Consumption can not go over defined maxConsumption value.
     *
     * @param amount in millilitres
     */
    public void consume(int amount) {
        if ((consumption + amount) > maxConsumption) {
            consumption = maxConsumption;
            return;
        }
        consumption += amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return target water consumption in millilitres
     */
    public int calculateTargetConsumption() {
        return targetConsumption;
    }

    /**
     * @return targetConsumption in millilitres
     */
    public int getTargetConsumption() {
        return targetConsumption;
    }

    /**
     * @param targetConsumption in millilitres
     */
    public void setTargetConsumption(int targetConsumption) {
        this.targetConsumption = targetConsumption;
    }

    /**
     * @return age in years
     */
    public int getAge() {
        return age;
    }

    /**
     * @param age in years
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * @return weight in kilograms
     */
    public int getWeight() {
        return weight;
    }

    /**
     * @param weight in kilograms
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }

    /**
     * @return current daily consumption in millilitres
     */
    public int getConsumption() {
        return this.consumption;
    }

    /**
     * @param amount in millilitres
     */
    public void setConsumption(int amount) {
        if (amount > maxConsumption) {
            this.consumption = maxConsumption;
            return;
        }
        this.consumption = amount;
    }

    /**
     * Returns the gender saved to the day data
     *
     * @return as "male", "female" or "other"
     */
    public String getGender() {
        return gender;
    }

    /**
     * Set the data gender
     *
     * @param gender "male", "female" or "other"
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return dateToFinnishLocaleString(this.date);
    }
}
