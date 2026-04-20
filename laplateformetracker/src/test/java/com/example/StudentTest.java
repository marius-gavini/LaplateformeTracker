package com.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    @Test
    void constructorAndAccessorMethodsShouldWork() {
        Student student = new Student(5, "Marie", "Durand", 19, 1);

        assertEquals(5, student.getId());
        assertEquals("Marie", student.getFirstName());
        assertEquals("Durand", student.getLastName());
        assertEquals(19, student.getAge());
        assertEquals(1, student.getPromotion());

        student.setId(10);
        student.setFirstName("Paul");
        student.setLastName("Martin");
        student.setAge(21);
        student.setPromotion(2);

        assertEquals(10, student.getId());
        assertEquals("Paul", student.getFirstName());
        assertEquals("Martin", student.getLastName());
        assertEquals(21, student.getAge());
        assertEquals(2, student.getPromotion());
    }
}
