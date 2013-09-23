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
package net.visualillusionsent.fruittrees.canary;

import net.canarymod.chat.Colors;
import net.canarymod.chat.MessageReceiver;
import net.canarymod.commandsys.Command;
import net.visualillusionsent.utils.VersionChecker;

/**
 * Fruit Trees Commands
 *
 * @author Jason (darkdiplomat)
 */
public final class FruitTreesCommands extends VisualIllusionsCanaryPluginInformationCommand {

    public FruitTreesCommands(CanaryFruitTrees fruit_trees) {
        super(fruit_trees);
    }

    @Command(aliases = {"fruittrees"},
            description = "Displays plugin information",
            permissions = {""},
            toolTip = "FruitTrees Information Command")
    public final void infoCommand(MessageReceiver msgrec, String[] args) {
        for (String msg : about) {
            if (msg.equals("$VERSION_CHECK$")) {
                VersionChecker vc = plugin.getVersionChecker();
                Boolean islatest = vc.isLatest();
                if (islatest == null) {
                    msgrec.message(center(Colors.GRAY + "VersionCheckerError: " + vc.getErrorMessage()));
                }
                else if (!islatest) {
                    msgrec.message(center(Colors.GRAY + vc.getUpdateAvailibleMessage()));
                }
                else {
                    msgrec.message(center(Colors.LIGHT_GREEN + "Latest Version Installed"));
                }
            }
            else {
                msgrec.message(msg);
            }
        }
    }
}
