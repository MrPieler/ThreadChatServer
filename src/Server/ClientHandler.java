package Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

class ClientHandler implements Runnable
{
    private Socket client;
    private List<Socket> clients;
    private Scanner input;
    private PrintWriter output;
    private String received;
    private String username;

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

        //Write until quit
        do
        {
            received = input.nextLine();
            inputTreating(received, output);
        } while(!received.equals("QUIT"));

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

    //Switch statement based on input from client
    private void inputTreating(String message, PrintWriter out)
    {
        switch (message.substring(0,4))
        {
            //Login case
            case "JOIN":
                loginCheck(message, out);
                break;

            case "DATA":
                broadcast(out, message);
                break;

            case "QUIT":
                quit();
                break;

            case "IMAV":
                System.out.println(this.username + " is alive");
                break;

            default:
                out.println("J_ER 2: Unknown_command");
                break;
        }
    }
    private void loginCheck (String message, PrintWriter out)
    {
        //Takes username out of String
        String username = message.substring(5,message.indexOf(","));

        //For testing purposes!
        System.out.println(message.substring(5,message.indexOf(",")));


        if(!ThreadServer.userNames.contains(username))
        {
            ThreadServer.userNames.add(username);
            this.username = username;
            clients.add(client);
            out.println("J_OK");
        }
        else
        {
            out.println("J_ER 1: Username_taken");
        }
        broadcast(output, getUserNames());
    }

    private String getUserNames()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("LIST ");
        for (String username:ThreadServer.userNames)
        {
            sb.append(username + " ");
        }
        System.out.println(sb.toString());
        return sb.toString();
    }

    private void broadcast(PrintWriter out, String message)
    {
        //Prints to every client
        for (Socket s: clients)
        {
            try
            {
                out = new PrintWriter(s.getOutputStream(), true);
                out.println(message);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void quit()
    {
        clients.remove(client);
        ThreadServer.userNames.remove(this.username);
        Thread.currentThread().interrupt();
        System.out.println("Client " + this.username + " removed from active users");
        broadcast(output, getUserNames());
    }
}
