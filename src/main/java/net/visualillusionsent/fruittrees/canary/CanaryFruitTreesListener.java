/*
 * This file is part of FruitTrees.
 *
 * Copyright © 2013-2014 Visual Illusions Entertainment
 *
 * FruitTrees is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see http://www.gnu.org/licenses/gpl.html.
 */
package net.visualillusionsent.fruittrees.canary;

import net.canarymod.api.GameMode;
import net.canarymod.api.entity.living.humanoid.Player;
import net.canarymod.api.inventory.Item;
import net.canarymod.api.inventory.ItemType;
import net.canarymod.api.world.blocks.Block;
import net.canarymod.api.world.blocks.BlockType;
import net.canarymod.hook.HookHandler;
import net.canarymod.hook.player.BlockDestroyHook;
import net.canarymod.hook.player.BlockRightClickHook;
import net.canarymod.hook.player.CraftHook;
import net.canarymod.hook.system.LoadWorldHook;
import net.canarymod.hook.system.UnloadWorldHook;
import net.canarymod.hook.world.BlockUpdateHook;
import net.canarymod.hook.world.TreeGrowHook;
import net.canarymod.plugin.PluginListener;
import net.canarymod.plugin.Priority;
import net.visualillusionsent.fruittrees.TreeDeathReason;
import net.visualillusionsent.fruittrees.TreeTracker;
import net.visualillusionsent.fruittrees.TreeType;
import net.visualillusionsent.fruittrees.TreeWorld;
import net.visualillusionsent.fruittrees.trees.*;

public final class CanaryFruitTreesListener implements PluginListener {

    public CanaryFruitTreesListener(CanaryFruitTrees cft) {
        cft.registerListener(this);
    }

    @HookHandler(priority = Priority.LOW)
    public final void craftSeeds(CraftHook hook) {
        if (!CanaryFruitTrees.instance().getFruitTreesConfig().requirePermissions()) {
            return;
        }
        if (SeedGen.recipes.contains(hook.getMatchingRecipe())) {
            String type = hook.getRecipeResult().getMetaTag().getCompoundTag("FruitTrees").getString("TreeType");
            if (!hook.getPlayer().hasPermission("fruittrees.craft.".concat(type.toLowerCase().replace("seeds", "")))) {
                hook.setCanceled();
                return;
            }
        }
    }

    @HookHandler(priority = Priority.LOW)
    public final void plantSeeds(BlockRightClickHook hook) {
        if (hook.getBlockClicked().getTypeId() == BlockType.OakSapling.getId()) {
            Item held = hook.getPlayer().getItemHeld();
            if (held != null && held.getType().equals(ItemType.Charcoal)) {
                Block block = hook.getBlockClicked();
                FruitTree tree = TreeTracker.getTreeAt(block.getX(), block.getY(), block.getZ(), CanaryFruitTrees.instance().getWorldForName(block.getWorld().getFqName()));
                hook.getPlayer().notice(tree != null ? "FruitTree sapling of type:" + tree.getType() : "Not a FruitTree sapling...");
            }
        }
        else if (hook.getBlockClicked().getType().equals(BlockType.Soil)) {
            Item seeds = hook.getPlayer().getItemHeld();
            Block block = hook.getBlockClicked();
            TreeWorld treeWorld = CanaryFruitTrees.instance().getWorldForName(block.getWorld().getFqName());
            if (seeds.hasMetaTag() && seeds.getMetaTag().containsKey("FruitTrees")) {
                String type = seeds.getMetaTag().getCompoundTag("FruitTrees").getString("TreeType");
                if (seeds.getType().equals(ItemType.MelonSeeds)) {
                    if (type.equals("AppleSeeds")) {
                        if (CanaryFruitTrees.instance().getFruitTreesConfig().checkEnabled(TreeType.APPLE)) {
                            DelayedTreeSave.schedule(block, new AppleTree(CanaryFruitTrees.instance(), block.getX(), block.getY() + 1, block.getZ(), treeWorld, true));
                            decreaseStack(hook.getPlayer());
                            hook.setCanceled();
                        }
                    }
                    else if (type.equals("GoldenAppleSeeds")) {
                        if (CanaryFruitTrees.instance().getFruitTreesConfig().checkEnabled(TreeType.GOLDEN_APPLE)) {
                            DelayedTreeSave.schedule(block, new GoldenAppleTree(CanaryFruitTrees.instance(), block.getX(), block.getY() + 1, block.getZ(), treeWorld, true));
                            decreaseStack(hook.getPlayer());
                            hook.setCanceled();
                        }
                    }
                    else if (type.equals("CoalSeeds")) {
                        if (CanaryFruitTrees.instance().getFruitTreesConfig().checkEnabled(TreeType.COAL)) {
                            DelayedTreeSave.schedule(block, new CoalTree(CanaryFruitTrees.instance(), block.getX(), block.getY() + 1, block.getZ(), treeWorld, true));
                            decreaseStack(hook.getPlayer());
                            hook.setCanceled();
                        }
                    }
                }
                else if (seeds.getType().equals(ItemType.PumpkinSeeds)) {
                    if (type.equals("RecordSeeds")) {
                        if (CanaryFruitTrees.instance().getFruitTreesConfig().checkEnabled(TreeType.RECORD)) {
                            DelayedTreeSave.schedule(block, new RecordTree(CanaryFruitTrees.instance(), block.getX(), block.getY() + 1, block.getZ(), treeWorld, true));
                            decreaseStack(hook.getPlayer());
                            hook.setCanceled();
                        }
                    }
                    else if (type.equals("SpongeSeeds")) {
                        if (CanaryFruitTrees.instance().getFruitTreesConfig().checkEnabled(TreeType.SPONGE)) {
                            DelayedTreeSave.schedule(block, new SpongeTree(CanaryFruitTrees.instance(), block.getX(), block.getY() + 1, block.getZ(), treeWorld, true));
                            decreaseStack(hook.getPlayer());
                            hook.setCanceled();
                        }
                    }
                }
                else if (seeds.getType().equals(ItemType.Seeds)) {
                    if (type.endsWith("DyeSeeds")) {
                        if (CanaryFruitTrees.instance().getFruitTreesConfig().checkEnabled(DyeTree.fromDyeColor(seeds.getMetaTag().getCompoundTag("FruitTrees").getByte("DyeColor")))) {
                            DelayedTreeSave.schedule(block, new DyeTree(CanaryFruitTrees.instance(), block.getX(), block.getY() + 1, block.getZ(), treeWorld, seeds.getMetaTag().getCompoundTag("FruitTrees").getByte("DyeColor"), true));
                            decreaseStack(hook.getPlayer());
                            hook.setCanceled();
                        }
                    }
                    else if (type.equals("RedstoneSeeds")) {
                        if (CanaryFruitTrees.instance().getFruitTreesConfig().checkEnabled(TreeType.REDSTONE)) {
                            DelayedTreeSave.schedule(block, new RedstoneTree(CanaryFruitTrees.instance(), block.getX(), block.getY() + 1, block.getZ(), treeWorld, true));
                            decreaseStack(hook.getPlayer());
                            hook.setCanceled();
                        }
                    }
                    else if (type.equals("IronSeeds")) {
                        if (CanaryFruitTrees.instance().getFruitTreesConfig().checkEnabled(TreeType.IRON)) {
                            DelayedTreeSave.schedule(block, new IronTree(CanaryFruitTrees.instance(), block.getX(), block.getY() + 1, block.getZ(), treeWorld, true));
                            decreaseStack(hook.getPlayer());
                            hook.setCanceled();
                        }
                    }
                    else if (type.equals("GoldSeeds")) {
                        if (CanaryFruitTrees.instance().getFruitTreesConfig().checkEnabled(TreeType.GOLD)) {
                            DelayedTreeSave.schedule(block, new GoldTree(CanaryFruitTrees.instance(), block.getX(), block.getY() + 1, block.getZ(), treeWorld, true));
                            decreaseStack(hook.getPlayer());
                            hook.setCanceled();
                        }
                    }
                    else if (type.equals("DiamondSeeds")) {
                        if (CanaryFruitTrees.instance().getFruitTreesConfig().checkEnabled(TreeType.DIAMOND)) {
                            DelayedTreeSave.schedule(block, new DiamondTree(CanaryFruitTrees.instance(), block.getX(), block.getY() + 1, block.getZ(), treeWorld, true));
                            decreaseStack(hook.getPlayer());
                            hook.setCanceled();
                        }
                    }
                    else if (type.equals("EmeraldSeeds")) {
                        if (CanaryFruitTrees.instance().getFruitTreesConfig().checkEnabled(TreeType.EMERALD)) {
                            DelayedTreeSave.schedule(block, new EmeraldTree(CanaryFruitTrees.instance(), block.getX(), block.getY() + 1, block.getZ(), treeWorld, true));
                            decreaseStack(hook.getPlayer());
                            hook.setCanceled();
                        }
                    }
                }
            }
        }
    }

    @HookHandler(priority = Priority.LOW)
    public final void killTree(BlockDestroyHook hook) {
        if (hook.getBlock().getTypeId() == BlockType.OakSapling.getId()) {
            Block block = hook.getBlock();
            FruitTree tree = TreeTracker.getTreeAt(block.getX(), block.getY(), block.getZ(), CanaryFruitTrees.instance().getWorldForName(block.getWorld().getFqName()));
            if (tree != null) {
                if (hook.getPlayer().getMode() != GameMode.CREATIVE) { //If creative, no need to drop stuff
                    hook.setCanceled();
                    block.getWorld().dropItem(block.getPosition(), SeedGen.seeds[tree.getType().ordinal()].clone());
                    block.setType(BlockType.Air);
                    block.update();
                }
                tree.killTree(TreeDeathReason.PLAYER_DESTROY);
            }
        }
    }

    @HookHandler(priority = Priority.LOW)
    public final void killTreeArea(BlockUpdateHook hook) { //BlockUpdate a little more reliable with tracking Tree destruction (Especially if editting out a tree)
        Block block = hook.getBlock();
        TreeWorld treeWorld = CanaryFruitTrees.instance().getWorldForName(block.getWorld().getFqName());
        FruitTree tree = null;
        if (block.getType().equals(BlockType.OakLog) ||
                block.getType().equals(BlockType.OakLeaves) ||
                block.getType().equals(BlockType.Sponge) ||
                block.getTypeId() == BlockType.WoolWhite.getId() ||
                block.getType().equals(BlockType.RedstoneBlock) ||
                block.getType().equals(BlockType.NoteBlock) ||
                block.getType().equals(BlockType.IronBlock) ||
                block.getType().equals(BlockType.GoldBlock) ||
                block.getType().equals(BlockType.DiamondBlock) ||
                block.getType().equals(BlockType.EmeraldBlock) ||
                block.getType().equals(BlockType.CoalBlock)
                ) {
            tree = TreeTracker.isTreeArea(block.getX(), block.getY(), block.getZ(), blockFqName(block), treeWorld);
        }

        if (tree != null && tree.isGrown() && !tree.isGrowing()) {
            tree.killTree(TreeDeathReason.WORLD_DESTROY);
        }
    }

    @HookHandler(priority = Priority.HIGH)
    public final void treeGrow(TreeGrowHook hook) {
        Block sapling = hook.getSapling();
        FruitTree tree = TreeTracker.getTreeAt(sapling.getX(), sapling.getY(), sapling.getZ(), CanaryFruitTrees.instance().getWorldForName(sapling.getWorld().getFqName()));
        if (tree != null) {
            hook.setCanceled();
            DelayedTreeGrowth.schedule(tree);
        }
    }

    @HookHandler(priority = Priority.PASSIVE)
    public final void worldLoad(LoadWorldHook hook) {
        CanaryFruitTrees.instance().addLoadedWorld(hook.getWorld());
    }

    @HookHandler(priority = Priority.PASSIVE)
    public final void worldunload(UnloadWorldHook hook) {
        if (hook.getWorld() == null) {
            CanaryFruitTrees.instance().debug("Null World attempted to unload...");
            return;
        }

        TreeWorld tree_world = CanaryFruitTrees.instance().getWorldForName(hook.getWorld().getFqName());
        if (tree_world != null) {
            CanaryFruitTrees.instance().debug("World Unloaded: " + tree_world);
            tree_world.unloadWorld();
        }
    }

    private void decreaseStack(Player player) {
        if (player.getMode() != GameMode.CREATIVE) {
            Item held = player.getItemHeld();
            held.setAmount(held.getAmount() - 1);
            if (held.getAmount() <= 0) {
                player.getInventory().setSlot(held.getSlot(), null);
            }
        }
    }

    private String blockFqName(Block block) {
        return block.getType().getMachineName() + ":" + block.getType().getData();
    }
}
