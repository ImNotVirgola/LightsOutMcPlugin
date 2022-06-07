package it.virgola.lightsout;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.UUID;

public class GamesHandler
{

    public static ArrayList<Game> games = new ArrayList<>();

    public static Game startGame(UUID pUID, Location pos1, Location pos2, LampData lampOn, LampData lampOff)
    {
        Game game = new Game(pUID, pos1, pos2, lampOn, lampOff);
        games.add(game);
        game.setTable();

        return game;
    }

    public static void stopGame(Game game)
    {
        game.setBack();
        games.remove(game);
    }
    public static Game getPlayerGame(UUID pUID)
    {
        for (Game game : games)
        {
            if(game.getPlayerUUID().equals(pUID)) return game;
        }

        return null;
    }

}
