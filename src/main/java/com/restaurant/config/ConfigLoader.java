package com.restaurant.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Încărcător simplificat pentru fișiere config JSON mici (fără dependențe externe).
 * Suportă câmpurile: restaurantName (string), tva (number), menuExportFile (string).
 */
public class ConfigLoader {
    private static final Pattern NAME_PATTERN = Pattern.compile("\"restaurantName\"\\s*:\\s*\"([^\"]*)\"");
    private static final Pattern TVA_PATTERN = Pattern.compile("\"tva\"\\s*:\\s*([0-9]+(?:\\.[0-9]+)?)");
    private static final Pattern EXPORT_PATTERN = Pattern.compile("\"menuExportFile\"\\s*:\\s*\"([^\"]*)\"");

    /**
     * Încarcă configurarea dintr-un fișier JSON.
     * Aruncă NoSuchFileException dacă fișierul lipsește.
     * Aruncă InvalidConfigException pentru erori de format sau valori invalide.
     */
    public static Config load(String path) throws NoSuchFileException, InvalidConfigException, IOException {
        Path p = Path.of(path);
        String content = Files.readString(p);
        if (content == null) return new Config();
        String trimmed = content.trim();
        if (trimmed.isEmpty()) return new Config();

        // Validare de bază: trebuie să înceapă cu { și să se termine cu }
        if (!trimmed.startsWith("{") || !trimmed.endsWith("}")) {
            throw new InvalidConfigException("Fișierul de configurare nu are format JSON valid.");
        }

        Config cfg = new Config();

        try {
            Matcher mName = NAME_PATTERN.matcher(content);
            if (mName.find()) {
                cfg.setRestaurantName(mName.group(1));
            }

            Matcher mTva = TVA_PATTERN.matcher(content);
            if (mTva.find()) {
                try {
                    double t = Double.parseDouble(mTva.group(1));
                    cfg.setTva(t);
                } catch (NumberFormatException ex) {
                    throw new InvalidConfigException("Valoare TVA invalidă în config.", ex);
                }
            }

            Matcher mExport = EXPORT_PATTERN.matcher(content);
            if (mExport.find()) {
                cfg.setMenuExportFile(mExport.group(1));
            }
        } catch (IllegalStateException e) {
            throw new InvalidConfigException("Eroare la parsarea configurării.", e);
        }

        return cfg;
    }
}
