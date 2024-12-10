import java.io.*;
import java.util.*;

public class PasswordValidator {

    // Constants
    static final int M_CHAINING = 1000;  // Size for separate chaining
    static final int M_PROBING = 20000;  // Size for linear probing
    static final double A = (Math.sqrt(5) - 1) / 2; // Constant multiplication method

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        // Load dictionary from file
        Set<String> dictionary = loadDictionary("src/dictionary.txt");

        // Create hash tables
        SeparateChainingHashTable chainingTable = new SeparateChainingHashTable(M_CHAINING);
        LinearProbingHashTable probingTable = new LinearProbingHashTable(M_PROBING);

        // Insert words into both tables
        int lineNumber = 1;
        for (String word : dictionary) {
            chainingTable.insert(word, lineNumber);
            probingTable.insert(word, lineNumber);
            lineNumber++;
        }

        // Keep asking passwords until the user says "no"
        while (true) {
            // Prompt user enter password
            System.out.println("Please enter a password to check if it is strong.");
            System.out.println("A strong password must: ");
            System.out.println("- Be at least 8 characters long.");
            System.out.println("- Not be a dictionary word.");
            System.out.println("- Iaccount8s not a word in the dictionary followed by a digit 0-9 ");

            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            // Check if password strong
            boolean isStrong = isStrongPassword(password, chainingTable, probingTable);

            if (isStrong) {
                System.out.println("The password is strong.");
            } else {
                System.out.println("The password is not strong.");
            }

            // Display search cost
            System.out.println("\nSearch Cost (Number of Comparisons):");
            System.out.println("Separate Chaining (Dictionary Lookup): " + chainingTable.getSearchCost(password));
            System.out.println("Linear Probing (Dictionary Lookup): " + probingTable.getSearchCost(password));

            // Ask if user wants to check another password
            String response;
            while (true) {
                System.out.print("\nDo you want to check another password? (yes/no): ");
                response = scanner.nextLine().trim().toLowerCase();

                if (response.equals("yes") || response.equals("no")) {
                    break;
                } else {
                    System.out.println("Invalid input. Please enter 'yes' or 'no'.");
                }
            }

            if (response.equals("no")) {
                System.out.println("Exiting program.");
                break;
            }
        }

        scanner.close();  // Close scanner to avoid resource leak
    }

    // Load dictionary from file
    public static Set<String> loadDictionary(String filename) throws IOException {
        Set<String> dictionary = new HashSet<>();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = br.readLine()) != null) {
            dictionary.add(line.toLowerCase());
        }
        return dictionary;
    }

    // Function to check if the password strong
    public static boolean isStrongPassword(String password, SeparateChainingHashTable chainingTable, LinearProbingHashTable probingTable) {
        // Check if password is at least 8 characters long
        if (password.length() < 8) {
            return false;
        }

        String passwordLower = password.toLowerCase();

        // Check if password in the dictionary
        if (chainingTable.contains(passwordLower) || probingTable.contains(passwordLower)) {
            return false;
        }

        // Check if password a word followed by a digit
        if (passwordLower.length() > 1 && (Character.isDigit(passwordLower.charAt(passwordLower.length() - 1)))) {
            String base = passwordLower.substring(0, passwordLower.length() - 1);
            if (chainingTable.contains(base) || probingTable.contains(base)) {
                return false;
            }
        }

        return true;
    }
}
