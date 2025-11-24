package com.restaurant.model;

import com.restaurant.service.DiscountStrategy;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Comanda {
    public static final double TVA = 0.09;

    private final Map<Produs, Integer> produse = new HashMap<>();

    public void adaugaProdus(Produs produs, int cantitate) {
        if (produs == null) throw new IllegalArgumentException("Produsul nu poate fi null");
        if (cantitate <= 0) throw new IllegalArgumentException("Cantitatea trebuie sa fie pozitiva");
        produse.merge(produs, cantitate, Integer::sum);
    }

    public Map<Produs, Integer> getProduse() {
        return Collections.unmodifiableMap(produse);
    }

    public double calculeazaSubtotal() {
        double s = 0.0;
        for (Map.Entry<Produs, Integer> e : produse.entrySet()) {
            s += e.getKey().getPret() * e.getValue();
        }
        return s;
    }

    public double calculeazaTotal(DiscountStrategy discountStrategy) {
        double subtotal = calculeazaSubtotal();
        double discount = 0.0;
        if (discountStrategy != null) {
            discount = discountStrategy.apply(this, subtotal);
            if (discount < 0) discount = 0;
            if (discount > subtotal) discount = subtotal; // avoid negative net
        }
        double net = subtotal - discount;
        return net * (1 + TVA);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Produs, Integer> e : produse.entrySet()) {
            sb.append(e.getValue()).append(" x ").append(e.getKey().getNume())
              .append(" (").append(e.getKey().getPret()).append(" RON)")
              .append(System.lineSeparator());
        }
        return sb.toString();
    }
}

