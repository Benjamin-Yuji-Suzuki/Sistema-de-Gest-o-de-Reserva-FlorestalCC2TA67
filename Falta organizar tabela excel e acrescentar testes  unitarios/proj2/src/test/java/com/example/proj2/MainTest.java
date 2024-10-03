package com.example.proj2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainTest {

    @Test
    public void testEspecieNome() {
        Especie especies = new Especie();
        
        especies.setNome("B");
                
        assertEquals(especies.getNome(), "A");
        
        
    }
    
    @Test
    public void testAreaNome() {
        Area area = new Area();
        
        area.setNome("ABC");
        
        assertEquals(area.getNome(), "ABC");
        
        
    }
    
    
}
