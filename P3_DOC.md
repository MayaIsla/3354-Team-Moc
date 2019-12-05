# Project Assignment #3

## 1. Design Patterns

### Singleton Pattern

**Problem:** We wanted all of the three calculator fragments to be able to share their calculation. However, we didn't want to duplicate the 'share' floating action button for each fragment. We were able to put the FAB and its onClick event handler in the main activity; however, the fragments needed to communicate both their inputs and outputs to the main activity. We decided the cleanest and simplest way to do this was to use a singleton. 

**Implementation:** We created a ShareDataSingleton class with a static getInstance() method. Whenever any of the fragments' calculations changed, they would call ShareDataSingleton.getInstance().setCurrentInput() and ShareDataSingleton.getInstance().setCurrentResult(). This way, the MainActivity's FAB event handler could call ShareDataSingleton.getInstance().getCurrentInput() and ShareDataSingleton.getInstance().getCurrentResult().

**Location:** com.moc.sharecalc.ui.ShareDataSingleton

## 2. Test classes

### 2.1. com.moc.sharecalc.util.ExpressionTests 
This test class (and its two nested test classes, bolded below) tests the Expression.evaluate() method as well as its many helper functions (e.g. preprocessors, top-down parser, etc.) **50 test cases by Jonathan, 4 test cases by Alex E.**
* makeNegationExplicitTest - tests Expression.makeNegationExplicit (2 tests by Jonathan)
* makeMultiplicationExplicit - tests Expression.makeMultiplicationExplicit (4 tests by Jonathan)
* makeSubtractionUseAddition - tests Expression.makeSubtractionUseAddition (3 tests by Jonathan)
* preprocessExpression - tests Expression.preprocessExpression (3 tests by Jonathan)
* **GetTokenFromStringTests** - **nested test class** to test Expression.getTokensFromString (12 tests by Jonathan, 1 test by Alex)
* **FactorialTests** - **nested test class** to test factorial operation in an expression (12 tests by Jonathan)
* evaluationTests - tests Expression.evaluate (8 tests by Jonathan, 3 tests by Alex)
* bitwiseTests - tests Expression.evaluate for bitwise operators (5 tests by Jonathan)

### 2.2. com.moc.sharecalc.util.ExpressionInputUtilsTests
This test class tests the ExpressionInputUtils methods. **28 tests by Jonathan**
* regexValidatorTests - test ExpressionInputUtils.PATTERN_VALID_AT_CURSOR (8 tests by Jonathan)
* moveCursorRight - test ExpressionInputUtils.moveCursorRight (10 tests by Jonathan)
* moveCursorLeft - test ExpressionInputUtils.moveCursorLeft (10 tests by Jonathan)

### 2.3. com.moc.sharecalc.unitutil.UnitConversionTests
This test class tests Unit.convert with the various unit categories.
**15 tests by Jonathan, 2 tests by Maya, 16 tests by Alexis Z.**

### 2.4. com.moc.sharecalc.unitutil.UnitStringTests 
This test class tests the enum Unit method toString() to assure that the units spinner on the Unit Converter UI is being properly dynamically populated from enum unit. **34 tests by Alexis Vu** 
* massWeightSpinnerTests - test Unit.[MASS_AND_WEIGHT].toString() (7 tests by Alexis Vu)
* temperatureSpinnerTests - test Unit.[TEMPERATURE].toString() (3 tests by Alexis Vu)
* lengthSpinnerTests - test Unit.[LENGTH].toString() (15 tests by Alexis Vu)
* volumeSpinnerTests - test Unit.[VOLUME].toString() (9 tests by Alexis Vu)


## 3. Build instructions & functionalities
Note: This app does not target phones with small screens as fitting the buttons to such a screen is impractical. When running the app on an emulator or device, we recommend using a target device such as the Pixel 2 or Nexus 5X or a similar device.

Note: It is recommended that the emulator or phone running the app be on Android API Level 28 or above. API Level 28 is the target Android OS of the app. However, API Level 24 is the minimum to run the app.

* Using scientific calculator, the user will be able to slide the top bar to reveal functions such as SIN, LOG and COS. The result is calculated automatically as the user enters the expression.

* The unit converter will calulate all conversions for a given unit and amount.

* In the programmer calculator, the user can perform operations like XOR, OR, AND, etc. on binary, hex, decimal, and octal numbers (even different types in the same expression, like 0xA<<0b11|0o44)
