package net;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        sendRequest();
    }

    private static void sendRequest() throws IOException {
        Socket socket = new Socket("127.0.0.1",25225);

        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        String sb ="Sergei,";

        bw.write(sb);
        bw.newLine();
        bw.flush();

        String answer = br.readLine();
        System.out.println(answer);

        br.close();
        bw.close();
    }
}
