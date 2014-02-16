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
package net.visualillusionsent.fruittrees.data;

import net.visualillusionsent.fruittrees.FruitTrees;
import net.visualillusionsent.fruittrees.TreeType;
import net.visualillusionsent.fruittrees.TreeWorld;
import net.visualillusionsent.fruittrees.trees.FruitTree;
import net.visualillusionsent.utils.SystemUtils;
import org.jdom2.Comment;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public final class XMLTreeStorage extends TreeStorage {

    private final String directory = "config/FruitTrees/trees/", file = "%world_name%_trees.xml";
    private final Format xmlform = Format.getPrettyFormat().setExpandEmptyElements(false).setOmitDeclaration(true).setOmitEncoding(true).setLineSeparator(SystemUtils.LINE_SEP);
    private final XMLOutputter outputter = new XMLOutputter(xmlform);
    private final SAXBuilder builder = new SAXBuilder();
    private FileWriter writer;

    public XMLTreeStorage(FruitTrees fruit_trees) {
        super(fruit_trees);
        File dir = new File(directory);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public final boolean storeTree(FruitTree tree) {
        fruit_trees.info(String.format("Storing Tree: %s", tree));
        String world_file = file.replace("%world_name%", tree.getTreeWorld().getName());
        File worldFile = new File(directory.concat(world_file));
        Exception ex = null;
        if (!worldFile.exists()) {
            genDefaultWorldFile(worldFile);
        }
        try {
            Document doc = builder.build(worldFile);
            Element root = doc.getRootElement();
            Element fruitTree = new Element("fruittree");
            fruitTree.setAttribute("X", String.valueOf(tree.getX()));
            fruitTree.setAttribute("Y", String.valueOf(tree.getY()));
            fruitTree.setAttribute("Z", String.valueOf(tree.getZ()));
            fruitTree.setAttribute("Type", tree.getType().toString());
            root.addContent(fruitTree);

            writer = new FileWriter(worldFile);
            outputter.output(root, writer);
        }
        catch (IOException e) {
            ex = e;
        }
        catch (JDOMException jdome) {
            ex = jdome;
        }
        finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            }
            catch (IOException e) {
            }
            writer = null;
            if (ex != null) {
                fruit_trees.severe("Failed to save tree: " + tree.toString(), ex);
                return false;
            }
        }
        fruit_trees.info("Tree stored successfully");
        return true;
    }

    public final void removeTree(FruitTree tree) {
        fruit_trees.info(String.format("Killing Tree: %s", tree));
        String world_file = file.replace("%world_name%", tree.getTreeWorld().getName());
        File worldFile = new File(directory.concat(world_file));
        Exception ex = null;
        if (!worldFile.exists()) {
            genDefaultWorldFile(worldFile);
        }
        else {
            try {
                Document root = builder.build(worldFile);
                Element fruittrees = root.getRootElement();
                List<Element> trees = fruittrees.getChildren();
                for (Element tree_element : trees) {
                    if (tree.equalsElement(tree_element)) {
                        tree_element.detach();
                        writer = new FileWriter(worldFile);
                        outputter.output(root, writer);
                        break;
                    }
                }
            }
            catch (IOException e) {
                ex = e;
            }
            catch (JDOMException jdome) {
                ex = jdome;
            }
            finally {
                try {
                    if (writer != null) {
                        writer.close();
                    }
                }
                catch (IOException e) {
                }
                writer = null;
                if (ex != null) {
                    fruit_trees.severe("Failed to remove tree: " + tree.toString(), ex);
                }
            }
        }
        fruit_trees.info("Tree killed successfully");
    }

    public final boolean loadTreesForWorld(TreeWorld tree_world) {
        fruit_trees.info(String.format("Loading Trees for TreeWorld: %s", tree_world));
        String world_file = file.replace("%world_name%", tree_world.getName());
        File worldFile = new File(directory.concat(world_file));
        Exception ex = null;
        int load = 0;
        if (!worldFile.exists()) {
            return genDefaultWorldFile(worldFile);
        }
        else {
            try {
                Document root = builder.build(worldFile);
                Element fruittrees = root.getRootElement();
                List<Element> trees = fruittrees.getChildren();
                for (Element tree : trees) {
                    try {
                        TreeType.valueOf(tree.getAttributeValue("Type")).newFruitTree(fruit_trees,
                                Integer.valueOf(tree.getAttributeValue("X")).intValue(),
                                Integer.valueOf(tree.getAttributeValue("Y")).intValue(),
                                Integer.valueOf(tree.getAttributeValue("Z")).intValue(),
                                tree_world);
                        load++;
                    }
                    catch (Exception exc) {
                        fruit_trees.warning(String.format("Unable to initialize tree: Type=%s X=%s Y=%s Z=%s TreeWorld=%s.",
                                tree.getAttribute("Type") != null ? tree.getAttributeValue("Type") : null,
                                tree.getAttribute("X") != null ? tree.getAttributeValue("X") : null,
                                tree.getAttribute("Y") != null ? tree.getAttributeValue("Y") : null,
                                tree.getAttribute("Z") != null ? tree.getAttributeValue("Z") : null,
                                tree_world));
                        continue;
                    }
                }
            }
            catch (IOException ioex) {
                ex = ioex;
            }
            catch (JDOMException jdome) {
                ex = jdome;
            }
            finally {
                if (ex != null) {
                    fruit_trees.severe("Failed to load trees for TreeWorld: ".concat(tree_world.toString()), ex);
                    return false;
                }
            }
        }
        fruit_trees.info(String.format("Loaded %d Trees for TreeWorld: %s", load, tree_world));
        return true;
    }

    private boolean genDefaultWorldFile(File worldFile) {
        Exception ex = null;
        Element fruittrees = new Element("fruittrees");
        Document root = new Document(fruittrees);
        fruittrees.addContent(new Comment("Modifing this file while server is running may cause issues or inconsistancies!"));
        try {
            writer = new FileWriter(worldFile);
            outputter.output(root, writer);
        }
        catch (IOException e) {
            ex = e;
        }
        finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            }
            catch (IOException e) {
            }
            writer = null;
            if (ex != null) {
                fruit_trees.severe("Failed to generate world file", ex);
                return false;
            }
        }
        return true;
    }
}
