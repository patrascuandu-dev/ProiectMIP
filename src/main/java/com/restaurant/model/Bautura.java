package com.restaurant.model;

public class Bautura implements Produs {
    private String nume;
    private double pret;
    private int volum;

    public Bautura(String nume, double pret, int volum) {
        this.nume = nume;
        this.pret = pret;
        this.volum = volum;
    }

    public String getNume() { return nume; }
    public double getPret() { return pret; }
    public int getVolum() { return volum; }

    @Override
    public String descriere() {
        return nume + " – " + pret + " RON – Volum: " + volum + "ml";
    }
}
