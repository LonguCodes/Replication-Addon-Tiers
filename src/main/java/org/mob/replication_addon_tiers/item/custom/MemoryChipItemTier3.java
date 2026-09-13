package org.mob.replication_addon_tiers.item.custom;

import com.buuz135.replication.item.MemoryChipItem;
import net.minecraft.world.item.ItemStack;

public class MemoryChipItemTier3 extends MemoryChipItem {
    public MemoryChipItemTier3(){
        super();
    }

    @Override
    public int getPatternSlots(ItemStack element) {
        return 256;
    }
}
