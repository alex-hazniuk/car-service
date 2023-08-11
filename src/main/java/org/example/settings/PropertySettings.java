package org.example.settings;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class PropertySettings implements Settings {

    private final Properties properties;

    public PropertySettings() {
        this.properties = readProperties("src/main/resources/garageSlot.properties");
    }

    public static Properties readProperties(String path) {
        Properties property = new Properties();
        try {
            property.load(new FileInputStream(
                    path));
        } catch (IOException e) {
            throw new IllegalArgumentException("This property file is absent");
        }
        return property;
    }

    @Override
    public boolean isGarageSlotAdditionEnabled() {
        return properties.getProperty("Ability-To-Add").equals("ON");
    }

    @Override
    public boolean isGarageSlotDeletionEnabled() {
        return properties.getProperty("Ability-To-Delete").equals("ON");
    }
}
