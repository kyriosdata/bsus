package com.github.kyriosdata.bsus.cid10.preprocessor;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Set;

public class ConversorTest {

    @Test
    public void montagemIndice() throws FileNotFoundException {
        Map<String, Set<String>> dados = Conversor.montaIndice();
        System.out.println(dados.size());
    }
}
