package org.example;

public abstract class Produs {
    private String nume;
    private double pret;

    public Produs(String nume, double pret) {
        this.nume = nume;
        this.pret = pret;
    }

    public String getNume() {
        return nume;
    }

    public double getPret() {
        return pret;
    }

    // common textual representation (name and price)
    @Override
    public String toString() {
        return String.format("%s - %.1f RON", nume, pret);
    }
}

