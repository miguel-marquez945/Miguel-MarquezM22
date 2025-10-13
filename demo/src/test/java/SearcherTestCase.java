import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.example.Searcher;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SearcherTestCase {

    private Searcher searcher;
    private List<String> words;

    @BeforeEach
    void setup() {
        searcher = new Searcher();
        words = Arrays.asList("casa", "carro", "perro", "gato", "carpeta");
    }

    @Test
    void testSearchWordExiste() {
        assertTrue(searcher.searchWord("casa", words));
    }

    @Test
    void testSearchWordNoExiste() {
        assertFalse(searcher.searchWord("avion", words));
    }

    @Test
    void testGetWordByIndexValido() {
        assertEquals("carro", searcher.getWordByIndex(words, 1));
    }

    @Test
    void testGetWordByIndexInvalido() {
        assertNull(searcher.getWordByIndex(words, -1));
        assertNull(searcher.getWordByIndex(words, 100));
    }

    @Test
    void testSearchByPrefixValido() {
        List<String> result = searcher.searchByPrefix("car", words);
        assertEquals(Arrays.asList("carro", "carpeta"), result);
    }

    @Test
    void testSearchByPrefixInvalido() {
        List<String> result = searcher.searchByPrefix("pe", words);
        assertEquals(Collections.singletonList("perro"), result);
    }

    @Test
    void testFilterByKeywordExiste() {
        List<String> result = searcher.filterByKeyword("rr", words);
        assertEquals(Arrays.asList("carro", "perro"), result);
    }

    @Test
    void testFilterByKeywordNoExiste() {
        List<String> result = searcher.filterByKeyword("zzz", words);
        assertTrue(result.isEmpty());
    }

    @Test
    void testSearchExactPhraseEncontrada() {
        assertTrue(searcher.searchExactPhrase("casa", words));
    }

    @Test
    void testSearchExactPhraseNoEncontrada() {
        assertFalse(searcher.searchExactPhrase("avion", words));
    }

    @Test
    void testSearchExactPhraseMiddle() {
        assertTrue(searcher.searchExactPhrase("perro", words));
    }
}