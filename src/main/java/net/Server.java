package net;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

public class Server {

    public static void main(String[] args) throws IOException, InterruptedException {

        ServerSocket socket = new ServerSocket(25225);

        Map<String, Greetable> handlers = loadHandlers();

        System.out.println("Server is started ");
        while (true) {
            Socket client = socket.accept();
            new SimpleServer(client,handlers).start();

        }
    }

    private static Map<String, Greetable> loadHandlers() {

        Map<String, Greetable> result = new HashMap<>();

        try(InputStream is = Server.class.getClassLoader().getResourceAsStream("server.properrties")){

            Properties properties = new Properties();
            properties.load(is);

            for (Object command : properties.keySet()){
                String className = properties.getProperty(command.toString());
                Class<Greetable> cl = (Class<Greetable>) Class.forName(className);

                Greetable handler = cl.getConstructor().newInstance();
                result.put(command.toString(),handler);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private static void handleRequest(Socket client) throws IOException, InterruptedException {

    }
}

class SimpleServer extends Thread {
    private Socket client;
    private Map<String,Greetable> handlers;
    public SimpleServer(Socket client, Map<String, Greetable> handlers) {
        this.client = client;
        this.handlers = handlers;
    }

    @Override
    public void run() {
        handleRequest();
    }

    private void handleRequest() {
        try {
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
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
    }

    private String buildResponse() {
        return "";
    }
}
