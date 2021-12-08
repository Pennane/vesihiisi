
package com.example.vesihiisi;

import java.util.ArrayList;

public class MotivationMessages {
    private ArrayList<String> messages = new ArrayList<>();

    public MotivationMessages() {
        messages.add("Vesihiisi sanoo juo vettä!");
        messages.add("Vesilasi päivässä pitää lääkärin loitolla!");
        messages.add("Jos on kuuma, ylitä päivittäinen tavoitteesi!");
        messages.add("Ottaisit bissen sijasta vettä!");
        messages.add("Muista nauttia vettä myös urheillessasi");
    }

    public String getRandomMessage() {
        int index = (int)(Math.random() * messages.size());
        return messages.get(index);
    }
}