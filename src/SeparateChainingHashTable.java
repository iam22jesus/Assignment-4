import java.util.*;

public class SeparateChainingHashTable {
    LinkedList<Entry>[] table;
    int searchComparisons;

    public SeparateChainingHashTable(int size) {
        table = new LinkedList[size];
        for (int i = 0; i < size; i++) {
            table[i] = new LinkedList<>();
        }
        searchComparisons = 0;
    }

    // Insert word into hash table
    public void insert(String word, int lineNumber) {
        int index = hash(word, PasswordValidator.M_CHAINING);
        table[index].add(new Entry(word, lineNumber));
    }

    // Check if word is in the hash table
    public boolean contains(String word) {
        int index = hash(word, PasswordValidator.M_CHAINING);
        searchComparisons = 0;
        for (Entry entry : table[index]) {
            searchComparisons++;
            if (entry.word.equals(word)) {
                return true;
            }
        }
        return false;
    }

    // Get number of comparisons made during search
    public int getSearchCost(String word) {
        contains(word);
        return searchComparisons;
    }

    // Hash function for separate chaining
    private int hash(String word, int size) {
        // Ensure index is non-negative
        return Math.abs(word.hashCode() % size);
    }
}
