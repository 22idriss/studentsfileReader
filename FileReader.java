import java.io.*;
import java.util.Scanner;

public class FileReader {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter your student number:");
        String studentNumber = input.nextLine();

        // Check if the user wants to look up a birthday or add a new student
        System.out.println("Do you want to add a new student (A) or look up a birthday (B)?");
        String choice = input.nextLine().toUpperCase();

        if (choice.equals("A")) {
            System.out.println("Enter your birthday:");
            String birthday = input.nextLine();

            // Check if the student already exists
            boolean studentExists = false;

            try (Scanner fileScanner = new Scanner(new File("students.txt"))) {
                while (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine();
                    if (line.contains("Student Number: " + studentNumber)) {
                        studentExists = true;
                        break;
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println("File not found");
            }

            if (studentExists) {
                System.out.println("Student with this student number already exists.");
            } else {
                // Saves the student information to the file if the details are not in the file
                try (PrintWriter writer = new PrintWriter(new FileWriter("students.txt", true))) {
                    writer.println("Student Number: " + studentNumber);
                    writer.println("Birthday: " + birthday);
                    writer.println();
                    System.out.println("Student information saved to the file succesfully.");
                } catch (IOException e) {
                    System.out.println("Error writing to the file.");
                }
            }
        } else if (choice.equals("B")) {
            // This looks up the birthday for the given student number
            try (Scanner fileScanner = new Scanner(new File("students.txt"))) {
                boolean found = false;
                while (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine();
                    if (line.contains("Student Number: " + studentNumber)) {
                        found = true;
                        // This  displays the associated birthday
                        String birthdayLine = fileScanner.nextLine();
                        System.out.println("Birthday for this student number is: " + birthdayLine.replace("Birthday: ", ""));
                        break;
                    }
                }
                if (!found) {
                    System.out.println("Student not found in the records.");
                }
            } catch (FileNotFoundException e) {
                System.out.println("File not found");
            }
        } else {
            System.out.println("Invalid choice. Please enter 'A' to add a new student or 'L' to look up a birthday.");
        }
    }
}
