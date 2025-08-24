package com.hazse.mcp.boardgame.app.stdio.provider;

import com.hazse.mcp.boardgame.client.core.BoardGame;
import com.hazse.mcp.boardgame.client.core.BoardGameInformationClient;
import com.hazse.mcp.boardgame.client.core.BoardGameSearchResult;
import io.modelcontextprotocol.spec.McpSchema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springaicommunity.mcp.annotation.McpTool;
import org.springaicommunity.mcp.annotation.McpToolParam;

import java.util.List;
import java.util.Set;

import static com.hazse.mcp.boardgame.app.stdio.provider.BoardGameResourceProvider.BOARD_GAME_DETAILS_URI;

@SuppressWarnings("unused")
@RequiredArgsConstructor
@Slf4j
public class BoardGameToolProvider {
    private final BoardGameInformationClient bggClient;

    @McpTool(
            name = "searchGames",
            description = "Search for a list of boardgames by the provided full or partial name"
    )
    public McpSchema.CallToolResult getBoardGamesWithName(
            @McpToolParam(description =  "The full or partial name of the games to look for", required = true)
            String name
    ) {
        List<McpSchema.Content> links = bggClient.searchGamesByName(name).stream()
                .map(this::convertToResourceLink)
                .toList();

        return McpSchema.CallToolResult.builder()
                .content(links)
                .build();
    }

    @McpTool(
            name = "gameDetails",
            description = "Provides details about a game by its id"
    )
    public BoardGame getBoardGameDetails(
            @McpToolParam(description =  "The id of the game", required = true)
            String id
    ) {
        int intBoardGameId = Integer.parseInt(id);

        List<BoardGame> gameDetailsByIds = bggClient.getGameDetailsByIds(Set.of(intBoardGameId));

        log.info("Found {} game details", gameDetailsByIds.size());

        return gameDetailsByIds
                .stream()
                .findFirst()
                .orElse(null);
    }


    private String convertToText(BoardGameSearchResult game) {
        return String.format(""" 
                Name: %s
                ID: %d
                Publication year: %s
                Url: https://boardgamegeek.com/boardgame/%d
                """,
                game.getName(),
                game.getId(),
                game.getPublicationYear() == null ? "Unknown" : String.valueOf(game.getPublicationYear()),
                game.getId()
        );
    }

    private McpSchema.Content convertToResourceLink(BoardGameSearchResult game) {
        return McpSchema.ResourceLink.builder()
                .name(game.getName())
                .description(convertToResourceText(game))
                .uri(BOARD_GAME_DETAILS_URI + game.getId())
                .build();
    }

    private String convertToResourceText(BoardGameSearchResult bggGame) {
        return String.format("Name: %s , Publication year: %s , Url: https://boardgamegeek.com/boardgame/%d",
                bggGame.getName(),
                bggGame.getPublicationYear() == null ? "Unknown" : String.valueOf(bggGame.getPublicationYear()),
                bggGame.getId()
        );
    }
}
