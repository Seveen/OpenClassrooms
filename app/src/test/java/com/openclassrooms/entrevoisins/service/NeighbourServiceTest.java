package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.hamcrest.core.IsNot;
import org.hamcrest.core.IsNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Unit test on Neighbour service
 */
@RunWith(JUnit4.class)
public class NeighbourServiceTest {

    private NeighbourApiService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void getNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    @Test
    public void getFavoriteNeighboursWithSuccess() {
        List<Neighbour> favoriteNeighbours = service.getFavoriteNeighbours();
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS
                .stream()
                .filter(neighbour -> neighbour.getIsFavorite())
                .collect(Collectors.toList());
        assertNotNull(favoriteNeighbours);
        assertNotNull(expectedNeighbours);
        assertThat(favoriteNeighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    @Test
    public void deleteNeighbourWithSuccess() {
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        service.deleteNeighbour(neighbourToDelete);
        assertFalse(service.getNeighbours().contains(neighbourToDelete));
    }

    @Test
    public void favoriteNeighbourWithSuccess() {
        int numberOfFavorites = service.getFavoriteNeighbours().size();
        Neighbour neighbourToFavorite = service.getNeighbours().get(3);
        neighbourToFavorite.setFavorite(true);
        assertEquals(service.getFavoriteNeighbours().size(), numberOfFavorites + 1);
    }

    @Test
    public void unfavoriteNeighbourWithSuccess() {
        int numberOfFavorites = service.getFavoriteNeighbours().size();
        Neighbour neighbourToUnfavorite = service.getFavoriteNeighbours().get(0);
        neighbourToUnfavorite.setFavorite(false);
        assertEquals(service.getFavoriteNeighbours().size(), numberOfFavorites - 1);
    }
}
