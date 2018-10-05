package Server;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

public class ActiveUsers extends TimerTask
{
    @Override
    public void run()
    {
        List<String> usernames = new ArrayList<>();
        usernames.addAll(ThreadServer.userNames.keySet());
        for (Map.Entry<String, Boolean> e: ThreadServer.userNames.entrySet())
        {
            if (!e.getValue())
            {
                ThreadServer.userNames.remove(e);
            }
        }
        if (!ThreadServer.userNames.keySet().containsAll(usernames))
        {

        }
    }
}
