
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.regex.Pattern.compile;

public class WordValidator {
    private final List<String> bannedWords;
    private final List<Pattern> regexPatterns;

    /**
     * Loads in banned words from file and makes regex patterns based off them
     */
    public WordValidator() {
        bannedWords = new ArrayList<>();
        loadBannedWords("blacklist.txt");
        regexPatterns = bannedWords.stream().map(this::makeRegexPattern).collect(Collectors.toList());
    }

    public WordValidator(String fileName) {
        bannedWords = new ArrayList<>();
        loadBannedWords(fileName);
        regexPatterns = bannedWords.stream().map(this::makeRegexPattern).collect(Collectors.toList());
    }

    /**
     * Compares provided code against all regex patterns and returns false if any match, returns true after
     * exhaustive search
     *
     * @param code a random code to validate
     * @return boolean
     */
    public boolean validate(String code) {
        return regexPatterns.stream().noneMatch(p -> p.matcher(code).matches());
    }

    /**
     * Creates a regex pattern from a word in the pattern .*[w].*[o].*[r].*[d].*
     *
     * @param word a forbidden word
     * @return a pattern of the forbidden word
     */
    private Pattern makeRegexPattern(String word) {
        StringBuilder pattern = new StringBuilder(".*["); //pattern start

        Arrays.stream(word.split("")).forEach(c -> pattern.append(c).append("].*[")); // adds character and pattern separator for each character
        pattern.deleteCharAt(pattern.length() - 1); // deletes extra open bracket

        return compile(pattern.toString());
    }

    /**
     * Reads a file, a line at a time into the banned words list
     */
    private void loadBannedWords(String fileName) {
        try {
            Scanner scanner = new Scanner(new File("src/main/resources/" + fileName));
            scanner.forEachRemaining(line -> bannedWords.add(line.toUpperCase()));
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error while reading blacklist file.");
            e.printStackTrace();
        }
    }
}
