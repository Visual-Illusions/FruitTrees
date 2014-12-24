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

import net.visualillusionsent.fruittrees.trees.FruitTree;

public final class TreeGen {

    private final FruitTree fruit_tree;

    private TreeGen(FruitTree fruit_tree) {
        this.fruit_tree = fruit_tree;
    }

    public static void growTree(FruitTree fruit_tree) {
        TreeGen gen = new TreeGen(fruit_tree);
        gen.grow();
    }

    public final boolean grow() {
        if (canGrow()) {
            logTree();
            leaves_layer1();
            leaves_layer2();
            leaves_layer3();
            leaves_layer4();
            return true;
        }
        else {
            return false;
        }
    }

    public final boolean canGrow() {
        return fruit_tree.getTreeWorld().isTreePart(fruit_tree.getX(), fruit_tree.getY(), fruit_tree.getZ(), fruit_tree.getType().getSaplingName());
    }

    private void logTree() {
        /* Vertically grow tree */
        int ly = fruit_tree.getY();
        for (int yTemp = ly; yTemp <= ly + 4; yTemp++) {
            fruit_tree.getTreeWorld().placeTreePart(fruit_tree.getX(), yTemp, fruit_tree.getZ(), fruit_tree.getType().getLogName(), (byte) 0);
        }
        /* The Sideways logs */
        ly += 3;
        fruit_tree.getTreeWorld().placeTreePart(fruit_tree.getX() + 1, ly, fruit_tree.getZ(), fruit_tree.getType().getLogName(), (byte) 1);
        fruit_tree.getTreeWorld().placeTreePart(fruit_tree.getX() - 1, ly, fruit_tree.getZ(), fruit_tree.getType().getLogName(), (byte) 1);
        fruit_tree.getTreeWorld().placeTreePart(fruit_tree.getX(), ly, fruit_tree.getZ() + 1, fruit_tree.getType().getLogName(), (byte) 2);
        fruit_tree.getTreeWorld().placeTreePart(fruit_tree.getX(), ly, fruit_tree.getZ() - 1, fruit_tree.getType().getLogName(), (byte) 2);
    }

    private void leaves_layer1() {
        /* Top Down View
         * Leaves: # Logs: @
         *  ABCDE
         * 1#####
         * 2#####
         * 3##@##
         * 4#####
         * 5#####
         */
        int x = fruit_tree.getX(), layer_y = fruit_tree.getY() + 2, z = fruit_tree.getZ();
        placeLeavesAt(x - 2, layer_y, z - 2); //A-1
        placeLeavesAt(x - 2, layer_y, z - 1); //A-2
        placeLeavesAt(x - 2, layer_y, z); //A-3
        placeLeavesAt(x - 2, layer_y, z + 1); //A-4
        placeLeavesAt(x - 2, layer_y, z + 2); //A-5
        placeLeavesAt(x - 1, layer_y, z - 2); //B-1
        placeLeavesAt(x - 1, layer_y, z - 1); //B-2
        placeLeavesAt(x - 1, layer_y, z); //B-3
        placeLeavesAt(x - 1, layer_y, z + 1); //B-4
        placeLeavesAt(x - 1, layer_y, z + 2); //B-5
        placeLeavesAt(x, layer_y, z - 2); //C-1
        placeLeavesAt(x, layer_y, z - 1); //C-2
        placeLeavesAt(x, layer_y, z + 1); //C-4
        placeLeavesAt(x, layer_y, z + 2); //C-5
        placeLeavesAt(x + 1, layer_y, z - 2); //D-1
        placeLeavesAt(x + 1, layer_y, z - 1); //D-2
        placeLeavesAt(x + 1, layer_y, z); //D-3
        placeLeavesAt(x + 1, layer_y, z + 1); //D-4
        placeLeavesAt(x + 1, layer_y, z + 2); //D-5
        placeLeavesAt(x + 2, layer_y, z - 2); //E-1
        placeLeavesAt(x + 2, layer_y, z - 1); //E-2
        placeLeavesAt(x + 2, layer_y, z); //E-3
        placeLeavesAt(x + 2, layer_y, z + 1); //E-4
        placeLeavesAt(x + 2, layer_y, z + 2); //E-5
    }

    private void leaves_layer2() {
        /* Top Down View
         * Leaves: # Logs: @
         *  ABCDE
         * 1 ###
         * 2##@##
         * 3#@@@#
         * 4##@##
         * 5 ###
         */
        int layer_y = fruit_tree.getY() + 3;
        placeLeavesAt(fruit_tree.getX() - 2, layer_y, fruit_tree.getZ() - 1); //A-2
        placeLeavesAt(fruit_tree.getX() - 2, layer_y, fruit_tree.getZ()); //A-3
        placeLeavesAt(fruit_tree.getX() - 2, layer_y, fruit_tree.getZ() + 1); //A-4
        placeLeavesAt(fruit_tree.getX() - 1, layer_y, fruit_tree.getZ() - 2); //B-1
        placeLeavesAt(fruit_tree.getX() - 1, layer_y, fruit_tree.getZ() - 1); //B-2
        placeLeavesAt(fruit_tree.getX() - 1, layer_y, fruit_tree.getZ() + 1); //B-4
        placeLeavesAt(fruit_tree.getX() - 1, layer_y, fruit_tree.getZ() + 2); //B-5
        placeLeavesAt(fruit_tree.getX(), layer_y, fruit_tree.getZ() - 2); //C-1
        placeLeavesAt(fruit_tree.getX(), layer_y, fruit_tree.getZ() + 2); //C-5
        placeLeavesAt(fruit_tree.getX() + 1, layer_y, fruit_tree.getZ() - 2); //D-1
        placeLeavesAt(fruit_tree.getX() + 1, layer_y, fruit_tree.getZ() - 1); //D-2
        placeLeavesAt(fruit_tree.getX() + 1, layer_y, fruit_tree.getZ() + 1); //D-4
        placeLeavesAt(fruit_tree.getX() + 1, layer_y, fruit_tree.getZ() + 2); //D-5
        placeLeavesAt(fruit_tree.getX() + 2, layer_y, fruit_tree.getZ() - 1); //E-2
        placeLeavesAt(fruit_tree.getX() + 2, layer_y, fruit_tree.getZ()); //E-3
        placeLeavesAt(fruit_tree.getX() + 2, layer_y, fruit_tree.getZ() + 1); //E-4
    }

    private void leaves_layer3() {
        /* Top Down View
         * Leaves: # Logs: @
         *  ABCDE
         * 1
         * 2 ###
         * 3 #@#
         * 4 ###
         * 5
         */
        int layer_y = fruit_tree.getY() + 4;
        placeLeavesAt(fruit_tree.getX() - 1, layer_y, fruit_tree.getZ() - 1); //B-2
        placeLeavesAt(fruit_tree.getX() - 1, layer_y, fruit_tree.getZ()); //B-3
        placeLeavesAt(fruit_tree.getX() - 1, layer_y, fruit_tree.getZ() + 1); //B-4
        placeLeavesAt(fruit_tree.getX(), layer_y, fruit_tree.getZ() - 1); //C-2
        placeLeavesAt(fruit_tree.getX(), layer_y, fruit_tree.getZ() + 1); // C-4
        placeLeavesAt(fruit_tree.getX() + 1, layer_y, fruit_tree.getZ() - 1); // D-2
        placeLeavesAt(fruit_tree.getX() + 1, layer_y, fruit_tree.getZ()); // D-3
        placeLeavesAt(fruit_tree.getX() + 1, layer_y, fruit_tree.getZ() + 1); // D-4
    }

    private void leaves_layer4() {
        /* Top Down View
         * Leaves: # Logs: @
         *
         *  ABCDE
         * 1
         * 2
         * 3  #
         * 4
         * 5
         */
        placeLeavesAt(fruit_tree.getX(), fruit_tree.getY() + 5, fruit_tree.getZ());
    }

    private void placeLeavesAt(int x, int y, int z) {
        fruit_tree.getTreeWorld().placeTreePart(x, y, z, fruit_tree.getType().getLeavesName());
    }
}
