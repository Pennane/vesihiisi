package com.example.vesihiisi;

import java.util.ArrayList;

/**
 * Calculates the amount of completed days
 * @author Adnan Avni
 * @author Nafisul Nazrul
 */
public class TrophyCalculator {

    public TrophyCalculator() {
    }

    /**
     * Searches the amount of completed days from daydatalist
     * @param dayDataList
     * @return Amount of completed days
     */

    public int amountOfCompletedDays(ArrayList<DayData> dayDataList) {
        return (int) dayDataList.stream().filter(dayData -> dayData.getConsumption() >= dayData.getTargetConsumption()).count();
    }
}
