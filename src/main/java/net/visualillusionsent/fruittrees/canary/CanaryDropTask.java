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
import net.visualillusionsent.fruittrees.DropTask;

import java.util.Random;

final class CanaryDropTask extends ServerTask {
    private static final Random random = new Random();
    private final DropTask task;

    private CanaryDropTask(DropTask task, long delay) {
        super(CanaryFruitTrees.instance(), delay, false);
        this.task = task;
    }

    @Override
    public void run() {
        CanaryFruitTrees.instance().debug("Running Task..." + this);
        if (task.isValid()) {
            if (task.drop()) {
                CanaryFruitTrees.instance().debug("Dropping fruit from Tree: " + task.getTree());
            }
            scheduleDropTask(task);
        }
    }

    static boolean scheduleDropTask(DropTask task) {
        if (!task.isValid()) {
            return false;
        }
        long delay = random.nextInt(4800) + 1200;  //Between 1 minute and 5 minutes
        boolean toRet = Canary.getServer().addSynchronousTask(new CanaryDropTask(task, delay));
        CanaryFruitTrees.instance().debug("Dropping fruit from Tree: " + task.getTree() + " in " + (delay / 20) + " seconds");
        return toRet;
    }
}
