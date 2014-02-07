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

import net.visualillusionsent.fruittrees.DropTask;
import net.visualillusionsent.fruittrees.FruitTrees;
import net.visualillusionsent.fruittrees.TreeTracker;
import net.visualillusionsent.fruittrees.TreeType;
import net.visualillusionsent.fruittrees.TreeWorld;
import org.jdom2.Element;

import java.util.Random;

public abstract class FruitTree {

    protected FruitTrees fruit_trees;
    protected TreeType type;
    protected int loc_x, loc_y, loc_z;
    protected TreeWorld world;
    protected static final byte[] offset_drop = new byte[]{ -2, -1, 1, 2 };
    protected static final Random random = new Random();

    public FruitTree(FruitTrees fruit_trees, TreeType type, int loc_x, int loc_y, int loc_z, TreeWorld world) {
        this.fruit_trees = fruit_trees;
        this.type = type;
        this.loc_x = loc_x;
        this.loc_y = loc_y;
        this.loc_z = loc_z;
        this.world = world;
        TreeTracker.trackTree(this);
        world.scheduleDrop(new DropTask(this));
    }

    public int getX() {
        return loc_x;
    }

    public int getY() {
        return loc_y;
    }

    public int getZ() {
        return loc_z;
    }

    public TreeWorld getTreeWorld() {
        return world;
    }

    public boolean isGrown() {
        return !world.isTreePart(loc_x, loc_y, loc_z, (short) 6, this.type.getLogData());
    }

    public abstract void dropFruit();

    public abstract void growTree();

    public final TreeType getType() {
        return type;
    }

    public final void killTree() {
        TreeTracker.untrackTree(this);
        fruit_trees.getStorage().removeTree(this);
    }

    public final boolean isStillValid() {
        if (!fruit_trees.getFruitTreesConfig().checkEnabled(this.type)) {
            fruit_trees.debug("Tree Type Fail at X: " + loc_x + " Y: " + loc_y + " Z: " + loc_z);
            return false;
        }
        //Trunk
        for (int check_y = loc_y; check_y <= loc_y + 4; check_y++) {
            if (!world.isTreePart(loc_x, check_y, loc_z, type.getLogId(), type.getLogData())) {
                fruit_trees.debug("Log Type Fail at X: " + loc_x + " Y: " + check_y + " Z: " + loc_z);
                killTree();
                return false;
            }
        }
        // point logs
        if (!world.isTreePart(loc_x + 1, loc_y + 3, loc_z, type.getLogId(), (byte) (type.getLogData() + 4))) {
            fruit_trees.debug("Log Type Fail at X: " + (loc_x + 1) + " Y: " + (loc_y + 3) + " Z: " + loc_z);
            killTree();
            return false;
        }
        if (!world.isTreePart(loc_x - 1, loc_y + 3, loc_z, type.getLogId(), (byte) (type.getLogData() + 4))) {
            fruit_trees.debug("Log Type Fail at X: " + (loc_x - 1) + " Y: " + (loc_y + 3) + " Z: " + loc_z);
            killTree();
            return false;
        }
        if (!world.isTreePart(loc_x, loc_y + 3, loc_z + 1, type.getLogId(), (byte) (type.getLogData() + 8))) {
            fruit_trees.debug("Log Type Fail at X: " + loc_x + " Y: " + (loc_y + 3) + " Z: " + (loc_z + 1));
            killTree();
            return false;
        }
        if (!world.isTreePart(loc_x, loc_y + 3, loc_z - 1, type.getLogId(), (byte) (type.getLogData() + 8))) {
            fruit_trees.debug("Log Type Fail at X: " + loc_x + " Y: " + (loc_y + 3) + " Z: " + (loc_z - 1));
            killTree();
            return false;
        }
        // Top leaves
        if (!world.isTreePart(loc_x, loc_y + 5, loc_z, type.getLeavesId(), type.getLeavesData())) {
            fruit_trees.debug("Leaves Type Fail at X: " + loc_x + " Y: " + (loc_y + 5) + " Z: " + loc_z);
            killTree();
            return false;
        }
        // Leaves layer 1
        for (int check_x = loc_x - 2; check_x <= loc_x + 2; check_x++) {
            for (int check_z = loc_z - 2; check_z <= loc_z + 2; check_z++) {
                if (check_x == loc_x && check_z == loc_z) {
                    // log
                    continue;
                }
                else if (!world.isTreePart(check_x, loc_y + 2, check_z, type.getLeavesId(), type.getLeavesData())) {
                    fruit_trees.debug("Leaves Type Fail at X: " + check_x + " Y: " + (loc_y + 2) + " Z: " + check_z);
                    killTree();
                    return false;
                }
            }
        }
        // Leaves layer 2
        for (int check_x = loc_x - 2; check_x <= loc_x + 2; check_x++) {
            for (int check_z = loc_z - 2; check_z <= loc_z + 2; check_z++) {
                if (check_x == loc_x && check_z == loc_z) {
                    // log
                    continue;
                }
                else if (((check_x == loc_x + 2 || check_x == loc_x - 2) && check_z == loc_z + 2) ||
                        ((check_x == loc_x + 2 || check_x == loc_x - 2) && check_z == loc_z - 2)) {
                    // Outside points
                    continue;
                }
                else if (((check_x == loc_x + 1 || check_x == loc_x - 1) && check_z == loc_z) ||
                        ((check_z == loc_z + 1 || check_z == loc_z - 1) && check_x == loc_x)) {
                    // Log points
                    continue;
                }
                else if (!world.isTreePart(check_x, loc_y + 3, check_z, type.getLeavesId(), type.getLeavesData())) {
                    fruit_trees.debug("Leaves Type Fail at X: " + check_x + " Y: " + (loc_y + 3) + " Z: " + check_z);
                    killTree();
                    return false;
                }
            }
        }
        // Leaves layer 3
        for (int check_x = loc_x - 1; check_x <= loc_x + 1; check_x++) {
            for (int check_z = loc_z - 1; check_z <= loc_z + 1; check_z++) {
                if (check_x == loc_x && check_z == loc_z) {
                    // Log center
                    continue;
                }
                else if (!world.isTreePart(check_x, loc_y + 4, check_z, type.getLeavesId(), type.getLeavesData())) {
                    fruit_trees.debug("Leaves Type Fail at X: " + check_x + " Y: " + (loc_y + 4) + " Z: " + check_z);
                    killTree();
                    return false;
                }
            }
        }
        return true;
    }

    public final boolean isBaseAt(int x, int y, int z, TreeWorld world) {
        if (x != loc_x) {
            return false;
        }
        if (y != loc_y) {
            return false;
        }
        if (z != loc_z) {
            return false;
        }
        if (!world.equals(this.world)) {
            return false;
        }
        return true;
    }

    public final boolean isInArea(int x, int y, int z, int id, int data, TreeWorld world) {
        if (!world.equals(this.world)) {
            return false;
        }
        else if (isBaseAt(x, y, z, world)) {
            return true;
        }
        else if (!inRange(loc_x, x, 0, 5) || !inRange(loc_y, y, 0, 5) || !inRange(loc_z, z, 0, 5)) {
            return false;
        }
        else if (x == loc_x && z == loc_z && inRange(loc_y, y, 1, 4)) {
            return type.getLogId() == id && type.getLogData() == data;
        }
        else if (x == loc_x && x == loc_z && y == (loc_y + 5)) {
            return type.getLeavesId() == id && type.getLeavesData() == data;
        }
        else if (y == (loc_y + 2) && inRange(loc_x, x, 0, 2) && inRange(loc_z, z, 0, 2)) {
            return type.getLeavesId() == id && type.getLeavesData() == data;
        }
        else if (y == (loc_y + 3) && inRange(loc_x, x, 0, 2) && inRange(loc_z, z, 0, 2)) {
            if (Math.abs(loc_x) + 2 == Math.abs(x) && Math.abs(loc_z) + 2 == Math.abs(z)) {
                return false;
            }
            return type.getLeavesId() == id && type.getLeavesData() == data;
        }
        else if (y == (loc_y + 3) && inRange(loc_x, x, 0, 1) && inRange(loc_z, z, 0, 1)) {
            return type.getLeavesId() == id && type.getLeavesData() == data;
        }
        else if (y == (loc_y + 4) && loc_x == x && loc_z == z) {
            return type.getLeavesId() == id && type.getLeavesData() == data;
        }
        return false;
    }

    private final boolean inRange(int num_1, int num_2, int min, int max) {
        int check = Math.abs(num_1 - num_2);
        return check >= min && max >= check;
    }

    public boolean equalsElement(Element tree_element) {
        if (!this.type.toString().equals(tree_element.getAttributeValue("Type"))) {
            return false;
        }
        else if (!String.valueOf(loc_x).equals(tree_element.getAttributeValue("X"))) {
            return false;
        }
        else if (!String.valueOf(loc_y).equals(tree_element.getAttributeValue("Y"))) {
            return false;
        }
        else if (!String.valueOf(loc_z).equals(tree_element.getAttributeValue("Z"))) {
            return false;
        }
        return true;
    }

    public final void save() {
        fruit_trees.getStorage().storeTree(this);
    }
}
