package net.eofitg.qcheck.mixin;

import net.eofitg.qcheck.QCheck;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityPlayerSP.class)
public class EntityPlayerSPMixin {
    @Inject(method = "dropOneItem", at = @At("HEAD"), cancellable = true)
    public void onDropOneItem(boolean dropAll, CallbackInfoReturnable<EntityItem> cir) {
        EntityPlayerSP player = (EntityPlayerSP) (Object) this;
        ItemStack heldItem = player.getHeldItem();

        if (QCheck.config.mapContains(heldItem)) {
            cir.setReturnValue(null);
            cir.cancel();
        }
    }
}