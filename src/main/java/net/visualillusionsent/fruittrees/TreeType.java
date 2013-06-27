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

public enum TreeType {

    APPLE(17, 0, 18, 3), //
    GOLDEN_APPLE(17, 1, 18, 3), //
    SPONGE(17, 2, 19, 0), //
    RECORD(17, 1, 25, 0), //
    DYE_BLACK(17, 2, 35, 15), //
    DYE_RED(17, 2, 35, 14), //
    DYE_GREEN(17, 2, 35, 13), //
    DYE_BROWN(17, 2, 35, 12), //
    DYE_BLUE(17, 2, 35, 11), //
    DYE_PURPLE(17, 2, 35, 10), //
    DYE_CYAN(17, 2, 35, 9), //
    DYE_LIGHT_GRAY(17, 2, 35, 8), //
    DYE_GRAY(17, 2, 35, 7), //
    DYE_PINK(17, 2, 35, 6), //
    DYE_LIME(17, 2, 35, 5), //
    DYE_YELLOW(17, 2, 35, 4), //
    DYE_LIGHT_BLUE(17, 2, 35, 3), //
    DYE_MAGENTA(17, 2, 35, 2), //
    DYE_ORANGE(17, 2, 35, 1), //
    DYE_WHITE(17, 2, 35, 0), //
    REDSTONE(17, 1, 152, 0), //
    IRON(17, 2, 42, 0), //
    GOLD(17, 1, 41, 0), //
    DIAMOND(17, 0, 57, 0), //
    EMERALD(17, 0, 133, 0), //
    COAL(17, 1, 0, 0), //
    ;

    private final short log_id, log_data, leaves_id, leaves_data;

    private TreeType(int log_id, int log_data, int leaves_id, int leaves_data) {
        this.log_id = (short) log_id;
        this.log_data = (short) log_data;
        this.leaves_id = (short) leaves_id;
        this.leaves_data = (short) leaves_data;
    }

    public final short getLogId() {
        return log_id;
    }

    public final short getLogData() {
        return log_data;
    }

    public final short getLeavesId() {
        return leaves_id;
    }

    public final short getLeavesData() {
        return leaves_data;
    }
}
