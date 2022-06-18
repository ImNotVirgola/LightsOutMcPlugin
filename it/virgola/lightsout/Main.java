package it.virgola.lightsout;

import it.virgola.lightsout.Commands.lightsout;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class Main extends JavaPlugin implements Listener
{

    public static Main instance;

    public void onEnable()
    {
        instance = this;
        Config();
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

    public void Config()
    {
        ArrayList<String> cmds = new ArrayList<>();
        cmds.add("&7/lightsout reload");
        cmds.add("&7/lightsout help");
        cmds.add("&7/lightsout start");
        cmds.add("&7/lightsout stop");

        getConfig().options().copyDefaults(true);
        getConfig().addDefault("Messages.load-configuration-message", "&7Configurazione ricaricata correttamente");
        getConfig().addDefault("Messages.start-game-message", "&a&lIl game é stato creato correttamente");
        getConfig().addDefault("Messages.stop-game-message", "&a&lIl game di %player% é stato stoppato");
        getConfig().addDefault("Messages.congratulations-message", "&a&lCongratulazioni!! Hai completato il gioco!");
        getConfig().addDefault("Messages.table-destroyed-message", "&c&lLa tabella é stata distrutta, il game verrá fermato");

        getConfig().addDefault("Commands.help-message", "&7Ecco la lista dei comandi:");
        getConfig().addDefault("Commands.commands-list", cmds);

        getConfig().addDefault("Messages.Positions.set-position-1-message", "&a&lPosizione 1");
        getConfig().addDefault("Messages.Positions.set-position-2-message", "&a&lPosizione 1");

        getConfig().addDefault("Messages.Errors.load-configuration-error-message", "&c&lErrore durante il reload del config");
        getConfig().addDefault("Messages.Errors.too-much-thick-message", "&c&lErrore durante il reload del config");
        getConfig().addDefault("Messages.Errors.overriding-another-game-message", "&c&lNon puoi iniziare un Game dentro un altro giá esistente");
        getConfig().addDefault("Messages.Errors.invalid-selection-message", "&c&lLa selezione che hai effettuato non é valida");
        getConfig().addDefault("Messages.Errors.max-exceeded-message", "&c&lMax di 10x10 superato");
        getConfig().addDefault("Messages.Errors.unknown-selection-message", "&c&lLa selezione che hai effettuato non é valida");
        getConfig().addDefault("Messages.Errors.already-playing-message", "&c&lHai giá una partita in corso");
        getConfig().addDefault("Messages.Errors.only-player-execution-message", "&a&lQuesto comando puó essere eseguito solamente da un Player");
        getConfig().addDefault("Messages.Errors.not-exist-game-message", "&c&lNon puoi stoppare una partita che non esiste");
        getConfig().addDefault("Messages.Errors.not-exist-command-message", "&c&lUnknown Argument");

        saveConfig();
        reloadConfig();
    }
}