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
        compares the friendly, human-readable string to the value that should be in the second
        spinner
        e.g. Unit.KILOGRAMS --> 'kilograms'
     */
    @Test
    public void massWeightSpinnerTest()  {
        assertEquals(Unit.KILOGRAMS.toString(), "kilograms");
        assertEquals(Unit.GRAMS.toString(), "grams");
        assertEquals(Unit.MILLIGRAMS.toString(), "milligrams");
        assertEquals(Unit.CENTIGRAMS.toString(), "centigrams");
        assertEquals(Unit.OUNCES.toString(), "ounces");
        assertEquals(Unit.POUNDS.toString(), "pounds");
        assertEquals(Unit.TONS.toString(), "tons");
    }
    
    @After
    public void tearDown() throws Exception {

    }
}
