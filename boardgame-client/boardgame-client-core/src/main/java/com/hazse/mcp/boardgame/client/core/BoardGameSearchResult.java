package com.hazse.mcp.boardgame.client.core;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class BoardGameSearchResult {
    private int id;
    private String name;
    private Integer publicationYear;
}
