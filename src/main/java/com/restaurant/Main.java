package com.restaurant;

import com.restaurant.config.Config;
import com.restaurant.config.ConfigLoader;
import com.restaurant.config.InvalidConfigException;
import com.restaurant.model.*;
import com.restaurant.service.MeniuService;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.List;
import java.util.OptionalDouble;

public class Main {
    public static void main(String[] args) {
        MeniuService service = new MeniuService();
        Meniu meniu = service.initializareMeniu();

        Config cfg = new Config(); // defaults
        try {
            cfg = ConfigLoader.load("config.json");
        } catch (NoSuchFileException e) {
            System.out.println("Eroare: Fișierul de configurare 'config.json' lipsește. Se vor folosi valorile implicite.");
        } catch (InvalidConfigException e) {
            System.out.println("Eroare: Fișierul de configurare este corupt sau are format invalid. Se vor folosi valorile implicite.");
        } catch (IOException e) {
            System.out.println("Eroare la citirea fișierului de configurare: " + e.getMessage() + ". Se vor folosi valorile implicite.");
        }

        // Set TVA from config (with validation inside setTVA)
        try {
            Comanda.setTVA(cfg.getTva());
        } catch (IllegalArgumentException e) {
            System.out.println("Valoare TVA invalidă în configurație. Se folosește TVA implicit: " + Comanda.TVA);
        }

        // Display menu with configured restaurant name
        service.afiseazaMeniu(meniu, cfg.getRestaurantName());

        // 1) Produse vegetariene sortate alfabetic
        System.out.println("\n--- Produse vegetariene (sortate alfabetic) ---");
        List<Mancare> vegetariene = meniu.produseVegetarieneSortate();
        vegetariene.forEach(p -> System.out.println("- " + p.getNume()));

        // 2) Pret mediu pentru desert
        System.out.println("\n--- Preț mediu pentru deserturi ---");
        OptionalDouble medieDesert = meniu.pretMediuPentruCategorie(Categorie.DESERT);
        if (medieDesert.isPresent()) {
            System.out.printf("Preț mediu deserturi: %.2f RON\n", medieDesert.getAsDouble());
        } else {
            System.out.println("Nu există deserturi în meniu.");
        }

        // 3) Există preparat > 100 RON?
        System.out.println("\n--- Există preparat cu preț > 100 RON? ---");
        boolean existaScump = meniu.existaProdusPestePret(100.0);
        System.out.println(existaScump ? "Da, avem preparate scumpe." : "Nu, toate preparatele sunt <= 100 RON.");

        // 4) Căutare sigură în meniu (Optional)
        System.out.println("\n--- Căutare produs (sigură) ---");
        String cautat = "Tiramisu";
        meniu.cautaDupaNume(cautat)
                .ifPresentOrElse(
                        p -> System.out.println("Produs găsit: " + p.descriere()),
                        () -> System.out.println("Produsul '" + cautat + "' nu a fost găsit."));

        // 5) Construire pizza customizabilă cu Builder
        System.out.println("\n--- Construire pizza custom ---");
        Pizza pizzaCustom = new Pizza.Builder("Pizza Custom Deluxe", 55.0, 500, "Pan", "BBQ")
                .adaugaTopping("Mozzarella")
                .adaugaTopping("Ciuperci")
                .adaugaTopping("Salam")
                .build();
        System.out.println(pizzaCustom.descriere());

        // Adăugăm pizza în meniu la fel principal și o afișăm
        meniu.adaugaProdus(Categorie.FEL_PRINCIPAL, pizzaCustom);
        System.out.println("\nAm adăugat pizza custom în categoria FEL_PRINCIPAL.");

        // Export meniu to JSON
        try {
            meniu.exportToJson(cfg.getMenuExportFile());
            System.out.println("Meniu exportat cu succes în '" + cfg.getMenuExportFile() + "'.");
        } catch (IOException e) {
            System.out.println("Eroare: Nu am putut exporta meniul: " + e.getMessage());
        }
    }
}
