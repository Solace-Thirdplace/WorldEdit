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

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FoliaRegionsTest {

    @Test
    void offFoliaEveryEditGoesAheadWithoutAskingForOwnership() {
        AtomicBoolean asked = new AtomicBoolean();
        assertTrue(FoliaRegions.mayEdit(false, () -> {
            asked.set(true);
            return false;
        }));
        assertFalse(asked.get(), "the ownership check does not exist off Folia");
    }

    @Test
    void onFoliaAnEditTheCurrentRegionOwnsGoesAhead() {
        assertTrue(FoliaRegions.mayEdit(true, () -> true));
    }

    @Test
    void onFoliaAnEditAnotherRegionOwnsIsSkipped() {
        assertFalse(FoliaRegions.mayEdit(true, () -> false));
    }
}
