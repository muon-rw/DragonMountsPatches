package dev.muon.dragon_mounts_patches.mixin;

import com.github.kay9.dragonmounts.client.KeyMappings;
import com.github.kay9.dragonmounts.dragon.TameableDragon;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = KeyMappings.class, remap = false)
public class KeyMappingsMixin {

    @Inject(method = "handleKeyPress", at = @At("HEAD"), cancellable = true)
    private static void checkNullPlayer(int key, int action, CallbackInfo ci) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null) ci.cancel();
    }

    @WrapOperation(
            method = "handleKeyPress",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;getVehicle()Lnet/minecraft/world/entity/Entity;", remap = true)
    )
    private static Entity wrapGetVehicle(LocalPlayer instance, Operation<Entity> original) {
        return instance != null ? original.call(instance) : null;
    }

    @WrapOperation(
            method = "handleKeyPress",
            at = @At(value = "INVOKE", target = "Lcom/github/kay9/dragonmounts/dragon/TameableDragon;getDisplayName()Lnet/minecraft/network/chat/Component;", remap = true)
    )
    private static net.minecraft.network.chat.Component wrapGetDisplayName(TameableDragon instance, Operation<Component> original) {
        return instance != null ? original.call(instance) : net.minecraft.network.chat.Component.empty();
    }
}