package it.virgola.lightsout;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.UUID;

public class GeneralEvents implements Listener
{

    public static HashMap<UUID, Location> pos1 = new HashMap<>();
    public static HashMap<UUID, Location> pos2 = new HashMap<>();

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getClickedBlock() == null) return;

        Player p = e.getPlayer();
        if (e.getItem() != null && e.getItem().getType().equals(Material.STICK)) {
            if (p.isSneaking()) {
                if (e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                    e.setCancelled(true);
                    pos1.put(p.getUniqueId(), e.getClickedBlock().getLocation());
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.instance.getConfig().getString("Messages.Positions.set-position-1-message")));
                } else if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                    e.setCancelled(true);
                    pos2.put(p.getUniqueId(), e.getClickedBlock().getLocation());
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.instance.getConfig().getString("Messages.Positions.set-position-2-message")));
                }
                return;
            }
        }

        Game game = GamesHandler.getPlayerGame(p.getUniqueId());
        if (game != null) {
            if (game.isGameBlock(e.getClickedBlock()))
            {
                game.reverseBlock(e.getClickedBlock());
            }
        }
    }

    @EventHandler
    public void blockPlace(BlockPlaceEvent e)
    {
        Player p = e.getPlayer();
        Game game = GamesHandler.getPlayerGame(p.getUniqueId());
        if (game != null) {
            if (game.isGameBlock(e.getBlock())) e.setCancelled(true);
        }
    }
    @EventHandler
    public void blockBreak(BlockBreakEvent e)
    {
        Location loc = new Location (e.getBlock().getWorld(), e.getBlock().getX(), e.getBlock().getY(), e.getBlock().getZ());
        for (Game game : GamesHandler.games)
        {
            if(game.isGameBlock(loc.getBlock())) {e.setCancelled(true); break;}
        }
    }

}