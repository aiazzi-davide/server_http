package com.example;

public class ThreadConsole extends Thread {
    public void run() {
        while (true) {
            // input da tastiera
            String input = System.console().readLine();
            switch (input) {
                case "exit":
                    System.exit(0);
                    break;
                case "help":
                    System.out.println("Comandi disponibili:");
                    System.out.println("exit: chiudi il server");
                    System.out.println("help: mostra questo messaggio");
                    break;
                default:
                    System.out.println("Comando non riconosciuto");
                    break;
            }
        }
    }
}
