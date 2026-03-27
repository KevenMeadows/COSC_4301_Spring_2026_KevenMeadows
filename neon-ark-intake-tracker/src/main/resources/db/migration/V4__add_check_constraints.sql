-- Had an issue with checks, realized I never included all the biomes, danger levels
-- or conditions. Added them all and now it is working properly.

-- Habitats biome constraint
ALTER TABLE habitats
    ADD CONSTRAINT chk_habitats_biome
        CHECK (biome IN ('FOREST', 'DESERT', 'OCEAN', 'ARCTIC', 'SWAMP', 'MOUNTAIN',
                         'JUNGLE', 'CAVE', 'VOLCANIC', 'PLAINS'));

-- Creatures danger_level constraint
ALTER TABLE creatures
    ADD CONSTRAINT chk_creatures_danger_level
        CHECK (danger_level IN ('LOW', 'MEDIUM', 'HIGH', 'EXTREME'));

-- Creatures condition constraint
ALTER TABLE creatures
    ADD CONSTRAINT chk_creatures_condition
        CHECK (condition IN ('STABLE', 'QUARANTINED', 'CRITICAL', 'ACTIVE',
                             'AGGRESSIVE', 'DORMANT'));