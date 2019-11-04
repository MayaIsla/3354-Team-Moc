package com.moc.sharecalc.util;


public interface Operator {
    int getPriority();
    int getArity();
    @Override
    String toString();
}
