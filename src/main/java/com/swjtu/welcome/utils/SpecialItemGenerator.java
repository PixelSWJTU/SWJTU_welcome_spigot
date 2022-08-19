package com.swjtu.welcome.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class SpecialItemGenerator {
    public ItemStack generateNewUniforms() {
        ItemStack res = new ItemStack(Material.ELYTRA);
        ItemMeta im = res.getItemMeta();
        assert im != null;
        im.setDisplayName("§6§l西南交通大学校服");
        im.setUnbreakable(true);
        // 附魔
        List<String> lore = new ArrayList<>();
        lore.add("§4§l西南交通大学校服");
        lore.add("§4§l可以设置为玩家的服装");
        lore.add("§4§l右键点击可以设置为玩家的服装");
        im.setLore(lore);
        res.setItemMeta(im);

        return res;
    }
}
