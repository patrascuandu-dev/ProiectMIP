package com.restaurant.service;

import com.restaurant.model.Comanda;

@FunctionalInterface
public interface DiscountStrategy {
    /**
     * Calculeaza valoarea discount-ului aplicabil unei comenzi, pe baza subtotalului.
     * Returneaza valoarea in RON (nu procent).
     */
    double apply(Comanda comanda, double subtotal);
}
