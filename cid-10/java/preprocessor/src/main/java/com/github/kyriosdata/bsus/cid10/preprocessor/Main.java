/*
 * Copyright (c) 2017
 *
 * Fábio Nogueira de Lucena
 * Fábrica de Software - Instituto de Informática (UFG)
 *
 * Creative Commons Attribution 4.0 International License.
 */

package com.github.kyriosdata.bsus.cid10.preprocessor;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

/**
 * Aplicativo que visa produzir versão mais compacta da CID-10 e
 * pronta para agilizar a consulta.
 */
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        String fileName = "cid-10-capitulos-lower.json";

        Main processador = new Main();
        processador.preprocessa(fileName);
    }

    private void preprocessa(String fileName) throws FileNotFoundException {
        Gson gson = new Gson();
        File file = getFileFromResourcesFolder(fileName);

        Capitulos cs = gson.fromJson(new FileReader(file), Capitulos.class);
        exibeAnalise(cs);
    }

    private File getFileFromResourcesFolder(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource(fileName).getFile());
    }

    public void exibeAnalise(Capitulos caps) {
        System.out.println("Total entradas: " + caps.catfim.length);

        Map<String,List<Integer>> mapa = montaDicionario(caps.descricao);

        System.out.println("Total palavras: " + mapa.size());

        for(String chave : mapa.keySet()) {
            System.out.println(chave + " " + mapa.get(chave));
        }
    }

    public Map<String, List<Integer>> montaDicionario(String[] sentencas) {
        Map<String, List<Integer>> mapa = new TreeMap<>();

        for(int i = 0; i < sentencas.length; i++) {
            String[] palavras = sentencas[i].split("(\\s+|,|\\[|\\])");
            for(String palavra : palavras) {
                palavra = trataPalavra(palavra);
                if (palavra == null) {
                    continue;
                }

                List<Integer> indices = mapa.get(palavra);
                if (indices == null) {
                    indices = new ArrayList<>();
                    mapa.put(palavra, indices);
                }

                if (indices.contains(i)) {
                    continue;
                }

                indices.add(i);
            }
        }

        return mapa;
    }

    private List<String> paraRemover = Arrays.asList(new String[] {
        "de", "da", "das", "do", "dos",
            "a", "as", "e", "o", "os",
            "na", "nas", "no", "nos",
            "para",
            "que", "com",
            "em",
            "-"
    });

    /**
     * Operações realizadas:
     * (a) toLower (realizado via linha de comandos)
     *     cat arquivo.json | tr "[A-Z]" "[a-z]"
     * (b) remover 'de', 'da', 'a', 'e', 'as', 'dos', '[', ']', '-', ',' e outros.
     * (c) eliminar acentos
     */
    public String trataPalavra(String palavra) {

        if (paraRemover.contains(palavra)) {
            return null;
        }

        if (palavra.isEmpty()) {
            return null;
        }

        return trocaAcentos(palavra);

    }

    public static String trocaAcentos(String texto)
    {
        String comAcentos = "äáâàãéêëèíîïìöóôòõüúûùç";
        String semAcentos = "aaaaaeeeeiiiiooooouuuuc";
        final int SIZE = comAcentos.length();

        for (int i = 0; i < SIZE; i++)
        {
            texto = texto.replace(comAcentos.charAt(i), semAcentos.charAt(i));
        }

        return texto;
    }

    /**
     * Busca:
     * (a) procura como palavra inteira, se não achar vai para partes
     */
    public void busca() {

    }
}
