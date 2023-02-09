package com.company.ProjectSpring.service;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;

public class OTPGenerator {
    public static String getOneTimePassword(){
        CharacterRule alphabets = new CharacterRule(EnglishCharacterData.Alphabetical);
        CharacterRule digits = new CharacterRule(EnglishCharacterData.Digit);
        PasswordGenerator passwordGenerator = new PasswordGenerator();
        String password = passwordGenerator.generatePassword(6, alphabets, digits);
        return password;
    }
}


