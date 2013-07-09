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
package net.visualillusionsent.fruittrees;

import net.visualillusionsent.utils.PropertiesFile;
import net.visualillusionsent.utils.UtilityException;

public final class FruitTreesConfigurations {

    private final PropertiesFile cfg;
    private final String[] dyes = new String[] { "Black", "Red", "Green", "Brown", "Blue", "Purple", "Cyan", "Light_Gray", "Gray", "Pink", "Lime", "Yellow", "Light_Blue", "Magenta", "Orange", "White" };

    public FruitTreesConfigurations(FruitTrees fruit_trees) {
        this.cfg = fruit_trees.getConfig();
        checkConfig();
    }

    private final void checkConfig() {
        // Debug Log
        if (!cfg.containsKey("debug.log.enabled")) {
            cfg.setString("debug.log.enabled", "no");
            cfg.addComment("debug.log.enabled", "Sets whether Debug Logging is enabled or not");
        }
        // Datasoure type
        if (!cfg.containsKey("datasource.type")) {
            cfg.setString("datasource.type", "xml");
            cfg.addComment("datasource.type", "Sets the Datasource to use (XML or MYSQL)");
        }
        // SQL URL
        if (!cfg.containsKey("sql.url")) { //jdbc:mysql://
            cfg.setString("sql.url", "my.sql.serv");
            cfg.addComment("sql.url", "The URL to the SQL database (no jdbc:mysql:// required)");
        }
        // SQL User
        if (!cfg.containsKey("sql.user")) {
            cfg.setString("sql.user", "my.sql.user");
            cfg.addComment("sql.user", "The user name of the SQL database");
        }
        // SQL Password
        if (!cfg.containsKey("sql.password")) {
            cfg.setString("sql.password", "my.sql.pass");
            cfg.addComment("sql.password", "The password of the SQL database (forwarded to DarkDiplomat [lol just kidding])");
        }
        //Header
        if (cfg.getHeaderLines().isEmpty()) {
            cfg.addHeaderLines("FruitTrees Configuration File", "Copyright (C) 2013 Visual Illusions Entertainment");
        }
        // Apple Tree
        if (!cfg.containsKey("apple.tree.enabled")) {
            cfg.setString("apple.tree.enabled", "yes");
            cfg.addComment("apple.tree.enabled", "Sets whether Apple Trees are enabled or not");
        }
        // Golden Apple Tree
        if (!cfg.containsKey("golden_apple.tree.enabled")) {
            cfg.setString("golden_apple.tree.enabled", "yes");
            cfg.addComment("golden_apple.tree.enabled", "Sets whether Golden Apple Trees are enabled or not");
        }
        // Sponge Tree
        if (!cfg.containsKey("sponge.tree.enabled")) {
            cfg.setString("sponge.tree.enabled", "yes");
            cfg.addComment("sponge.tree.enabled", "Sets whether Sponge Trees are enabled or not");
        }
        // Record Tree
        if (!cfg.containsKey("record.tree.enabled")) {
            cfg.setString("record.tree.enabled", "yes");
            cfg.addComment("record.tree.enabled", "Sets whether Record Trees are enabled or not");
        }
        // Redstone Tree
        if (!cfg.containsKey("redstone.tree.enabled")) {
            cfg.setString("redstone.tree.enabled", "yes");
            cfg.addComment("redstone.tree.enabled", "Sets whether Redstone Trees are enabled or not");
        }
        // Dye Trees
        for (int index = 0; index <= 15; index++) {
            String key = String.format("%s.dye.tree.enabled", dyes[index].toLowerCase());
            if (!cfg.containsKey(key)) {
                cfg.setString(key, "yes");
                cfg.addComment(key, String.format("Sets whether %s Dye Trees are enabled or not", dyes[index]));
            }
        }
        //Iron Tree
        if (!cfg.containsKey("iron.tree.enabled")) {
            cfg.setString("iron.tree.enabled", "yes");
            cfg.addComment("iron.tree.enabled", "Sets whether Iron Trees are enabled or not");
        }
        //Gold Tree
        if (!cfg.containsKey("gold.tree.enabled")) {
            cfg.setString("gold.tree.enabled", "yes");
            cfg.addComment("gold.tree.enabled", "Sets whether Gold Trees are enabled or not");
        }
        //Diamond Tree
        if (!cfg.containsKey("diamond.tree.enabled")) {
            cfg.setString("diamond.tree.enabled", "yes");
            cfg.addComment("diamond.tree.enabled", "Sets whether Diamond Trees are enabled or not");
        }
        //Emerald Tree
        if (!cfg.containsKey("emerald.tree.enabled")) {
            cfg.setString("emerald.tree.enabled", "yes");
            cfg.addComment("emerald.tree.enabled", "Sets whether Emerald Trees are enabled or not");
        }
        //Coal Tree
        if (!cfg.containsKey("coal.tree.enabled")) {
            cfg.setString("coal.tree.enabled", "yes");
            cfg.addComment("coal.tree.enabled", "Sets whether Coal Trees are enabled or not");
        }
        cfg.save();
    }

    public final boolean checkEnabled(TreeType type) {
        try {
            switch (type) {
                case APPLE:
                case GOLDEN_APPLE:
                case SPONGE:
                case RECORD:
                case REDSTONE:
                case IRON:
                case GOLD:
                case DIAMOND:
                case EMERALD:
                case COAL:
                    return cfg.getBoolean(type.name().toLowerCase() + ".tree.enabled");
                case DYE_BLACK:
                case DYE_BLUE:
                case DYE_BROWN:
                case DYE_CYAN:
                case DYE_GRAY:
                case DYE_GREEN:
                case DYE_LIGHT_BLUE:
                case DYE_LIGHT_GRAY:
                case DYE_LIME:
                case DYE_MAGENTA:
                case DYE_ORANGE:
                case DYE_PINK:
                case DYE_PURPLE:
                case DYE_RED:
                case DYE_WHITE:
                case DYE_YELLOW:
                    return cfg.getBoolean(type.name().replace("DYE_", "").toLowerCase() + ".dye.tree.enabled");
                default:
                    return false;
            }
        }
        catch (UtilityException uex) {
            // Missing Property
            return false;
        }
    }

    public final boolean debug() {
        return cfg.getBoolean("debug.log.enabled");
    }

    public boolean isMySQL() {
        return cfg.getString("datasource.type").toLowerCase().equals("mysql");
    }

    public String getSQL_URL() {
        return cfg.getString("sql.url");
    }

    public String getSQL_User() {
        return cfg.getString("sql.user");
    }

    public String getSQL_Password() {
        return cfg.getString("sql.password");
    }
}
