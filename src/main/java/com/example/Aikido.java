package com.example;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Aikido {
    private int kyuGraduation = 6; 
    private int currentMonthAmount = 0;
    private int sessionAmount = 0;
    
    private Map<LocalDate, Integer> sessions = new HashMap<>();

    public int getSessionAmount() {
        return sessionAmount;
    }

    public int getCurrentMonthAmount() {
        return currentMonthAmount;
    }

    public int getKyuGraduation() {
        return kyuGraduation;
    }

    public Map<LocalDate, Integer> getSessions() {
        return sessions;
    }
            
    public void addSession(String sessionDate, Integer sessionDuration) {
        // Increasing the sessionAmount for later check
        sessionAmount += 1;
    
        LocalDate parsedSessionDate;
        try {
            parsedSessionDate = LocalDate.parse(sessionDate, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please enter in YYYY-MM-DD format.");
            return;
        }
    
        // Storing the parsed session date and duration in the sessions Map
        sessions.put(parsedSessionDate, sessionDuration);
    
        // Checking if the session is older than 6 months
        LocalDate sixMonthsAgo = LocalDate.now().minusMonths(6);
    
        // If the session date is older than 6 months, increment currentMonthAmount by 6
        if (parsedSessionDate.isBefore(sixMonthsAgo)) {
            currentMonthAmount += 6; 
        }
    
        // Printing session details
        System.out.println("Session added: " + parsedSessionDate + " (" + sessionDuration + " minutes)");
    }
    
    
    public int getTotalTrainingTime() {
        int totalTrainingTime = 0;
        for (Map.Entry<LocalDate, Integer> entry : sessions.entrySet()) {
            totalTrainingTime += entry.getValue();
        }
        return totalTrainingTime;
    }
    
    public void checkGraduationEligibility() {
        if (kyuGraduation <= 1) {
            System.out.println("You already own the best belt! BLACK BELT!");
        }

        if (sessionAmount >= 100 || currentMonthAmount == 6) {
            kyuGraduation -= 1;
            System.out.println("You are eligible for the new Belt! Your current level is " + kyuGraduation);
            currentMonthAmount = 0;
            sessionAmount = 0;
        } else {
            System.out.println("You are not eligible for belt upgrade! Keep gathering experience!");
        }
    }
        
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Aikido aikido = new Aikido();
        
        while (true) {
            System.out.println("===== Aikido Practice Tracker =====\n" + //
                                "\n" + //
                                "1. Add Practice Session\n" + //
                                "2. View Total Practice Time\n" + //
                                "3. Check Graduation Eligibility\n" + //
                                "4. Exit Choose an option:");
            
            int answer = scanner.nextInt();

            switch (answer) {
                case 1:
                    // Get date input
                    System.out.println("Please provide the date in YYYY-MM-DD form: ");
                    scanner.nextLine();
                    String sessionDate = scanner.nextLine();
                    
                    // Get duration input
                    System.out.println("Please provide the duration in minutes: ");
                    int sessionDuration = scanner.nextInt();
                    aikido.addSession(sessionDate, sessionDuration);
                    break;
        
                case 2:
                    int practiseTime = aikido.getTotalTrainingTime();
                    System.out.println("Total practise time is: " + practiseTime);
                    break;

                case 3: 
                    aikido.checkGraduationEligibility();
                    break;
                
                case 4:
                    System.out.println("Exiting program...");
                    scanner.close();
                    return;
            }
        }
    }
}
