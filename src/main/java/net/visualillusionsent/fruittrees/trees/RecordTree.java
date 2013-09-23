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
package net.visualillusionsent.fruittrees.trees;

import net.visualillusionsent.fruittrees.FruitTrees;
import net.visualillusionsent.fruittrees.TreeGen;
import net.visualillusionsent.fruittrees.TreeType;
import net.visualillusionsent.fruittrees.TreeWorld;

public final class RecordTree extends FruitTree {

    private static final short[] records = new short[]{2256, 2257, 2258, 2259, 2260, 2261, 2262, 2263, 2264, 2265, 2266, 2267};

    public RecordTree(FruitTrees fruit_trees, int loc_x, int loc_y, int loc_z, TreeWorld world) {
        super(fruit_trees, TreeType.RECORD, loc_x, loc_y, loc_z, world);
    }

    @Override
    public final void dropFruit() {
        if (isGrown() && world.isAreaLoaded(this) && fruit_trees.getFruitTreesConfig().checkEnabled(this.type)) {
            int drop_x = offset_drop[random.nextInt(3)];
            int drop_z = offset_drop[random.nextInt(3)];
            short record = records[random.nextInt(records.length)];
            fruit_trees.debug(this + " dropping Record ID: " + record);
            world.dropFruit(loc_x + drop_x, loc_y + 1, loc_z + drop_z, 1, record, (byte)0);
        }
    }

    @Override
    public final void growTree() {
        new TreeGen(this).grow();
    }

    @Override
    public final String toString() {
        return String.format("RecordTree[X:%d Y:%d Z:%d World:%s]", loc_x, loc_y, loc_z, world);
    }
}
