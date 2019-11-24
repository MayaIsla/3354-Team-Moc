package com.moc.sharecalc.unitutil;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
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
                Arguments.of(55, Unit.KELVIN, 55, Unit.KELVIN)
        );
    }

}