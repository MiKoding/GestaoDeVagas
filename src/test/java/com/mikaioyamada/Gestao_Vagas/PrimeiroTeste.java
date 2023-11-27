package com.mikaioyamada.Gestao_Vagas;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class PrimeiroTeste {

    @Test
    public void deveSerPossivelCalcularDoisNumeros(){
        var result = calculate(2,3);
        assertEquals(result,5);
    }

    @Test
    public void validar_valor_incorreto(){
        var result = calculate(3,3);
        assertNotEquals(result,4);
    }
    public static int calculate(int num1, int num2){
        return num1 + num2;
    }


}
