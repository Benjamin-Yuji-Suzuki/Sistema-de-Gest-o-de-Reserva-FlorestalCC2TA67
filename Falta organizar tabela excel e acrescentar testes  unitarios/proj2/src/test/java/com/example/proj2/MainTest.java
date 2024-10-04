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
    
    @Test
    public void testTipo() {
        Especie especies = new Especie();
        
        especies.setTipo("teste");
        
        assertEquals(especies.getTipo(), "teste");
    }
    
    
    @Test
    public void testLatitude() {
        Area area = new Area();
        
        area.setLatitude(2.0);
        
        assertEquals(area.getLatitude(), 2.0);
    }
    
    @Test
    public void testLongitude() {
        Area area = new Area();
        
        area.setLongitude(4.0);
        
        assertEquals(area.getLongitude(), 4.0);
    }
    
    @Test
    public void testHectare() {
        Area area = new Area();
        
        area.setHectares(15.0);
        
        assertEquals(area.getHectares(), 15.0);
    }
    
    @Test
    public void testCondicao(){
        Especie especie = new Especie();
        
        especie.setCondicao("ameacada");
        
        assertEquals(especie.getCondicao(), "ameacada");
    }
}
