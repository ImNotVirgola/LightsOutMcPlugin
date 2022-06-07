package it.virgola.lightsout;

import it.virgola.lightsout.Commands.lightsout;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener
{

    public static Main instance;

    public void onEnable()
    {
        instance = this;
        getCommand("lightsout").setExecutor(new lightsout());
        Bukkit.getPluginManager().registerEvents(new GeneralEvents(), this);
    }

    public void onDisable()
    {
        for (Game game : GamesHandler.games)
        {
            GamesHandler.stopGame(game);
        }
    }
}

