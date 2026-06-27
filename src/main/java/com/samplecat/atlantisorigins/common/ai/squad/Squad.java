package com.samplecat.atlantisorigins.common.ai.squad;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Lightweight runtime snapshot of a Deep Guardian squad.
 *
 * <p>The authoritative state is still stored on each entity's NBT; this class is
 * rebuilt on demand by {@link SquadManager} so the AI goals can reason about the
 * squad without touching entity internals.</p>
 */
public class Squad {

    private final UUID id;
    @Nullable
    private UUID captainId;
    private final Set<UUID> memberIds = new HashSet<>();

    public Squad(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return this.id;
    }

    @Nullable
    public UUID getCaptainId() {
        return this.captainId;
    }

    public void setCaptainId(@Nullable UUID captainId) {
        this.captainId = captainId;
    }

    public Set<UUID> getMemberIds() {
        return Collections.unmodifiableSet(this.memberIds);
    }

    public void addMember(UUID memberId) {
        this.memberIds.add(memberId);
    }

    public void removeMember(UUID memberId) {
        this.memberIds.remove(memberId);
    }

    public boolean isEmpty() {
        return this.memberIds.isEmpty();
    }
}
