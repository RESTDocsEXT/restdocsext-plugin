package com.example.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Component;

import com.example.model.Team;

/**
 *
 * @author Paul Samsotha
 */
@Component
public class TeamRepositoryImpl implements TeamRepository {
    
    private final Map<Long, Team> store = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(0);
    
    public TeamRepositoryImpl() {
        long id = idCounter.incrementAndGet();
        this.store.put(id, new Team(id, "Sharks", 15, "Sharktankville"));
        id = idCounter.incrementAndGet();
        this.store.put(id, new Team(id, "Bears", 14, "NorthCanadaville"));
        id = idCounter.incrementAndGet();
        this.store.put(id, new Team(id, "Badgers", 13, "Midwestville"));
        id = idCounter.incrementAndGet();
        this.store.put(id, new Team(id, "Tigers", 12, "Asia Town"));
        id = idCounter.incrementAndGet();
        this.store.put(id, new Team(id, "Lions", 15, "Savannah Town"));
        id = idCounter.incrementAndGet();
        this.store.put(id, new Team(id, "Panthers", 13, "Amazonv City"));
        id = idCounter.incrementAndGet();
        this.store.put(id, new Team(id, "Gorillas", 14, "Jungle City"));
        id = idCounter.incrementAndGet();
        this.store.put(id, new Team(id, "Canaries", 15, "Canary Island"));
        id = idCounter.incrementAndGet();
        this.store.put(id, new Team(id, "Eagles", 15, "Skyhighville"));
        id = idCounter.incrementAndGet();
        this.store.put(id, new Team(id, "Martians", 15, "Mars"));
    }
    
    @Override
    public List<Team> findAll() {
        return new ArrayList<>(this.store.values());
    }

    @Override
    public Team findById(long id) {
        return this.store.get(id);
    }

    @Override
    public Team create(Team team)  {
        if (team.getId() != null) {
            throw new IllegalArgumentException("team must not have an id when creating.");
        }
        final long id = this.idCounter.incrementAndGet();
        team.setId(id);
        this.store.put(id, team);
        return team;
    }

    @Override
    public Team update(Team team) {
        final Team toUpdate = this.store.get(team.getId());
        if (toUpdate == null) {
            throw new IllegalArgumentException("team with id " + team.getId() + " does not exist.");
        }
        toUpdate.setCity(team.getCity());
        toUpdate.setName(team.getName());
        toUpdate.setNumOfPlayers(team.getNumOfPlayers());
        this.store.put(toUpdate.getId(), toUpdate);
        return toUpdate;
    }

    @Override
    public void delete(Team team) {
        this.store.remove(team.getId());
    }
}