package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

class AikidoTest {

    private Aikido aikido;

    @BeforeEach
    void setUp() {
        aikido = new Aikido();
    }

    @Test
    void testAddSession() {
        String sessionDate = "2021-02-22";
        Integer sessionDuration = 54;

        aikido.addSession(sessionDate, sessionDuration);

        assertEquals(54, aikido.getTotalTrainingTime());
    }

    @Test
    void testAddSessionInvalidDate() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream); 

        aikido.addSession("invalid-date", 60);

        String expectedMessage = "Invalid date format. Please enter in YYYY-MM-DD format.";

        assertEquals(expectedMessage + System.lineSeparator(), outputStream.toString(), "Error message does not match");

        System.setOut(System.out);
    }

    @Test 
    void testGetTotalTrainingTime() {
        String sessionDate = "2021-02-22";
        Integer sessionDuration = 54;

        aikido.addSession(sessionDate, sessionDuration);

        assertEquals(54, aikido.getTotalTrainingTime());
    }

    @Test
    void testCheckGraduationEligibility() {
        // Should stay as the lowes Belt Level
        aikido.checkGraduationEligibility();
        assertEquals(6, aikido.getKyuGraduation());


        String sessionDate = "2000-02-22";
        Integer sessionDuration = 54;

        aikido.addSession(sessionDate, sessionDuration);

        // The Graduation level starts from 6, this should decrese it to 5
        aikido.checkGraduationEligibility();

        assertEquals(5, aikido.getKyuGraduation());

        // Adding more to reach the Black Belt
        aikido.addSession("2000-02-21", 32);
        aikido.checkGraduationEligibility();
        aikido.addSession("2000-02-20", 41);
        aikido.checkGraduationEligibility();
        aikido.addSession("2000-02-23", 23);
        aikido.checkGraduationEligibility();
        aikido.addSession("2000-02-24", 41);
        aikido.checkGraduationEligibility();

        assertEquals(1, aikido.getKyuGraduation());

        aikido.addSession("2000-02-24", 41);
        aikido.checkGraduationEligibility();
        assertEquals(0, aikido.getKyuGraduation());
    }

    @Test
    void testGetSessionAmount() {
        Integer result = aikido.getSessionAmount();

        assertEquals(0, result);
    }

    @Test
    void testGetCurrentMonthAmount() {
        Integer result = aikido.getCurrentMonthAmount();

        assertEquals(0, result);
    }

    @Test
    void testGetSessions() {
        aikido.addSession("2023-02-02", 60);

    Map<LocalDate, Integer> sessions = aikido.getSessions();

    Map<LocalDate, Integer> expectedSessions = new HashMap<>();
    expectedSessions.put(LocalDate.parse("2023-02-02"), 60);

    assertEquals(expectedSessions, sessions);
    }
}