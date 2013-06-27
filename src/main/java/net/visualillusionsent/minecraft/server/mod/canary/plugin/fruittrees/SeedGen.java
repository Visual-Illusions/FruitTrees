package net.visualillusionsent.minecraft.server.mod.canary.plugin.fruittrees;

import java.util.Arrays;
import net.canarymod.Canary;
import net.canarymod.api.inventory.Item;
import net.canarymod.api.inventory.ItemType;
import net.canarymod.api.inventory.recipes.CraftingRecipe;
import net.canarymod.api.inventory.recipes.RecipeRow;
import net.canarymod.api.nbt.CompoundTag;
import net.visualillusionsent.fruittrees.TreeType;

final class SeedGen {

    final static Item[] seeds = new Item[128]; //This is set to 128 for quick expansion durring development
    private final static String[] dyes = new String[] { "Black", "Red", "Green", "Brown", "Blue", "Purple", "Cyan", "Light_Gray", "Gray", "Pink", "Lime", "Yellow", "Light_Blue", "Magenta", "Orange", "White" };

    private SeedGen() {}

    static final void clearSeeds() {
        Arrays.fill(seeds, null);
    }

    static final void genAll() {
        // Gen Seeds
        genAppleSeeds();
        genGoldAppleSeeds();
        genSpongeSeeds();
        genRecordSeeds();
        genDyeSeeds();
        genRedstoneSeeds();
        genIronSeeds();

        //Gen Recipes
        addAppleSeedsRecipe();
        addGoldenAppleSeedsRecipe0();
        addGoldenAppleSeedsRecipe1();
        addSpongeSeedRecipe0();
        addSpongeSeedRecipe1();
        addRecordSeedRecipe();
        addDyeSeedsRecipe();
        addRedstoneSeedsRecipe();
        addIronSeedsRecipe();
    }

    private static final void genAppleSeeds() {
        if (CanaryFruitTrees.instance().checkEnabled(TreeType.APPLE)) {
            Item appleSeeds = Canary.factory().getItemFactory().newItem(ItemType.MelonSeeds, 0, 1);
            appleSeeds.setDisplayName("Apple Seeds");
            appleSeeds.setLore("Plant to grow an Apple Tree");
            CompoundTag fruit_trees_tag = Canary.factory().getNBTFactory().newCompoundTag("FruitTrees");
            fruit_trees_tag.put("TreeType", Canary.factory().getNBTFactory().newStringTag("TreeType", "AppleSeeds"));
            appleSeeds.getMetaTag().put("FruitTrees", fruit_trees_tag);
            seeds[0] = appleSeeds;
        }
    }

    private static final void genGoldAppleSeeds() {
        if (CanaryFruitTrees.instance().checkEnabled(TreeType.GOLDEN_APPLE)) {
            Item gold_apple_seeds = Canary.factory().getItemFactory().newItem(ItemType.MelonSeeds, 0, 1);
            gold_apple_seeds.setDisplayName("Golden Apple Seeds");
            gold_apple_seeds.setLore("Plant to grow a Golden Apple Tree");
            CompoundTag fruit_trees_tag = Canary.factory().getNBTFactory().newCompoundTag("FruitTrees");
            fruit_trees_tag.put("TreeType", Canary.factory().getNBTFactory().newStringTag("TreeType", "GoldenAppleSeeds"));
            gold_apple_seeds.getMetaTag().put("FruitTrees", fruit_trees_tag);
            seeds[1] = gold_apple_seeds;
        }
    }

    private static final void genSpongeSeeds() {
        if (CanaryFruitTrees.instance().checkEnabled(TreeType.SPONGE)) {
            Item sponge_seeds = Canary.factory().getItemFactory().newItem(ItemType.PumpkinSeeds, 0, 1);
            sponge_seeds.setDisplayName("Sponge Seeds");
            sponge_seeds.setLore("Plant to grow a Sponge Tree");
            CompoundTag fruit_trees_tag = Canary.factory().getNBTFactory().newCompoundTag("FruitTrees");
            fruit_trees_tag.put("TreeType", Canary.factory().getNBTFactory().newStringTag("TreeType", "SpongeSeeds"));
            sponge_seeds.getMetaTag().put("FruitTrees", fruit_trees_tag);
            seeds[2] = sponge_seeds;
        }
    }

    private static final void genRecordSeeds() {
        if (CanaryFruitTrees.instance().checkEnabled(TreeType.RECORD)) {
            Item record_seeds = Canary.factory().getItemFactory().newItem(ItemType.PumpkinSeeds, 0, 1);
            record_seeds.setDisplayName("Record Seeds");
            record_seeds.setLore("Plant to grow a Record Tree");
            CompoundTag fruit_trees_tag = Canary.factory().getNBTFactory().newCompoundTag("FruitTrees");
            fruit_trees_tag.put("TreeType", Canary.factory().getNBTFactory().newStringTag("TreeType", "RecordSeeds"));
            record_seeds.getMetaTag().put("FruitTrees", fruit_trees_tag);
            seeds[3] = record_seeds;
        }
    }

    private static final void genDyeSeeds() {
        Item dye_seeds = Canary.factory().getItemFactory().newItem(ItemType.Seeds, 0, 1);
        for (int index = 0; index <= 15; index++) {
            if (CanaryFruitTrees.instance().checkEnabled(TreeType.valueOf("DYE_".concat(dyes[index].toUpperCase())))) {
                dye_seeds.setDisplayName(String.format("%s Dye Seeds", dyes[index]));
                dye_seeds.setLore(String.format("Plant to grow a %s Dye Tree", dyes[index]));
                CompoundTag fruit_trees_tag = Canary.factory().getNBTFactory().newCompoundTag("FruitTrees");
                fruit_trees_tag.put("TreeType", Canary.factory().getNBTFactory().newStringTag("TreeType", "DyeSeeds"));
                fruit_trees_tag.put("DyeColor", Canary.factory().getNBTFactory().newByteTag("DyeColor", (byte) index));
                dye_seeds.getMetaTag().put("FruitTrees", fruit_trees_tag);
                seeds[index + 4] = dye_seeds.clone();
            }
        }
    }

    private static final void genRedstoneSeeds() {
        if (CanaryFruitTrees.instance().checkEnabled(TreeType.REDSTONE)) {
            Item redstone_seeds = Canary.factory().getItemFactory().newItem(ItemType.Seeds, 0, 1);
            redstone_seeds.setDisplayName("Redstone Seeds");
            redstone_seeds.setLore("Plant to grow a Redstone Tree");
            CompoundTag fruit_trees_tag = Canary.factory().getNBTFactory().newCompoundTag("FruitTrees");
            fruit_trees_tag.put("TreeType", Canary.factory().getNBTFactory().newStringTag("TreeType", "RedstoneSeeds"));
            redstone_seeds.getMetaTag().put("FruitTrees", fruit_trees_tag);
            seeds[20] = redstone_seeds;
        }
    }

    private static final void genIronSeeds() {
        if (CanaryFruitTrees.instance().checkEnabled(TreeType.IRON)) {
            Item iron_seeds = Canary.factory().getItemFactory().newItem(ItemType.Seeds, 0, 1);
            iron_seeds.setDisplayName("Iron Seeds");
            iron_seeds.setLore("Plant to grow a Iron Tree");
            CompoundTag fruit_trees_tag = Canary.factory().getNBTFactory().newCompoundTag("FruitTrees");
            fruit_trees_tag.put("TreeType", Canary.factory().getNBTFactory().newStringTag("TreeType", "IronSeeds"));
            iron_seeds.getMetaTag().put("FruitTrees", fruit_trees_tag);
            seeds[21] = iron_seeds;
        }
    }

    //22 is the next seeds index

    private static final void addAppleSeedsRecipe() {
        if (CanaryFruitTrees.instance().checkEnabled(TreeType.APPLE)) {
            Item apple = Canary.factory().getItemFactory().newItem(ItemType.Apple, 0, 1);
            Item aplSeeds = seeds[0].clone();
            aplSeeds.setAmount(3);
            Canary.getServer().addRecipe(new CraftingRecipe(aplSeeds, apple));
        }
    }

    private static final void addGoldenAppleSeedsRecipe0() {
        if (CanaryFruitTrees.instance().checkEnabled(TreeType.GOLDEN_APPLE)) {
            Item golden_apple = Canary.factory().getItemFactory().newItem(ItemType.GoldenApple, 0, 1);
            Item gld_apl_seeds = seeds[1].clone();
            gld_apl_seeds.setAmount(3);
            Canary.getServer().addRecipe(new CraftingRecipe(gld_apl_seeds, golden_apple));
        }
    }

    private static final void addGoldenAppleSeedsRecipe1() {
        if (CanaryFruitTrees.instance().checkEnabled(TreeType.GOLDEN_APPLE)) {
            Item gld_apl_seeds = seeds[1].clone();
            Item gold_nuggets = Canary.factory().getItemFactory().newItem(ItemType.GoldNugget, 0, 1);
            RecipeRow[] rows = new RecipeRow[] { new RecipeRow("NNN", new Item[] { gold_nuggets }),
                new RecipeRow("NSN", new Item[] { gold_nuggets, seeds[0].clone() }),
                new RecipeRow("NNN", new Item[] { gold_nuggets })
            };
            Canary.getServer().addRecipe(new CraftingRecipe(gld_apl_seeds, rows));
        }
    }

    private static final void addSpongeSeedRecipe0() {
        if (CanaryFruitTrees.instance().checkEnabled(TreeType.SPONGE)) {
            Item sponge_seeds = seeds[2].clone();
            sponge_seeds.setAmount(2);
            Item yellow_wool = Canary.factory().getItemFactory().newItem(ItemType.Cloth, 4, 1);
            Item clay_ball = Canary.factory().getItemFactory().newItem(ItemType.ClayBall, 0, 1);
            Canary.getServer().addRecipe(new CraftingRecipe(sponge_seeds, yellow_wool, clay_ball));
        }
    }

    private static final void addSpongeSeedRecipe1() {
        if (CanaryFruitTrees.instance().checkEnabled(TreeType.SPONGE)) {
            Item sponge_seeds = seeds[2].clone();
            sponge_seeds.setAmount(3);
            Item sponge = Canary.factory().getItemFactory().newItem(ItemType.Sponge, 0, 1);
            Canary.getServer().addRecipe(new CraftingRecipe(sponge_seeds, sponge));
        }
    }

    private static final void addRecordSeedRecipe() {
        if (CanaryFruitTrees.instance().checkEnabled(TreeType.RECORD)) {
            Item record_seeds = seeds[3].clone();
            record_seeds.setAmount(2);
            Item record = Canary.factory().getItemFactory().newItem(ItemType.GoldRecord, 0, 1);
            Canary.getServer().addRecipe(new CraftingRecipe(record_seeds.clone(), record));
            record = Canary.factory().getItemFactory().newItem(ItemType.GreenRecord, 0, 1);
            Canary.getServer().addRecipe(new CraftingRecipe(record_seeds.clone(), record));
            record = Canary.factory().getItemFactory().newItem(ItemType.BlocksRecord, 0, 1);
            Canary.getServer().addRecipe(new CraftingRecipe(record_seeds.clone(), record));
            record = Canary.factory().getItemFactory().newItem(ItemType.ChirpRecord, 0, 1);
            Canary.getServer().addRecipe(new CraftingRecipe(record_seeds.clone(), record));
            record = Canary.factory().getItemFactory().newItem(ItemType.FarRecord, 0, 1);
            Canary.getServer().addRecipe(new CraftingRecipe(record_seeds.clone(), record));
            record = Canary.factory().getItemFactory().newItem(ItemType.MallRecord, 0, 1);
            Canary.getServer().addRecipe(new CraftingRecipe(record_seeds.clone(), record));
            record = Canary.factory().getItemFactory().newItem(ItemType.MellohiRecord, 0, 1);
            Canary.getServer().addRecipe(new CraftingRecipe(record_seeds.clone(), record));
            record = Canary.factory().getItemFactory().newItem(ItemType.StalRecord, 0, 1);
            Canary.getServer().addRecipe(new CraftingRecipe(record_seeds.clone(), record));
            record = Canary.factory().getItemFactory().newItem(ItemType.StradRecord, 0, 1);
            Canary.getServer().addRecipe(new CraftingRecipe(record_seeds.clone(), record));
            record = Canary.factory().getItemFactory().newItem(ItemType.WardRecord, 0, 1);
            Canary.getServer().addRecipe(new CraftingRecipe(record_seeds.clone(), record));
            record = Canary.factory().getItemFactory().newItem(ItemType.ElevenRecord, 0, 1);
            Canary.getServer().addRecipe(new CraftingRecipe(record_seeds.clone(), record));
            record = Canary.factory().getItemFactory().newItem(ItemType.WaitRecord, 0, 1);
            Canary.getServer().addRecipe(new CraftingRecipe(record_seeds.clone(), record));
        }
    }

    private static final void addDyeSeedsRecipe() {
        for (int index = 0; index <= 15; index++) {
            if (CanaryFruitTrees.instance().checkEnabled(TreeType.valueOf("DYE_".concat(dyes[index].toUpperCase())))) {
                Item dye_seeds = seeds[index + 4].clone();
                dye_seeds.setAmount(2);
                Item seeds = Canary.factory().getItemFactory().newItem(ItemType.Seeds, 0, 1);
                Item dye = Canary.factory().getItemFactory().newItem(ItemType.InkSack, index, 1);
                Canary.getServer().addRecipe(new CraftingRecipe(dye_seeds.clone(), seeds, dye));
            }
        }
    }

    private static final void addRedstoneSeedsRecipe() {
        if (CanaryFruitTrees.instance().checkEnabled(TreeType.REDSTONE)) {
            Item redstone_seeds = seeds[20].clone();
            redstone_seeds.setAmount(2);
            Item redstone_dust = Canary.factory().getItemFactory().newItem(ItemType.RedStone, 0, 1);
            Item normal_seeds = Canary.factory().getItemFactory().newItem(ItemType.Seeds, 0, 1);
            Canary.getServer().addRecipe(new CraftingRecipe(redstone_seeds, redstone_dust, normal_seeds));
        }
    }

    private static final void addIronSeedsRecipe() {
        if (CanaryFruitTrees.instance().checkEnabled(TreeType.IRON)) {
            Item iron_seeds = seeds[21].clone();
            iron_seeds.setAmount(1);
            Item iron_ingot = Canary.factory().getItemFactory().newItem(ItemType.IronIngot, 0, 1);
            Item normal_seeds = Canary.factory().getItemFactory().newItem(ItemType.Seeds, 0, 1);
            Canary.getServer().addRecipe(new CraftingRecipe(iron_seeds, iron_ingot, normal_seeds));
        }
    }
}
