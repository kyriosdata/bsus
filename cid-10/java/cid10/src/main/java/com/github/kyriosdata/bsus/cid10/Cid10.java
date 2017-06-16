package com.github.kyriosdata.bsus.cid10;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
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
            InputStream dat = get("/cid10.dat");
            byte[] bytes = getData(dat);
            cid = new Sequencia(bytes);

            InputStream org = get("/cid10.org");
            InputStreamReader isr = new InputStreamReader(org);
            BufferedReader br = new BufferedReader(isr);
            original = new ArrayList<>();
            String linha;
            while ((linha = br.readLine()) != null) {
                original.add(linha);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] getData(InputStream is) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[16384];

        while ((nRead = is.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        buffer.flush();

        return buffer.toByteArray();
    }

    public InputStream get(String fileName) {
        return this.getClass().getResourceAsStream(fileName);
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
    public String[] search(String[] criterios) {
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
