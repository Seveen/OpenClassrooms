package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.ArrayList;
import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyNeighbourApiService implements  NeighbourApiService {

    private List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();


    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getNeighbours() {
        return neighbours;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getFavoriteNeighbours() {
        List<Neighbour> favorites = new ArrayList<>();
        for (Neighbour neighbour: neighbours) {
            if (neighbour.getIsFavorite()) {
                favorites.add(neighbour);
            }
        }
        return favorites;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteNeighbour(Neighbour neighbour) {
        neighbours.remove(neighbour);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNeighbourToFavorite(Neighbour neighbour) {
        Boolean isFavorite = neighbours.get(neighbours.indexOf(neighbour)).getIsFavorite();
        neighbours.get(neighbours.indexOf(neighbour)).setFavorite(!isFavorite);
    }
}
