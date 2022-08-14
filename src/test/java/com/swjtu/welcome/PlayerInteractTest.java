package com.swjtu.welcome;

import org.bukkit.Material;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.swjtu.welcome.PlayerInteract;
//import static org.junit.Assert.assertEquals;

class PlayerInteractTest {


    @Test
    void testGetBlock() {
        PlayerInteract playerInteract = new PlayerInteract();
        Material m = playerInteract.getBlock("GRAY_CONCRETE");
        System.out.println(m.toString());
        Assertions.assertEquals(Material.GRAY_CONCRETE, m);
    }

    @Test
    void testGetVersion() {
        PlayerInteract playerInteract = new PlayerInteract();
        String version = playerInteract.getVersion("1234-123-123-1234 (MC: 1.12)");
        System.out.println(version);
        Assertions.assertEquals("1.12", version);
    }
}