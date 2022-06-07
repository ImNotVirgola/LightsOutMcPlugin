package it.virgola.lightsout.Commands;

import it.virgola.lightsout.*;
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
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Ecco la lista dei comandi:"));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7/lightsout reload"));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7/lightsout start"));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7/lightsout help"));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7/lightsout stop"));
            }
            else if(args[0].equalsIgnoreCase("reload"))
            {
                try
                {
                    Main.instance.reloadConfig();
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Configurazione ricaricata correttamente"));
                }
                catch(Exception e)
                {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lErrore durante il reload del config"));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lErrore: " + e.getMessage()));
                    e.printStackTrace();
                }
            }
            else if(args[0].equalsIgnoreCase("start"))
            {
                if (sender instanceof Player) {
                    Player p = (Player) sender;

                    if (GamesHandler.getPlayerGame(p.getUniqueId()) == null)
                    {
                        if (GeneralEvents.pos1.containsKey(p.getUniqueId()) && GeneralEvents.pos2.containsKey(p.getUniqueId()))
                        {
                            Location p_pos1 = GeneralEvents.pos1.get(p.getUniqueId());
                            Location p_pos2 = GeneralEvents.pos2.get(p.getUniqueId());

                            if (p_pos1.getWorld().equals(p_pos2.getWorld()))
                            {
                                if (p_pos1.getBlockX() == p_pos2.getBlockX() || p_pos1.getBlockY() == p_pos2.getBlockY() || p_pos1.getBlockZ() == p_pos2.getBlockZ())
                                {
                                        GamesHandler.startGame(p.getUniqueId(), p_pos1, p_pos2, lampOn(), lampOff());
                                } else
                                {
                                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lLa tabella deve essere spessa un blocco"));
                                }
                            }
                            else
                            {
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lLa selezione che hai effettuato non é valida!!!"));
                            }
                        }
                        else
                        {
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lLa selezione che hai effettuato non é valida!!!"));
                        }
                    }
                    else
                    {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lHai giá una partita in corso!!!"));
                    }
                }
                else
                {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&lQuesto comando puó essere eseguito solamente da un Player"));
                }
            }
            else if(args[0].equalsIgnoreCase("stop"))
            {
                if (sender instanceof Player)
                {
                    Player p = (Player) sender;
                    Game game = GamesHandler.getPlayerGame(p.getUniqueId());

                    if(game != null)
                    {
                        GamesHandler.stopGame(game);
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&lPartita terminata con successo"));
                    }
                    else {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lNon puoi stoppare una partita che non esiste!"));
                    }
                }
            }
            else
            {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&cUnknown Argument"));
            }
        }
        else
        {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Ecco la lista dei comandi:"));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7/lightsout reload"));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7/lightsout start"));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7/lightsout help"));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7/lightsout stop"));
        }
        return false;
    }

}
