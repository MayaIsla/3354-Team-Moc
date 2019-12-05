package com.moc.sharecalc.unitutil;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

/*
    This unit testing class determines whether the second spinner in the unit converter UI is being
    properly populated with unit bases from the Unit enum.
 */
public class UnitStringTests {

    @Before
    public void setUp() throws Exception {

    }

    /*
        compares the friendly, human-readable mass and weight string to the value that should
        be in the second spinner
        e.g. Unit.KILOGRAMS --> 'kilograms'
     */
    @Test
    public void massWeightSpinnerTests()  {
        assertEquals(Unit.KILOGRAMS.toString(), "kilograms");
        assertEquals(Unit.GRAMS.toString(), "grams");
        assertEquals(Unit.MILLIGRAMS.toString(), "milligrams");
        assertEquals(Unit.CENTIGRAMS.toString(), "centigrams");
        assertEquals(Unit.OUNCES.toString(), "ounces");
        assertEquals(Unit.POUNDS.toString(), "pounds");
        assertEquals(Unit.TONS.toString(), "tons");
    }
    
    /*
        compares the friendly, human-readable temperature string to the value that should
        be in the second spinner
        e.g. Unit.KELVIN --> 'Kelvin'
     */
    @Test
    public void temperatureSpinnerTests()  {
        assertEquals(Unit.KELVIN.toString(), "Kelvin");
        assertEquals(Unit.FAHRENHEIT.toString(), "Fahrenheit");
        assertEquals(Unit.CELSIUS.toString(), "Celsius");
    }
    
    /*
        compares the friendly, human-readable length string to the value that should
        be in the second spinner
        e.g. Unit.METERS --> 'meters'
     */
    @Test void lengthSpinnerTests()  {
        assertEquals(Unit.METERS.toString(), "meters");
        assertEquals(Unit.MICROMETERS.toString(), "micrometers");
        assertEquals(Unit.MILLIMETERS.toString(), "millimeters");
        assertEquals(Unit.CENTIMETERS.toString(), "centimeters");
        assertEquals(Unit.DECIMETERS.toString(), "decimeters");
        assertEquals(Unit.DECAMETERS.toString(), "decameters");
        assertEquals(Unit.KILOMETERS.toString(), "kilometers");
        assertEquals(Unit.INCHES.toString(), "inches");
        assertEquals(Unit.FEET.toString(), "feet");
        assertEquals(Unit.YARDS.toString(), "yards");
        assertEquals(Unit.MILES.toString(), "miles");
        assertEquals(Unit.LIGHTSECONDS.toString(), "lightseconds");
        assertEquals(Unit.LIGHTMINUTES.toString(), "lightminutes");
        assertEquals(Unit.LIGHTMONTHS.toString(), "lightmonths");
        assertEquals(Unit.LIGHTYEARS.toString(), "lightyears");
    }
    
    /*
        compares the friendly, human-readable volume string to the value that should
        be in the second spinner
        e.g. Unit.LITERS --> 'liters'
     */
    @Test
    public void volumeSpinnerTests()  {
        assertEquals(Unit.LITERS.toString(), "liters");
        assertEquals(Unit.MILLILITERS.toString(), "milliliters");
        assertEquals(Unit.TEASPOONS.toString(), "teaspoons");
        assertEquals(Unit.TABLESPOONS.toString(), "tablespoons");
        assertEquals(Unit.FL_OZ.toString(), "fl oz");
        assertEquals(Unit.CUPS.toString(), "cups");
        assertEquals(Unit.PINTS.toString(), "pints");
        assertEquals(Unit.QUARTS.toString(), "quarts");
        assertEquals(Unit.GALLONS.toString(), "gallons");
    }
    
    @After
    public void tearDown() throws Exception {

    }
}
