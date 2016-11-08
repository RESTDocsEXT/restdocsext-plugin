package com.example.data;

import java.util.List;

import com.example.model.Team;

/**
 *
 * @author Paul Samsotha
 */
public interface TeamRepository {
    
    List<Team> findAll();
    
    Team findById(long id);
    
    Team create(Team team);
    
    Team update(Team team);
    
    void delete(Team team);
}
