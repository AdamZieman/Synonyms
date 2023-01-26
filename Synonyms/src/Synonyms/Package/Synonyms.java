package Synonyms.Package;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Constructor instantiates the URL[] corpus, and HashMap<String, <String, Integer>> descriptors, then populates the
 * HashMap by calling parseCorpus(). The toString() method calculates the cosine similarities, printing each words
 * value, and which word is the closest synonym.
 * <p>
 *     Constructor instantiates and populates data structures by calling the parseCorpus() method. The parseCorpus()
 *     method opens each URL in turn, to instantiate and populate the nested HashMap<String, Integer>, within the
 *     HashMap descriptors. A toString() method is associated to the constructor to calculate the cosine similarity of
 *     the words, print each words values, and which word has the highest cosine similarity. To calculate the cosine
 *     similarity, the calculateCosineSimilarity() method is called, which calls upon to private helper methods:
 *     calculateVectorDotProduct() and calculateVectorMagnitude().
 * </p>
 *
 * @author  Adam Zieman
 * @version Java SE 19.0.2
 */
public class Synonyms extends MainDriver {
    /*
    HashMaps where keys are word under consideration, and values are a nested HashMap. Nested HashMap where keys are
    words contained in the same sentence(s) as the word under consideration, and values are the count to how often the
    word appears.
    */
    private static HashMap<String, HashMap<String, Integer>> descriptors;

    /**
     * Constructor instantiates the URL[] corpus, and HashMap<String, HashMap<String, Integer>> descriptors, then
     * populates the HashMap by calling parseCorpus().
     * <p>
     *     Within a try-block, URL[] corpus is instantiated. The indices are hard-coded to URL links of classic
     *     literature. To extend the scope of corpus, the class-level variable corpus is overwritten to. Within a
     *     catch-block, MalformedURLException is caught. Since the program should not run without being able to access
     *     all URLs, an error message is printed and the program terminates with an exit code 1. Subsequent the
     *     try-catch blocks, the HashMap<String, Hashmap<String, Integer>> descriptors is instantiated. Lastly, the
     *     parseCorpus() method is called to populate the HashMap.
     * </p>
     */
    public Synonyms(URL[] corpus) {
        /*
        Attempts to create a URL[], with index values holding hardcoded URLs. Any MalformedURLException are caught,
        printing where the error occurred and terminating the program.
        */
        try {
            // Overwrites the class-level variable
            corpus = new URL[]{
                    // Pride and Prejudice, by Jane Austen
                    new URL("https://www.gutenberg.org/files/1342/1342-0.txt"),
                    // The Adventures of Sherlock Holmes, by A. Conan Doyle
                    new URL("https://www.gutenberg.org/cache/epub/1661/pg1661.txt"),
                    // A Tale of Two Cities, by Charles Dickens
                    new URL("https://www.gutenberg.org/files/98/98-0.txt"),
                    // Alice's Adventures In Wonderland, by Lewis Carroll
                    new URL("https://www.gutenberg.org/files/11/11-0.txt"),
                    // Moby Dick; or The Whale, by Herman Melville
                    new URL("https://www.gutenberg.org/files/2701/2701-0.txt"),
                    // War and Peace, by Leo Tolstoy
                    new URL("https://www.gutenberg.org/files/2600/2600-0.txt"),
                    // The Importance of Being Earnest, by Oscar Wilde
                    new URL("https://www.gutenberg.org/cache/epub/844/pg844.txt"),
                    // The Wisdom of Father Brown, by G.K. Chesterton
                    new URL("https://www.gutenberg.org/files/223/223-0.txt"),
            };
        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.exit(1);
        }

        /*
        The HashMap's keys are words contained in the same sentence(s) as the word under consideration, and values are a
        count of how many times that word appears in the key's sentences.
        */
        descriptors = new HashMap<>();

        /*
        Calls parseCorpus() method from the Synonyms object to populate the HashMap with the primary word
        and each word in the list of possible synonyms.
        */
        parseCorpus(corpus, primaryWord);
        for (String wordChoice : wordChoices) {
            parseCorpus(corpus, wordChoice);
        }
    }

    /**
     * Public method computes and returns the cosine similarity of two words. If word1 or word2 does not appear in
     * corpus or has no intersection between both words, returns -1.0.
     * <p>
     *     An ArrayList<String> is created to hold the intersection of both maps. If the intersection holds any values,
     *     the return values of the methods, calculateVectorDotProduct() and calculateVectorMagnitude() are stored
     *     accordingly. The cosine similarity is calculated and returned. However, if either map is empty or there or
     *     their is no intersection, -1.0 is returned.
     * </p>
     *
     * @param map1 HashMap<String, Integer> map: key values are words within a sentence, values are a count of their
     *             occurrence
     * @param map2 HashMap<String, Integer> map: key values are words within a sentence, values are a count of their
     *             occurrence
     * @return double value representing the cosine similarity between map1 and map2
     */
    public static double calculateCosineSimilarity(HashMap<String, Integer> map1, HashMap<String, Integer> map2) {
        // Checks if either map is empty
        if (!map1.isEmpty() || !map2.isEmpty()) {

            // Declares and instantiates an ArrayList to hold an intersection of keys between both maps
            ArrayList<String> intersects = new ArrayList<>();

            // Populates the ArrayList with the intersection of keys
            for (String key : map1.keySet()) {
                if (map2.containsKey(key)) {
                    intersects.add(key);
                }
            }

            // Checks if the maps have an intersection
            if (!intersects.isEmpty()) {
                // Stores the dot product of both maps
                int dotProduct = calculateVectorDotProduct(map1, map2, intersects);

                // Stores the magnitude of map1
                double magnitude1 = calculateVectorMagnitude(map1);

                // Stores the magnitude of map2
                double magnitude2 = calculateVectorMagnitude(map2);

                // returns the cosine similarity of both maps
                return dotProduct / Math.sqrt(magnitude1 * magnitude2);
            }
        }

        // Returns -1.0 if either map is empty, or there was no intersection between the maps
        return -1.0;
    }

    /**
     * Private method which traverses through the HashMap with an enhanced for loop, to calculate and return the
     * summation of every value, raise to the power of 2.
     *
     * @param map HashMap<String, Integer> map: key values are words within a sentence, values are a count of their
     *            occurrence
     * @return double value representing the vector magnitude
     */
    private static double calculateVectorMagnitude(HashMap<String, Integer> map) {
        // Declares the return variable, with a default value of 0.0.
        double magnitude = 0.0;

        // Traverses through the HashMap, to calculate the summation of every value raise to the power of 2
        for (Map.Entry<String, Integer> stringIntegerEntry : map.entrySet()) {
            magnitude += Math.pow((double) stringIntegerEntry.getValue(), 2);
        }

        // Returns the vector magnitude
        return magnitude;
    }

    /**
     * Private method calculates and returns the dot product between two maps.
     * <p>
     *     Each word within the intersection of both maps, find the value of the key associated to the intersection
     *     word in both maps. The product of the values are added to the return variable. Returns the summation of
     *     the return variable.
     * </p>
     *
     * @param map1 HashMap<String, Integer> map: key values are words within a sentence, values are a count of their
     *             occurrence
     * @param map2 HashMap<String, Integer> map: key values are words within a sentence, values are a count of their
     *             occurrence
     * @param intersects Array<String> of words in both maps
     * @return Integer value representing the dot product
     */
    private static int calculateVectorDotProduct(HashMap<String, Integer> map1, HashMap<String, Integer> map2,
                                                 ArrayList<String> intersects) {
        int dotProduct = 0;

        for (String intersection : intersects) {
            dotProduct += map1.get(intersection) * map2.get(intersection);
        }

        return dotProduct;
    }

    @Override
    public String toString() {
        // Declares and instantiates a StringBuilder to return a String
        StringBuilder returnStringBuilder = new StringBuilder();

        // Declares a double variable to hold the largest cosine value, with a default value of -1.0.
        double largestCosineValue = -1.0;

        // Declares a String variable to hold the word with the largest cosine value.
        String largestCosineWord = "";

        /*
        Every possible synonym is calculated against the word desired to find the closest synonym to find their
        cosine similarity value. Prints the word and their cosine similarity value.
         */
        for (String wordChoice : wordChoices) {
            double cosineValue = calculateCosineSimilarity(descriptors.get(primaryWord),
                    descriptors.get(wordChoice));

            // Appends the search word, it's semantic cosine value, with a new line character for each word choice
            returnStringBuilder.append("\t").append(wordChoice).append(" ").append(cosineValue).append("\n");

            /*
            If the cosine similarity value calculated is larger than the currently stored value, overwrite the largest
            cosine similarity value and word.
             */
            if (cosineValue > largestCosineValue) {
                largestCosineWord = wordChoice;
                largestCosineValue = cosineValue;
            }
        }

        /*
        Prints the word of the largest cosine similarity as the closest synonym, or a prompt if the options weren't
        valid.
         */
        if (largestCosineValue == -1.0) {
            return "There are no synonyms\n";
        } else {
            return returnStringBuilder.append(largestCosineWord).append("\n").toString();
        }
    }

    /**
     * Opens each URL in turn to read its contents, and populate the HashMaps
     * <p>
     *     Instantiates a class-level HashMap with keys of words of a sentence which contains the search word,
     *     excluding the search word itself, and values of the count of their occurrence.
     * </p>
     * <p>
     *     Each URL is open in turn, analyzing each sentence. If the sentence contains the search word, the words are
     *     split into a String array. The words, excluding the search word, are added to the HashMap with a value of 1
     *     if the word has not already been added. Otherwise, the value to the word is incremented by 1. After all URLs
     *     have been searched, the class-level HashMap descriptors is populated with the key being the searched word,
     *     and values being the HashMap.
     * </p>
     *
     * @param corpus URL[] representing an array of URLs
     * @param searchWord String value representing the word being search for in the sentences within each URL
     */
    public void parseCorpus(URL[] corpus, String searchWord) {
        // Declares a Scanner object to read the URLs
        Scanner scanURL = null;

        /*
        Instantiates the class-level HashMap,String, Integer> keys are of words in a sentence, values are a count of
        their occurrence.
         */
        /*
        An extraction of the nested HashMap where keys are words contained in the same sentence(s) as the word under
        consideration, and values are the count to how often the word appears.
        */
        HashMap<String, Integer> descriptorVectors = new HashMap<>();

        /*
        Attempts to populate the HashMap<String, Integer> then HashMap<String, HashMap<String, Integer>>. Catches any
        IOExceptions, and handles it by printing a message.
         */
        try {
            // Open each URL in turn
            for (URL url : corpus) {
                scanURL = new Scanner(url.openStream());

                // Set the delimiter of the Scanner
                scanURL.useDelimiter("[\\.\\?\\!]|\\Z");

                // Reads the contents of the URL, one sentence at a time
                while (scanURL.hasNext()) {

                    // Stores the sentence as a String, stripped of punctuation and in lower case
                    String sentence = scanURL.next().replaceAll("\\W+", " ").toLowerCase();

                    // Checks if the sentence contains the word being searched for
                    if (sentence.contains(searchWord)) {
                        // Stores the words in a String array
                        String[] words = sentence.split("\\s+");

                        // Reads each word in turn
                        for (String word : words) {
                            // Checks if the word is neither the search word and empty
                            if (!word.equals(searchWord) && !word.equals("")) {
                                /*
                                Adds the word to the HashMap with a value of 1 if it does not already exist. Otherwise,
                                The key containing the word is overwritten to hold a value incremented by 1.
                                 */
                                if (descriptorVectors.containsKey(word)) {
                                    descriptorVectors.replace(word, descriptorVectors.get(word) + 1);
                                } else {
                                    descriptorVectors.put(word, 1);
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Adds the search word and HashMap created to the HashMap<String, HashMap<String, Integer>>
        descriptors.put(searchWord, descriptorVectors);

        // Closes the Scanner object, if it was instantiated
        if (scanURL != null) {
            scanURL.close();
        }
    }
}
