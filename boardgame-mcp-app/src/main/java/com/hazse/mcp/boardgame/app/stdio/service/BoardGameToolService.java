package com.hazse.mcp.boardgame.app.stdio.service;

import com.hazse.mcp.boardgame.client.core.BoardGameInformationClient;
import com.hazse.mcp.boardgame.client.core.BoardGameSearchResult;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

import java.util.stream.Collectors;

@SuppressWarnings("unused")
@RequiredArgsConstructor
public class BoardGameToolService {
    private final BoardGameInformationClient bggClient;

    @Tool(description = "Get list of boardgames by the provided full or partial name")
    public String getBoardgamesWithName(
            @ToolParam( description =  "The full or partial name of games")
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
