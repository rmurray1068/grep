package sol;

import src.IGrep;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

/**
 * Class for finding the line numbers where a given word appears in a file.
 */

public class Grep implements IGrep {

    private FileReader reader;
    private BufferedReader buff;
    private HashMap<String, HashSet<Integer>> set;


    /**
     * Constructor for grep
     *
     * @param filename the name of the file
     */
    public Grep(String filename) throws FileNotFoundException {
        String line = null;
        this.set = new HashMap<>();
        try {
            this.reader = new FileReader(filename);
            this.buff = new BufferedReader(this.reader);
            line = this.buff.readLine();
        } catch (IOException e) {
            System.out.println("Encountered an error: " + e.getMessage());
        }
        int lineNum = 0;
        while (line != null) {
            lineNum++;
            String[] words = line.split(" ");
            for (String word : words) {
                if (!(this.set.containsKey(word))) {
                    HashSet<Integer> numSet = new HashSet<>();
                    numSet.add(lineNum);
                    this.set.put(word, numSet);
                }
                HashSet<Integer> newSet = this.set.get(word);
                newSet.add(lineNum);
            }
            try {
                line = this.buff.readLine();
            } catch (IOException e) {
                System.out.println("Encountered an error: " + e.getMessage());
            }
        }
    }

    /**
     * The lookup function
     *
     * @param word - the word to look up
     * @return The set of all line numbers where the word appears
     */
    @Override
    public Set<Integer> lookup(String word) {
        //this should be return this.set.dict values
        if (this.set.containsKey(word)) {
            return this.set.get(word);
        } else {
            throw new RuntimeException();
        }
    }

    // Feel free to write additional helper methods :)

    /**
     * This is the main method. It takes in arguments (i.e. a file name and a word(s))
     * and calls your implementation of Grep.
     *
     * @param args - file name and word(s) you are looking up
     */
    public static void main(String[] args) throws FileNotFoundException {
        Grep grep = new Grep(args[0]);
        try {
            for (int i = 1; i < args.length; i++) {
                String word = args[i];
                System.out.print(word + "was found on lines:" + grep.lookup(word).toString());
            }
        } catch (Exception e) {
            System.out.println("Encountered an error: " + e.getMessage());
        }
    }
}