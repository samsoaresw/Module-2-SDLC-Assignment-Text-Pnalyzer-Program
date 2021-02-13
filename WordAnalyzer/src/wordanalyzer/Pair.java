package wordanalyzer;

// Stores word and frequency
public class Pair {
    // Stores word
    private String word;
    // Stores word count
    private int count;

    // Constructor to initialization of fields
    public Pair(String word, int count) {
        this.word = word;
        this.count = count;
    }

    // Getter method for word
    public String getWord() {
        return word;
    }

    // Getter method for count
    public int getCount() {
        return count;
    }
}
