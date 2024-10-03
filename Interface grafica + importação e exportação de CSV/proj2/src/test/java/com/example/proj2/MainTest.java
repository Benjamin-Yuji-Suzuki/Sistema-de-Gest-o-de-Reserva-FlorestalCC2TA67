package com.example.proj2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainTest {

    @Test
    public void testSomar() {
        Main main = new Main();
        int resultado = main.somar(2, 3);
        assertEquals(5, resultado, "A soma de 2 e 3 deve ser 5");
    }
}
