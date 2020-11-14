package net.cinderous.geomancyandterraforming;

import net.cinderous.geomancyandterraforming.item.SpellDev;
import net.cinderous.geomancyandterraforming.item.SpellDev2;
import net.cinderous.geomancyandterraforming.item.SpellDev3;
import net.cinderous.geomancyandterraforming.item.SpellDev4;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = GeomancyAndTerraforming.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistryHandler
{

    public static void init() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
//        ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
//        BIOMES.register(FMLJavaModLoadingContext.get().getModEventBus());
//        TILE_ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }


    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, GeomancyAndTerraforming.MODID);
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, GeomancyAndTerraforming.MODID);
//    private static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, Cinderbane.MODID);
//    private static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, Cinderbane.MODID);
//    private static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Cinderbane.MODID);
//    private static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Cinderbane.MODID);
    //




    //Blocks
    public static final RegistryObject<Block> EARTH_REAGENT_BLOCK = BLOCKS.register("earth_reagent_block", () -> new Block(Block.Properties.create(Material.EARTH)));
    //Blocks Item
    public static final RegistryObject<Item> EARTH_REAGENT_BLOCK_ITEM = ITEMS.register("earth_reagent_block", () -> new BlockItem(EARTH_REAGENT_BLOCK.get(), new Item.Properties().group(GeomancyAndTerraforming.GEOMANCYANDTERRAFORMING_TAB)));
    //Items
    public static final RegistryObject<Item> SPELLDEV = ITEMS.register("spelldev", () -> new SpellDev(new Item.Properties().group(GeomancyAndTerraforming.GEOMANCYANDTERRAFORMING_TAB)));
    public static final RegistryObject<Item> SPELLDEV2 = ITEMS.register("spelldev2", () -> new SpellDev2(new Item.Properties().group(GeomancyAndTerraforming.GEOMANCYANDTERRAFORMING_TAB)));
    public static final RegistryObject<Item> SPELLDEV3 = ITEMS.register("spelldev3", () -> new SpellDev3(new Item.Properties().group(GeomancyAndTerraforming.GEOMANCYANDTERRAFORMING_TAB)));
    public static final RegistryObject<Item> SPELLDEV4 = ITEMS.register("spelldev4", () -> new SpellDev4(new Item.Properties().group(GeomancyAndTerraforming.GEOMANCYANDTERRAFORMING_TAB)));






}