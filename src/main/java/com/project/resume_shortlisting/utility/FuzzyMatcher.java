package com.project.resume_shortlisting.utility;

import org.apache.commons.text.similarity.LevenshteinDistance;

public class FuzzyMatcher {
    private static final LevenshteinDistance distance = new LevenshteinDistance();

    public static boolean isSimilar(String input, String keyword, int threshold) {
        int d = distance.apply(input.toLowerCase(), keyword.toLowerCase());
        return d <= threshold; // lower distance = more similar
    }
}
