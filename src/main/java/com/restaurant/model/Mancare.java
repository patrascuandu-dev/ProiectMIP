package com.restaurant.model;

public class Mancare implements Produs {
    private String nume;
    private double pret;
    private int gramaj;
    private boolean vegetarian;

    public Mancare(String nume, double pret, int gramaj) {
        this(nume, pret, gramaj, false);
    }

    public Mancare(String nume, double pret, int gramaj, boolean vegetarian) {
        this.nume = nume;
        this.pret = pret;
        this.gramaj = gramaj;
        this.vegetarian = vegetarian;
    }

    public String getNume() { return nume; }
    public double getPret() { return pret; }
    public int getGramaj() { return gramaj; }
    public boolean isVegetarian() { return vegetarian; }

    @Override
    public String descriere() {
        return nume + " – " + pret + " RON – Gramaj: " + gramaj + "g" + (vegetarian ? " – Vegetarian" : "");
    }

    @Override
    public String toString() {
        return descriere();
    }
}
