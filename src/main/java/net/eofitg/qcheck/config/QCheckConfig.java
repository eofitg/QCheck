package net.eofitg.qcheck.config;

import net.minecraft.item.ItemStack;

import java.util.LinkedHashMap;

public class QCheckConfig {

    private boolean enabled = true;
    private final LinkedHashMap<Integer, String> checkMap = new LinkedHashMap<>();

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public LinkedHashMap<Integer, String> getCheckMap() {
        return checkMap;
    }

    public boolean mapContains(ItemStack itemStack) {
        if (itemStack == null) return false;
        return checkMap.containsValue(getId(itemStack));
    }

    public void mapAdd(ItemStack itemStack) {
        if (itemStack == null) return;
        checkMap.put(checkMap.size(), getId(itemStack));
    }

    public void mapRemove(int id) {
        checkMap.remove(id);
    }

    private String getId(ItemStack itemStack) {
        return itemStack.getItem().getRegistryName().substring("minecraft.".length()) + "|" + itemStack.getMetadata() + "|" + itemStack.getTagCompound();
    }

}