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

import net.visualillusionsent.fruittrees.*;
import org.jdom2.Element;

import java.util.Random;

public abstract class FruitTree {
    protected static final byte[] offset_drop = new byte[]{ -2, -1, 1, 2 };
    protected static final Random random = new Random();

    protected FruitTrees fruit_trees;
    protected TreeType type;
    protected int loc_x, loc_y, loc_z;
    protected TreeWorld world;
    private DropTask task;
    private boolean dead = false;

    public FruitTree(FruitTrees fruit_trees, TreeType type, int loc_x, int loc_y, int loc_z, TreeWorld world, boolean planting) {
        this.fruit_trees = fruit_trees;
        this.type = type;
        this.loc_x = loc_x;
        this.loc_y = loc_y;
        this.loc_z = loc_z;
        this.world = world;

        if (!planting && isGrown() && isStillValid()) {
            world.scheduleDrop(new DropTask(this));
        }
        TreeTracker.trackTree(this);
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
        return !world.isTreePart(loc_x, loc_y, loc_z, this.getType().getSaplingName());
    }

    public boolean isDead() {
        return dead;
    }

    public abstract boolean dropFruit();

    public final void growTree() {
        TreeGen.growTree(this);
        world.scheduleDrop(task = new DropTask(this));
    }

    public final TreeType getType() {
        return type;
    }

    public final void killTree(String reason) {
        if (!dead) {
            fruit_trees.debug("Proceeding to kill tree due to '" + reason + "'");
            dead = true;
            TreeTracker.untrackTree(this);
            fruit_trees.getStorage().removeTree(this);
        }
    }

    public final boolean isStillValid() {
        if (dead) {
            return false;
        }
        if (!fruit_trees.getFruitTreesConfig().checkEnabled(this.type)) {
            fruit_trees.debug("Tree Type Fail at X: " + loc_x + " Y: " + loc_y + " Z: " + loc_z);
            return false;
        }
        //Trunk
        for (int check_y = loc_y; check_y <= loc_y + 4; check_y++) {
            if (!world.isTreePart(loc_x, check_y, loc_z, type.getLogName())) {
                fruit_trees.debug("Log Type Fail at X: " + loc_x + " Y: " + check_y + " Z: " + loc_z);
                killTree("Log Type");
                return false;
            }
        }
        // point logs
        if (!world.isTreePart(loc_x + 1, loc_y + 3, loc_z, type.getLogName())) {
            fruit_trees.debug("Log Type Fail at X: " + (loc_x + 1) + " Y: " + (loc_y + 3) + " Z: " + loc_z);
            killTree("Log Type");
            return false;
        }
        if (!world.isTreePart(loc_x - 1, loc_y + 3, loc_z, type.getLogName())) {
            fruit_trees.debug("Log Type Fail at X: " + (loc_x - 1) + " Y: " + (loc_y + 3) + " Z: " + loc_z);
            killTree("Log Type");
            return false;
        }
        if (!world.isTreePart(loc_x, loc_y + 3, loc_z + 1, type.getLogName())) {
            fruit_trees.debug("Log Type Fail at X: " + loc_x + " Y: " + (loc_y + 3) + " Z: " + (loc_z + 1));
            killTree("Log Type");
            return false;
        }
        if (!world.isTreePart(loc_x, loc_y + 3, loc_z - 1, type.getLogName())) {
            fruit_trees.debug("Log Type Fail at X: " + loc_x + " Y: " + (loc_y + 3) + " Z: " + (loc_z - 1));
            killTree("Log Type");
            return false;
        }
        // Top leaves
        if (!world.isTreePart(loc_x, loc_y + 5, loc_z, type.getLeavesName())) {
            fruit_trees.debug("Leaves Type Fail at X: " + loc_x + " Y: " + (loc_y + 5) + " Z: " + loc_z);
            killTree("Log Type");
            return false;
        }
        // Leaves layer 1
        for (int check_x = loc_x - 2; check_x <= loc_x + 2; check_x++) {
            for (int check_z = loc_z - 2; check_z <= loc_z + 2; check_z++) {
                if (check_x == loc_x && check_z == loc_z) {
                    // log
                    continue;
                }
                else if (!world.isTreePart(check_x, loc_y + 2, check_z, type.getLeavesName())) {
                    fruit_trees.debug("Leaves Type Fail at X: " + check_x + " Y: " + (loc_y + 2) + " Z: " + check_z);
                    killTree("Leaves Type");
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
                else if (!world.isTreePart(check_x, loc_y + 3, check_z, type.getLeavesName())) {
                    fruit_trees.debug("Leaves Type Fail at X: " + check_x + " Y: " + (loc_y + 3) + " Z: " + check_z);
                    killTree("Leaves Type");
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
                else if (!world.isTreePart(check_x, loc_y + 4, check_z, type.getLeavesName())) {
                    fruit_trees.debug("Leaves Type Fail at X: " + check_x + " Y: " + (loc_y + 4) + " Z: " + check_z);
                    killTree("Leaves Type");
                    return false;
                }
            }
        }
        return true;
    }

    public final boolean isBaseAt(int x, int y, int z, TreeWorld world) {
        return x == loc_x && y == loc_y && z == loc_z && world.equals(this.world);
    }

    public final boolean isInArea(int x, int y, int z, String blockName, TreeWorld world) {
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
            return type.getLogName().equals(blockName);
        }
        else if (x == loc_x && x == loc_z && y == (loc_y + 5)) {
            return type.getLeavesName().equals(blockName);
        }
        else if (y == (loc_y + 2) && inRange(loc_x, x, 0, 2) && inRange(loc_z, z, 0, 2)) {
            return type.getLeavesName().equals(blockName);
        }
        else if (y == (loc_y + 3) && inRange(loc_x, x, 0, 2) && inRange(loc_z, z, 0, 2)) {
            return !(Math.abs(loc_x) + 2 == Math.abs(x) && Math.abs(loc_z) + 2 == Math.abs(z)) && type.getLeavesName().equals(blockName);
        }
        else if (y == (loc_y + 3) && inRange(loc_x, x, 0, 1) && inRange(loc_z, z, 0, 1)) {
            return type.getLeavesName().equals(blockName);
        }
        else if (y == (loc_y + 4) && loc_x == x && loc_z == z) {
            return type.getLeavesName().equals(blockName);
        }
        return false;
    }

    private boolean inRange(int num_1, int num_2, int min, int max) {
        int check = Math.abs(num_1 - num_2);
        return check >= min && max >= check;
    }

    public final boolean equalsElement(Element tree_element) {
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
