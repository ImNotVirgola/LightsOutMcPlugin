package it.virgola.lightsout;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.UUID;

public class GamesHandler
{

    public static ArrayList<Game> games = new ArrayList<>();

    public static Game startGame(UUID pUID, Location pos1, Location pos2, LampData lampOn, LampData lampOff) {

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
    public static boolean isOverridingAnotherGame(Location pos1, Location pos2)
    {
        for (int i = Math.min(pos1.getBlockX(), pos2.getBlockX()); i <= Math.max(pos1.getBlockX(), pos2.getBlockX()); i++)
        {
            for (int j = Math.min(pos1.getBlockY(), pos2.getBlockY()); j <= Math.max(pos1.getBlockY(), pos2.getBlockY()); j++)
            {
                for (int k = Math.min(pos1.getBlockZ(), pos2.getBlockZ()); k <= Math.max(pos1.getBlockZ(), pos2.getBlockZ()); k++)
                {
                    for(Game game : games)
                    {
                        Location temp_loc = new Location(pos1.getWorld(), i, j, k);
                        if(game.region_blocks_locations.contains(temp_loc)) return true;
                    }
                }
            }
        }
        return false;
    }
}