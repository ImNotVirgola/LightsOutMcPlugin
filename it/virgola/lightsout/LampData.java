package it.virgola.lightsout;

import org.bukkit.Material;

public class LampData {

    private final Material material;
    private final byte data;

    public LampData(Material material, byte data) {
        this.material = material;
        this.data = data;
    }

    public Material getMaterial() { return material; }
    public byte getData() { return data; }

}