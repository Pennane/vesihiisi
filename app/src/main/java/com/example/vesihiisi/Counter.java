package com.example.vesihiisi;

public class Counter {
    private double targetConsumption;
    private double consume;
    private int age;
    private double weight;

    public Counter(int age, double weight) {
        this.age = age;
        this.weight = weight;
    }

    public double getTargetConsumption() {
        targetConsumption = this.age * this.weight;
        return targetConsumption;
    }

    public void consume(int amount) {
        consume += amount;
    }

    public double getConsume() {
        return this.consume;
    }

    public void setConsumption(int amount) {
        this.consume = amount;
    }
}
