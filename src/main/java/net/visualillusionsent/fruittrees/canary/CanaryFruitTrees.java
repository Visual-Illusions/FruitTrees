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

import net.canarymod.Canary;
import net.canarymod.api.world.World;
import net.canarymod.commandsys.CommandDependencyException;
import net.canarymod.logger.CanaryLevel;
import net.visualillusionsent.fruittrees.FruitTrees;
import net.visualillusionsent.fruittrees.FruitTreesConfigurations;
import net.visualillusionsent.fruittrees.TreeTracker;
import net.visualillusionsent.fruittrees.TreeType;
import net.visualillusionsent.fruittrees.TreeWorld;
import net.visualillusionsent.fruittrees.data.MySQLTreeStorage;
import net.visualillusionsent.fruittrees.data.SQLiteTreeStorage;
import net.visualillusionsent.fruittrees.data.TreeStorage;
import net.visualillusionsent.fruittrees.data.XMLTreeStorage;
import net.visualillusionsent.fruittrees.trees.FruitTree;
import net.visualillusionsent.minecraft.plugin.canary.VisualIllusionsCanaryPlugin;

import java.util.logging.Level;

public class CanaryFruitTrees extends VisualIllusionsCanaryPlugin implements FruitTrees {

    private final CanaryTreeWorldCache world_cache = new CanaryTreeWorldCache(this);
    private TreeStorage storage;
    private FruitTreesConfigurations ft_cfg;
    private static CanaryFruitTrees $;

    public CanaryFruitTrees() {
        super();
        getLogman().setLevel(Level.ALL);
    }

    @Override
    public boolean enable() {
        $ = this;
        checkStatus();
        checkVersion();
        ft_cfg = new FruitTreesConfigurations(this);
        SeedGen.genAll();
        if (ft_cfg.isMySQL()) {
            try {
                storage = new MySQLTreeStorage(this);
            } catch (Exception ex) {
                getLogman().log(Level.SEVERE, "Failed to initialize MySQL Data storage...", ex);
                disable();
                return false;
            }
        } else if (ft_cfg.isSQLite()) {
            try {
                storage = new SQLiteTreeStorage(this);
            } catch (Exception ex) {
                getLogman().log(Level.SEVERE, "Failed to initialize SQLite Data storage...", ex);
                disable();
                return false;
            }
        } else {
            try {
                storage = new XMLTreeStorage(this);
            } catch (Exception ex) {
                getLogman().log(Level.SEVERE, "Failed to initialize XML Data storage...", ex);
                disable();
                return false;
            }
        }
        for (World world : Canary.getServer().getWorldManager().getAllWorlds()) {
            world_cache.setExistingWorlds(new CanaryTreeWorld(this, world, world.getFqName()));
            storage.loadTreesForWorld(world_cache.getTreeWorld(world.getFqName()));
        }
        new CanaryFruitTreesListener(this);
        try {
            new FruitTreesCommands(this);
        } catch (CommandDependencyException e) {
            getLogman().logWarning("Failed to register FruitTrees information command.");
        }
        return true;
    }

    @Override
    public void disable() {
        $ = null;
        SeedGen.clearSeeds();
    }

    public TreeWorld getWorldForName(String name) {
        return world_cache.getTreeWorld(name);
    }

    protected void addLoadedWorld(World world) {
        TreeWorld tree_world = world_cache.getTreeWorld(world.getFqName());
        if (tree_world == null) {
            world_cache.setExistingWorlds(new CanaryTreeWorld(this, world, world.getFqName()));
            storage.loadTreesForWorld(world_cache.getTreeWorld(world.getFqName()));
        } else {
            tree_world.isLoaded();
        }
    }

    protected final void worldUnload(TreeWorld tree_world) {
        for (FruitTree tree : TreeTracker.getTreesInWorld(tree_world)) {
            storage.storeTree(tree);
        }
    }

    public static final CanaryFruitTrees instance() {
        return $;
    }

    public final void debug(String msg) {
        if (ft_cfg.debug()) {
            getLogman().log(CanaryLevel.PLUGIN_DEBUG, msg);
        }
    }

    public final void info(String msg) {
        getLogman().info(msg);
    }

    public final void warning(String msg) {
        getLogman().warning(msg);
    }

    public final void severe(String msg) {
        getLogman().severe(msg);
    }

    public final void severe(String msg, Throwable thrown) {
        getLogman().log(Level.SEVERE, msg, thrown);
    }

    public final FruitTreesConfigurations getFruitTreesConfig() {
        return this.ft_cfg;
    }

    public final TreeStorage getStorage() {
        return storage;
    }

    public boolean checkEnabled(TreeType type) {
        return ft_cfg.checkEnabled(type);
    }
}
