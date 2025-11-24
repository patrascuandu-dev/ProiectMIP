package com.restaurant.service;

import com.restaurant.model.Bautura;
import com.restaurant.model.Comanda;

import java.time.LocalTime;

/**
 * Aplică un discount procentual numai pentru băuturile alcoolice în intervalul orar specificat.
 * Pentru demonstrație puteți forța activarea prin parametrul forceActive.
 */
public class HappyHourDiscount implements DiscountStrategy {
    private final LocalTime start;
    private final LocalTime end;
    private final double percent; // ex: 0.20 pentru 20%
    private final boolean forceActive;

    public HappyHourDiscount(LocalTime start, LocalTime end, double percent) {
        this(start, end, percent, false);
    }

    public HappyHourDiscount(LocalTime start, LocalTime end, double percent, boolean forceActive) {
        this.start = start;
        this.end = end;
        this.percent = percent;
        this.forceActive = forceActive;
    }

    private boolean esteActiv() {
        if (forceActive) return true;
        LocalTime now = LocalTime.now();
        if (start.isBefore(end) || start.equals(end)) {
            return !now.isBefore(start) && !now.isAfter(end);
        } else {
            // interval care trece peste miezul noptii
            return !now.isBefore(start) || !now.isAfter(end);
        }
    }

    @Override
    public double apply(Comanda comanda, double subtotal) {
        if (!esteActiv()) return 0.0;
        double sumaBauturiAlcoolice = 0.0;
        for (var e : comanda.getProduse().entrySet()) {
            if (e.getKey() instanceof Bautura b && b.isAlcoolica()) {
                sumaBauturiAlcoolice += b.getPret() * e.getValue();
            }
        }
        return sumaBauturiAlcoolice * percent;
    }
}

