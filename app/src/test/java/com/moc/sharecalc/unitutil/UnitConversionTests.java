package com.moc.sharecalc.unitutil;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class UnitConversionTests {



    // Basic unit conversion tests.
    // Note that we use a precision delta of 0.5 to ignore rounding errors.
    @ParameterizedTest
    @MethodSource("basicConvertTestProvider")
    void basicConvertTest(double amount, Unit unit, double toAmount, Unit toUnit) {
        assertEquals(toAmount, unit.convertTo(toUnit, amount), 0.5);
    }
    private static Stream<Arguments> basicConvertTestProvider() {
        return Stream.of(
                // Because temperature conversions are special, it helps to test them extra thoroughly
                Arguments.of(55, Unit.FAHRENHEIT, 12.8, Unit.CELSIUS),
                Arguments.of(55, Unit.FAHRENHEIT, 55, Unit.FAHRENHEIT),
                Arguments.of(55, Unit.FAHRENHEIT, 285.9, Unit.KELVIN),

                Arguments.of(55, Unit.CELSIUS, 55, Unit.CELSIUS),
                Arguments.of(55, Unit.CELSIUS, 131, Unit.FAHRENHEIT),
                Arguments.of(55, Unit.CELSIUS, 328.15, Unit.KELVIN),

                Arguments.of(55, Unit.KELVIN, -218.5, Unit.CELSIUS),
                Arguments.of(55, Unit.KELVIN, -360.7, Unit.FAHRENHEIT),
                Arguments.of(55, Unit.KELVIN, 55, Unit.KELVIN),

                Arguments.of(55, Unit.METERS, 55, Unit.METERS),
                Arguments.of(55, Unit.METERS, 55E2, Unit.CENTIMETERS),
                Arguments.of(55, Unit.FEET, 660, Unit.INCHES),
                Arguments.of(55E-8, Unit.LIGHTSECONDS, 1.803E2, Unit.YARDS),
                Arguments.of(55E-5, Unit.MILES, 8.851E2, Unit.MILLIMETERS),
                Arguments.of(55E-5, Unit.DECAMETERS, 5500, Unit.MICROMETERS)

                );
    }


    @ParameterizedTest
    @MethodSource("basicVolumeConvertTestProvider")
    void basicVolumeConvertTest(double amount, Unit unit, double toAmount, Unit toUnit) {
        assertEquals(toAmount, unit.convertTo(toUnit, amount), 0.5);
    }

    private static Stream<Arguments> basicVolumeConvertTestProvider() {
        return Stream.of(
        //Conversion Test for Volume units
        Arguments.of(10, Unit.LITERS, 10, Unit.LITERS),
        Arguments.of(10, Unit.LITERS, 10E3, Unit.MILLILITERS),
        Arguments.of(10, Unit.LITERS, 2028.84, Unit.TEASPOONS),
        Arguments.of(10, Unit.LITERS, 676.28, Unit.TABLESPOONS),
        Arguments.of(10, Unit.LITERS, 338.14, Unit.FL_OZ),
        Arguments.of(10, Unit.LITERS, 42.2675, Unit.CUPS),
        Arguments.of(10, Unit.LITERS, 21.1338, Unit.PINTS),
        Arguments.of(10, Unit.LITERS, 10.5669, Unit.QUARTS),
        Arguments.of(10, Unit.LITERS, 2.64172, Unit.GALLONS)

        );

    }


    @ParameterizedTest
    @CsvSource({
            "55,CELSIUS,'in kelvin: 328.15\nin fahrenheit: 131.0\n'"
            // Additional tests not needed at this time because we have covered the main/only equivalency class
    })
    void getConversionsTests(Double inputAmount, Unit inputUnit, String output) {
        assertEquals(output, Unit.getConversions(inputUnit, inputAmount));
    }

}