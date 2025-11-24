package org.example;

public class Mancare extends Produs {
    private int gramaj; // in grams

    public Mancare(String nume, double pret, int gramaj) {
        super(nume, pret);
        this.gramaj = gramaj;
    }

    public int getGramaj() {
        return gramaj;
    }

    @Override
    public String toString() {
        // reuse base representation and append specific attribute
        return String.format("%s - Gramaj : %dg", super.toString(), gramaj);
    }
}

