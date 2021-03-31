import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static java.util.Collections.emptyList;

public class RandomCodeGenerator {

    private final List<String> allowedCharactersPool;
    private final WordValidator validator;

    /**
     * Prints either 4 codes, or the number entered of codes to the screen.
     *
     * @param args First argument is optional, enter number of codes desired
     */
    public static void main(String[] args) {

        System.out.println("Random Code Generator");
        List<String> codes;

        codes = new RandomCodeGenerator().generate(args.length < 1 ? 4 : Integer.parseInt(args[0]));
        codes.forEach(System.out::println);

    }

    /**
     * Initializes lists, loads banned words and sets the allowed characters for codes
     */
    public RandomCodeGenerator() {
        allowedCharactersPool = new ArrayList<>();
        validator = new WordValidator();

        setAllowedCharacters();
    }

    public RandomCodeGenerator(String fileName) {
        allowedCharactersPool = new ArrayList<>();
        validator = new WordValidator(fileName);
        setAllowedCharacters();
    }


    /**
     * Gets validated codes until the number of needed codes are achieved
     * Handles exception generated from getValidatedCode
     *
     * @param numberOfCodes specified number of codes needed
     * @return generatedCodes is all the generated, and validated codes
     */
    public List<String> generate(int numberOfCodes) {
        List<String> generatedCodes = new ArrayList<>();
        if (numberOfCodes < 1) {
            numberOfCodes = 1;
        }
        for (int index = 0; index < numberOfCodes; index++) {
            try {
                generatedCodes.add(getValidatedCode());
            } catch (Exception loopLimitException) {
                System.out.println("Error while generating random codes. No valid codes could be produced after 1000 attempts. This is most likely due to an overly restrictive forbidden word list.");
                loopLimitException.printStackTrace();
                return emptyList();
            }
        }
        return generatedCodes;
    }

    /**
     * Gets a raw unvalidated code and validates it. If it's invalid the process repeats until
     * a valid code is discovered and returned
     * <p>
     * throws exception if it cant generate a valid code after 1000 (runLimit) attempts
     *
     * @return a random validated code
     */
    private String getValidatedCode() throws Exception {

        String randomCode = generateRandomCode();
        int runCount;
        int runLimit = 1000;
        for (runCount = 0; runCount < runLimit && !validator.validate(randomCode); runCount++) {
            randomCode = generateRandomCode();
        }
        if (runCount >= runLimit) {
            throw new Exception();
        }
        return randomCode;
    }

    /**
     * Makes a string of 6 random characters from the allowed characters pool
     *
     * @return one formatted code as a String
     */
    private String generateRandomCode() {

        Random rand = new Random();
        StringBuilder randomCode = new StringBuilder();
        int poolSize = allowedCharactersPool.size();
        for (int index = 0; index < 6; index++) {
            randomCode.append(allowedCharactersPool.get(rand.nextInt(poolSize)));
        }
        return randomCode.toString();
    }

    /**
     * Reads a string of all usable characters and sets it as the allowed character pool
     * absent characters are "ILO10"
     */
    private void setAllowedCharacters() {
        String allCharacters = "ABCDEFGHJKMNPQRSTUVWXYZ23456789";
        allowedCharactersPool.addAll(Arrays.asList(allCharacters.split("")));
    }

    public void setAllowedCharacters(String allCharacters) {
        allowedCharactersPool.clear();
        allowedCharactersPool.addAll(Arrays.asList(allCharacters.split("")));
    }
}
