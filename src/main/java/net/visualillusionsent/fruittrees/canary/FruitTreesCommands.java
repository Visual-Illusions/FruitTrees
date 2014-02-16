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

import net.canarymod.chat.MessageReceiver;
import net.canarymod.commandsys.Command;
import net.canarymod.commandsys.CommandDependencyException;
import net.visualillusionsent.minecraft.plugin.canary.VisualIllusionsCanaryPluginInformationCommand;

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
            toolTip = "FruitTrees Information Command"
    )
    public final void infoCommand(MessageReceiver msgrec, String[] args) {
        super.sendInformation(msgrec);
    }
}
