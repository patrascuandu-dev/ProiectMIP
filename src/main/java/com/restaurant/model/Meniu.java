package com.restaurant.model;

import java.io.FileWriter;
import java.io.IOException;
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

    // Simple JSON escaping for strings
    private static String esc(String s) {
        if (s == null) return "";
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            switch (c) {
                case '\\' -> sb.append("\\\\");
                case '"' -> sb.append("\\\"");
                case '\b' -> sb.append("\\b");
                case '\f' -> sb.append("\\f");
                case '\n' -> sb.append("\\n");
                case '\r' -> sb.append("\\r");
                case '\t' -> sb.append("\\t");
                default -> {
                    if (c < 0x20) sb.append(String.format("\\u%04x", (int)c));
                    else sb.append(c);
                }
            }
        }
        return sb.toString();
    }

    /**
     * Exportă meniul curent într-un fișier JSON (formatat) la calea specificată.
     * Creează o reprezentare simplificată pentru a păstra tipurile și a evita problemele de serializare polymorphic.
     */
    public void exportToJson(String filePath) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append('{').append('\n');
        int catCount = 0;
        for (Map.Entry<Categorie, List<Produs>> e : categorii.entrySet()) {
            sb.append("  \"").append(e.getKey().name()).append("\": [\n");
            List<Produs> list = e.getValue();
            for (int i = 0; i < list.size(); i++) {
                Produs p = list.get(i);
                sb.append("    {");
                // type
                sb.append("\"type\": \"").append(esc(p.getClass().getSimpleName())).append("\"");
                // nume
                sb.append(", \"nume\": \"").append(esc(p.getNume())).append("\"");
                // pret
                sb.append(", \"pret\": ").append(p.getPret());
                if (p instanceof Mancare m) {
                    sb.append(", \"gramaj\": ").append(m.getGramaj());
                    sb.append(", \"vegetarian\": ").append(m.isVegetarian());
                }
                if (p instanceof Bautura b) {
                    sb.append(", \"volum\": ").append(b.getVolum());
                    sb.append(", \"alcoolica\": ").append(b.isAlcoolica());
                }
                if (p instanceof Pizza piz) {
                    sb.append(", \"blat\": \"").append(esc(piz.getBlat())).append("\"");
                    sb.append(", \"sos\": \"").append(esc(piz.getSos())).append("\"");
                    sb.append(", \"toppinguri\": [");
                    List<String> tops = piz.getToppinguri();
                    for (int j = 0; j < tops.size(); j++) {
                        sb.append('\"').append(esc(tops.get(j))).append('\"');
                        if (j < tops.size() - 1) sb.append(", ");
                    }
                    sb.append(']');
                }
                sb.append('}');
                if (i < list.size() - 1) sb.append(',');
                sb.append('\n');
            }
            sb.append("  ]");
            catCount++;
            if (catCount < categorii.size()) sb.append(',');
            sb.append('\n');
        }
        sb.append('}').append('\n');

        try (FileWriter fw = new FileWriter(filePath)) {
            fw.write(sb.toString());
        }
    }
}
