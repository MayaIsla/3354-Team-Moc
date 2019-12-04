# Project Assignment #3

## 1. Design Patterns

### Singleton Pattern

**Problem:** We wanted all of the three calculator fragments to be able to share their calculation. However, we didn't want to duplicate the 'share' floating action button for each fragment. We were able to put the FAB and its onClick event handler in the main activity; however, the fragments needed to communicate both their inputs and outputs to the main activity. We decided the cleanest and simplest way to do this was to use a singleton. 

**Implementation:** We created a ShareDataSingleton class with a static getInstance() method. Whenever any of the fragments' calculations changed, they would call ShareDataSingleton.getInstance().setCurrentInput() and ShareDataSingleton.getInstance().setCurrentResult(). This way, the MainActivity's FAB event handler could call ShareDataSingleton.getInstance().getCurrentInput() and ShareDataSingleton.getInstance().getCurrentResult().

## 2. Test classes

## 3. Build instructions & functionalities
