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

public interface TreeWorld{

    void dropFruit(int x, int y, int z, int count, short id, short data);

    boolean isClear(int x, int y, int z);

    void placeTreePart(int x, int y, int z, short type, short data);

    void scheduleDrop(DropTask task);

    boolean isTreePart(int x, int y, int z, short part_id, short s);

    boolean isLoaded();

    String getName();

    boolean isAreaLoaded(FruitTree fruit_tree);

    void unloadWorld();
}
