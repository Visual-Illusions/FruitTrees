/*
 * This file is part of FruitTrees.
 *
 * Copyright © 2013-2013 Visual Illusions Entertainment
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
package net.visualillusionsent.minecraft.server.mod.fruittrees;

public final class TreeGen{

    private final FruitTree fruit_tree;
    private final boolean canGrow;

    public TreeGen(FruitTree fruit_tree){
        this.fruit_tree = fruit_tree;
        this.canGrow = canGrow();
    }

    final boolean getCanGrow(){
        return canGrow;
    }

    public final boolean grow(){
        if (canGrow) {
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

    private final boolean canGrow(){
        return true;
    }

    public final void logTree(){
        /* Vertically grow tree */
        for (int log_y = fruit_tree.getY(); log_y <= fruit_tree.getY() + 4; log_y++) {
            fruit_tree.getTreeWorld().placeTreePart(fruit_tree.getX(), log_y, fruit_tree.getZ(), fruit_tree.getType().getLogId(), fruit_tree.getType().getLogData());
        }
        /* The Sideways logs */
        int log_y = fruit_tree.getY() + 3;
        fruit_tree.getTreeWorld().placeTreePart(fruit_tree.getX() + 1, log_y, fruit_tree.getZ(), fruit_tree.getType().getLogId(), (short) (fruit_tree.getType().getLogData() + 4));
        fruit_tree.getTreeWorld().placeTreePart(fruit_tree.getX() - 1, log_y, fruit_tree.getZ(), fruit_tree.getType().getLogId(), (short) (fruit_tree.getType().getLogData() + 4));
        fruit_tree.getTreeWorld().placeTreePart(fruit_tree.getX(), log_y, fruit_tree.getZ() + 1, fruit_tree.getType().getLogId(), (short) (fruit_tree.getType().getLogData() + 8));
        fruit_tree.getTreeWorld().placeTreePart(fruit_tree.getX(), log_y, fruit_tree.getZ() - 1, fruit_tree.getType().getLogId(), (short) (fruit_tree.getType().getLogData() + 8));
    }

    private final void leaves_layer1(){
        /* Top Down View
         * Leaves: # Logs: @
         *  ABCDE
         * 1#####
         * 2#####
         * 3##@##
         * 4#####
         * 5##### 
         */
        int layer_y = fruit_tree.getY() + 2;
        placeLeavesAt(fruit_tree.getX() - 2, layer_y, fruit_tree.getZ() - 2); //A-1
        placeLeavesAt(fruit_tree.getX() - 2, layer_y, fruit_tree.getZ() - 1); //A-2
        placeLeavesAt(fruit_tree.getX() - 2, layer_y, fruit_tree.getZ()); //A-3
        placeLeavesAt(fruit_tree.getX() - 2, layer_y, fruit_tree.getZ() + 1); //A-4
        placeLeavesAt(fruit_tree.getX() - 2, layer_y, fruit_tree.getZ() + 2); //A-5
        placeLeavesAt(fruit_tree.getX() - 1, layer_y, fruit_tree.getZ() - 2); //B-1
        placeLeavesAt(fruit_tree.getX() - 1, layer_y, fruit_tree.getZ() - 1); //B-2
        placeLeavesAt(fruit_tree.getX() - 1, layer_y, fruit_tree.getZ()); //B-3
        placeLeavesAt(fruit_tree.getX() - 1, layer_y, fruit_tree.getZ() + 1); //B-4
        placeLeavesAt(fruit_tree.getX() - 1, layer_y, fruit_tree.getZ() + 2); //B-5
        placeLeavesAt(fruit_tree.getX(), layer_y, fruit_tree.getZ() - 2); //C-1
        placeLeavesAt(fruit_tree.getX(), layer_y, fruit_tree.getZ() - 1); //C-2
        placeLeavesAt(fruit_tree.getX(), layer_y, fruit_tree.getZ() + 1); //C-4
        placeLeavesAt(fruit_tree.getX(), layer_y, fruit_tree.getZ() + 2); //C-5
        placeLeavesAt(fruit_tree.getX() + 1, layer_y, fruit_tree.getZ() - 2); //D-1
        placeLeavesAt(fruit_tree.getX() + 1, layer_y, fruit_tree.getZ() - 1); //D-2
        placeLeavesAt(fruit_tree.getX() + 1, layer_y, fruit_tree.getZ()); //D-3
        placeLeavesAt(fruit_tree.getX() + 1, layer_y, fruit_tree.getZ() + 1); //D-4
        placeLeavesAt(fruit_tree.getX() + 1, layer_y, fruit_tree.getZ() + 2); //D-5
        placeLeavesAt(fruit_tree.getX() + 2, layer_y, fruit_tree.getZ() - 2); //E-1
        placeLeavesAt(fruit_tree.getX() + 2, layer_y, fruit_tree.getZ() - 1); //E-2
        placeLeavesAt(fruit_tree.getX() + 2, layer_y, fruit_tree.getZ()); //E-3
        placeLeavesAt(fruit_tree.getX() + 2, layer_y, fruit_tree.getZ() + 1); //E-4
        placeLeavesAt(fruit_tree.getX() + 2, layer_y, fruit_tree.getZ() + 2); //E-5
    }

    private final void leaves_layer2(){
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

    private final void leaves_layer3(){
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

    private final void leaves_layer4(){
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

    private final void placeLeavesAt(int x, int y, int z){
        fruit_tree.getTreeWorld().placeTreePart(x, y, z, fruit_tree.getType().getLeavesId(), fruit_tree.getType().getLeavesData());
    }
}
