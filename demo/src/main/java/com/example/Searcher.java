package com.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Searcher {

    // Devuelve true si la frase aparece EXACTAMENTE (match completo) en la lista
    public static boolean searchExactPhrase(String phrase, List<String> list) {
        if (phrase == null || list == null) return false;
        for (String s : list) {
            if (phrase.equals(s)) return true;
        }
        return false;
    }

    // Devuelve true si la palabra aparece EXACTA en la lista
    public static boolean searchWord(String word, List<String> list) {
        if (word == null || list == null) return false;
        for (String s : list) {
            if (word.equals(s)) return true;
        }
        return false;
    }

    // Devuelve el elemento por índice; si el índice es inválido, devuelve null
    public static String getWordByIndex(List<String> list, int index) {
        if (list == null || index < 0 || index >= list.size()) return null;
        return list.get(index);
    }

    // Devuelve las cadenas que EMPIEZAN por el prefijo (case-sensitive)
    public static List<String> searchByPrefix(String prefix, List<String> list) {
        if (prefix == null || list == null) return Collections.emptyList();
        List<String> result = new ArrayList<>();
        for (String s : list) {
            if (s != null && s.startsWith(prefix)) {
                result.add(s);
            }
        }
        return result;
    }

    // Devuelve las cadenas que CONTIENEN la keyword (case-sensitive)
    public static List<String> filterByKeyword(String keyword, List<String> list) {
        if (keyword == null || list == null) return Collections.emptyList();
        List<String> result = new ArrayList<>();
        for (String s : list) {
            if (s != null && s.contains(keyword)) {
                result.add(s);
            }
        }
        return result;
    }
}
