package net;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws IOException, InterruptedException {

        ServerSocket socket = new ServerSocket(25225);
        System.out.println("Server is started ");
        while (true){
            Socket client = socket.accept();
            new SimpleServer(client).start();

        }
    }

    private static void handleRequest(Socket client) throws IOException, InterruptedException {

    }
}

class SimpleServer extends Thread{
    private Socket client;

    public SimpleServer (Socket client){
        this.client = client;
    }
    @Override
    public void run(){
        handleRequest();
    }
    private void handleRequest(){
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));


            String request = br.readLine();
            String[] lines = request.split("\\s+");
            String command = lines[0];
            String userName = lines[1];

            System.out.println("server got string 1" + command);
            System.out.println("server got string 2" + userName);

            StringBuilder sb = new StringBuilder("Hello, ");
            //Thread.sleep(2000);
            sb.append(userName);
            bw.write(sb.toString());
            bw.newLine();
            bw.flush();

            br.close();
            bw.close();

            client.close();
        }catch (Exception ex){
            ex.printStackTrace(System.out);
        }
    }

    private String buildResponse(){
        return "";
    }
}
