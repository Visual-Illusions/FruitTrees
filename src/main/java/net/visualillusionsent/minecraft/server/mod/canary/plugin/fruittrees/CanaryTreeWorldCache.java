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
package net.visualillusionsent.minecraft.server.mod.canary.plugin.fruittrees;

import java.util.concurrent.ConcurrentHashMap;
import net.canarymod.Canary;
import net.visualillusionsent.fruittrees.TreeWorld;
import net.visualillusionsent.fruittrees.TreeWorldCache;

public final class CanaryTreeWorldCache extends TreeWorldCache{

    private final ConcurrentHashMap<String, TreeWorld> tree_worlds = new ConcurrentHashMap<String, TreeWorld>();

    public CanaryTreeWorldCache(CanaryFruitTrees fruit_trees){
        super(fruit_trees);
    }

    public final TreeWorld getTreeWorld(String world_name){
        if (tree_worlds.contains(world_name)) {
            return tree_worlds.get(world_name);
        }
        else if (Canary.getServer().getWorldManager().worldIsLoaded(world_name)) {
            return tree_worlds.putIfAbsent(world_name, new CanaryTreeWorld((CanaryFruitTrees) fruit_trees, Canary.getServer().getWorld(world_name), world_name));
        }
        return null;
    }

    protected final void setExistingWorlds(CanaryTreeWorld canaryTreeWorld){
        tree_worlds.putIfAbsent(canaryTreeWorld.getName(), canaryTreeWorld);
    }
}
