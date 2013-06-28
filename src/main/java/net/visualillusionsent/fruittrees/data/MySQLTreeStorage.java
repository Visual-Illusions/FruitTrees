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
package net.visualillusionsent.fruittrees.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import net.visualillusionsent.fruittrees.FruitTree;
import net.visualillusionsent.fruittrees.FruitTrees;
import net.visualillusionsent.fruittrees.TreeType;
import net.visualillusionsent.fruittrees.TreeWorld;

public class MySQLTreeStorage extends TreeStorage {

    private final String tree_table = "FruitTrees";
    private Connection conn;

    public MySQLTreeStorage(FruitTrees fruit_trees) throws SQLException {
        super(fruit_trees);
        checkOrGenTable();
    }

    private final void checkOrGenTable() throws SQLException {
        PreparedStatement ps = conn.prepareStatement("CREATE TABLE IF NOT EXISTS " + tree_table + " (Type VARCHAR(30), X INT, Y INT, Z INT, TreeWorld VARCHAR(64))");
        ps.execute();
    }

    @Override
    public boolean storeTree(FruitTree tree) {
        SQLException sqlex = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            testConnection();
            ps = conn.prepareStatement("INSERT INTO " + tree_table + " (Type,X,Y,Z,TreeWorld) VALUES(?,?,?,?,?)");
            ps.setString(1, tree.getType().name());
            ps.setInt(2, tree.getX());
            ps.setInt(3, tree.getY());
            ps.setInt(4, tree.getZ());
            ps.setString(5, tree.getTreeWorld().getName());
        }
        catch (SQLException sqle) {
            sqlex = sqle;
        }
        finally {
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
                if (ps != null && !ps.isClosed()) {
                    ps.close();
                }
            }
            catch (Exception e) {}
            if (sqlex != null) {
                fruit_trees.severe("Failed to store Tree: ".concat(tree.toString()), sqlex);
                return false;
            }
        }
        return true;
    }

    @Override
    public void removeTree(FruitTree tree) {
        SQLException sqlex = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            testConnection();
            ps = conn.prepareStatement("DELETE FROM " + tree_table + " WHERE X=? AND Y=? AND Z=? AND TreeWorld=?");
            ps.setInt(1, tree.getX());
            ps.setInt(2, tree.getY());
            ps.setInt(3, tree.getZ());
            ps.setString(4, tree.getTreeWorld().getName());
        }
        catch (SQLException sqle) {
            sqlex = sqle;
        }
        finally {
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
                if (ps != null && !ps.isClosed()) {
                    ps.close();
                }
            }
            catch (Exception e) {}
            if (sqlex != null) {
                fruit_trees.severe("Failed to remove Tree: ".concat(tree.toString()), sqlex);
            }
        }
    }

    @Override
    public boolean loadTreesForWorld(TreeWorld tree_world) {
        SQLException sqlex = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int load = 0;
        try {
            testConnection();
            ps = conn.prepareStatement("SELECT * FROM " + tree_table + " WHERE TreeWorld=" + tree_world.getName());
            rs = ps.executeQuery();
            while (rs.next()) {
                String type = null;
                Integer X = null, Y = null, Z = null;
                try {
                    type = rs.getString("Type");
                    X = rs.getInt("X");
                    Y = rs.getInt("Y");
                    Z = rs.getInt("Z");
                    TreeType.valueOf(type).newFruitTree(fruit_trees, X.intValue(), Y.intValue(), Z.intValue(), tree_world);
                    load++;
                }
                catch (Exception ex) {
                    fruit_trees.warning(String.format("Unable to initialize tree: Type=%s X=%s Y=%s Z=%s TreeWorld=%s.",
                        type, X, Y, Z, tree_world));
                    continue;
                }
            }
        }
        catch (SQLException sqle) {
            sqlex = sqle;
        }
        finally {
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
                if (ps != null && !ps.isClosed()) {
                    ps.close();
                }
            }
            catch (Exception e) {}
            if (sqlex != null) {
                fruit_trees.severe("Failed to load trees for TreeWorld: ".concat(tree_world.toString()), sqlex);
                return false;
            }
        }
        fruit_trees.info(String.format("Loaded %d Trees for TreeWorld: %s", load, tree_world));
        return true;
    }

    private final void testConnection() throws SQLException {
        if (conn == null || conn.isClosed() || !conn.isValid(1)) {
            conn = DriverManager.getConnection("jdbc:mysql://" + fruit_trees.getFruitTreesConfig().getSQL_URL(), fruit_trees.getFruitTreesConfig().getSQL_User(), fruit_trees.getFruitTreesConfig().getSQL_Password());
        }
    }
}
