
package com.example.vesihiisi;

import static com.example.vesihiisi.Utilities.randomFromArrayList;

import java.util.ArrayList;

/**
 * Provides random motivation messages about hydration.
 */
public class MotivationMessages {
    private ArrayList<String> messages = new ArrayList<>();

    public MotivationMessages() {
        messages.add("Vesihiisi sanoo, juo vettä!");
        messages.add("Vesilasi päivässä pitää lääkärin loitolla!");
        messages.add("Jos on kuuma, ylitä päivittäinen tavoitteesi!");
        messages.add("Ottaisit bissen sijasta vettä!");
        messages.add("Muista nauttia vettä myös urheillessasi");
        messages.add("Kuntoiluun huikka vettä vartin välein");
        messages.add("Ei vettä, ei liikuntaa");
        messages.add("Mt. Everistin valloittamiseen tarvitsee vähintään 100l vettä");
        messages.add("Nesteen tarve on yksilöllinen");
        messages.add("Kipeänä veden tarve korostuu");
        messages.add("Myös ruuasta saa vettä");
        messages.add("Juo vettä t: vesihiisi");
    }

    /**
     *
     * @return a random motivational message from pre-defined messages.
     */
    public String getRandomMessage() {
        return randomFromArrayList(this.messages);
    }
}