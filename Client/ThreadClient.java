package ThreadChatProgram.Client;
import java.io.*;
import java.net.*;

public class ThreadClient
{
        private static InetAddress host;
        private static final int PORT = 1234;

        public static void main(String[] args) throws IOException
        {
            try
            {
                host = InetAddress.getLocalHost();
            }
            catch(UnknownHostException uhEx)
            {
                System.out.println("\nHost ID not found!\n");
                System.exit(1);
            }
            sendMessages();
        }
        private static void sendMessages() throws IOException
        {
            Socket socket = null;
            try
            {
                socket = new Socket(host, PORT);

                Thread clientIn = new Thread(new ClientRead(socket));
                Thread clientOut = new Thread(new ClientWrite(socket));
                clientIn.start();
                clientOut.start();
            }
            catch(IOException ioEx)
            {
                ioEx.printStackTrace();
            }
        }
}