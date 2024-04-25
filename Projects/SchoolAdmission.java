import java.util.ArrayList;
import java.util.Scanner;

public class SchoolAdmission {
    static ArrayList<String> SubjectTrace = new ArrayList<>();
    static final double MAX_UNITS = 21;
    public static void main(String[] args) {
        String[][] subjects = {
                { "IT27L"  , "Intermediate Prog."  , "4.5" },
                { "CCE101L", "Intro to Programming", "4.5" },
                { "PHY01"  , "Physics 101"         , "3.0" },
                { "PE02"   , "Physical Education 2", "3.0" },
                { "IT101"  , "Intro to Computing"  , "3.0" },
                { "DBMS1L" , "DATABASE MGT 1"      , "4.5" },
                { "ITSEC02", "Adv IT Security"     , "3.0" },
                { "CNTP402", "Computer Networks"   , "3.0" },
                { "ITETHCS", "IT Ethics"           , "3.0" },
                { "ELECT1" , "IT Elective 1"       , "4.5" },
                { "MBLDV"  , "Mobile Development"  , "4.5" },
                { "ENG1"   , "English 1"           , "3.0" }
        };
        ArrayList<String> StudentSubjects = new ArrayList<>();
        int totalUnits = 0, choice = 0;
        Scanner scnNig = new Scanner(System.in);
        String[] AccountFullName = new String[2];

        displayIntro(AccountFullName, scnNig);
        do choice = mainMenu(subjects, StudentSubjects, scnNig, totalUnits, AccountFullName);while (choice != 5);
        scnNig.close();
    }

    public static int mainMenu(String[][] subjects, ArrayList<String> StudentSubjects, Scanner scnNig, int totalUnits, String[] AccountFullName) {
        System.out.println("1. View Subject List\n2. Add Subject\n3. Remove Subject\n4. Clear List\n5. Finish Adding Subjects\n");
        System.out.print("Enter Your Choice (1-5) : ");
        while (!scnNig.hasNextInt()) {
            System.out.println("(NOTICE) PLEASE INPUT CORRECTLY!");
            System.out.print("Enter Your Choice (1-5) : ");
            scnNig.next();
        }
        int choice = scnNig.nextInt();
        displaySpace();
        switch (choice) {
            case 1:
                displayList(subjects, StudentSubjects, totalUnits);
                break;
            case 2:
                addSubject(subjects, StudentSubjects, scnNig, totalUnits);
                break;
            case 3:
                removeSubject(StudentSubjects, scnNig, totalUnits);
                break;
            case 4:
                clearList(StudentSubjects, scnNig, totalUnits);
                break;
            case 5:
                displayOutro(subjects, StudentSubjects, totalUnits, AccountFullName);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }
        return choice;
    }
    public static void displayList(String[][] subjects, ArrayList<String> StudentSubjects, int totalUnits) {
        double totalUnitsFinal = 0;
        System.out.println("**Current Subject List:**\n");
        if (StudentSubjects.isEmpty()) {
            System.out.println("There are no subjects chosen yet.\n");
        } else {
            for (int i = 0; i < StudentSubjects.size(); i++) {
                System.out.println((i + 1) + ". " + StudentSubjects.get(i));
            }
            for (int i = 0; i < subjects.length; i++) {
                String[] subject = subjects[i];
                if (SubjectTrace.contains(subject[0])) {
                    double unit = Double.parseDouble(subject[2]);
                    totalUnitsFinal += unit;
                }
            }
            System.out.println("\nTotal Units: " + totalUnitsFinal + "\n\n==========================================");
        }
    }
    public static void addSubject(String[][] subjects, ArrayList<String> StudentSubjects, Scanner scnNig, int totalUnits) {
        System.out.print("Enter Subject Code : ");
        String code = scnNig.next();
        displaySpace();
        double tempUnit = getMaxUnits(subjects, StudentSubjects, totalUnits);
        boolean found = false, duplicate = false;
        if (getMaxUnits(subjects, StudentSubjects, totalUnits) < MAX_UNITS) {
            for (int i = 0; i < StudentSubjects.size(); i++) {
                if (code.equalsIgnoreCase(SubjectTrace.get(i))) {
                    duplicate = true;
                }
            }
            if (!duplicate) {
                for (String[] subject : subjects) {
                    if (code.equals(subject[0])) {
                        if (MAX_UNITS>=(tempUnit+=Double.parseDouble(subject[2]))){
                        double unit = Double.parseDouble(subject[2]);
                        SubjectTrace.add(code);
                        StudentSubjects.add(subject[1] + " (" + unit + " units)");
                        // totalUnits += unit;
                        System.out.println(subject[1] + " (" + unit + " units) successfully added!\n==========================================");
                        getMaxUnits(subjects, StudentSubjects, totalUnits);
                        found = true;
                        break;
                        } else System.out.println("Total Units have been reached\n"); found = true;
                    }
                }
            } else {
                System.out.println("Subject si Already Selected!\n");
                found = true;
            }
        } else {
            System.out.println("Total Units have been reached\n"); found = true;
        }
        if (!found)System.out.println("Invalid subject code! Please enter a valid code.\n");
    }
    public static void removeSubject(ArrayList<String> StudentSubjects, Scanner scnNig, int totalUnits) {
        if (StudentSubjects.isEmpty()) {
            System.out.println("No subjects to remove!\n");
            return;
        }
        System.out.print("Enter the index of the subject to remove: ");
        int index = scnNig.nextInt();
        scnNig.nextLine();
        if (index < 1 || index > StudentSubjects.size()) {
            System.out.println("Invalid index! Please enter a valid index.");
        } else {
            String removedSubject = StudentSubjects.remove(index - 1);
            SubjectTrace.remove(index - 1);
            double unit = Double.parseDouble(
                    removedSubject.substring(removedSubject.lastIndexOf("(") + 1, removedSubject.lastIndexOf(" ")));
            totalUnits -= unit;
            System.out.println(removedSubject + " removed successfully!\n");
        }
    }
    public static void clearList(ArrayList<String> StudentSubjects, Scanner scnNig, int totalUnits) {
        System.out.print("clear all your selections? (Y/N) :");
        String i = scnNig.next();
        if (i.equalsIgnoreCase("y")) {
            StudentSubjects.clear();
            SubjectTrace.clear();
            totalUnits = 0;
            System.out.println("\nSubject list cleared!\n\n==========================================");
        } else {
            System.out.println("\nSubject list is not cleared!\n\n==========================================");
        }
    }
    public static double getMaxUnits(String[][] subjects, ArrayList<String> StudentSubjects, int totalUnits) {
        double totalUnitsFinal = 0;
        for (int i = 0; i < subjects.length; i++) {
            String[] subject = subjects[i];
            if (SubjectTrace.contains(subject[0])) {
                double unit = Double.parseDouble(subject[2]);
                totalUnitsFinal += unit;
            }
        }
        return totalUnitsFinal;
    }
    public static void displayOutro(String[][] subjects, ArrayList<String> StudentSubjects, int totalUnits, String[] AccountFullName) {
        System.out.println("==========================================\nThank you, " + AccountFullName[0] + " "
                + AccountFullName[1]
                + ". Your subject selection\nis finalized.\n------------------------------------------");
        displayList(subjects, StudentSubjects, totalUnits);
    }
    public static void displaySpace(){
        System.out.println(" ");
    }
    public static void displayIntro(String[] AccountFullName, Scanner scnNig) {
        System.out.println("==========================================\nWelcome to Computing Education Department\n==========================================\nPlease, provide the following information:");
        System.out.print("First Name : ");
        AccountFullName[0] = scnNig.next();
        System.out.print("Last Name  : ");
        AccountFullName[1] = scnNig.next();
        System.out.println("==========================================\nHello, " + AccountFullName[1] + ", " + AccountFullName[0]);
        System.out.println("Please choose subjects below, and you only\nhave a maximum of 21 units of subjects.\n\n------------------------------------------\r\n"
                        + //
                        "| Code    | Subject              | Unit |\r\n" + //
                        "------------------------------------------\r\n" + //
                        "| IT27L   | Intermediate Prog.   | 4.5 |\r\n" + //
                        "| CCE101L | Intro to Programming | 4.5 |\r\n" + //
                        "| PHY01   | Physics 101          | 3.0 |\r\n" + //
                        "| PE02    | Physical Education 2 | 3.0 |\r\n" + //
                        "| IT101   | Intro to Computing   | 3.0 |\r\n" + //
                        "| DBMS1L  | DATABASE MGT 1       | 4.5 |\r\n" + //
                        "| ITSEC02 | Adv IT Security      | 3.0 |\r\n" + //
                        "| CNTP402 | Computer Networks    | 3.0 |\r\n" + //
                        "| ITETHCS | IT Ethics            | 3.0 |\r\n" + //
                        "| ELECT1  | IT Elective 1        | 4.5 |\r\n" + //
                        "| MBLDV   | Mobile Development   | 4.5 |\r\n" + //
                        "| ENG1    | English 1            | 3.0 |\r\n" + //
                        "------------------------------------------\n");
    }
}
