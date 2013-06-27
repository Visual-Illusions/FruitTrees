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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import net.visualillusionsent.fruittrees.*;
import net.visualillusionsent.utils.SystemUtils;
import org.jdom2.Comment;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

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

    public boolean storeTree(FruitTree tree) {
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
            catch (IOException e) {}
            writer = null;
            if (ex != null) {
                fruit_trees.severe("Failed to save tree: " + tree.toString(), ex);
                return false;
            }
        }
        return true;
    }

    public void removeTree(FruitTree tree) {
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
                catch (IOException e) {}
                writer = null;
                if (ex != null) {
                    fruit_trees.severe("Failed to remove tree: " + tree.toString(), ex);
                }
            }
        }
    }

    public boolean loadTreesForWorld(TreeWorld tree_world) {
        String world_file = file.replace("%world_name%", tree_world.getName());
        File worldFile = new File(directory.concat(world_file));
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
                        // "0:Black", "1:Red", "2:Green", "3:Brown", "4:Blue", "5:Purple", "6:Cyan",
                        // "7:Light_Gray", "8:Gray", "9:Pink", "10:Lime", "11:Yellow", "12:Light_Blue",
                        // "13:Magenta", "14:Orange", "15:White"
                        switch (TreeType.valueOf(tree.getAttributeValue("Type"))) {
                            case APPLE:
                                if (fruit_trees.getFruitTreesConfig().checkEnabled(TreeType.APPLE)) {
                                    new AppleTree(fruit_trees,
                                        Integer.valueOf(tree.getAttributeValue("X")).intValue(),
                                        Integer.valueOf(tree.getAttributeValue("Y")).intValue(),
                                        Integer.valueOf(tree.getAttributeValue("Z")).intValue(),
                                        tree_world);
                                }
                                else {
                                    // Pass it down to the catch block
                                    throw new IllegalArgumentException("HerpDerp");
                                }
                                break;
                            case GOLDEN_APPLE:
                                if (fruit_trees.getFruitTreesConfig().checkEnabled(TreeType.GOLDEN_APPLE)) {
                                    new GoldenAppleTree(fruit_trees,
                                        Integer.valueOf(tree.getAttributeValue("X")).intValue(),
                                        Integer.valueOf(tree.getAttributeValue("Y")).intValue(),
                                        Integer.valueOf(tree.getAttributeValue("Z")).intValue(),
                                        tree_world);
                                }
                                else {
                                    // Pass it down to the catch block
                                    throw new IllegalArgumentException("HerpDerp");
                                }
                                break;
                            case RECORD:
                                if (fruit_trees.getFruitTreesConfig().checkEnabled(TreeType.RECORD)) {
                                    new RecordTree(fruit_trees,
                                        Integer.valueOf(tree.getAttributeValue("X")).intValue(),
                                        Integer.valueOf(tree.getAttributeValue("Y")).intValue(),
                                        Integer.valueOf(tree.getAttributeValue("Z")).intValue(),
                                        tree_world);
                                }
                                else {
                                    // Pass it down to the catch block
                                    throw new IllegalArgumentException("HerpDerp");
                                }
                                break;
                            case SPONGE:
                                if (fruit_trees.getFruitTreesConfig().checkEnabled(TreeType.SPONGE)) {
                                    new SpongeTree(fruit_trees,
                                        Integer.valueOf(tree.getAttributeValue("X")).intValue(),
                                        Integer.valueOf(tree.getAttributeValue("Y")).intValue(),
                                        Integer.valueOf(tree.getAttributeValue("Z")).intValue(),
                                        tree_world);
                                }
                                else {
                                    // Pass it down to the catch block
                                    throw new IllegalArgumentException("HerpDerp");
                                }
                                break;
                            case DYE_BLACK:
                                if (fruit_trees.getFruitTreesConfig().checkEnabled(TreeType.DYE_BLACK)) {
                                    new DyeTree(fruit_trees,
                                        Integer.valueOf(tree.getAttributeValue("X")).intValue(),
                                        Integer.valueOf(tree.getAttributeValue("Y")).intValue(),
                                        Integer.valueOf(tree.getAttributeValue("Z")).intValue(),
                                        tree_world,
                                        (byte) 0);
                                }
                                else {
                                    // Pass it down to the catch block
                                    throw new IllegalArgumentException("HerpDerp");
                                }
                                break;
                            case DYE_BLUE:
                                if (fruit_trees.getFruitTreesConfig().checkEnabled(TreeType.DYE_BLUE)) {
                                    new DyeTree(fruit_trees,
                                        Integer.valueOf(tree.getAttributeValue("X")).intValue(),
                                        Integer.valueOf(tree.getAttributeValue("Y")).intValue(),
                                        Integer.valueOf(tree.getAttributeValue("Z")).intValue(),
                                        tree_world,
                                        (byte) 4);
                                }
                                else {
                                    // Pass it down to the catch block
                                    throw new IllegalArgumentException("HerpDerp");
                                }
                                break;
                            case DYE_BROWN:
                                if (fruit_trees.getFruitTreesConfig().checkEnabled(TreeType.DYE_BROWN)) {
                                    new DyeTree(fruit_trees,
                                        Integer.valueOf(tree.getAttributeValue("X")).intValue(),
                                        Integer.valueOf(tree.getAttributeValue("Y")).intValue(),
                                        Integer.valueOf(tree.getAttributeValue("Z")).intValue(),
                                        tree_world,
                                        (byte) 3);
                                }
                                else {
                                    // Pass it down to the catch block
                                    throw new IllegalArgumentException("HerpDerp");
                                }
                                break;
                            case DYE_CYAN:
                                if (fruit_trees.getFruitTreesConfig().checkEnabled(TreeType.DYE_CYAN)) {
                                    new DyeTree(fruit_trees,
                                        Integer.valueOf(tree.getAttributeValue("X")).intValue(),
                                        Integer.valueOf(tree.getAttributeValue("Y")).intValue(),
                                        Integer.valueOf(tree.getAttributeValue("Z")).intValue(),
                                        tree_world,
                                        (byte) 6);
                                }
                                else {
                                    // Pass it down to the catch block
                                    throw new IllegalArgumentException("HerpDerp");
                                }
                                break;
                            case DYE_GRAY:
                                if (fruit_trees.getFruitTreesConfig().checkEnabled(TreeType.DYE_GRAY)) {
                                    new DyeTree(fruit_trees,
                                        Integer.valueOf(tree.getAttributeValue("X")).intValue(),
                                        Integer.valueOf(tree.getAttributeValue("Y")).intValue(),
                                        Integer.valueOf(tree.getAttributeValue("Z")).intValue(),
                                        tree_world,
                                        (byte) 8);
                                }
                                else {
                                    // Pass it down to the catch block
                                    throw new IllegalArgumentException("HerpDerp");
                                }
                                break;
                            case DYE_GREEN:
                                if (fruit_trees.getFruitTreesConfig().checkEnabled(TreeType.DYE_GREEN)) {
                                    new DyeTree(fruit_trees,
                                        Integer.valueOf(tree.getAttributeValue("X")).intValue(),
                                        Integer.valueOf(tree.getAttributeValue("Y")).intValue(),
                                        Integer.valueOf(tree.getAttributeValue("Z")).intValue(),
                                        tree_world,
                                        (byte) 2);
                                }
                                else {
                                    // Pass it down to the catch block
                                    throw new IllegalArgumentException("HerpDerp");
                                }
                                break;
                            case DYE_LIGHT_BLUE:
                                if (fruit_trees.getFruitTreesConfig().checkEnabled(TreeType.DYE_LIGHT_BLUE)) {
                                    new DyeTree(fruit_trees,
                                        Integer.valueOf(tree.getAttributeValue("X")).intValue(),
                                        Integer.valueOf(tree.getAttributeValue("Y")).intValue(),
                                        Integer.valueOf(tree.getAttributeValue("Z")).intValue(),
                                        tree_world,
                                        (byte) 12);
                                }
                                else {
                                    // Pass it down to the catch block
                                    throw new IllegalArgumentException("HerpDerp");
                                }
                                break;
                            case DYE_LIGHT_GRAY:
                                if (fruit_trees.getFruitTreesConfig().checkEnabled(TreeType.DYE_LIGHT_GRAY)) {
                                    new DyeTree(fruit_trees,
                                        Integer.valueOf(tree.getAttributeValue("X")).intValue(),
                                        Integer.valueOf(tree.getAttributeValue("Y")).intValue(),
                                        Integer.valueOf(tree.getAttributeValue("Z")).intValue(),
                                        tree_world,
                                        (byte) 7);
                                }
                                else {
                                    // Pass it down to the catch block
                                    throw new IllegalArgumentException("HerpDerp");
                                }
                                break;
                            case DYE_LIME:
                                if (fruit_trees.getFruitTreesConfig().checkEnabled(TreeType.DYE_LIME)) {
                                    new DyeTree(fruit_trees,
                                        Integer.valueOf(tree.getAttributeValue("X")).intValue(),
                                        Integer.valueOf(tree.getAttributeValue("Y")).intValue(),
                                        Integer.valueOf(tree.getAttributeValue("Z")).intValue(),
                                        tree_world,
                                        (byte) 10);
                                }
                                else {
                                    // Pass it down to the catch block
                                    throw new IllegalArgumentException("HerpDerp");
                                }
                                break;
                            case DYE_MAGENTA:
                                if (fruit_trees.getFruitTreesConfig().checkEnabled(TreeType.DYE_MAGENTA)) {
                                    new DyeTree(fruit_trees,
                                        Integer.valueOf(tree.getAttributeValue("X")).intValue(),
                                        Integer.valueOf(tree.getAttributeValue("Y")).intValue(),
                                        Integer.valueOf(tree.getAttributeValue("Z")).intValue(),
                                        tree_world,
                                        (byte) 13);
                                }
                                else {
                                    // Pass it down to the catch block
                                    throw new IllegalArgumentException("HerpDerp");
                                }
                                break;
                            case DYE_ORANGE:
                                if (fruit_trees.getFruitTreesConfig().checkEnabled(TreeType.DYE_ORANGE)) {
                                    new DyeTree(fruit_trees,
                                        Integer.valueOf(tree.getAttributeValue("X")).intValue(),
                                        Integer.valueOf(tree.getAttributeValue("Y")).intValue(),
                                        Integer.valueOf(tree.getAttributeValue("Z")).intValue(),
                                        tree_world,
                                        (byte) 14);
                                }
                                else {
                                    // Pass it down to the catch block
                                    throw new IllegalArgumentException("HerpDerp");
                                }
                                break;
                            case DYE_PINK:
                                if (fruit_trees.getFruitTreesConfig().checkEnabled(TreeType.DYE_PINK)) {
                                    new DyeTree(fruit_trees,
                                        Integer.valueOf(tree.getAttributeValue("X")).intValue(),
                                        Integer.valueOf(tree.getAttributeValue("Y")).intValue(),
                                        Integer.valueOf(tree.getAttributeValue("Z")).intValue(),
                                        tree_world,
                                        (byte) 9);
                                }
                                else {
                                    // Pass it down to the catch block
                                    throw new IllegalArgumentException("HerpDerp");
                                }
                                break;
                            case DYE_PURPLE:
                                if (fruit_trees.getFruitTreesConfig().checkEnabled(TreeType.DYE_PURPLE)) {
                                    new DyeTree(fruit_trees,
                                        Integer.valueOf(tree.getAttributeValue("X")).intValue(),
                                        Integer.valueOf(tree.getAttributeValue("Y")).intValue(),
                                        Integer.valueOf(tree.getAttributeValue("Z")).intValue(),
                                        tree_world,
                                        (byte) 5);
                                }
                                else {
                                    // Pass it down to the catch block
                                    throw new IllegalArgumentException("HerpDerp");
                                }
                                break;
                            case DYE_RED:
                                if (fruit_trees.getFruitTreesConfig().checkEnabled(TreeType.DYE_RED)) {
                                    new DyeTree(fruit_trees,
                                        Integer.valueOf(tree.getAttributeValue("X")).intValue(),
                                        Integer.valueOf(tree.getAttributeValue("Y")).intValue(),
                                        Integer.valueOf(tree.getAttributeValue("Z")).intValue(),
                                        tree_world,
                                        (byte) 1);
                                }
                                else {
                                    // Pass it down to the catch block
                                    throw new IllegalArgumentException("HerpDerp");
                                }
                                break;
                            case DYE_WHITE:
                                if (fruit_trees.getFruitTreesConfig().checkEnabled(TreeType.DYE_WHITE)) {
                                    new DyeTree(fruit_trees,
                                        Integer.valueOf(tree.getAttributeValue("X")).intValue(),
                                        Integer.valueOf(tree.getAttributeValue("Y")).intValue(),
                                        Integer.valueOf(tree.getAttributeValue("Z")).intValue(),
                                        tree_world,
                                        (byte) 15);
                                }
                                else {
                                    // Pass it down to the catch block
                                    throw new IllegalArgumentException("HerpDerp");
                                }
                                break;
                            case DYE_YELLOW:
                                if (fruit_trees.getFruitTreesConfig().checkEnabled(TreeType.DYE_YELLOW)) {
                                    new DyeTree(fruit_trees,
                                        Integer.valueOf(tree.getAttributeValue("X")).intValue(),
                                        Integer.valueOf(tree.getAttributeValue("Y")).intValue(),
                                        Integer.valueOf(tree.getAttributeValue("Z")).intValue(),
                                        tree_world,
                                        (byte) 11);
                                }
                                else {
                                    // Pass it down to the catch block
                                    throw new IllegalArgumentException("HerpDerp");
                                }
                                break;
                            case IRON:
                                if (fruit_trees.getFruitTreesConfig().checkEnabled(TreeType.IRON)) {
                                    new IronTree(fruit_trees,
                                        Integer.valueOf(tree.getAttributeValue("X")).intValue(),
                                        Integer.valueOf(tree.getAttributeValue("Y")).intValue(),
                                        Integer.valueOf(tree.getAttributeValue("Z")).intValue(),
                                        tree_world);
                                }
                                else {
                                    // Pass it down to the catch block
                                    throw new IllegalArgumentException("HerpDerp");
                                }
                                break;
                            case GOLD:
                                if (fruit_trees.getFruitTreesConfig().checkEnabled(TreeType.GOLD)) {
                                    new GoldTree(fruit_trees,
                                        Integer.valueOf(tree.getAttributeValue("X")).intValue(),
                                        Integer.valueOf(tree.getAttributeValue("Y")).intValue(),
                                        Integer.valueOf(tree.getAttributeValue("Z")).intValue(),
                                        tree_world);
                                }
                                else {
                                    // Pass it down to the catch block
                                    throw new IllegalArgumentException("HerpDerp");
                                }
                                break;
                            case DIAMOND:
                            case EMERALD:
                            case COAL:
                            default:
                                continue;
                        }
                    }
                    catch (IllegalArgumentException iae) {
                        fruit_trees.warning(String.format("Unable to initialize tree: Type=%s X=%s Y=%s Z=%s.",
                            tree.getAttributeValue("Type"),
                            tree.getAttributeValue("X"),
                            tree.getAttributeValue("Y"),
                            tree.getAttributeValue("Z")));
                        continue;
                    }
                }
            }
            catch (JDOMException ex1) {}
            catch (IOException ex1) {}
        }
        return true;
    }

    private final boolean genDefaultWorldFile(File worldFile) {
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
            catch (IOException e) {}
            writer = null;
            if (ex != null) {
                fruit_trees.severe("Failed to generate world file", ex);
                return false;
            }
        }
        return true;
    }
}
