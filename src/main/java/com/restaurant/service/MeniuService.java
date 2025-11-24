package com.restaurant.service;

import com.restaurant.model.*;

import java.util.List;

public class MeniuService {
    public Meniu initializareMeniu() {
        Meniu meniu = new Meniu();

        // Aperitive
        meniu.adaugaProdus(Categorie.APERITIVE, new Mancare("Bruschetta", 18.0, 150, true));
        meniu.adaugaProdus(Categorie.APERITIVE, new Mancare("Salată Caesar", 25.0, 200));

        // Fel principal
        meniu.adaugaProdus(Categorie.FEL_PRINCIPAL, new Pizza.Builder("Pizza Margherita", 45.00, 450, "Subțire", "Roșii")
                .adaugaTopping("Mozzarella")
                .build());
        meniu.adaugaProdus(Categorie.FEL_PRINCIPAL, new Mancare("Ciorbă de văcuță", 35.00, 400));
        meniu.adaugaProdus(Categorie.FEL_PRINCIPAL, new Mancare("Paste Carbonara", 40.00, 350));

        // Desert
        meniu.adaugaProdus(Categorie.DESERT, new Mancare("Tiramisu", 22.0, 150));
        meniu.adaugaProdus(Categorie.DESERT, new Mancare("Panna Cotta", 20.0, 120));

        // Bauturi racoritoare
        meniu.adaugaProdus(Categorie.BAUTURI_RACORITOARE, new Bautura("Limonadă", 15.00, 400));
        meniu.adaugaProdus(Categorie.BAUTURI_RACORITOARE, new Bautura("Apă plată", 6.00, 500));

        // Bauturi alcoolice
        meniu.adaugaProdus(Categorie.BAUTURI_ALCOOLICE, new Bautura("Vin Roșu", 25.0, 200, true));
        meniu.adaugaProdus(Categorie.BAUTURI_ALCOOLICE, new Bautura("Bere Artizanală", 18.0, 500, true));

        // Un fel scump pentru test
        meniu.adaugaProdus(Categorie.FEL_PRINCIPAL, new Mancare("Steak Wagyu", 250.0, 350));

        return meniu;
    }

    public void afiseazaMeniu(Meniu meniu) {
        System.out.println("Meniu Restaurantului \"La Andrei\"");
        for (Categorie c : Categorie.values()) {
            System.out.println("== " + c + " ==");
            List<Produs> list = meniu.getProduseDinCategorie(c);
            if (list.isEmpty()) System.out.println("  (fără produse)");
            for (Produs p : list) {
                System.out.println("  > " + p.descriere());
            }
        }
    }
}
