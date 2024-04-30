package com.reverso.utilitaire;

import java.util.regex.Pattern;

public class Regex {

    /**
     * Pattern pour comparer les Email.
     */
    public static final Pattern PATTERN_EMAIL =
            Pattern.compile("^([a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,})$");
}
