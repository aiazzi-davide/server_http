package com.example;

import java.io.*;
import java.lang.reflect.Array;
import java.net.*;
import java.time.LocalDateTime;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class Response {
    ServerSocket server;
    Socket client = null;
    DataOutputStream out;
    BufferedReader in;
    String[] parts;
    Date date = new Date(2000, 1, 1);
    String readLine;
    String firstLine;

    Alunno a1 = new Alunno("Mario", "Rossi", date);
    Alunno a2 = new Alunno("Luigi", "Verdi", date);
    ArrayList<Alunno> alunni = new ArrayList<>(Arrays.asList(a1, a2));
    Classe classe = new Classe(1, "A", "Aula 1", alunni);

    public Response(int port) {
        try {
            server = new ServerSocket(port);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void recieve(){
        try {
            //init
            client = server.accept();
            out = new DataOutputStream(client.getOutputStream());
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            //read request
            do {
                readLine = in.readLine();
                if (readLine.contains("GET")) {
                    firstLine = readLine;
                }
                System.out.println(readLine);
            } while (!readLine.equals(""));
            
            //respond
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
            File file = new File("server/htdocs" + parts[1]);
            if (parts[1].contains(".json")) {
                JsonMapper(classe);
            }
            if(!findFile(file.getPath())){
                fileNotFound();
                return;
            }
            System.out.println(parts[1]);
            System.out.println(file.getPath());
            sendBinaryFile(out, file);
            client.close();
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
                out.writeBytes("Date: " + LocalDateTime.now() + "\n");
                out.writeBytes("content-length: 26\n");
                out.writeBytes("Server: meucci-server\n");
                out.writeBytes("Content-Type: text/plain; charset=UTF-8\n");
                out.writeBytes("\n");
                out.writeBytes("The resource was not found\n");
            } catch (IOException e1) {
                e1.printStackTrace();
            }

    }
    public String getContentType(File f){
        String[] parts = f.getName().split("\\.");
        String ext = parts[parts.length - 1];
        switch (ext) {
            case "html":
                return "text/html; ";
            case "css":
                return "text/css; ";
            case "js":
                return "text/javascript; ";
            case "jpg":
                return "image/jpg; ";
            case "jpeg":
                return "image/jpeg; ";
            case "png":
                return "image/png; ";
            case "gif":
                return "image/gif; ";
        }
        return "text/plain; ";
    }

    public void sendBinaryFile(DataOutputStream out, File file) throws IOException {
        out.writeBytes("HTTP/1.1 200 OK\n");
        out.writeBytes("Date: " + LocalDateTime.now() + "\n");
        out.writeBytes("content-length: " + file.length() + "\n");
        out.writeBytes("Server: meucci-server\n");
        out.writeBytes("Content-Type: " + getContentType(file) + "charset=UTF-8\n");
        out.writeBytes("\n");
        InputStream In = new FileInputStream(file);
        byte[] buffer = new byte[8192];
        int n;
        while ((n = In.read(buffer)) != -1) {
            out.write(buffer, 0, n);
        }
        //in.close();
    }

    public void JsonMapper(Classe obj) {
        String s1;
        try {
            System.out.println("Serializing list...");
            ObjectMapper Mapper = new ObjectMapper();
            s1 = Mapper.writeValueAsString(obj);
            Mapper.writeValue(new File("server/htdocs/Classe.json"), obj);
            System.out.println("list serialized: " + s1);
            //return s1;
        } catch (Exception i) {
            i.printStackTrace();
        }
        //return "error";
    }
}
