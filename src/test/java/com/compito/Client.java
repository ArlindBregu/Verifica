package com.compito;

import java.io.*;
import java.net.*;

public class Client {

    String nomeServer = "localhost"; // indirizzo server locale
    int portaServer = 6789; // numero della porta
    Socket miosoket;
    BufferedReader tastiera; // buffer per input da tastiera
    String stringaUtente; // stringa inserita da utente
    DataOutputStream outVersoServer; // stream di output
    BufferedReader inDalServer; // stream di input

    public Socket connetti() {
        System.out.println("CLIENT partito in esecuzione ...");
        try {
            tastiera = new BufferedReader(new InputStreamReader(System.in)); // input da tastiera
            miosoket = new Socket(nomeServer, portaServer); // creo un socket
            outVersoServer = new DataOutputStream(miosoket.getOutputStream());
            inDalServer = new BufferedReader(new InputStreamReader(miosoket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Errore host sconosciuto");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Errore durante la trasmissione");
            System.exit(1);
        }
        return miosoket;
    }

    public void comunica() {
        try {
            if (miosoket.isConnected()) {
                System.out.println(inDalServer.readLine());
            }
            do {
                System.out.println(inDalServer.readLine());
                outVersoServer.writeBytes(tastiera.readLine() + '\n');
                System.out.println(inDalServer.readLine());
                outVersoServer.writeBytes(tastiera.readLine() + '\n');
                System.out.println(inDalServer.readLine());
                outVersoServer.writeBytes(tastiera.readLine() + '\n');
                System.out.println(inDalServer.readLine());
                System.out.println(inDalServer.readLine());
                stringaUtente = tastiera.readLine();
                outVersoServer.writeBytes(stringaUtente + '\n');
            } while (stringaUtente.equals("Y"));
            System.out.println(inDalServer.readLine());
            miosoket.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Errore durante la comunicazione col server");
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.connetti();
        client.comunica();
    }

}