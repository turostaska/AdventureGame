package org.github.turostaska.dao.impl.list;

import org.github.turostaska.dao.INPCDao;
import org.github.turostaska.domain.NPC;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CollectionNPCDao implements INPCDao {
    private List<NPC> NPCs = new ArrayList<>();

    @Override
    public List<NPC> getAll() {
        return NPCs;
    }

    @Override
    public Optional<NPC> getById(Long ID) {
        for (NPC npc : NPCs) {
            if (npc.getID() == ID)
                return Optional.of(npc);
        }
        return Optional.empty();
    }

    @Override
    public Optional<NPC> getByName(String name) {
        for (NPC npc : NPCs) {
            if (npc.getName().equals(name))
                return Optional.of(npc);
        }
        return Optional.empty();
    }

    @Override
    public void update(NPC npc) {
        for (int i = 0; i < NPCs.size(); ++i) {
            if (NPCs.get(i).equals(npc)) {
                NPCs.set(i, npc);
                return;
            }
        }
    }

    @Override
    public void delete(NPC npc) {
        NPCs.remove(npc);
    }

    @Override
    public void create(NPC npc) {
        if (npc == null)
            throw new IllegalArgumentException("NPC can't be null.");

        if (!NPCs.contains(npc))
            NPCs.add(npc);
    }
}
