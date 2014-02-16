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
package net.visualillusionsent.fruittrees.trees;

import net.visualillusionsent.fruittrees.FruitTrees;
import net.visualillusionsent.fruittrees.TreeType;
import net.visualillusionsent.fruittrees.TreeWorld;

public final class DyeTree extends FruitTree {

    private final byte dye_color;

    public DyeTree(FruitTrees fruit_trees, int loc_x, int loc_y, int loc_z, TreeWorld world, byte dye_color) {
        super(fruit_trees, fromDyeColor(dye_color), loc_x, loc_y, loc_z, world);
        this.dye_color = dye_color;
    }

    private static TreeType fromDyeColor(int dye_color) {
        switch (dye_color) {
            case 0x0:
                return TreeType.DYE_BLACK;
            case 0x1:
                return TreeType.DYE_RED;
            case 0x2:
                return TreeType.DYE_GREEN;
            case 0x3:
                return TreeType.DYE_BROWN;
            case 0x4:
                return TreeType.DYE_BLUE;
            case 0x5:
                return TreeType.DYE_PURPLE;
            case 0x6:
                return TreeType.DYE_CYAN;
            case 0x7:
                return TreeType.DYE_LIGHT_GRAY;
            case 0x8:
                return TreeType.DYE_GRAY;
            case 0x9:
                return TreeType.DYE_PINK;
            case 0xA:
                return TreeType.DYE_LIME;
            case 0xB:
                return TreeType.DYE_YELLOW;
            case 0xC:
                return TreeType.DYE_LIGHT_BLUE;
            case 0xD:
                return TreeType.DYE_MAGENTA;
            case 0xE:
                return TreeType.DYE_ORANGE;
            case 0xF:
                return TreeType.DYE_WHITE;
            default:
                return TreeType.DYE_BLACK;
        }
    }

    @Override
    public final void dropFruit() {
        if (isGrown() && world.isAreaLoaded(this) && fruit_trees.getFruitTreesConfig().checkEnabled(this.type)) {
            int drop_x = offset_drop[random.nextInt(3)];
            int drop_z = offset_drop[random.nextInt(3)];
            world.dropFruit(loc_x + drop_x, loc_y + 1, loc_z + drop_z, 1, (short) 351, dye_color);
        }
    }

    @Override
    public final String toString() {
        return String.format("DyeTree[X:%d Y:%d Z:%d World:%s Type:%s]", loc_x, loc_y, loc_z, world, type);
    }
}
