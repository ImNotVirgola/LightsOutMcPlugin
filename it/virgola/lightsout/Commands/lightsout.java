package it.virgola.lightsout.Commands;

import it.virgola.lightsout.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class lightsout implements CommandExecutor
{

    public LampData lampOn()
    {
        return new LampData(Material.WOOL, (byte) 1);
    }

    public LampData lampOff()
    {
        return new LampData(Material.WOOL, (byte) 0);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args)
    {
        if(args.length != 0)
        {
            if(args[0].equalsIgnoreCase("help"))
            {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.instance.getConfig().getString("Commands.help-message")));
                for (String str1 : Main.instance.getConfig().getStringList("Commands.commands-list"))
                {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', str1));
                }
            }
            else if(args[0].equalsIgnoreCase("reload"))
            {
                try
                {
                    Main.instance.reloadConfig();
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.instance.getConfig().getString("Messages.load-configuration-message")));
                }
                catch(Exception e)
                {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.instance.getConfig().getString("Message.load-configuration-error-message")));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lError: " + e.getMessage()));
                    e.printStackTrace();
                }
            }
            else if(args[0].equalsIgnoreCase("start"))
            {
                if (sender instanceof Player) {
                    Player p = (Player) sender;
                    if (GamesHandler.getPlayerGame(p.getUniqueId()) == null) {
                        if (GeneralEvents.pos1.containsKey(p.getUniqueId()) && GeneralEvents.pos2.containsKey(p.getUniqueId())) {
                            Location p_pos1 = GeneralEvents.pos1.get(p.getUniqueId());
                            Location p_pos2 = GeneralEvents.pos2.get(p.getUniqueId());
                            if(Game.maxReached(p_pos1, p_pos2)) {
                                boolean isPossible = Game.isPossible(p_pos1, p_pos2);
                                if (p_pos1.getWorld().equals(p_pos2.getWorld()) && isPossible) {
                                    if (!GamesHandler.isOverridingAnotherGame(p_pos1, p_pos2)) {
                                        if (p_pos1.getBlockX() == p_pos2.getBlockX() || p_pos1.getBlockY() == p_pos2.getBlockY() || p_pos1.getBlockZ() == p_pos2.getBlockZ()) {
                                            GamesHandler.startGame(p.getUniqueId(), p_pos1, p_pos2, lampOn(), lampOff());
                                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.instance.getConfig().getString("Messages.game-start-message")));
                                        } else {
                                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.instance.getConfig().getString("Messages.Errors.too-much-thick-message")));
                                        }
                                    } else {
                                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.instance.getConfig().getString("Messages.Errors.overriding-another-game-message")));
                                    }
                                } else {
                                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.instance.getConfig().getString("Messages.Errors.invalid-selection-message")));
                                }
                            } else {
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.instance.getConfig().getString("Messages.Errors.max-exceeded-message")));
                            }
                        } else {
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.instance.getConfig().getString("Messages.Errors.unknown-selection-message")));
                        }
                    } else {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.instance.getConfig().getString("Messages.Errors.already-playing-message")));
                    }
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.instance.getConfig().getString("Messages.Errors.only-player-execution-message")));
                }
            } else if(args[0].equalsIgnoreCase("stop")) {
                if(args.length > 1) {
                    if (sender instanceof Player) {
                        Player p = Bukkit.getPlayerExact(args[1]);
                        Game game = GamesHandler.getPlayerGame(p.getUniqueId());
                        if (Bukkit.getPlayerExact(args[1]) != null) {
                            if (game != null) {

                                GamesHandler.stopGame(game);
                            }
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.instance.getConfig().getString("Messages.stop-game-message").replace("%player%", args[1])));
                        }
                    }
                } else {
                    if (sender instanceof Player) {
                        Player p = (Player) sender;
                        Game game = GamesHandler.getPlayerGame(p.getUniqueId());
                        if (GamesHandler.getPlayerGame(p.getUniqueId()) != null) {
                            if (game != null) {
                                GamesHandler.stopGame(game);
                            }
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.instance.getConfig().getString("Messages.stop-game-message").replace("%player%", p.getDisplayName())));
                        } else {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.instance.getConfig().getString("Messages.Errors.not-exist-game")));
                        }
                    }
                }
            }
            else
            {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',Main.instance.getConfig().getString("Messages.Errors.not-exist-command-message")));
            }
        }
        else
        {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.instance.getConfig().getString("Commands.help-message")));
            for (String str1 : Main.instance.getConfig().getStringList("Commands.commands-list"))
            {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', str1));
            }
        }
        return false;
    }

}