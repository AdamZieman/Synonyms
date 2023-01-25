package Synonyms.Package;

import java.net.URL;
import java.util.Scanner;

/**
 * Driver class for the main() method.
 *
 * @author  Adam Zieman
 * @version Java SE 19.0.2
 */
public class MainDriver {
    // Word to find the closest synonym
    protected static String primaryWord;

    // List of possible synonyms to the primary word
    protected static String[] wordChoices;

    // Array of URLs
    protected static URL[] corpus;

    /**
     * main() method contains a loop, to retrieve user input, create a Synonyms object, and prints the closest synonym
     * for an array of words.
     * <p>
     *     Within a loop, retrieves keyboard input for a primary word to check for a possible synonym, and an array of
     *     possible synonym options. Then a Synonyms object is created which contains an instantiated HashMap and the
     *     corpus. The toString() method of the Synonyms object calculates the cosine similarity of each word, then
     *     prints every word in the list with it's cosine similarity value, and which word has the is the closest
     *     synonym to the primary word.
     * </p>
     *
     * @param args String[] value of parameters passed when the program is executed - no args required for this program.
     */
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);

        // Loop until a blank line is returned as the primary word
        do {
            // Prompt the user to enter the primary word, and store the value
            System.out.println("Enter a word (return a blank line to exit the program):");
            primaryWord = keyboard.nextLine().toLowerCase();

            // Check if the user entered a blank line for the primary word
            if (!primaryWord.isEmpty()) {
                // Prompt the user to enter a list of words, and store the values
                System.out.println("Enter the choices on one line (seperated by spaces):");
                wordChoices = keyboard.nextLine().toLowerCase().split("\\s+");

                // Checks if the user entered a blank line for the list of words
                if (wordChoices.length != 0) {
                    /*
                    Declares and instantiates a Synonyms object. Calls printClosestSynonym() method from the Synonyms
                    class to calculate the cosine similarity, print every word with its cosine similarity value, and
                    prints the word that is most likely the synonym of the primary word.
                     */
                    System.out.println(new Synonyms(corpus));
                }
            }
        } while(!primaryWord.isEmpty());

        // Closes the scanner object
        keyboard.close();
    }
}