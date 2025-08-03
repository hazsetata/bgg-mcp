package com.hazse.mcp.bggstdio.client;

import java.util.List;
import java.util.Set;

public interface BggClient {
    List<BggGameSearchResult> searchGamesByName(String name);

    List<BggGame> getGameDetailsByIds(Set<Integer> ids);
}
