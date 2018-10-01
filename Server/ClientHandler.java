package ThreadChatProgram.Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

class ClientHandler extends Thread
{
    private Socket client;
    private List<Socket> clients;
    private Scanner input;
    private PrintWriter output;
    private String received;

    public ClientHandler(Socket socket, List<Socket> clients)
    {
        this.client = socket;
        this.clients = clients;
        try
        {
            input = new Scanner(client.getInputStream());
            output = new PrintWriter(client.getOutputStream(),true);
        }
        catch(IOException ioEx)

        {
            ioEx.printStackTrace();
        }
    }
    public void run()
    {
        do
        {
            received = input.nextLine();

            PrintWriter out = null;
            try
            {
                out = new PrintWriter(client.getOutputStream(), true);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            inputTreating(received, out);
        }
        while (!received.equals("QUIT"));
        try
        {
            if (client!=null)
            {
                System.out.println("Closing down connection…");
                client.close();
            }
        }
        catch(IOException ioEx)
        {
            System.out.println("Unable to disconnect!");
        }
    }

    private void inputTreating(String message, PrintWriter out)
    {
        switch (message.substring(0,4))
        {
            //Login case
            case "JOIN":
                String username = message.substring(5,message.indexOf(","));
                System.out.println(message.substring(5,message.indexOf(",")));
                if(!ThreadServer.userNames.contains(username))
                {
                    ThreadServer.userNames.add(username);
                    out.println("J_OK");
                }
                else
                {
                    out.println("J_ER 1: Username taken");
                }
                break;

            case "DATA":
                for (Socket s: clients)
                {
                    try
                    {
                        out = new PrintWriter(s.getOutputStream(), true);
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                break;

            default:
                out.println("Error");
                break;
        }
    }
}
