package com.river.interfaceDemo;

public interface InterfaceExample {

    void func1();

    default void func2() {
        System.out.println("func2");
    }

    int x = 123;
    //int y;                // Variable 'y' might not have been initialized
    int z = 0;       // Modifier 'public' is redundant(多余的) for interface fields
    //private int k = 0;    // Modifier 'private' not allowed here
    //protected int l = 0;  // Modifier 'protected' not allowed here
    //private void func3(); // Modifier 'private' not allowed here
}
