package com.rakovets.course.java.core.practice.exception_handling;

public class Task2Demo {
    public static void main(String[] args) {
        try {
            Task2.illegalArray();
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("This is ArrayIndexOutOfBoundsException");
        }
    }
}
