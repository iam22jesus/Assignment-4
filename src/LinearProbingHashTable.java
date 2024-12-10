public class LinearProbingHashTable {
    Entry[] table;
    int searchComparisons;

    public LinearProbingHashTable(int size) {
        table = new Entry[size];
        searchComparisons = 0;
    }

    // Insert word into the hash table
    public void insert(String word, int lineNumber) {
        int index = hash(word, PasswordValidator.M_PROBING);
        while (table[index] != null) {
            index = (index + 1) % PasswordValidator.M_PROBING;
        }
        table[index] = new Entry(word, lineNumber);
    }

    // Check if the word is in the hash table
    public boolean contains(String word) {
        int index = hash(word, PasswordValidator.M_PROBING);
        searchComparisons = 0;
        while (table[index] != null) {
            searchComparisons++;
            if (table[index].word.equals(word)) {
                return true;
            }
            index = (index + 1) % PasswordValidator.M_PROBING;
        }
        return false;
    }

    // Get the number of comparisons made during search
    public int getSearchCost(String word) {
        contains(word);
        return searchComparisons;
    }

    // Hash function for linear probing
    private int hash(String word, int size) {
        // Ensure the index is non-negative
        return Math.abs(word.hashCode() % size);
    }
}
