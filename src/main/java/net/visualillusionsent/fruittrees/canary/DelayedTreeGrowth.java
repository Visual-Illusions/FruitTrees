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
package net.visualillusionsent.fruittrees.canary;

import net.canarymod.Canary;
import net.canarymod.tasks.ServerTask;
import net.visualillusionsent.fruittrees.trees.FruitTree;

public class DelayedTreeGrowth extends ServerTask {
    private final FruitTree tree;

    private DelayedTreeGrowth(FruitTree tree) {
        super(CanaryFruitTrees.instance(), 2, false);
        this.tree = tree;
    }

    public final void run() {
        tree.growTree();
    }

    static void schedule(FruitTree tree) {
        Canary.getServer().addSynchronousTask(new DelayedTreeGrowth(tree));
    }

}
