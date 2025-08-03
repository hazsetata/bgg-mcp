package com.hazse.mcp.bggstdio.client;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class BggGame {
    private int id;
    private String name;
    private Integer publicationYear;
    private String thumbnailUrl;
    private String imageUrl;
    private Integer minPlayerAge;
    private Integer minPlayers;
    private Integer maxPlayers;
}
