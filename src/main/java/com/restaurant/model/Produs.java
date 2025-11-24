package com.restaurant.model;

public sealed interface Produs permits Mancare, Bautura {
    String getNume();
    double getPret();
    String descriere();
}
