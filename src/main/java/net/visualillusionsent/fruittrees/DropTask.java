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

public final class DropTask{

    private final FruitTree tree;

    public DropTask(FruitTree tree){
        this.tree = tree;
    }

    public final void drop(){
        tree.dropFruit();
    }

    public final boolean isValid(){
        FruitTree test_tree = TreeTracker.getTreeAt(tree.getX(), tree.getY(), tree.getZ(), tree.getTreeWorld());
        if (test_tree != null) {
            if (test_tree.isGrown()) {
                return tree.isStillValid();
            }
        }
        return false;
    }
}
