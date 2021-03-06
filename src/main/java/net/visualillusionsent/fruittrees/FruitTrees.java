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
package net.visualillusionsent.fruittrees;

import net.visualillusionsent.fruittrees.data.TreeStorage;
import net.visualillusionsent.utils.PropertiesFile;

public interface FruitTrees {

    public TreeWorld getWorldForName(String name);

    public PropertiesFile getConfig();

    public void debug(String msg);

    public void info(String msg);

    public void warning(String msg);

    public void severe(String msg);

    public void severe(String msg, Throwable thrown);

    public FruitTreesConfigurations getFruitTreesConfig();

    public TreeStorage getStorage();

}
