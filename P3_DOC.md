# Project Assignment #3

## 1. Design Patterns

### Singleton Pattern

**Problem:** We wanted all of the three calculator fragments to be able to share their calculation. However, we didn't want to duplicate the 'share' floating action button for each fragment. We were able to put the FAB and its onClick event handler in the main activity; however, the fragments needed to communicate both their inputs and outputs to the main activity. We decided the cleanest and simplest way to do this was to use a singleton. 

**Implementation:** We created a ShareDataSingleton class with a static getInstance() method. Whenever any of the fragments' calculations changed, they would call ShareDataSingleton.getInstance().setCurrentInput() and ShareDataSingleton.getInstance().setCurrentResult(). This way, the MainActivity's FAB event handler could call ShareDataSingleton.getInstance().getCurrentInput() and ShareDataSingleton.getInstance().getCurrentResult().

**Location:** com.moc.sharecalc.ui.ShareDataSingleton

## 2. Test classes

### A. ExpressionTests class (com.moc.sharecalc.util)
This test class tests the Expression.evaluate() method as well as its many helper functions (e.g. preprocessors, top-down parser, etc.) **50 test cases by Jonathan, 3 test cases by Alex.**
* makeNegationExplicitTest - tests Expression.makeNegationExplicit (2 tests by Jonathan)
* makeMultiplicationExplicit - tests Expression.makeMultiplicationExplicit (4 tests by Jonathan)
* makeSubtractionUseAddition - tests Expression.makeSubtractionUseAddition (3 tests by Jonathan)
* preprocessExpression - tests Expression.preprocessExpression (3 tests by Jonathan)
* GetTokenFromStringTests - nested test class to test Expression.getTokensFromString (12 tests by Jonathan)
* FactorialTests - nested test class to test factorial operation in an expression (12 tests by Jonathan)
* evaluationTests - test Expression.evaluate (8 tests by Jonathan, 3 tests by Alex)
* bitwiseTests - test Expression.evaluate for bitwise operators (5 tests by Jonathan)
  

## 3. Build instructions & functionalities
