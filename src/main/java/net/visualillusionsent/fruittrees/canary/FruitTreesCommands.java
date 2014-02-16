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

import net.canarymod.api.entity.living.humanoid.Player;
import net.canarymod.api.world.position.Vector3D;
import net.canarymod.chat.MessageReceiver;
import net.canarymod.commandsys.Command;
import net.canarymod.commandsys.CommandDependencyException;
import net.visualillusionsent.fruittrees.TreeTracker;
import net.visualillusionsent.fruittrees.trees.FruitTree;
import net.visualillusionsent.minecraft.plugin.canary.VisualIllusionsCanaryPluginInformationCommand;

import java.util.List;

/**
 * Fruit Trees Commands
 *
 * @author Jason (darkdiplomat)
 */
public final class FruitTreesCommands extends VisualIllusionsCanaryPluginInformationCommand {

    public FruitTreesCommands(CanaryFruitTrees fruit_trees) throws CommandDependencyException {
        super(fruit_trees);
        fruit_trees.registerCommands(this, false);
    }

    @Command(
            aliases = { "fruittrees" },
            description = "Displays plugin information",
            permissions = { "" },
            toolTip = "/fruittrees"
    )
    public final void infoCommand(MessageReceiver msgrec, String[] args) {
        super.sendInformation(msgrec);
    }

    @Command(
            aliases = { "debug" },
            description = "Drop Debugger",
            permissions = { "fruittrees.debug" },
            toolTip = "/fruittrees debug",
            parent = "fruittrees"
    )
    public final void forceDrop(MessageReceiver msgrec, String[] args) {
        if (msgrec instanceof Player) {
            Player player = (Player) msgrec;
            List<FruitTree> trees = TreeTracker.getTreesInWorld(((CanaryFruitTrees) getPlugin()).getWorldForName(player.getWorld().getFqName()));
            FruitTree closest = null;
            double dist = 0;
            for (FruitTree tree : trees) {
                if (closest == null) {
                    closest = tree;
                    dist = new Vector3D(tree.getX(), tree.getY(), tree.getZ()).getDistance(player.getLocation());
                }
                else {
                    double test = new Vector3D(tree.getX(), tree.getY(), tree.getZ()).getDistance(player.getLocation());
                    if (test < dist) {
                        closest = tree;
                        dist = test;
                    }
                }
            }
            player.message("Grown?: " + closest.isGrown() + " Valid?: " + closest.isStillValid());
            closest.dropFruit();
        }
    }
}
