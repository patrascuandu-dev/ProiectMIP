package com.restaurant.model;

import java.util.*;
import java.util.stream.Collectors;

public class Meniu {
    private final Map<Categorie, List<Produs>> categorii = new EnumMap<>(Categorie.class);

    public Meniu() {
        for (Categorie c : Categorie.values()) {
            categorii.put(c, new ArrayList<>());
        }
    }

    public void adaugaProdus(Categorie categorie, Produs produs) {
        if (categorie == null || produs == null) throw new IllegalArgumentException("Categorie sau produs null");
        categorii.get(categorie).add(produs);
    }

    public List<Produs> getProduseDinCategorie(Categorie categorie) {
        List<Produs> list = categorii.get(categorie);
        if (list == null) return Collections.emptyList();
        return Collections.unmodifiableList(list);
    }

    public Optional<Produs> cautaDupaNume(String nume) {
        if (nume == null || nume.isBlank()) return Optional.empty();
        String t = nume.trim().toLowerCase();
        return categorii.values().stream()
                .flatMap(List::stream)
                .filter(p -> p.getNume().toLowerCase().equals(t))
                .findFirst();
    }

    // Interogări
    public List<Mancare> produseVegetarieneSortate() {
        return categorii.values().stream()
                .flatMap(List::stream)
                .filter(p -> p instanceof Mancare)
                .map(p -> (Mancare) p)
                .filter(Mancare::isVegetarian)
                .sorted(Comparator.comparing(Mancare::getNume))
                .collect(Collectors.toList());
    }

    public OptionalDouble pretMediuPentruCategorie(Categorie categorie) {
        List<Produs> list = categorii.getOrDefault(categorie, Collections.emptyList());
        return list.stream().mapToDouble(Produs::getPret).average();
    }

    public boolean existaProdusPestePret(double prag) {
        return categorii.values().stream()
                .flatMap(List::stream)
                .anyMatch(p -> p.getPret() > prag);
    }
}

