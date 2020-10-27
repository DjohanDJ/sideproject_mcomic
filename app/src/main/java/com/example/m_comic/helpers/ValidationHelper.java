package com.example.m_comic.helpers;

import android.util.Patterns;

import java.util.regex.Pattern;

public class ValidationHelper {

    public static boolean doEmailValidation(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

}
