import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class RandomCodeGeneratorTest {

    @Test
    public void generateDefaultNumberOfCodesTest() {
        RandomCodeGenerator randomCodeGenerator = new RandomCodeGenerator();

        int expected = 1;
        int result = randomCodeGenerator.generate(0).size();

        Assert.assertEquals(expected, result);
    }

    @Test
    public void generateTenCodesTest() {
        RandomCodeGenerator randomCodeGenerator = new RandomCodeGenerator();

        int expected = 10;
        int result = randomCodeGenerator.generate(10).size();

        Assert.assertEquals(expected, result);
    }

    @Test
    public void generateOneHundredCodesTest() {
        RandomCodeGenerator randomCodeGenerator = new RandomCodeGenerator();

        int expected = 100;
        int result = randomCodeGenerator.generate(100).size();

        Assert.assertEquals(expected, result);
    }

    @Test
    public void getExceptionTest(){
        RandomCodeGenerator randomCodeGenerator = new RandomCodeGenerator("blacklistTEST.txt");
        System.out.println("INTENTIONAL EXCEPTION FOLLOWS -->>");
        int expected = 0;
        int result = randomCodeGenerator.generate(10).size();
        System.out.println("<<-- INTENTIONAL EXCEPTION ENDS");
        Assert.assertEquals(expected,result);
    }

    @Test
    public void oppsAllBsTest(){
        RandomCodeGenerator randomCodeGenerator = new RandomCodeGenerator();
        randomCodeGenerator.setAllowedCharacters("B");
        List<String> expected = new ArrayList(Arrays.asList("BBBBBB","BBBBBB","BBBBBB"));
        List<String> result = randomCodeGenerator.generate(3);
        Assert.assertEquals(expected,result);
    }

    @Test
    public void validateThreeLetterFailureTest() {
        RandomCodeGenerator randomCodeGenerator = new RandomCodeGenerator();
        boolean result = new WordValidator().validate("XXX777");
        Assert.assertFalse(result);
    }

    @Test
    public void validateThreeLetterSuccessTest() {
        RandomCodeGenerator randomCodeGenerator = new RandomCodeGenerator();

        boolean result = new WordValidator().validate("XXX778");
        Assert.assertTrue(result);
    }

    @Test
    public void validateFourLetterFailureTest() {
        RandomCodeGenerator randomCodeGenerator = new RandomCodeGenerator();

        boolean result = new WordValidator().validate("RATSXX");
        Assert.assertFalse(result);
    }

    @Test
    public void validateFourLetterSuccessTest() {
        RandomCodeGenerator randomCodeGenerator = new RandomCodeGenerator();

        boolean result = new WordValidator().validate("CATSXX");
        Assert.assertTrue(result);
    }

    @Test
    public void validateFiveLetterFailureTest() {
        RandomCodeGenerator randomCodeGenerator = new RandomCodeGenerator();

        boolean result = new WordValidator().validate("FUXZZY");
        Assert.assertFalse(result);
    }

    @Test
    public void validateFiveLetterSuceessTest() {
        RandomCodeGenerator randomCodeGenerator = new RandomCodeGenerator();

        boolean result = new WordValidator().validate("KUXZZY");
        Assert.assertTrue(result);
    }

    @Test
    public void validateSixLetterFailureTest() {
        RandomCodeGenerator randomCodeGenerator = new RandomCodeGenerator();

        boolean result = new WordValidator().validate("CHEESE");
        Assert.assertFalse(result);
    }

    @Test
    public void validateSixLetterSuccessTest() {
        RandomCodeGenerator randomCodeGenerator = new RandomCodeGenerator();

        boolean result = new WordValidator().validate("CHEEZE");
        Assert.assertTrue(result);
    }

}
