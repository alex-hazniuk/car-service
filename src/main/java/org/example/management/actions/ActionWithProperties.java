package org.example.management.actions;

import org.example.settings.PropertySettings;

public abstract class ActionWithProperties extends Action {
    protected final PropertySettings propertySettings = new PropertySettings();
}
