package com.gmail.maxsvynarchuk.util;

import java.util.ResourceBundle;

public enum ResourceManager {
    VIEW(ResourceBundle.getBundle("properties.view"));

    private ResourceBundle resourceBundle;

    ResourceManager(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    /**
     * Gets a string for the given key from this resource bundle
     *
     * @param key the key for the desired string
     * @return the string for the given key
     */
    public String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}