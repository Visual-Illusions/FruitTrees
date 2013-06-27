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

import java.util.Random;
import net.canarymod.Canary;
import net.canarymod.tasks.ServerTask;
import net.visualillusionsent.fruittrees.DropTask;

public final class CanaryDropTask extends ServerTask{

    private static final Random random = new Random();
    private static final CanaryFruitTrees cft = CanaryFruitTrees.instance();
    private final DropTask task;

    public CanaryDropTask(DropTask task){
        super(cft, random.nextInt(540000) + 60000, false); //Between 1 minute and 5 minutes
        this.task = task;
        Canary.getServer().addSynchronousTask(this);
    }

    @Override
    public void run(){
        if (task.isValid()) {
            task.drop();
            new CanaryDropTask(task);
        }
    }
}
