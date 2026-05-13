-- Capstone says status, assumed that should be matched so condition needs to be changed
-- CREATURES Table alter
ALTER TABLE creatures
    RENAME COLUMN condition TO status;
ALTER TABLE creatures
DROP CONSTRAINT IF EXISTS chk_creatures_condition;

-- Add previous condition constraint to new name status
ALTER TABLE creatures
    ADD CONSTRAINT chk_creatures_status
        CHECK ( status IN ('STABLE', 'QUARANTINED', 'CRITICAL', 'ACTIVE',
                           'AGGRESSIVE', 'DORMANT', 'REMOVED'));

-- Add updatedAt column for when users update something
ALTER TABLE creatures
    ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP WITHOUT TIME ZONE;

-- Update creatures table to set null updatedAt variable equal to createdAt
UPDATE creatures
SET updated_at = created_at
WHERE updated_at IS NULL;
ALTER TABLE creatures
    ALTER COLUMN updated_at SET NOT NULL;

-- Add constraint so there are no duplicates
ALTER TABLE creatures
    ADD CONSTRAINT uq_creatures_habitat_name UNIQUE (habitat_id, name);