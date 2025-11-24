package com.restaurant.model;

public final class Bautura implements Produs {
    private String nume;
    private double pret;
    private int volum;
    private boolean alcoolica;

    public Bautura(String nume, double pret, int volum) {
        this(nume, pret, volum, false);
    }

    public Bautura(String nume, double pret, int volum, boolean alcoolica) {
        this.nume = nume;
        this.pret = pret;
        this.volum = volum;
        this.alcoolica = alcoolica;
    }

    public String getNume() { return nume; }
    public double getPret() { return pret; }
    public int getVolum() { return volum; }
    public boolean isAlcoolica() { return alcoolica; }

    @Override
    public String descriere() {
        return nume + " – " + pret + " RON – Volum: " + volum + "ml" + (alcoolica ? " – Alcoolică" : "");
    }

    @Override
    public String toString() {
        return descriere();
    }
}
