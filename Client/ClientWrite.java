package ThreadChatProgram.Client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientWrite implements Runnable
{
    private Socket socket;

    public ClientWrite(Socket socket)
    {
        this.socket = socket;
    }

    @Override
    public void run()
    {
        String message = "";
        Scanner userEntry = new Scanner(System.in);
        PrintWriter output;
        try
        {
            output = new PrintWriter(socket.getOutputStream(),true);
            System.out.print("Enter message ('QUIT' to exit): \n");
            while (!message.equals("QUIT"))
            {
                message = userEntry.nextLine();
                output.println(message);
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
