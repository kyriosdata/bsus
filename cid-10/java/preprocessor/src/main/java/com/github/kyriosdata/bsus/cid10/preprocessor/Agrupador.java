package com.github.kyriosdata.bsus.cid10.preprocessor;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Agrupa conteúdo de arquivos JSON em um único arquivo cid10.json depositado
 * no diretório resources.
 */
public class Agrupador {
    public static void main(String[] args) throws IOException {
        Cid c10 = new Cid();

        Capitulos capitulos = getConteudo("CID-10-CAPITULOS.JSON", Capitulos.class);
        for (int i = 0; i < capitulos.DESCRICAO.length; i++) {
            String catinic = capitulos.CATINIC[i];
            String catfim = capitulos.CATFIM[i];
            c10.codigo.add(catinic + "-" + catfim);
            c10.descricao.add(capitulos.DESCRICAO[i]);
        }

        Categorias categorias = getConteudo("CID-10-CATEGORIAS.JSON", Categorias.class);
        for (int i = 0; i < categorias.DESCRICAO.length; i++) {
            c10.codigo.add(categorias.CAT[i]);
            c10.descricao.add(categorias.DESCRICAO[i]);
        }

        Grupos grupos = getConteudo("CID-10-GRUPOS.JSON", Grupos.class);
        for (int i = 0; i < grupos.DESCRICAO.length; i++) {
            c10.codigo.add(grupos.CATINIC[i] + "-" + grupos.CATFIM[i]);
            c10.descricao.add(grupos.DESCRICAO[i]);
        }

        Subcategorias subcategorias = getConteudo("CID-10-SUBCATEGORIAS.JSON", Subcategorias.class);
        for (int i = 0; i < subcategorias.DESCRICAO.length; i++) {
            c10.codigo.add(subcategorias.SUBCAT[i]);
            c10.descricao.add(subcategorias.DESCRICAO[i]);
        }

        CategoriasO categoriasO = getConteudo("CID-O-CATEGORIAS.JSON", CategoriasO.class);
        for (int i = 0; i < categoriasO.DESCRICAO.length; i++) {
            c10.codigo.add(categoriasO.CAT[i]);
            c10.descricao.add(categoriasO.DESCRICAO[i]);
        }

        GruposO gruposO = getConteudo("CID-O-GRUPOS.JSON", GruposO.class);
        for (int i = 0; i < gruposO.DESCRICAO.length; i++) {
            c10.codigo.add(gruposO.CATINIC[i] + "-" + gruposO.CATFIM[i]);
            c10.descricao.add(gruposO.DESCRICAO[i]);
        }

        int total = 0;
        for (int i = 0; i < c10.descricao.size(); i++) {
            final String codigo = c10.codigo.get(i);
            final String descricao = c10.descricao.get(i);
            total += codigo.length() + descricao.length();
            System.out.println(codigo + " " + descricao);
        }

        System.out.println("Total de entradas: " + c10.descricao.size());
        System.out.println("Total de bytes: " + total);

        Gson gson = new Gson();
        String json = gson.toJson(c10);
        Files.write(Paths.get("./src/main/resources/cid10.json"), json.getBytes());
    }

    public static <T> T getConteudo(String fileName, Class<T> classOfT) throws FileNotFoundException {
        File capitulosJson = FileFromResourcesFolder.get(fileName);
        FileReader fileReader = new FileReader(capitulosJson);

        Gson gson = new Gson();
        return gson.fromJson(fileReader, classOfT);
    }
}
