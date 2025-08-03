package com.hazse.mcp.bggstdio.client.impl;

import com.hazse.mcp.bggstdio.client.BggClient;
import com.hazse.mcp.bggstdio.client.BggGame;
import com.hazse.mcp.bggstdio.client.BggGameSearchResult;
import org.audux.bgg.common.ThingType;
import org.audux.bgg.response.*;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class AuduxBggClient implements BggClient {
    @Override
    public List<BggGameSearchResult> searchGamesByName(String name) {
        try {
            Response<SearchResults> searchResponse = org.audux.bgg.BggClient
                    .search(name, new ThingType[]{ThingType.BOARD_GAME})
                    .callAsync()
                    .get();
            if (searchResponse.isSuccess()) {
                SearchResults searchResults = searchResponse.getData();

                return searchResults.getResults()
                        .stream()
                        .map(this::convertToBggGameSearchResult)
                        .toList();
            }
        }
        catch (InterruptedException | ExecutionException e) {
            // We do nothing with these, just return an empty list below
        }

        return List.of();
    }

    @Override
    public List<BggGame> getGameDetailsByIds(Set<Integer> ids) {
        try {
            Response<Things> fetchResponse = org.audux.bgg.BggClient
                    .things(
                            ids.toArray(new Integer[0]),
                            new ThingType[]{ThingType.BOARD_GAME}
                    )
                    .callAsync()
                    .get();
            if (fetchResponse.isSuccess()) {
                Things fetchResults = fetchResponse.getData();

                return fetchResults.getThings()
                        .stream()
                        .map(this::convertToBggGame)
                        .toList();
            }
        }
        catch (InterruptedException | ExecutionException e) {
            // We do nothing with these, just return an empty list below
        }

        return List.of();
    }

    private BggGameSearchResult convertToBggGameSearchResult(SearchResult result) {
        return BggGameSearchResult.builder()
                .id(result.getId())
                .name(result.getName().getValue())
                .publicationYear(result.getYearPublished())
                .build();
    }

    private BggGame convertToBggGame(Thing thing) {
        BggGame.BggGameBuilder retValue = BggGame.builder()
                .id(thing.getId())
                .name(thing.getName());

        if (thing.getYearPublished() != null) {
            retValue.publicationYear(thing.getYearPublished());
        }
        if (thing.getThumbnail() != null) {
            retValue.thumbnailUrl(thing.getThumbnail());
        }
        if (thing.getImage() != null) {
            retValue.imageUrl(thing.getImage());
        }
        if (thing.getMinAge() != null) {
            retValue.minPlayerAge(thing.getMinAge());
        }
        if (thing.getMinPlayers() != null) {
            retValue.minPlayers(thing.getMinPlayers());
        }
        if (thing.getMaxPlayers() != null) {
            retValue.maxPlayers(thing.getMaxPlayers().intValue());
        }

        return retValue.build();
    }
}
