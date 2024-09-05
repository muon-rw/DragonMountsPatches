package dev.muon.dragon_mounts_patches;

import com.github.kay9.dragonmounts.DMLRegistry;
import com.github.kay9.dragonmounts.dragon.TameableDragon;
import com.github.kay9.dragonmounts.dragon.egg.HatchableEggBlock;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

@JeiPlugin
public class DMPJEIPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(DragonMountsPatches.MODID, "jei_plugin");
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        registration.registerSubtypeInterpreter(DMLRegistry.SPAWN_EGG.get(), new DragonEggSubtypeInterpreter());
        registration.registerSubtypeInterpreter(DMLRegistry.EGG_BLOCK_ITEM.get(), new DragonEggBlockSubtypeInterpreter());
    }

    private static class DragonEggSubtypeInterpreter implements IIngredientSubtypeInterpreter<ItemStack> {
        @Override
        public String apply(ItemStack itemStack, UidContext context) {
            var entityTag = itemStack.getTagElement("EntityTag");
            if (entityTag != null && entityTag.contains(TameableDragon.NBT_BREED)) {
                return entityTag.getString(TameableDragon.NBT_BREED);
            }
            return IIngredientSubtypeInterpreter.NONE;
        }
    }

    private static class DragonEggBlockSubtypeInterpreter implements IIngredientSubtypeInterpreter<ItemStack> {
        @Override
        public String apply(ItemStack itemStack, UidContext context) {
            var blockEntityTag = itemStack.getTagElement("BlockEntityTag");
            if (blockEntityTag != null && blockEntityTag.contains(HatchableEggBlock.NBT_BREED)) {
                return blockEntityTag.getString(HatchableEggBlock.NBT_BREED);
            }
            return IIngredientSubtypeInterpreter.NONE;
        }
    }
}