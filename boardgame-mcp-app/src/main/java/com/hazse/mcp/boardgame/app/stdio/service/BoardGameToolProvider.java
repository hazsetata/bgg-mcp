package com.hazse.mcp.boardgame.app.stdio.service;

import com.hazse.mcp.boardgame.client.core.BoardGameInformationClient;
import com.hazse.mcp.boardgame.client.core.BoardGameSearchResult;
import lombok.RequiredArgsConstructor;
import org.springaicommunity.mcp.annotation.McpTool;
import org.springaicommunity.mcp.annotation.McpToolParam;

import java.util.stream.Collectors;

@SuppressWarnings("unused")
@RequiredArgsConstructor
public class BoardGameToolProvider {
    private final BoardGameInformationClient bggClient;

    @McpTool(
            name = "searchGames",
            description = "Search for a list of boardgames by the provided full or partial name"
    )
    public String getBoardgamesWithName(
            @McpToolParam(description =  "The full or partial name of the games to look for", required = true)
            String name
    ) {
        return bggClient.searchGamesByName(name).stream()
                .map(this::convertToText)
                .collect(Collectors.joining("\n"));
    }

    private String convertToText(BoardGameSearchResult bggGame) {
        return String.format(""" 
                Name: %s
                BGG URL: https://boardgamegeek.com/boardgame/%d
                Published: %s
                """,
                bggGame.getName(),
                bggGame.getId(),
                bggGame.getPublicationYear() == null ? "Unknown" : String.valueOf(bggGame.getPublicationYear())
        );
    }
}
