package com.ccks;

/**
 * Classe de inicialização (Launcher) para o aplicativo.
 * Esta classe é usada como ponto de entrada principal para o JAR executável,
 * contornando um problema comum do JavaFX ao empacotar dependências.
 */
public class Launcher {
    public static void main(String[] args) {
        App.main(args);
    }
}
