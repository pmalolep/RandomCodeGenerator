# RandomCodeGenerator

## Description

Generates a series of random six character alphanumeric codes that exclude codes that contain restricted words.

## Table of Contents

> * [RandomCodeGenerator](#RandomCodeGenerator)
>      * [Description](#Description)
>   * [Table of Contents](#table-of-contents)
>   * [Code](#code)
>       * [Extra Bits](#extra-bits)
>   * [Requirements](#requirements)
>   * [Build](#build)
>   * [From Terminal](#From-Terminal)
> * [About me](#about-me)

## Code

The code will be explained from the bottom up. Starting with which characters are allowed in the generated code and
ending with delivering all validated codes.

### Method: setAllowedCharacters
Creates a list of alphanumeric characters used in the generated codes.
```java
private void setAllowedCharacters(){
        String allCharacters="ABCDEFGHJKMNPQRSTUVWXYZ23456789";
        allowedCharactersPool.addAll(Arrays.asList(allCharacters.split("")));
        }
```

Only capital alpha-numeric characters are allowed for readability. Letters i and l, and number 1 are disallowed because
of their similarity, as are letter o and number 0 for the same reason.

They're prepared as a single string then split into a list for easy access. This method was originally going to have an
argument to set the allCharacters string, but it wasn't necessary for this version of the code.

### Method: generateRandomCode
Returns a string of 6 random characters.
```java
private String generateRandomCode(){
    Random rand=new Random();
    StringBuilder randomCode=new StringBuilder();
    int poolSize=allowedCharactersPool.size();
    for(int index=0;index< 6;index++){
        randomCode.append(allowedCharactersPool.get(rand.nextInt(poolSize)));
    }
    return randomCode.toString();
}
```
Using the Random library, a character is selected from the allowed characters pool six times to be added to a string. 

### Method: getValidatedCode
Will continuously generate codes until one passes validation.
```java
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
```
If it fails to validate a code after a set amount of attempts it will throw an exception.

### Method: generate
The core method of the class that collects and delivers a list of valid generated codes.
```java
public List<String> generate(int numberOfCodes) {
    generatedCodes = new ArrayList<>();
    if (numberOfCodes < 1) {
        numberOfCodes = 1; 
    }
    for (int index = 0; index < numberOfCodes; index++) { 
        try {
            generatedCodes.add(getValidatedCode());
```
numberOfCodes is the number of requested codes needed to be generated. It defaults to a single code.

Codes are generated through the getValidatedCode method, and added to the generated codes list one at a time. 
```java
        } catch (Exception loopLimitException) {
            System.out.println("Error while generating random codes. No valid codes could be produced after 1000 attempts. This is most likely due to an overly restrictive word forbidden list.");
            loopLimitException.printStackTrace();
            return emptyList();
        }
    }
```
If getValidatedCode throws an exception a message will print with the stack trace, and an empty list is returned to break the loop and prevent bad codes from being returned.
```java
    return generatedCodes;
}
```
If no exception is caught, all generated codes in the list are returned.

## Extra Bits

### Class: WordValidator
WordValidator is built to read words from a file, build a list of regex patterns from those words, and compare an input to the list.

### Method: loadBannedWords
Reads the contents of a file into a list.
```java
private void loadBannedWords() {
    try {
        Scanner scanner = new Scanner(new File("src/main/resources/blacklistTEST.txt"));
        scanner.forEachRemaining(line -> bannedWords.add(line.toUpperCase()));
        scanner.close();
    } catch (FileNotFoundException e) {
        System.out.println("Error while reading blacklist file.");
        e.printStackTrace();
    }
}
```
One line is one entry in the list. Everything entered is first made to be upper case.
### Method: makeRegexPattern
Turns a word into a REGEX pattern 
```java
private Pattern makeRegexPattern( String word ){
    StringBuilder pattern = new StringBuilder(".*[");

    Arrays.stream(word.split("")).forEach(c->pattern.append(c).append("].*["));
    pattern.deleteCharAt(pattern.length()-1);

    return compile(pattern.toString());
}
```
In REGEX ".\*" means any number, or zero, of any character. [A] is literal A. So, the pattern ".\*[A].\*" would be "any number of characters, then an A, then any number of characters". With this we can easily build patterns that find have sequential, but not necessarily consecutive sequences inside them. The pattern ".\*[H].\*[A].\*[T].\*" would find any word that contained the sequential series HAT, such as AHAT, HAUT, HAVETHAT, but not HOTAF because A is out of sequence.

Every pattern starts with ".\*[", so that is baked in first. For each character, the character and "].\*[" is added. When all characters are added, the final opening bracket is removed, and the pattern is compiled.

### Method: validate
Checks a provided code against all patterns in a list. If any of the patterns match the code, the method will return false.
```java
public boolean validate(String code){
    return regexPatterns.stream().noneMatch(p->p.matcher(code).matches());
}
```

## Potential Changes
Duplicate codes are currently possible, but with sufficiently large character pools they shouldn't occur with any frequency. Fixing this by utilizing a set wouldn't be difficult, but it's unnecessary at the moment.

Parallelization of code generation should be possible, and would help when building large lists of codes. At the same time, a more robust collection and delivery method should be considered because Lists of Strings can be excessively large.
## Requirements

Maven Version 3.6.3

Java 1.8

## Build

```
    > mvn clean install
```

## From Terminal

```
    > java -jar .\target\RandomCodeGen-1.0-SNAPSHOT.jar 10
```

## About Me
More information is available at [https://github.com/pmalolep/](https://github.com/pmalolep).