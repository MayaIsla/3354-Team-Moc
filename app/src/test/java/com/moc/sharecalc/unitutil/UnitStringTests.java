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
    
    @After
    public void tearDown() throws Exception {

    }
}
