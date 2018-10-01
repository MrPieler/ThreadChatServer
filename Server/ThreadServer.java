package Server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadServer
{
    private static List<Socket> clients = new ArrayList<>();
    public static List<String> userNames = new ArrayList<>();
    private static ServerSocket serverSocket;
    private static final int PORT = 1234;

    public static void main(String[] args) throws IOException
    {
        try
        {
            serverSocket = new ServerSocket(PORT);
        }
        catch (IOException ioEx)
        {
            System.out.println("\nUnable to set up port!");
            System.exit(1);
        }

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        while(true)
        {
            //Wait for client…
            Socket client = serverSocket.accept();
            System.out.println("\nNew client accepted.\n");

            //Add client to threadPool (which executes thread)
            clients.add(client);
            ClientHandler handler = new ClientHandler(client, clients);
            executor.submit(handler);
        }
    }
}
