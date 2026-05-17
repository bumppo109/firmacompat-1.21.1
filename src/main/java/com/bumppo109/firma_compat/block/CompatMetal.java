package com.bumppo109.firma_compat.block;

import com.bumppo109.firma_compat.block.ModRegistryMetal;
import com.bumppo109.firma_compat.item.ModItems;
import net.dries007.tfc.common.LevelTier;
import net.dries007.tfc.common.TFCArmorMaterials;
import net.dries007.tfc.common.TFCTiers;
import net.dries007.tfc.common.blocks.*;
import net.dries007.tfc.common.blocks.IWeatheringBlock.Age;
import net.dries007.tfc.common.items.ToolItem;
import net.minecraft.core.Holder;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.*;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public enum CompatMetal implements StringRepresentable, ModRegistryMetal {
    NETHERITE("#694117", MapColor.COLOR_BLACK, Rarity.EPIC, -1.0F, PartType.ALL, TFCTiers.BLACK_STEEL, TFCArmorMaterials.BLACK_STEEL),
    POOR_NETHERITE("#211203", MapColor.WOOD, Rarity.UNCOMMON, -1.0F, PartType.INGOT, TFCTiers.STEEL, null)
    ;

    private final String serializedName;
    private final PartType partType;
    private final @Nullable LevelTier toolTier;
    private final TFCArmorMaterials.@Nullable Id armorMaterial;
    private final MapColor mapColor;
    private final Rarity rarity;
    private final String color;
    private final float weathering;

    private CompatMetal(String color, MapColor mapColor, Rarity rarity, float weathering, @Nullable PartType partType, @Nullable LevelTier toolTier,
                        @Nullable TFCArmorMaterials.Id armorTier) {
        this.serializedName = this.name().toLowerCase(Locale.ROOT);
        this.color = color;
        this.mapColor = mapColor;
        this.rarity = rarity;
        this.weathering = weathering;
        this.partType = partType != null ? partType : PartType.DEFAULT; // default fallback
        this.toolTier = toolTier;
        this.armorMaterial = armorTier;
    }

    private CompatMetal(String color, MapColor mapColor, Rarity rarity, float weathering, LevelTier toolTier, TFCArmorMaterials.Id armorTier) {
        this(color, mapColor, rarity, weathering, null, toolTier, armorTier);
    }

    private CompatMetal(String color, MapColor mapColor, Rarity rarity, float weathering, PartType partType) {
        this(color, mapColor, rarity, weathering, partType, null, null);
    }

    private CompatMetal(String color, MapColor mapColor, Rarity rarity, float weathering) {
        this(color, mapColor, rarity, weathering, PartType.DEFAULT, null, null);
    }

    public String getSerializedName() {
        return this.serializedName;
    }

    public int getColor() {
        return hexToInt(this.color);
    }

    public Rarity rarity() {
        return this.rarity;
    }

    public float weatheringResistance() {
        return this.weathering;
    }

    /*
    public boolean defaultParts() {
        return this.partType == PartType.DEFAULT;
    }

     */
    // In CompatMetal enum or helper class
    public boolean supportsAtLeast(PartType required) {
        if (required == null) return false;
        if (this.partType == PartType.ALL) return true;
        return this.partType.ordinal() >= required.ordinal();
    }
    // Then your convenience methods:
    public boolean defaultOnlyParts() { return supportsAtLeast(PartType.DEFAULT); }
    public boolean toolParts()        { return supportsAtLeast(PartType.TOOL);    }
    public boolean weaponParts()      { return supportsAtLeast(PartType.WEAPON);  }
    public boolean armorParts()       { return supportsAtLeast(PartType.ARMOR);   }

    //TODO - add "or" check for weathering & blocks
    /*
    public boolean allParts() {
        return this.partType != PartType.INGOT;
    }
     */

    public LevelTier toolTier() {
        return (LevelTier)Objects.requireNonNull(this.toolTier, "Tried to get non-existent tier from " + this.name());
    }

    public Holder<ArmorMaterial> armorMaterial() {
        return ((TFCArmorMaterials.Id)Objects.requireNonNull(this.armorMaterial)).holder();
    }

    public int armorDurability(Type type) {
        Objects.requireNonNull(this.armorMaterial);
        int var10000;
        switch (type) {
            case HELMET:
                var10000 = this.armorMaterial.headDamage();
                break;
            case BODY:
            case CHESTPLATE:
                var10000 = this.armorMaterial.chestDamage();
                break;
            case LEGGINGS:
                var10000 = this.armorMaterial.legDamage();
                break;
            case BOOTS:
                var10000 = this.armorMaterial.feetDamage();
                break;
            default:
                throw new MatchException((String)null, (Throwable)null);
        }

        return var10000;
    }

    public MapColor mapColor() {
        return this.mapColor;
    }

    public Block getBlock(BlockType type) {
        return (Block)((TFCBlocks.Id)((Map)TFCBlocks.METALS.get(this)).get(type)).get();
    }

    public int tier() {
        return this.toolTier != null ? this.toolTier.level() : 0;
    }

    public static enum BlockType {

        ;

        private final Function<ModRegistryMetal, Block> blockFactory;
        private final BiFunction<Block, Item.Properties, ? extends BlockItem> blockItemFactory;
        private final PartType type;
        private final String serializedName;

        private static Function<ModRegistryMetal, Block> grate(Age age) {
            return (metal) -> {
                Properties prop = blockProperties(metal).noOcclusion().sound(SoundType.COPPER_GRATE).noOcclusion().isValidSpawn(TFCBlocks::neverEntity).isRedstoneConductor(TFCBlocks::never).isSuffocating(TFCBlocks::never).isViewBlocking(TFCBlocks::never).lightLevel(TFCBlocks.lavaLoggedBlockEmission());
                return (Block)(metal.weatheredParts() ? new WeatheringGrateBlock(prop, age, metal.weatheringResistance()) : new GrateBlock(prop));
            };
        }

        private static Function<ModRegistryMetal, Block> block(Age age) {
            return (metal) -> (Block)(metal.weatheredParts() ? new WeatheringBlock(blockProperties(metal), age, metal.weatheringResistance()) : new Block(blockProperties(metal)));
        }

        private static Function<ModRegistryMetal, Block> slab(Age age) {
            return (metal) -> (Block)(metal.weatheredParts() ? new WeatheringSlabBlock(blockProperties(metal), age, metal.weatheringResistance()) : new SlabBlock(blockProperties(metal)));
        }

        private static Function<ModRegistryMetal, Block> stairs(BlockType block, Age age) {
            return (metal) -> (Block)(metal.weatheredParts() ? new WeatheringStairBlock(metal.getBlock(block).defaultBlockState(), blockProperties(metal), age, metal.weatheringResistance()) : new StairBlock(metal.getBlock(block).defaultBlockState(), blockProperties(metal)));
        }

        private static Properties blockProperties(ModRegistryMetal metal) {
            return Properties.of().mapColor(metal.mapColor()).instrument(NoteBlockInstrument.IRON_XYLOPHONE).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL);
        }

        private BlockType(PartType type, Function<ModRegistryMetal, Block> blockFactory, BiFunction<Block, Item.Properties, ? extends BlockItem> blockItemFactory) {
            this.type = type;
            this.blockFactory = blockFactory;
            this.blockItemFactory = blockItemFactory;
            this.serializedName = this.name().toLowerCase(Locale.ROOT);
        }

        private BlockType(PartType type, Function<ModRegistryMetal, Block> blockFactory) {
            this(type, blockFactory, BlockItem::new);
        }

        public Supplier<Block> create(ModRegistryMetal metal) {
            return () -> (Block)this.blockFactory.apply(metal);
        }

        public Function<Block, BlockItem> createBlockItem(Item.Properties properties) {
            return (block) -> (BlockItem)this.blockItemFactory.apply(block, properties);
        }

        /*
        public boolean has(CompatMetal metal) {
            return this.type.hasMetal(metal.partType);
        }

         */

        public String createName(ModRegistryMetal metal) {
            String slab = "_slab";
            if (this.serializedName.contains(slab)) {
                String var5 = this.serializedName.split(slab)[0];
                return "metal/" + var5 + "/" + metal.getSerializedName() + slab;
            } else {
                String stairs = "_stairs";
                if (this.serializedName.contains(stairs)) {
                    String var4 = this.serializedName.split(stairs)[0];
                    return "metal/" + var4 + "/" + metal.getSerializedName() + stairs;
                } else {
                    String var10000 = this.serializedName;
                    return "metal/" + var10000 + "/" + metal.getSerializedName();
                }
            }
        }
    }

    public static enum ItemType {
        DOUBLE_INGOT(PartType.DEFAULT, false),
        SHEET(PartType.DEFAULT, false),
        DOUBLE_SHEET(PartType.DEFAULT, false),
        ROD(PartType.DEFAULT, false),
        PICKAXE_HEAD(PartType.TOOL, true),
        AXE_HEAD(PartType.TOOL, true),
        SHOVEL_HEAD(PartType.TOOL, true),
        HOE_HEAD(PartType.TOOL, true),
        SWORD_BLADE(PartType.WEAPON, true),
        UNFINISHED_HELMET(PartType.ARMOR, false),
        UNFINISHED_CHESTPLATE(PartType.ARMOR, false),
        UNFINISHED_LEGGINGS(PartType.ARMOR, false),
        UNFINISHED_BOOTS(PartType.ARMOR, false),
        ;

        private final Function<ModRegistryMetal, Item> itemFactory;
        private final PartType type;
        private final boolean mold;

        private static Item.Properties base(ModRegistryMetal metal) {
            return (new Item.Properties()).rarity(metal.rarity());
        }

        private static Item.Properties tool(ModRegistryMetal metal, float attackDamageFactor, float attackSpeed) {
            return base(metal).attributes(ToolItem.productAttributes(metal.toolTier(), attackDamageFactor, attackSpeed));
        }

        private static Function<ModRegistryMetal, Item> armor(Type type) {
            return (metal) -> new ArmorItem(metal.armorMaterial(), type, base(metal).durability(metal.armorDurability(type)));
        }

        private ItemType(PartType type, boolean mold) {
            this(type, mold, (metal) -> new Item(base(metal)));
        }

        private ItemType(PartType type, Function<ModRegistryMetal, Item> itemFactory) {
            this(type, false, itemFactory);
        }

        private ItemType(PartType type, boolean mold, Function<ModRegistryMetal, Item> itemFactory) {
            this.type = type;
            this.mold = mold;
            this.itemFactory = itemFactory;
        }

        public Item create(ModRegistryMetal metal) {
            return (Item)this.itemFactory.apply(metal);
        }

        public boolean has(CompatMetal metal) {
            return this.type.hasMetal(metal.partType);
        }

        public boolean hasMold() {
            return this.mold;
        }

        public boolean isCommonTagPart() {
            return this.type == PartType.DEFAULT;
        }
    }

    static enum PartType {
        INGOT,
        DEFAULT,
        TOOL,
        WEAPON,
        ARMOR,
        ALL
        ;

        public boolean hasMetal(PartType requestedPart) {
            return requestedPart.ordinal() >= this.ordinal();
        }
    }

    public static int hexToInt(String hexColor) {
        // Remove # prefix if present
        String cleanHex = hexColor.startsWith("#") ? hexColor.substring(1) : hexColor;

        // Validate length (must be 6 or 8 digits)
        if (cleanHex.length() != 6 && cleanHex.length() != 8) {
            throw new IllegalArgumentException("Invalid hex color: must be 6 or 8 characters (RRGGBB or AARRGGBB), got: " + hexColor);
        }

        try {
            // Parse as unsigned int (base 16)
            long parsed = Long.parseLong(cleanHex, 16);

            // If input was 6 digits (RGB), add full opacity (FF alpha)
            if (cleanHex.length() == 6) {
                parsed = (parsed & 0xFFFFFF) | 0xFF000000L;
            }

            return (int) parsed;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid hex color format: " + hexColor, e);
        }
    }

    public record MetalSet(
            Supplier<Item> ingot,           // ← Change to Supplier<Item>
            Supplier<Item> nugget,
            Supplier<Block> storageBlock,   // Also change Block to Supplier<Block> if needed
            Supplier<Item> sword,
            Supplier<Item> pickaxe,
            Supplier<Item> shovel,
            Supplier<Item> axe,
            Supplier<Item> hoe,
            Supplier<Item> helmet,
            Supplier<Item> chestplate,
            Supplier<Item> leggings,
            Supplier<Item> boots,
            Supplier<Item> shield
    ) {

    }

    public static final Map<CompatMetal, CompatMetal.MetalSet> METAL_SETS = Map.of(
            CompatMetal.NETHERITE, new CompatMetal.MetalSet(
                    () -> Items.NETHERITE_INGOT,                // Vanilla → direct (safe)
                    () -> null,
                    () -> Blocks.NETHERITE_BLOCK,
                    () -> Items.NETHERITE_SWORD,
                    () -> Items.NETHERITE_PICKAXE,
                    () -> Items.NETHERITE_SHOVEL,
                    () -> Items.NETHERITE_AXE,
                    () -> Items.NETHERITE_HOE,
                    () -> Items.NETHERITE_HELMET,
                    () -> Items.NETHERITE_CHESTPLATE,
                    () -> Items.NETHERITE_LEGGINGS,
                    () -> Items.NETHERITE_BOOTS,
                    () -> null
            ),
            CompatMetal.POOR_NETHERITE, new CompatMetal.MetalSet(
                    () -> ModItems.POOR_NETHERITE_INGOT.get(), // Deferred → lazy lambda!
                    () -> null,
                    () -> null,
                    () -> null,
                    () -> null,
                    () -> null,
                    () -> null,
                    () -> null,
                    () -> null,
                    () -> null,
                    () -> null,
                    () -> null,
                    () -> null
            )
    );
}
