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
import net.canarymod.api.world.World;
import net.canarymod.api.world.blocks.Block;
import net.canarymod.api.world.blocks.BlockType;
import net.visualillusionsent.fruittrees.DropTask;
import net.visualillusionsent.fruittrees.TreeWorld;
import net.visualillusionsent.fruittrees.trees.FruitTree;

public final class CanaryTreeWorld implements TreeWorld {

    private final CanaryFruitTrees cft;
    private World world;
    private final String known_world_name;

    public CanaryTreeWorld(CanaryFruitTrees cft, World world, String known_world_name) {
        this.cft = cft;
        this.world = world;
        this.known_world_name = known_world_name;
    }

    public final void dropFruit(int x, int y, int z, int count, short id, short data) {
        cft.debug(String.format("Dropping Fruit @ X:%d Y:%d Z:%d in the amount of %d", x, y, z, count));
        world.dropItem(x, y, z, id, count, data);
    }

    public final boolean isClear(int x, int y, int z) {
        return world.getBlockAt(x, y, z).isAir();
    }

    public final void placeTreePart(int x, int y, int z, String blockName) {
        world.setBlockAt(x, y, z, BlockType.fromString(blockName));
    }

    public final void scheduleDrop(DropTask task) {
        cft.debug("Scheduling Task: " + task + " Scheduled?: " + CanaryDropTask.scheduleDropTask(task));
    }

    public final boolean isTreePart(int x, int y, int z, String blockName) {
        Block block = world.getBlockAt(x, y, z);
        return block.getType().getMachineName().equals(blockName);
    }

    public final boolean isLoaded() {
        if (world == null) {
            boolean loaded = Canary.getServer().getWorldManager().worldIsLoaded(known_world_name);
            if (loaded) {
                world = Canary.getServer().getWorldManager().getWorld(known_world_name, false);
                return true;
            }
        }
        return Canary.getServer().getWorldManager().worldIsLoaded(world.getFqName());
    }

    public final boolean isAreaLoaded(FruitTree fruit_tree) {
        return this.isLoaded() && world.isChunkLoaded(fruit_tree.getX(), fruit_tree.getY(), fruit_tree.getZ());
    }

    public final String getName() {
        return world.getFqName();
    }

    public final void unloadWorld() {
        cft.worldUnload(this);
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        else if (obj instanceof CanaryTreeWorld) {
            return ((CanaryTreeWorld) obj).world.equals(world);
        }
        else if (obj instanceof World) {
            return obj.equals(world);
        }
        return false;
    }

    public final String toString() {
        return String.format("CanaryTreeWorld[WorldName:%s]", world != null ? world.getFqName() : "null");
    }
}
