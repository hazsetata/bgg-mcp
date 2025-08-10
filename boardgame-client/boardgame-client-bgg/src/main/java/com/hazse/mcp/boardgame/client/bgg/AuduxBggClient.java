package com.hazse.mcp.boardgame.client.bgg;

import com.hazse.mcp.boardgame.client.core.BoardGame;
import com.hazse.mcp.boardgame.client.core.BoardGameInformationClient;
import com.hazse.mcp.boardgame.client.core.BoardGameSearchResult;
import org.audux.bgg.common.ThingType;
import org.audux.bgg.response.*;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class AuduxBggClient implements BoardGameInformationClient {
    private static final String PROVIDER_ID = "bgg";

    @Override
    public List<BoardGameSearchResult> searchGamesByName(String name) {
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
    public List<BoardGame> getGameDetailsByIds(Set<Integer> ids) {
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

    private BoardGameSearchResult convertToBggGameSearchResult(SearchResult result) {
        return BoardGameSearchResult.builder()
                .providerId(PROVIDER_ID)
                .id(result.getId())
                .name(result.getName().getValue())
                .publicationYear(result.getYearPublished())
                .build();
    }

    private BoardGame convertToBggGame(Thing thing) {
        BoardGame.BoardGameBuilder retValue = BoardGame.builder()
                .providerId(PROVIDER_ID)
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
