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

import net.canarymod.Canary;
import net.canarymod.api.world.World;
import net.canarymod.api.world.blocks.Block;
import net.visualillusionsent.minecraft.server.mod.fruittrees.DropTask;
import net.visualillusionsent.minecraft.server.mod.fruittrees.FruitTree;
import net.visualillusionsent.minecraft.server.mod.fruittrees.TreeWorld;

public final class CanaryTreeWorld implements TreeWorld{

    private final CanaryFruitTrees cft;
    private World world;
    private final String known_world_name;

    public CanaryTreeWorld(CanaryFruitTrees cft, World world, String known_world_name){
        this.cft = cft;
        this.world = world;
        this.known_world_name = known_world_name;
    }

    public final void dropFruit(int x, int y, int z, int count, short id, short data){
        world.dropItem(x, y, z, id, count, data);
    }

    public final boolean isClear(int x, int y, int z){
        return world.getBlockAt(x, y, z).isAir();
    }

    public final void placeTreePart(int x, int y, int z, short type, short data){
        world.setBlockAt(x, y, z, type, data);
    }

    public final void scheduleDrop(DropTask task){
        new CanaryDropTask(task);
    }

    public final boolean isTreePart(int x, int y, int z, short part_id, short part_data){
        Block block = world.getBlockAt(x, y, z);
        return block.getTypeId() == part_id && block.getData() == part_data;
    }

    public final boolean isLoaded(){
        if (world == null) {
            boolean loaded = Canary.getServer().getWorldManager().worldIsLoaded(known_world_name);
            if (loaded) {
                world = Canary.getServer().getWorldManager().getWorld(known_world_name, false);
                return true;
            }
        }
        return Canary.getServer().getWorldManager().worldIsLoaded(world.getFqName());
    }

    public final boolean isAreaLoaded(FruitTree fruit_tree){
        if (this.isLoaded()) {
            return world.isChunkLoaded(fruit_tree.getX(), fruit_tree.getY(), fruit_tree.getZ());
        }
        return false;
    }

    public final String getName(){
        return world.getFqName();
    }

    public final void unloadWorld(){
        this.world = null;
        cft.worldUnload(this);
    }

    public final boolean equals(Object obj){
        if (obj == this) {
            return true;
        }
        else if (obj instanceof CanaryTreeWorld) {
            return ((CanaryTreeWorld) obj).world.equals(world);
        }
        else if (obj instanceof World) {
            return ((World) obj).equals(world);
        }
        return false;
    }

    public final String toString(){
        return String.format("CanaryTreeWorld[WorldName:%s]", world != null ? world.getFqName() : "null");
    }
}
