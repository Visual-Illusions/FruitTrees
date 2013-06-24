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

import java.util.ArrayList;

public final class TreeTracker{

    private final static TreeTracker $ = new TreeTracker();
    private final ArrayList<FruitTree> fruit_trees = new ArrayList<FruitTree>();

    static final void trackTree(FruitTree fruit_tree){
        $.fruit_trees.add(fruit_tree);

    }

    static final void untrackTree(FruitTree fruit_tree){
        $.fruit_trees.remove(fruit_tree);
    }

    public final static FruitTree getTreeAt(int x, int y, int z, TreeWorld tree_world){
        for (FruitTree fruit_tree : $.fruit_trees) {
            if (fruit_tree.isBaseAt(x, y, z, tree_world)) {
                return fruit_tree;
            }
        }
        return null;
    }

    public static final FruitTree isTreeArea(int x, int y, int z, int id, int data, TreeWorld tree_world){
        for (FruitTree fruit_tree : $.fruit_trees) {
            if (fruit_tree.isBaseAt(x, y, z, tree_world)) {
                return fruit_tree;
            }
            else if (fruit_tree.isInArea(x, y, z, id, data, tree_world)) {
                return fruit_tree;
            }
        }
        return null;
    }

    public static final ArrayList<FruitTree> getTreesInWorld(TreeWorld tree_world){
        ArrayList<FruitTree> trees = new ArrayList<FruitTree>();
        for (FruitTree tree : $.fruit_trees) {
            if (tree.getTreeWorld() == tree_world) {
                trees.add(tree);
            }
        }
        return trees;
    }
}
