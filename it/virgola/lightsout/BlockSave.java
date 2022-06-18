package it.virgola.lightsout;

import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;

public class BlockSave
{

    public final Material material;
    public final Byte data;

    public final ItemStack[] contents;

    public BlockSave(Material material, Byte data, ItemStack[] contents) {
        this.material = material;
        this.data = data;
        this.contents = contents;
    }
}