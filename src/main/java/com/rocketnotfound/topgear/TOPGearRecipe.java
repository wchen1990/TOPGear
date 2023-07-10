package com.rocketnotfound.topgear;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;

public class TOPGearRecipe extends CustomRecipe {
    public TOPGearRecipe(ResourceLocation id) {
        super(id);
    }

    protected boolean stackMeetsCriteria(ItemStack stack) {
        return Mob.getEquipmentSlotForItem(stack) == EquipmentSlot.HEAD;
    }

    @Override
    public boolean matches(@Nonnull CraftingContainer inv, @Nonnull Level world) {
        boolean foundSource = false;
        boolean foundTarget = false;

        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack stack = inv.getItem(i);
            if (!stack.isEmpty()) {
                Item item = stack.getItem();
                if (item.equals(ForgeRegistries.ITEMS.getValue(new ResourceLocation("theoneprobe:probe")))) {
                    if (foundTarget) {
                        return false;
                    }
                    foundTarget = true;
                } else if (stackMeetsCriteria(stack)) {
                    if (foundSource) {
                        return false;
                    }
                    foundSource = true;
                } else {
                    return false;
                }
            }
        }

        return foundSource && foundTarget;
    }

    @Nonnull
    @Override
    public ItemStack assemble(@Nonnull CraftingContainer inv) {
        ItemStack target = ItemStack.EMPTY;

        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack stack = inv.getItem(i);
            if (!stack.isEmpty()) {
                if (stackMeetsCriteria(stack)) {
                    target = stack;
                }
            }
        }

        ItemStack copy = target.copy();
        copy.addTagElement("theoneprobe", IntTag.valueOf(1));
        CompoundTag nbt = copy.getTag();
        if (nbt != null) {
            CompoundTag displayNBT = nbt.contains("display") ? (CompoundTag) nbt.get("display") : new CompoundTag();
            ListTag listNBT = displayNBT.contains("Lore") ? (ListTag) displayNBT.get("Lore") : new ListTag();
            listNBT.add(StringTag.valueOf("\"Probe Attached\""));
            displayNBT.put("Lore", listNBT);
            nbt.put("display", displayNBT);
            copy.setTag(nbt);
        }

        return copy;
    }

    @Nonnull
    @Override
    public RecipeSerializer<?> getSerializer() {
        return TOPGear.TOPGEAR_RECIPE.get();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }
}
