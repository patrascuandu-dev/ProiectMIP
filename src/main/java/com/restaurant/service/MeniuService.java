package com.restaurant.service;

import com.restaurant.model.*;

import java.util.ArrayList;
import java.util.List;

public class MeniuService {
    public List<Produs> initializareMeniu() {
        List<Produs> meniu = new ArrayList<>();
        meniu.add(new Mancare("Pizza Margherita", 45.00, 450));
        meniu.add(new Mancare("Ciorbă de văcuță", 35.00, 400));
        meniu.add(new Bautura("Limonadă", 15.00, 400));
        meniu.add(new Bautura("Limonadă", 20.00, 500));
        return meniu;
    }

    public void afiseazaMeniu(List<Produs> produse) {
        System.out.println("Meniu Restaurantului \"La Andrei\"");
        for (Produs p : produse) {
            System.out.println("> " + p.descriere());
        }
    }
}
