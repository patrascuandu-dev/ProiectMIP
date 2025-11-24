package com.restaurant.model;

public final class Mancare implements Produs {
    private String nume;
    private double pret;
    private int gramaj;

    public Mancare(String nume, double pret, int gramaj) {
        this.nume = nume;
        this.pret = pret;
        this.gramaj = gramaj;
    }

    public String getNume() { return nume; }
    public double getPret() { return pret; }
    public int getGramaj() { return gramaj; }

    @Override
    public String descriere() {
        return nume + " – " + pret + " RON – Gramaj: " + gramaj + "g";
    }

    @Override
    public String toString() {
        return descriere();
    }
}
