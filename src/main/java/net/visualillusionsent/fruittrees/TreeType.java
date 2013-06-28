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
package net.visualillusionsent.fruittrees;

public enum TreeType {

    APPLE(AppleTree.class, 17, 0, 18, 3), //
    GOLDEN_APPLE(GoldenAppleTree.class, 17, 1, 18, 3), //
    SPONGE(SpongeTree.class, 17, 2, 19, 0), //
    RECORD(RecordTree.class, 17, 1, 25, 0), //
    DYE_BLACK(DyeTree.class, 17, 2, 35, 15), //
    DYE_RED(DyeTree.class, 17, 2, 35, 14), //
    DYE_GREEN(DyeTree.class, 17, 2, 35, 13), //
    DYE_BROWN(DyeTree.class, 17, 2, 35, 12), //
    DYE_BLUE(DyeTree.class, 17, 2, 35, 11), //
    DYE_PURPLE(DyeTree.class, 17, 2, 35, 10), //
    DYE_CYAN(DyeTree.class, 17, 2, 35, 9), //
    DYE_LIGHT_GRAY(DyeTree.class, 17, 2, 35, 8), //
    DYE_GRAY(DyeTree.class, 17, 2, 35, 7), //
    DYE_PINK(DyeTree.class, 17, 2, 35, 6), //
    DYE_LIME(DyeTree.class, 17, 2, 35, 5), //
    DYE_YELLOW(DyeTree.class, 17, 2, 35, 4), //
    DYE_LIGHT_BLUE(DyeTree.class, 17, 2, 35, 3), //
    DYE_MAGENTA(DyeTree.class, 17, 2, 35, 2), //
    DYE_ORANGE(DyeTree.class, 17, 2, 35, 1), //
    DYE_WHITE(DyeTree.class, 17, 2, 35, 0), //
    REDSTONE(RedstoneTree.class, 17, 1, 152, 0), //
    IRON(IronTree.class, 17, 2, 42, 0), //
    GOLD(GoldTree.class, 17, 1, 41, 0), //
    DIAMOND(DiamondTree.class, 17, 0, 57, 0), //
    EMERALD(EmeraldTree.class, 17, 0, 133, 0), //
    COAL(CoalTree.class, 17, 1, 173, 0), //
    ;

    private final Class<? extends FruitTree> tree_class;
    private final short log_id, log_data, leaves_id, leaves_data;

    private TreeType(Class<? extends FruitTree> tree_class, int log_id, int log_data, int leaves_id, int leaves_data) {
        this.tree_class = tree_class;
        this.log_id = (short) log_id;
        this.log_data = (short) log_data;
        this.leaves_id = (short) leaves_id;
        this.leaves_data = (short) leaves_data;
    }

    public final short getLogId() {
        return log_id;
    }

    public final short getLogData() {
        return log_data;
    }

    public final short getLeavesId() {
        return leaves_id;
    }

    public final short getLeavesData() {
        return leaves_data;
    }

    public final FruitTree newFruitTree(FruitTrees fruit_trees, int x, int y, int z, TreeWorld tree_world) throws Exception {
        if (this.tree_class == DyeTree.class) {
            return tree_class.getConstructor(FruitTrees.class, Integer.TYPE, Integer.TYPE, Integer.TYPE, TreeWorld.class, Byte.TYPE).newInstance(fruit_trees, x, y, z, tree_world, (byte) (this.ordinal() - 4));
        }
        else {
            return tree_class.getConstructor(FruitTrees.class, Integer.TYPE, Integer.TYPE, Integer.TYPE, TreeWorld.class).newInstance(fruit_trees, x, y, z, tree_world);
        }
    }
}
