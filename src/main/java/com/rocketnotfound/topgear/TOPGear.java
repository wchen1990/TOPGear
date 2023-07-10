package com.rocketnotfound.topgear;

import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

@Mod(TOPGear.MODID)
public class TOPGear
{
    public static final String MODID = "topgear";
    public static final DeferredRegister<RecipeSerializer<?>> DEF_REG = DeferredRegister.create(Registry.RECIPE_SERIALIZER_REGISTRY, TOPGear.MODID);
    public static final RegistryObject<RecipeSerializer<?>> TOPGEAR_RECIPE = DEF_REG.register("topgear_recipe", () -> new SimpleRecipeSerializer<>(TOPGearRecipe::new));

    public TOPGear() {
        IEventBus modBusEvent = FMLJavaModLoadingContext.get().getModEventBus();
        DEF_REG.register(modBusEvent);
    }
}
