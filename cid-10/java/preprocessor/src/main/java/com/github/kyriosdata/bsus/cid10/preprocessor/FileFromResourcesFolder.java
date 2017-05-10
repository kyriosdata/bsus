/*
 * Copyright (c) 2017
 *
 * Fábio Nogueira de Lucena
 * Fábrica de Software - Instituto de Informática (UFG)
 *
 * Creative Commons Attribution 4.0 International License.
 */

package com.github.kyriosdata.bsus.cid10.preprocessor;

import java.io.File;

/**
 * Classe utilitária para obter 'File' de arquivo incluído no
 * diretório 'resources'.
 */
public class FileFromResourcesFolder {

    public static File get(String fileName) {
        FileFromResourcesFolder obj = new FileFromResourcesFolder();
        ClassLoader classLoader = obj.getClass().getClassLoader();
        return new File(classLoader.getResource(fileName).getFile());
    }
}
