package Client;
import java.io.*;
import java.net.*;
import java.util.Scanner;

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
                //Connects to server
                socket = new Socket(host, PORT);



                // PROBLEM!!!!
                Scanner scanner = new Scanner(socket.getInputStream());
                String answer;
                do
                {
                    System.out.println("Enter desired username: ");
                    Scanner userEntry = new Scanner(System.in);
                    String userName = userEntry.nextLine();
                    System.out.println(userName);
                    PrintWriter out = new PrintWriter(socket.getOutputStream());
                    out.println("JOIN " + userName + "," + host + ":" + PORT);
                    answer = scanner.nextLine();
                    System.out.println(answer);
                }
                while (!answer.equals("J_OK"));
                //PROBLEM SLUT HER!!!


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