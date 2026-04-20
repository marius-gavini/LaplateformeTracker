package com.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatisticsManagerTest {

    @Test
    void calculateStatisticsShouldReturnAggregatedValuesFromDAO() {
        StudentDAO stubDao = new StudentDAO() {
            @Override
            public int getStudentCount() {
                return 7;
            }

            @Override
            public int getStudentCountByAgeRange(int minAge, int maxAge) {
                if (minAge == 0 && maxAge == 17) {
                    return 2;
                }
                if (minAge == 18 && maxAge == 25) {
                    return 4;
                }
                if (minAge == 26 && maxAge == 120) {
                    return 1;
                }
                return 0;
            }
        };

        StudentStatistics stats = StatisticsManager.calculateStatistics(stubDao);

        assertNotNull(stats);
        assertEquals(7, stats.getTotalStudents());
        assertEquals(2, stats.getStudentsUnder18());
        assertEquals(4, stats.getStudents18To25());
        assertEquals(1, stats.getStudentsOver25());
    }
}
