package com.github.kyriosdata.bsus.cid10;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

/**
 * Created by fabio on 15/06/17.
 */
public class Cid10 {

    private Sequencia cid;
    private List<String> original;

    /**
     * Permite que a instância da classe seja "preparada"
     * antes do uso.
     */
    public void load() {
        try {
            File dat = get("cid10.dat");
            byte[] bytes = Files.readAllBytes(dat.toPath());
            cid = new Sequencia(bytes);

            File org = get("cid10.org");
            original = Files.readAllLines(org.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File get(String fileName) {
        ClassLoader classLoader = this.getClass().getClassLoader();
        return new File(classLoader.getResource(fileName).getFile());
    }

    /**
     * Permite que a instância libere recursos que não mais
     * serão empregados.
     */
    public void unload() {
        cid = null;
        original.clear();
        original = null;
    }

    /**
     * Retorna as entradas que contêm todos os critérios fornecidos.
     *
     * @param criterios Palavras ou trecho de palavras e dos códigos
     *                  das doenças contidas na CID-10.
     *
     * @return {@code null} se nenhuma entrada da CID-10 contém os
     * critérios fornecidos ou, caso contrário, uma entrada (código
     * seguido da descrição) para cada entrada da CID-10 que satisfaz
     * os critérios.
     */
    String[] search(String[] criterios) {
        List<Integer> encontradas = cid.procurePor(criterios);
        if (encontradas.size() == 0) {
            return null;
        }

        String[] resposta = new String[encontradas.size()];
        for (int i = 0; i < resposta.length; i++) {
            resposta[i] = original.get(encontradas.get(i));
        }

        return resposta;
    }
}
