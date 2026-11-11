/*
 * WorldEdit, a Minecraft world manipulation toolkit
 * Copyright (C) sk89q <http://www.sk89q.com>
 * Copyright (C) WorldEdit team and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.sk89q.worldedit.bukkit;

import com.sk89q.worldedit.bukkit.folia.FoliaExtentListener;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.util.function.BooleanSupplier;

/**
 * Entity counterpart of {@link FoliaExtentListener}: on Folia, WorldEdit only touches entities that the current
 * region owns, and skips the rest, the same way it skips blocks outside the region.
 */
final class FoliaRegions {

    private FoliaRegions() {
    }

    /**
     * Whether an edit may go ahead. Off Folia every edit may; on Folia only an edit the current region owns may.
     * The ownership check runs only on Folia, because the Bukkit call does not exist elsewhere.
     */
    static boolean mayEdit(boolean folia, BooleanSupplier ownedByCurrentRegion) {
        return !folia || ownedByCurrentRegion.getAsBoolean();
    }

    static boolean mayEdit(Location location) {
        return mayEdit(WorldEditPlugin.getInstance().isFolia(), () -> Bukkit.isOwnedByCurrentRegion(location));
    }

    static boolean mayEdit(Entity entity) {
        return mayEdit(WorldEditPlugin.getInstance().isFolia(), () -> Bukkit.isOwnedByCurrentRegion(entity));
    }
}
