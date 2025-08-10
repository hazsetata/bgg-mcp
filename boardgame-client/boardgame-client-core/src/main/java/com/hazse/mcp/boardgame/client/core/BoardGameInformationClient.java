package com.hazse.mcp.boardgame.client.core;

import java.util.List;
import java.util.Set;

/**
 * Implementing classes can fetch information about board-games from various sites.
 */
public interface BoardGameInformationClient {
    List<BoardGameSearchResult> searchGamesByName(String name);

    List<BoardGame> getGameDetailsByIds(Set<Integer> ids);
}
