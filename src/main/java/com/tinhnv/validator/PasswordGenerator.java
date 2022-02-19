package com.tinhnv.validator;

import java.security.SecureRandom;

import org.springframework.stereotype.Component;

@Component
public class PasswordGenerator {
	
	private static final String CHAR_LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_UPPERCASE = CHAR_LOWERCASE.toUpperCase();
    private static final String DIGIT = "0123456789";
    private static final String OTHER_PUNCTUATION = "!@#&()–[{}]:;',?/*";
    private static final String OTHER_SYMBOL = "~$^+=<>";
    private static final String SPECIAL_CHAR = OTHER_PUNCTUATION + OTHER_SYMBOL;
    

    private static SecureRandom random = new SecureRandom();
    
    public String getStrongPassword() {
    	StringBuilder password = new StringBuilder();
    	password.append(generateRandomString(CHAR_UPPERCASE, 1));
    	password.append(generateRandomString(CHAR_LOWERCASE, 1));
    	password.append(generateRandomString(SPECIAL_CHAR, 1));
    	password.append(generateRandomString(CHAR_LOWERCASE, 2));
    	password.append(generateRandomString(DIGIT, 1));
    	password.append(generateRandomString(CHAR_UPPERCASE, 1));
    	String result = password.toString();
//    	shuffleString(result);
    	return result;
    }
    
    private String generateRandomString(String input, int size) {

        if (input == null || input.length() <= 0)
            throw new IllegalArgumentException("Invalid input.");
        if (size < 1) throw new IllegalArgumentException("Invalid size.");

        StringBuilder result = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            int index = random.nextInt(input.length());
            result.append(input.charAt(index));
        }
        return result.toString();
    }

//    public String shuffleString(String input) {
//        List<String> result = Arrays.asList(input.split(""));
//        Collections.shuffle(result);
//        return result.stream().collect(Collectors.joining());
//    }
}
