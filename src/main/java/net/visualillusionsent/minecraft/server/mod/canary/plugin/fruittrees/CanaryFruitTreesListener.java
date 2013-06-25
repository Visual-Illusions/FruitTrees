/*
 * This file is part of FruitTrees.
 *
 * Copyright © 2013 Visual Illusions Entertainment
 *
 * FruitTrees is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * FruitTrees is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with FruitTrees.
 * If not, see http://www.gnu.org/licenses/gpl.html.
 */
package net.visualillusionsent.minecraft.server.mod.canary.plugin.fruittrees;

import net.canarymod.Canary;
import net.canarymod.api.GameMode;
import net.canarymod.api.entity.living.humanoid.Player;
import net.canarymod.api.inventory.Item;
import net.canarymod.api.inventory.ItemType;
import net.canarymod.api.world.blocks.Block;
import net.canarymod.api.world.blocks.BlockType;
import net.canarymod.hook.HookHandler;
import net.canarymod.hook.player.BlockDestroyHook;
import net.canarymod.hook.player.BlockRightClickHook;
import net.canarymod.hook.system.LoadWorldHook;
import net.canarymod.hook.system.UnloadWorldHook;
import net.canarymod.hook.world.BlockUpdateHook;
import net.canarymod.hook.world.TreeGrowHook;
import net.canarymod.plugin.PluginListener;
import net.canarymod.plugin.Priority;
import net.visualillusionsent.minecraft.server.mod.fruittrees.*;

public class CanaryFruitTreesListener implements PluginListener{

    private final CanaryFruitTrees cft;

    public CanaryFruitTreesListener(CanaryFruitTrees cft){
        this.cft = cft;
        Canary.hooks().registerListener(this, cft);
    }

    @HookHandler(priority = Priority.LOW)
    public final void plantSeeds(BlockRightClickHook hook){
        if (hook.getBlockClicked().getType() == BlockType.Soil) {
            Item seeds = hook.getPlayer().getItemHeld();
            Block block = hook.getBlockClicked();
            if (seeds.getType() == ItemType.MelonSeeds) {
                if (seeds.getMetaTag().containsKey("FruitTrees") && seeds.getMetaTag().getCompoundTag("FruitTrees").getString("TreeType").equals("AppleSeeds")) {
                    if (cft.getFruitTreesConfig().checkEnabled(TreeType.APPLE)) {
                        block.getWorld().setBlockAt(block.getPosition(), (short) 3);
                        block.getWorld().setBlockAt(block.getX(), block.getY() + 1, block.getZ(), (short) 6);
                        FruitTree tree = new AppleTree(cft, block.getX(), block.getY() + 1, block.getZ(), cft.getWorldForName(block.getWorld().getFqName()));
                        tree.save();
                        decreaseStack(hook.getPlayer());
                        hook.setCanceled();
                    }
                }
                else if (seeds.getMetaTag().containsKey("FruitTrees") && seeds.getMetaTag().getCompoundTag("FruitTrees").getString("TreeType").equals("GoldenAppleSeeds")) {
                    if (cft.getFruitTreesConfig().checkEnabled(TreeType.GOLDEN_APPLE)) {
                        block.getWorld().setBlockAt(block.getPosition(), (short) 3);
                        block.getWorld().setBlockAt(block.getX(), block.getY() + 1, block.getZ(), (short) 6, (short) 1);
                        FruitTree tree = new GoldenAppleTree(cft, block.getX(), block.getY() + 1, block.getZ(), cft.getWorldForName(block.getWorld().getFqName()));
                        tree.save();
                        decreaseStack(hook.getPlayer());
                        hook.setCanceled();
                    }
                }
            }
            else if (seeds.getType() == ItemType.PumpkinSeeds) {
                if (seeds.getMetaTag().containsKey("FruitTrees") && seeds.getMetaTag().getCompoundTag("FruitTrees").getString("TreeType").equals("RecordSeeds")) {
                    block.getWorld().setBlockAt(block.getPosition(), (short) 3);
                    block.getWorld().setBlockAt(block.getX(), block.getY() + 1, block.getZ(), (short) 6, (short) 1);
                    FruitTree tree = new RecordTree(cft, block.getX(), block.getY() + 1, block.getZ(), cft.getWorldForName(block.getWorld().getFqName()));
                    tree.save();
                    decreaseStack(hook.getPlayer());
                    hook.setCanceled();
                }
                else if (seeds.getMetaTag().containsKey("FruitTrees") && seeds.getMetaTag().getCompoundTag("FruitTrees").getString("TreeType").equals("SpongeSeeds")) {
                    block.getWorld().setBlockAt(block.getPosition(), (short) 3);
                    block.getWorld().setBlockAt(block.getX(), block.getY() + 1, block.getZ(), (short) 6, (short) 2);
                    FruitTree tree = new SpongeTree(cft, block.getX(), block.getY() + 1, block.getZ(), cft.getWorldForName(block.getWorld().getFqName()));
                    tree.save();
                    decreaseStack(hook.getPlayer());
                    hook.setCanceled();
                }
            }
            else if (seeds.getType() == ItemType.Seeds) {
                if (seeds.getMetaTag().containsKey("FruitTrees") && seeds.getMetaTag().getCompoundTag("FruitTrees").getString("TreeType").equals("DyeSeeds")) {
                    block.getWorld().setBlockAt(block.getPosition(), (short) 3);
                    block.getWorld().setBlockAt(block.getX(), block.getY() + 1, block.getZ(), (short) 6, (short) 2);
                    FruitTree tree = new DyeTree(cft, block.getX(), block.getY() + 1, block.getZ(), cft.getWorldForName(block.getWorld().getFqName()), seeds.getMetaTag().getCompoundTag("FruitTrees").getByte("DyeColor"));
                    tree.save();
                    decreaseStack(hook.getPlayer());
                    hook.setCanceled();
                }
                else if (seeds.getMetaTag().containsKey("FruitTrees") && seeds.getMetaTag().getCompoundTag("FruitTrees").getString("TreeType").equals("RedstoneSeeds")) {
                    block.getWorld().setBlockAt(block.getPosition(), (short) 3);
                    block.getWorld().setBlockAt(block.getX(), block.getY() + 1, block.getZ(), (short) 6, (short) 1);
                    FruitTree tree = new RedstoneTree(cft, block.getX(), block.getY() + 1, block.getZ(), cft.getWorldForName(block.getWorld().getFqName()));
                    tree.save();
                    decreaseStack(hook.getPlayer());
                    hook.setCanceled();
                }
            }
        }
    }

    @HookHandler(priority = Priority.LOW)
    public final void killTree(BlockDestroyHook hook){
        if (BlockType.fromId(hook.getBlock().getTypeId()) == BlockType.OakSapling) {
            Block block = hook.getBlock();
            FruitTree tree = TreeTracker.getTreeAt(block.getX(), block.getY(), block.getZ(), cft.getWorldForName(block.getWorld().getFqName()));
            if (tree != null) {
                if (hook.getPlayer().getMode() != GameMode.CREATIVE) { //If creative, no need to drop stuff
                    hook.setCanceled();
                    switch (tree.getType()) {
                        case APPLE:
                            block.getWorld().dropItem(block.getPosition(), CanaryFruitTrees.seeds[0].clone());
                            break;
                        case GOLDEN_APPLE:
                            block.getWorld().dropItem(block.getPosition(), CanaryFruitTrees.seeds[1].clone());
                            break;
                        case SPONGE:
                            block.getWorld().dropItem(block.getPosition(), CanaryFruitTrees.seeds[2].clone());
                            break;
                        case RECORD:
                            block.getWorld().dropItem(block.getPosition(), CanaryFruitTrees.seeds[3].clone());
                            break;
                        case DYE_BLACK:
                            block.getWorld().dropItem(block.getPosition(), CanaryFruitTrees.seeds[4].clone());
                            break;
                        case DYE_RED:
                            block.getWorld().dropItem(block.getPosition(), CanaryFruitTrees.seeds[5].clone());
                            break;
                        case DYE_GREEN:
                            block.getWorld().dropItem(block.getPosition(), CanaryFruitTrees.seeds[6].clone());
                            break;
                        case DYE_BROWN:
                            block.getWorld().dropItem(block.getPosition(), CanaryFruitTrees.seeds[7].clone());
                            break;
                        case DYE_BLUE:
                            block.getWorld().dropItem(block.getPosition(), CanaryFruitTrees.seeds[8].clone());
                            break;
                        case DYE_PURPLE:
                            block.getWorld().dropItem(block.getPosition(), CanaryFruitTrees.seeds[9].clone());
                            break;
                        case DYE_CYAN:
                            block.getWorld().dropItem(block.getPosition(), CanaryFruitTrees.seeds[10].clone());
                            break;
                        case DYE_LIGHT_GRAY:
                            block.getWorld().dropItem(block.getPosition(), CanaryFruitTrees.seeds[11].clone());
                            break;
                        case DYE_GRAY:
                            block.getWorld().dropItem(block.getPosition(), CanaryFruitTrees.seeds[12].clone());
                            break;
                        case DYE_PINK:
                            block.getWorld().dropItem(block.getPosition(), CanaryFruitTrees.seeds[13].clone());
                            break;
                        case DYE_LIME:
                            block.getWorld().dropItem(block.getPosition(), CanaryFruitTrees.seeds[14].clone());
                            break;
                        case DYE_YELLOW:
                            block.getWorld().dropItem(block.getPosition(), CanaryFruitTrees.seeds[15].clone());
                            break;
                        case DYE_LIGHT_BLUE:
                            block.getWorld().dropItem(block.getPosition(), CanaryFruitTrees.seeds[16].clone());
                            break;
                        case DYE_MAGENTA:
                            block.getWorld().dropItem(block.getPosition(), CanaryFruitTrees.seeds[17].clone());
                            break;
                        case DYE_ORANGE:
                            block.getWorld().dropItem(block.getPosition(), CanaryFruitTrees.seeds[18].clone());
                            break;
                        case DYE_WHITE:
                            block.getWorld().dropItem(block.getPosition(), CanaryFruitTrees.seeds[19].clone());
                            break;
                        case REDSTONE:
                            block.getWorld().dropItem(block.getPosition(), CanaryFruitTrees.seeds[20].clone());
                        default:
                            break;
                    }
                    block.setTypeId((short) 0);
                    block.getWorld().setBlock(block);
                }
                tree.killTree();
            }
        }
    }

    @HookHandler(priority = Priority.LOW)
    public final void killTreeArea(BlockUpdateHook hook){ //BlockUpdate a little more reliable with tracking Tree destruction (Especially if editting out a tree)
        if (hook.getBlock().getTypeId() == BlockType.OakLog.getId()) {
            Block block = hook.getBlock();
            FruitTree tree = TreeTracker.isTreeArea(block.getX(), block.getY(), block.getZ(), block.getTypeId(), block.getData(), cft.getWorldForName(block.getWorld().getFqName()));
            if (tree != null) {
                tree.killTree();
            }
        }
        else if (hook.getBlock().getTypeId() == BlockType.OakLeaves.getId()) {
            Block block = hook.getBlock();
            FruitTree tree = TreeTracker.isTreeArea(block.getX(), block.getY(), block.getZ(), block.getTypeId(), block.getData(), cft.getWorldForName(block.getWorld().getFqName()));
            if (tree != null) {
                tree.killTree();
            }
        }
        else if (hook.getBlock().getType() == BlockType.Sponge) {
            Block block = hook.getBlock();
            FruitTree tree = TreeTracker.isTreeArea(block.getX(), block.getY(), block.getZ(), block.getTypeId(), block.getData(), cft.getWorldForName(block.getWorld().getFqName()));
            if (tree != null) {
                tree.killTree();
            }
        }
        else if (hook.getBlock().getTypeId() == BlockType.WoolWhite.getId()) {
            Block block = hook.getBlock();
            FruitTree tree = TreeTracker.isTreeArea(block.getX(), block.getY(), block.getZ(), block.getTypeId(), block.getData(), cft.getWorldForName(block.getWorld().getFqName()));
            if (tree != null) {
                tree.killTree();
            }
        }
        else if (hook.getBlock().getType() == BlockType.RedstoneBlock) {
            Block block = hook.getBlock();
            FruitTree tree = TreeTracker.isTreeArea(block.getX(), block.getY(), block.getZ(), block.getTypeId(), block.getData(), cft.getWorldForName(block.getWorld().getFqName()));
            if (tree != null) {
                tree.killTree();
            }
        }
        else if (hook.getBlock().getType() == BlockType.NoteBlock) {
            Block block = hook.getBlock();
            FruitTree tree = TreeTracker.isTreeArea(block.getX(), block.getY(), block.getZ(), block.getTypeId(), block.getData(), cft.getWorldForName(block.getWorld().getFqName()));
            if (tree != null) {
                tree.killTree();
            }
        }
    }

    @HookHandler(priority = Priority.HIGH)
    public final void treeGrow(TreeGrowHook hook){
        Block sapling = hook.getSapling();
        FruitTree tree = TreeTracker.getTreeAt(sapling.getX(), sapling.getY(), sapling.getZ(), cft.getWorldForName(sapling.getWorld().getFqName()));
        if (tree != null) {
            tree.growTree();
            hook.setCanceled();
        }
    }

    @HookHandler
    public final void worldLoad(LoadWorldHook hook){
        cft.addLoadedWorld(hook.getWorld());
    }

    @HookHandler
    public final void worldunload(UnloadWorldHook hook){
        TreeWorld tree_world = cft.getWorldForName(hook.getWorld().getFqName());
        if (tree_world != null) {
            cft.debug("World Unloaded: " + tree_world);
            tree_world.unloadWorld();
        }
    }

    private final void decreaseStack(Player player){
        if (player.getMode() != GameMode.CREATIVE) {
            Item held = player.getItemHeld();
            held.setAmount(held.getAmount() - 1);
            if (held.getAmount() <= 0) {
                player.getInventory().setSlot(held.getSlot(), null);
            }
        }
    }
}
