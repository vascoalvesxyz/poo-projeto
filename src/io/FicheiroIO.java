package io;

import gestao.GestorClientes;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FicheiroIO {

    Leitor leitor;

    public FicheiroIO(Leitor leitor) {
        this.leitor = leitor;
    }

    public GestorClientes importarClientes(String filename) {
        if (!existeFicheiro(filename)) return null;
        String[] temp = filename.split(".");
        String ext = temp[temp.length-1];
        if (ext.equalsIgnoreCase("ser")) {
            return  lerFicheiroObjetos(filename);
        } else if (ext.equalsIgnoreCase("txt")) {
            return  lerFicheiroTexto(filename);
        }
        System.out.println("Não existe sei como ler este ficheiro.");
        return null;
    }

    public boolean existeFicheiro(String pathStr) {
        Path caminho = Paths.get(pathStr);
        return Files.exists(caminho);
    }

    private GestorClientes lerFicheiroObjetos(String filename) {
        if (!existeFicheiro(filename)) return null;
        try (
                FileInputStream fileIn = new FileInputStream(filename);
                ObjectInputStream objIn = new ObjectInputStream(fileIn)
        ) {
            GestorClientes gestorClientes = (GestorClientes) objIn.readObject();
        } catch (Exception e) {
            System.out.printf("Algo correu mal: %s\n", e);
        }
        return null;
    }

    /* TODO */
    private GestorClientes lerFicheiroTexto(String filename) {
        if (!existeFicheiro(filename)) return null;
        return new GestorClientes(leitor);
    };

}
