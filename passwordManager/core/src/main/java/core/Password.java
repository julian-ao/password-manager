package core;

import java.util.Random;

public class Password {
    private String password;
    private int score;

    public Password() {
        this.password = "";
        randomPassword();
        this.score = calcScore();
    }

    public Password(String password) {
        this.password = password;
        this.score = calcScore();
    }

    public void setPassword() {
        randomPassword();
        this.score = calcScore();
    }

    public void setPassword(String password) {
        this.password = password;
        this.score = calcScore();
    }

    public String getPassword() {
        return this.password;
    }

    public int getScore() {
        return this.score;
    }

    public void randomPassword() {
        // Generates a random password with ascii characters from 33 to 122, that is 16 characters long
        while (calcScore() < 3) {
            this.password = new Random().ints(16, 33, 122).collect(StringBuilder::new,
                StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        }
    }

    public int calcScore() {
        int length = this.password.length();
        int upperCase = 0;
        int lowerCase = 0;
        int numbers = 0;
        int specialCharacters = 0;

        for (int i = 0; i < length; i++) {
            if (Character.isUpperCase(password.charAt(i))) {
                upperCase++;
            } else if (Character.isLowerCase(password.charAt(i))) {
                lowerCase++;
            } else if (Character.isDigit(password.charAt(i))) {
                numbers++;
            } else {
                specialCharacters++;
            }
        }

        if (length >= 14 && (upperCase >= 2 && lowerCase >= 2 && numbers >= 2 && specialCharacters >= 2)) {
            this.score = 3;
        } else if (
            length >= 6 && 
            ((upperCase >= 1 && (lowerCase >= 1 || numbers >= 1 || specialCharacters >= 1)) 
            || (lowerCase >= 1 && (numbers >= 1 || specialCharacters >= 1 || upperCase >= 1)) 
            || (numbers >= 1 && (specialCharacters >= 1 || upperCase >= 1 || lowerCase >= 1)) 
            || (specialCharacters >= 1 && (upperCase >= 1 || lowerCase >= 1 || numbers >= 1))))
            {
            this.score = 2;
        } else {
            this.score = 1;
        }
        return this.score;
    }
}
