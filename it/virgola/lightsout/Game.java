package it.virgola.lightsout;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Game implements Listener
{

    public final Location pos1, pos2;
    protected final UUID pUUID;
    public final LampData lampOn, lampOff;
    public HashMap<Location, BlockSave> old_blocks = new HashMap<>();
    public ArrayList<Location> region_blocks_locations = new ArrayList<>();

    public Game(UUID pUUID, Location pos1, Location pos2, LampData lampOn, LampData lampOff)
    {
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.pUUID = pUUID;
        this.lampOn = lampOn;
        this.lampOff = lampOff;

        Bukkit.getPluginManager().registerEvents(this, Main.instance);
    }

    public void setTable()
    {
        for (int i = Math.min(pos1.getBlockX(), pos2.getBlockX()); i <= Math.max(pos1.getBlockX(), pos2.getBlockX()); i++)
        {
            for (int j = Math.min(pos1.getBlockY(), pos2.getBlockY()); j <= Math.max(pos1.getBlockY(), pos2.getBlockY()); j++)
            {
                for (int k = Math.min(pos1.getBlockZ(), pos2.getBlockZ()); k <= Math.max(pos1.getBlockZ(), pos2.getBlockZ()); k++)
                {
                    Block temp_block = new Location(pos1.getWorld(), i, j, k).getBlock();
                    region_blocks_locations.add(temp_block.getLocation());
                    old_blocks.put(temp_block.getLocation(), new BlockSave(temp_block.getType(), temp_block.getData()));
                    setLampOff(temp_block);
                }
            }
        }

        //Cache Save
    }

    public void setBack()
    {
        for (Location block_location : old_blocks.keySet()) {
            BlockSave temp_blocksave = old_blocks.get(block_location);
            block_location.getWorld().getBlockAt(block_location).setType(temp_blocksave.material);
            block_location.getWorld().getBlockAt(block_location).setData(temp_blocksave.data);
        }
    }

    ////////////////////////////////////////////////

    public boolean isLampOn(Block block)
    {
        return block.getType().equals(lampOn.getMaterial()) && block.getData() == lampOn.getData();
    }

    public boolean isLampOff(Block block)
    {
        return block.getType().equals(lampOff.getMaterial()) && block.getData() == lampOff.getData();
    }
    public void setLampOn(Block block)
    {
        block.setType(lampOn.getMaterial());
        block.setData(lampOn.getData());
    }

    public void setLampOff(Block block)
    {
        block.setType(lampOff.getMaterial());
        block.setData(lampOff.getData());
    }

    public UUID getPlayerUUID()
    {
        return pUUID;
    }

    public boolean isGameBlock(Block block)
    {
        return region_blocks_locations.contains(block.getLocation());
    }

    public void reverseBlock(Block block)
    {
        Material lampOff = new LampData(Material.WOOL, (byte) 0).getMaterial();
        Material lampOn = new LampData(Material.WOOL, (byte) 0).getMaterial();
        if (block.getType().equals(lampOff) || block.getType().equals(lampOn)) {

            Block bl0 = block.getLocation().getWorld().getBlockAt(block.getLocation().getBlockX(), block.getLocation().getBlockY(), block.getLocation().getBlockZ());
            Block bl1 = block.getLocation().getWorld().getBlockAt(block.getLocation().getBlockX() - 1, block.getLocation().getBlockY(), block.getLocation().getBlockZ());
            Block bl2 = block.getLocation().getWorld().getBlockAt(block.getLocation().getBlockX() + 1, block.getLocation().getBlockY(), block.getLocation().getBlockZ());
            Block bl3 = block.getLocation().getWorld().getBlockAt(block.getLocation().getBlockX(), block.getLocation().getBlockY() - 1, block.getLocation().getBlockZ());
            Block bl4 = block.getLocation().getWorld().getBlockAt(block.getLocation().getBlockX(), block.getLocation().getBlockY() + 1, block.getLocation().getBlockZ());
            Block bl5 = block.getLocation().getWorld().getBlockAt(block.getLocation().getBlockX(), block.getLocation().getBlockY(), block.getLocation().getBlockZ() - 1);
            Block bl6 = block.getLocation().getWorld().getBlockAt(block.getLocation().getBlockX(), block.getLocation().getBlockY(), block.getLocation().getBlockZ() + 1);


            if (isLampOff(bl0)) setLampOn(bl0);
            else if (isLampOn(bl0)) setLampOff(bl0);
            if (isLampOff(bl1)) setLampOn(bl1);
            else if (isLampOn(bl1)) setLampOff(bl1);
            if (isLampOff(bl2)) setLampOn(bl2);
            else if (isLampOn(bl2)) setLampOff(bl2);
            if (isLampOff(bl3)) setLampOn(bl3);
            else if (isLampOn(bl3)) setLampOff(bl3);
            if (isLampOff(bl4)) setLampOn(bl4);
            else if (isLampOn(bl4)) setLampOff(bl4);
            if (isLampOff(bl5)) setLampOn(bl5);
            else if (isLampOn(bl5)) setLampOff(bl5);
            if (isLampOff(bl6)) setLampOn(bl6);
            else if (isLampOn(bl6)) setLampOff(bl6);
            block.getLocation().getWorld().playSound(block.getLocation(), Sound.CLICK, 1,2);

            if (win())
            {
                block.getLocation().getWorld().playSound(block.getLocation(), Sound.FIREWORK_BLAST, 1,1);
                block.getLocation().getWorld().playSound(block.getLocation(), Sound.LEVEL_UP, 1,1);
                GamesHandler.stopGame(this);
                Bukkit.getPlayer(pUUID).sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&lCongratulazioni!! Hai completato il gioco!"));
            }
        }
    }

    public boolean win()
    {
        for (Location loc : region_blocks_locations) {
            if (isLampOff(loc.getWorld().getBlockAt(loc))) return false;
        }
        return true;
    }
}
