package com.restaurant.config;

public class Config {
    private String restaurantName = "La Andrei";
    private double tva = 0.09;
    private String menuExportFile = "meniu_export.json";

    public String getRestaurantName() { return restaurantName; }
    public double getTva() { return tva; }
    public String getMenuExportFile() { return menuExportFile; }

    // setters for Gson
    public void setRestaurantName(String restaurantName) { this.restaurantName = restaurantName; }
    public void setTva(double tva) { this.tva = tva; }
    public void setMenuExportFile(String menuExportFile) { this.menuExportFile = menuExportFile; }
}

