package com.github.kyriosdata.bsus.cid10.preprocessor;

import com.github.kyriosdata.bsus.cid10.preprocessor.json.Cid;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BuscaTest {

    private static Transformador sc;

    // Dados "originais" sem transformações
    private static Cid c;

    private static List<String> sentencas;

    private static Sequencia cid;

    @BeforeClass
    public static void montagemIndice() throws FileNotFoundException {

        // Converte versão original em versão eficiente para consulta
        final String fileName = "cid10.json";
        sc = Transformador.newInstance(fileName);

        // Empregado exclusivamente para retornar resultado (sem transformações)
        c = FileFromResourcesFolder.getConteudo("cid10.json", Cid.class);

        // TODO persistir cid.toByteArray() e sentencas (comparar tamanhos)

        sentencas = sc.getSentencas();
        cid = new Sequencia(sentencas);
    }

    @Test
    public void tamanhos() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(sentencas);
        oos.flush();
        oos.close();

        byte[] sentencasBytes = baos.toByteArray();
        byte[] sequenciaBytes = cid.toByteArray();
        assertTrue(sentencasBytes.length > sequenciaBytes.length);


    }

    @Test
    public void analisaSentencas() {
        int maxSize = 0;
        for (String sentenca : sentencas) {
            if (sentenca.length() > maxSize) {
                maxSize = sentenca.length();
            }
        }

        assertTrue(maxSize < 256);
    }

    @Test
    public void verificaCid() {
        int indice = 0;
        int total = 0;
        while (indice != -1) {
            indice = cid.proxima(indice);
            total++;
        }

        System.out.println("Size in bytes: " + cid.toByteArray().length + " Total: " + total);
    }

    @Test
    public void montagemCorreta() {
        int idxCid = 0;
        for (int i = 0; i < sc.codigo.length; i++) {
            assertTrue(cid.toString(idxCid), cid.toString(idxCid).contains(sc.codigo[i]));
            assertTrue(cid.toString(idxCid).contains(sc.descricao[i]));
            idxCid = cid.proxima(idxCid);
        }
    }

    @Test
    public void buscaPorPalavraInexistente() {

        final String[] criterios = {"y112", "intoxicacao", "anti" };

        List<Integer> identificadas = cid.procurePor(criterios);
        for (int i : identificadas) {
            System.out.println(c.codigo.get(i) + " " + c.descricao.get(i));
        }

        assertEquals(1, identificadas.size());
    }
}
