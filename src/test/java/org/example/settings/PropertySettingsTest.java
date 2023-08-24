package org.example.settings;

import org.junit.jupiter.api.Test;



import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PropertySettingsTest {

    @Test
    void givenFileExists_whenReadSettings_thenExpectedSettingsReturned(){
        var properties = PropertySettings.readProperties("src/test/resources/garageSlot.properties");

        assertEquals(properties.getProperty("Ability-To-Add"),("OFF"));
        assertEquals(properties.getProperty("Ability-To-Delete"),("ON"));
    }

    @Test
    void givenFileDoNotExist_whenCreateSettings_thenIllegalArgumentExceptionThrown() {

        assertThatThrownBy(() -> PropertySettings.readProperties("non-existent-file"))
                .isInstanceOf(IllegalArgumentException.class);

    }
}