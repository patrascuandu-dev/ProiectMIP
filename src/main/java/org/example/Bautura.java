package org.example;

public class Bautura extends Produs {
    private int volum; // in milliliters

    public Bautura(String nume, double pret, int volum) {
        super(nume, pret);
        this.volum = volum;
    }

    public int getVolum() {
        return volum;
    }

    @Override
    public String toString() {
        return String.format("%s - Volum : %dml", super.toString(), volum);
    }
}

