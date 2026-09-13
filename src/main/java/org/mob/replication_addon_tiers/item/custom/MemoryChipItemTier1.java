package org.mob.replication_addon_tiers.item.custom;

import com.buuz135.replication.item.MemoryChipItem;
import net.minecraft.world.item.ItemStack;

public class MemoryChipItemTier1 extends MemoryChipItem {
    public MemoryChipItemTier1(){
        super();
    }

    @Override
    public int getPatternSlots(ItemStack element) {
        return 64;
    }
}
