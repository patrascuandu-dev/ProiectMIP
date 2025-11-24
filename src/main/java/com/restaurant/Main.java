package com.restaurant;

import com.restaurant.model.*;
import com.restaurant.service.HappyHourDiscount;
import com.restaurant.service.MeniuService;

import java.time.LocalTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        MeniuService service = new MeniuService();
        List<Produs> produse = service.initializareMeniu();
        service.afiseazaMeniu(produse);

        // Construim o comanda
        Comanda comanda = new Comanda();

        Produs pizza = findProdusByNume(produse, "Pizza Margherita");
        if (pizza != null) comanda.adaugaProdus(pizza, 2);

        Produs ciorba = findProdusByNume(produse, "Ciorbă de văcuță");
        if (ciorba != null) comanda.adaugaProdus(ciorba, 1);

        Produs limonada = findProdusByNume(produse, "Limonadă");
        if (limonada != null) comanda.adaugaProdus(limonada, 1);

        // Adăugăm o băutură alcoolică care nu este în meniu, pentru demonstrație
        Bautura vin = new Bautura("Vin Roșu", 25.0, 200, true);
        comanda.adaugaProdus(vin, 1);

        System.out.println("\n--- Comanda curentă ---");
        System.out.println(comanda);

        double subtotal = comanda.calculeazaSubtotal();
        System.out.printf("Subtotal: %.2f RON\n", subtotal);
        System.out.printf("TVA (%.0f%%): %.2f RON\n", Comanda.TVA * 100, subtotal * Comanda.TVA);
        System.out.printf("Total fără discount: %.2f RON\n", comanda.calculeazaTotal(null));

        // Demonstrație Happy Hour: 20% reducere la băuturi alcoolice - forțăm activarea pentru demo
        HappyHourDiscount happy = new HappyHourDiscount(LocalTime.of(17, 0), LocalTime.of(19, 0), 0.20, true);
        double totalCuDiscount = comanda.calculeazaTotal(happy);
        System.out.printf("Total cu Happy Hour (20%% pe băuturi alcoolice): %.2f RON\n", totalCuDiscount);
    }

    private static Produs findProdusByNume(List<Produs> produse, String nume) {
        for (Produs p : produse) {
            if (p.getNume().equalsIgnoreCase(nume)) return p;
        }
        return null;
    }
}
