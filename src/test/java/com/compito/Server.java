package com.compito;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class Server {

    ServerSocket server = null;
    Socket client = null;
    BufferedReader inDalClient;
    PrintWriter outVersoClient;
    int numero1 = 0;
    int numero2 = 0;
    String operazione = null;

    public Socket attendi() {
        try {
            System.out.println("Server partito in esecuzione...");
            server = new ServerSocket(6789); // creo server sulla porta 6789
            client = server.accept(); // rimane in attesa del client
            inDalClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
            outVersoClient = new PrintWriter(client.getOutputStream(), true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Errore durante l'istanza del server");
            System.exit(1);
        }
        return client;
    }

    public void comunica() throws IOException {
        if (client.isConnected()) {
            outVersoClient.println("Server: Connessione effettuata");
        }
        do {
            outVersoClient.println("Server: Digita il primo numero:");
            numero1 = Integer.parseInt(inDalClient.readLine());
            outVersoClient.println("Server: Digita il secondo numero:");
            numero2 = Integer.parseInt(inDalClient.readLine());
            outVersoClient.println("Server: Scegli l'operazione da eseguire:");
            operazione = inDalClient.readLine();
            if (operazione.equals("+")) {
                outVersoClient.println("Server: Il risultato dell'operazione è " + (numero1 + numero2));
            } else if (operazione.equals("-")) {
                outVersoClient.println("Server: Il risultato dell'operazione è " + (numero1 - numero2));
            } else if (operazione.equals("*")) {
                outVersoClient.println("Server: Il risultato dell'operazione è " + (numero1 * numero2));
            } else {
                if (numero2 == 0) {
                    outVersoClient.println("non è possibile eseguire una divisione con il denomitare uguale 0");
                } else {
                    outVersoClient.println("Server: Il risultato dell'operazione è " + (numero1 / numero2));
                }
            }
            outVersoClient.println("Vuoi effettuare un nuovo calcolo (Y/N)?");
        } while (inDalClient.readLine().equals("Y"));
        outVersoClient.println("Server: Chiusura Connessione");
        client.close();
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.attendi();
        server.comunica();
    }
}