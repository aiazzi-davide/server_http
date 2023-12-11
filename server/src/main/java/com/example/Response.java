package com.example;

import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.util.*;

public class Response {
    ServerSocket server;
    Socket client = null;
    DataOutputStream out;
    BufferedReader in;
    String[] parts;
    Date date = new Date();
    String readLine;
    String firstLine;

    public Response(int port) {
        try {
            ServerSocket server = new ServerSocket(port);
            client = server.accept();
            out = new DataOutputStream(client.getOutputStream());
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void recive(){
        try {
            do {
                readLine = in.readLine();
                if (readLine.contains("GET")) {
                    firstLine = readLine;
                }
                System.out.println(readLine);
            } while (!readLine.equals(""));
            respond(firstLine);
        } catch(NullPointerException e){
            
        }
        catch (IOException e) {
            e.printStackTrace();
        } 
    }   
    //htttp response
    public void respond(String message){
        if (message == null) {
            return;
        }
        parts = message.split(" ");

        try {
            File file = new File(parts[1].substring(1));
            System.out.println(file.getPath());
            Scanner MyReader = new Scanner(file);
            out.writeBytes("HTTP/1.1 200 OK\n");
            out.writeBytes("Date: " + LocalDateTime.now() + "\n");
            out.writeBytes("content-length: " + file.length() + "\n");
            out.writeBytes("Server: meucci-server\n");
            out.writeBytes("Content-Type: text/plain; charset=UTF-8\n");
            out.writeBytes("\n");            
            while (MyReader.hasNextLine()) {
                out.writeBytes(MyReader.nextLine());
            }
        } catch (FileNotFoundException e) {
            fileNotFound();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean findFile(String path){
        File file = new File(path);
        return file.isFile();
    }
    public void fileNotFound(){
        try {
                out.writeBytes("HTTP/1.1 404 Not Found\n");
                out.writeBytes("Date: " + date.toString() + "\n");
                out.writeBytes("content-length: 26\n");
                out.writeBytes("Server: meucci-server\n");
                out.writeBytes("Content-Type: text/plain; charset=UTF-8\n");
                out.writeBytes("\n");
                out.writeBytes("The resource was not found\n");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
    }
}
