import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

/**
 * Scrabble Scorer, scores inputted Scrabble words and detects bad data
 * @author Kayla Cao
 * @version 01.13.2026
 * Flint session: https://app.flintk12.com/activities/algorithm-helpe-1ae606/sessions/84f37871-ef1f-4a46-ae23-1068c0d95c80
 */
public class ScrabbleScorer {
    private final int[] points = {1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10};
    private final String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final ArrayList<ArrayList<String>> dictionary;

    /**
     * Constructor for class, initialize dictionary and insert 26 empty "buckets" or sub ArrayLists
     */
    public ScrabbleScorer() {
        dictionary = new ArrayList<>();
        for (int i = 0; i < alpha.length(); i++) {
            dictionary.add(new ArrayList<>());
        }
        buildDictionary();
    }

    /**
     * Adds all words from the file SCRABBLE_WORDS.txt and sorts them into "buckets" or sub ArrayLists based on first letter,
     * then sorts each bucket into alphabetical order
     */
    public void buildDictionary() {
        Scanner in;
        try {
            in = new Scanner(new File("SCRABBLE_WORDS.txt"));
            String word;
            while (in.hasNext()) {
                word = in.nextLine();
                //gets first letter of word, finds what place in alphabet it's at, takes that index to find which bucket of dictionary to put the word in, adds it to that bucket
                dictionary.get(alpha.indexOf(word.charAt(0))).add(word);
            }
            in.close();
        }
        catch (Exception e) {
            System.out.println("There was an error: " + e);
        }

        for (ArrayList<String> strings : dictionary) {
            Collections.sort(strings);
        }
    }

    /**
     * Determines if word is valid by checking against dictionary
     * @param word the word to check
     * @return whether the word is in dictionary
     */
    public boolean isValidWord(String word) {
        //gets first letter of word, finds bucket, checks if bucket has word with binary search
        int alphaIndex = alpha.indexOf(word.charAt(0));
        return alphaIndex >= 0 && Collections.binarySearch(dictionary.get(alphaIndex), word) >= 0;
    }

    /**
     * Calculates the score of word using points array
     * @param word Prereq: word must exist in dictionary - call isValidWord before this method can be called
     * @return the calculated score of word if played in Scrabble
     */
    public int getWordScore(String word) {
        int score = 0;
        for (int i = 0; i < word.length(); i++) {
            score += points[alpha.indexOf(word.charAt(i))];
        }
        return score;
    }

    /**
     * Main entry point for class ScrabbleScorer
     * @param args command line arguments, if needed
     */
    public static void main(String[] args) {
        ScrabbleScorer app = new ScrabbleScorer();
        Scanner in = new Scanner(System.in);
        System.out.println("* Welcome to the Scrabble Word Scorer app *");
        while (true) {
            System.out.print("Enter a word to score or 0 to quit: ");
            String input = in.nextLine();
            if (input.isEmpty()) {
                System.out.println("Invalid input");
                continue;
            }
            if (input.equals("0")) {
                break;
            }
            else if (app.isValidWord(input.toUpperCase())) {
                System.out.println(input + " = " + app.getWordScore(input.toUpperCase()) + " points");
            }
            else {
                System.out.println(input + " is not a valid word in the dictionary");
            }
        }
        System.out.println("Exiting the program thanks for playing");
        in.close();
    }
}