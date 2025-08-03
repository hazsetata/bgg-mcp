package com.hazse.mcp.bggstdio.client;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class BggGameSearchResult {
    private int id;
    private String name;
    private Integer publicationYear;
}
