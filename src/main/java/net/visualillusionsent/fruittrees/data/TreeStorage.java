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
package net.visualillusionsent.fruittrees.data;

import net.visualillusionsent.fruittrees.FruitTrees;
import net.visualillusionsent.fruittrees.TreeDeathReason;
import net.visualillusionsent.fruittrees.TreeWorld;
import net.visualillusionsent.fruittrees.trees.FruitTree;

public abstract class TreeStorage {

    protected final FruitTrees fruit_trees;

    public TreeStorage(FruitTrees fruit_trees) {
        this.fruit_trees = fruit_trees;
    }

    public abstract boolean storeTree(FruitTree tree);

    public abstract void removeTree(FruitTree tree, TreeDeathReason reason);

    public abstract boolean loadTreesForWorld(TreeWorld tree_world);

}
