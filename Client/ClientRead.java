package ThreadChatProgram.Client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientRead implements Runnable
{
    private Socket socket;

    public ClientRead(Socket socket)
    {
        this.socket = socket;
    }

    @Override
    public void run()
    {
        String message;
        Scanner networkInput = null;
        try
        {
            networkInput = new Scanner(socket.getInputStream());
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        while(true)
        {
            message = networkInput.nextLine();
            System.out.println(message);
        }
    }
}
