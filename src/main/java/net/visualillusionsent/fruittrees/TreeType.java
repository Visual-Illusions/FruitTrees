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
package net.visualillusionsent.fruittrees;

import net.visualillusionsent.fruittrees.trees.*;

public enum TreeType {

    APPLE(AppleTree.class, "minecraft:log:0", "minecraft:leaves:3", "minecraft:sapling:0"),
    GOLDEN_APPLE(GoldenAppleTree.class, "minecraft:log:1", "minecraft:leaves:3", "minecraft:sapling:1"),
    SPONGE(SpongeTree.class, "minecraft:log2:1", "minecraft:sponge:0", "minecraft:sapling:5"),
    RECORD(RecordTree.class, "minecraft:log:1", "minecraft:jukebox:0", "minecraft:sapling:1"),
    DYE_BLACK(DyeTree.class, "minecraft:log:2", "minecraft:wool:15", "minecraft:sapling:2"),
    DYE_RED(DyeTree.class, "minecraft:log:2", "minecraft:wool:14", "minecraft:sapling:2"),
    DYE_GREEN(DyeTree.class, "minecraft:log:2", "minecraft:wool:13", "minecraft:sapling:2"),
    DYE_BROWN(DyeTree.class, "minecraft:log:2", "minecraft:wool:12", "minecraft:sapling:2"),
    DYE_BLUE(DyeTree.class, "minecraft:log:2", "minecraft:wool:11", "minecraft:sapling:2"),
    DYE_PURPLE(DyeTree.class, "minecraft:log:2", "minecraft:wool:10", "minecraft:sapling:2"),
    DYE_CYAN(DyeTree.class, "minecraft:log:2", "minecraft:wool:9", "minecraft:sapling:2"),
    DYE_LIGHT_GRAY(DyeTree.class, "minecraft:log:2", "minecraft:wool:8", "minecraft:sapling:2"),
    DYE_GRAY(DyeTree.class, "minecraft:log:2", "minecraft:wool:7", "minecraft:sapling:2"),
    DYE_PINK(DyeTree.class, "minecraft:log:2", "minecraft:wool:6", "minecraft:sapling:2"),
    DYE_LIME(DyeTree.class, "minecraft:log:2", "minecraft:wool:5", "minecraft:sapling:2"),
    DYE_YELLOW(DyeTree.class, "minecraft:log:2", "minecraft:wool:4", "minecraft:sapling:2"),
    DYE_LIGHT_BLUE(DyeTree.class, "minecraft:log:2", "minecraft:wool:3", "minecraft:sapling:2"),
    DYE_MAGENTA(DyeTree.class, "minecraft:log:2", "minecraft:wool:2", "minecraft:sapling:2"),
    DYE_ORANGE(DyeTree.class, "minecraft:log:2", "minecraft:wool:1", "minecraft:sapling:2"),
    DYE_WHITE(DyeTree.class, "minecraft:log:2", "minecraft:wool:0", "minecraft:sapling:2"),
    REDSTONE(RedstoneTree.class, "minecraft:log2:0", "minecraft:redstone_block:0", "minecraft:sapling:4"),
    IRON(IronTree.class, "minecraft:log:2", "minecraft:iron_block:0", "minecraft:sapling:2"),
    GOLD(GoldTree.class, "minecraft:log:1", "minecraft:gold_block:0", "minecraft:sapling:1"),
    DIAMOND(DiamondTree.class, "minecraft:log:0", "minecraft:diamond_block:0", "minecraft:sapling:0"),
    EMERALD(EmeraldTree.class, "minecraft:log:0", "minecraft:emerald_block:0", "minecraft:sapling:0"),
    COAL(CoalTree.class, "minecraft:log2:1", "minecraft:coal_block", "minecraft:sapling:5"),
    //
    ;

    private final Class<? extends FruitTree> tree_class;
    private final String logName, leavesName, saplingName;

    private TreeType(Class<? extends FruitTree> tree_class, String logName, String leavesName, String saplingName) {
        this.tree_class = tree_class;
        this.logName = logName;
        this.leavesName = leavesName;
        this.saplingName = saplingName;
    }

    public final String getLogName() {
        return logName;
    }

    public final String getLeavesName() {
        return leavesName;
    }

    public final String getSaplingName() {
        return saplingName;
    }

    public final FruitTree newFruitTree(FruitTrees fruit_trees, int x, int y, int z, TreeWorld tree_world) throws Exception {
        if (this.tree_class.equals(DyeTree.class)) {
            return tree_class.getConstructor(FruitTrees.class, Integer.TYPE, Integer.TYPE, Integer.TYPE, TreeWorld.class, Byte.TYPE, Boolean.TYPE).newInstance(fruit_trees, x, y, z, tree_world, (byte) (this.ordinal() - 4), false);
        }
        else {
            return tree_class.getConstructor(FruitTrees.class, Integer.TYPE, Integer.TYPE, Integer.TYPE, TreeWorld.class, Boolean.TYPE).newInstance(fruit_trees, x, y, z, tree_world, false);
        }
    }
}
