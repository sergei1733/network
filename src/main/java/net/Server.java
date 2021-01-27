package net;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws IOException {

        ServerSocket socket = new ServerSocket(25225);
        System.out.println("Server is started ");
        while (true){
            Socket client = socket.accept();
            handleRequest(client);

        }
    }

    private static void handleRequest(Socket client) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

        StringBuilder sb = new StringBuilder("Hello, ");
        String userName = br.readLine();
        System.out.println("server got string " + userName);

        sb.append(userName);
        bw.write(sb.toString());
        bw.newLine();
        bw.flush();

        br.close();
        bw.close();

        client.close();
    }
}
