package it.virgola.lightsout;


import org.bukkit.Material;

public class BlockSave
{

    public final Material material;
    public final Byte data;

    public BlockSave(Material material, Byte data) {
        this.material = material;
        this.data = data;
    }

}
