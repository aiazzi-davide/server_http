package com.example;
import java.net.*;

public class Main {
    public static void main(String[] args) {
        Response response = new Response(8080);
        ThreadConsole console = new ThreadConsole();
        console.start();
        while (true) {
            response.recive();
        }
    }
}