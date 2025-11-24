package com.restaurant.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Pizza este un tip special de Mancare, construit cu Builder.
 * Builder-ul impune setarea blatului si a sosului.
 */
public final class Pizza extends Mancare {
    private final String blat;
    private final String sos;
    private final List<String> toppinguri;

    private Pizza(String nume, double pret, int gramaj, String blat, String sos, List<String> toppinguri) {
        super(nume, pret, gramaj);
        this.blat = blat;
        this.sos = sos;
        this.toppinguri = new ArrayList<>(toppinguri);
    }

    public String getBlat() { return blat; }
    public String getSos() { return sos; }
    public List<String> getToppinguri() { return Collections.unmodifiableList(toppinguri); }

    @Override
    public String descriere() {
        return super.getNume() + " – " + super.getPret() + " RON – Blat: " + blat + " – Sos: " + sos + " – Toppinguri: " + toppinguri;
    }

    public static class Builder {
        private final String nume;
        private final double pret;
        private final int gramaj;
        private final String blat; // obligatoriu
        private final String sos;  // obligatoriu
        private final List<String> toppinguri = new ArrayList<>();

        public Builder(String nume, double pret, int gramaj, String blat, String sos) {
            if (nume == null || nume.isBlank()) throw new IllegalArgumentException("Nume pizza invalid");
            if (blat == null || blat.isBlank()) throw new IllegalArgumentException("Blat invalid");
            if (sos == null || sos.isBlank()) throw new IllegalArgumentException("Sos invalid");
            this.nume = nume;
            this.pret = pret;
            this.gramaj = gramaj;
            this.blat = blat;
            this.sos = sos;
        }

        public Builder adaugaTopping(String topping) {
            if (topping != null && !topping.isBlank()) toppinguri.add(topping);
            return this;
        }

        public Pizza build() {
            return new Pizza(nume, pret, gramaj, blat, sos, toppinguri);
        }
    }
}

