package Test_Model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.model.Article;

public class ArticleTest {

    private Article article;

    @BeforeEach
    void setup() {
        article = new Article("Pan", 5, 2.0, 10);
    }

    @Test
    void testGetGrossAmount() {
        assertEquals(10.0, article.getGrossAmount(), 0.01);
    }

    @Test
    void testGetDiscountedAmount() {
        assertEquals(9.0, article.getDiscountedAmount(), 0.01);
    }
}
