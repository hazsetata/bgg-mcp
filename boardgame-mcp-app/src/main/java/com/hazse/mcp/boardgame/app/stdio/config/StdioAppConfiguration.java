package com.hazse.mcp.boardgame.app.stdio.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazse.mcp.boardgame.app.stdio.provider.BoardGameResourceProvider;
import com.hazse.mcp.boardgame.app.stdio.provider.BoardGameToolProvider;
import com.hazse.mcp.boardgame.client.bgg.AuduxBggClient;
import com.hazse.mcp.boardgame.client.core.BoardGameInformationClient;
import io.modelcontextprotocol.server.McpServerFeatures;
import org.springaicommunity.mcp.spring.SyncMcpAnnotationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class StdioAppConfiguration {
    @Bean
    public BoardGameInformationClient auduxBggClient() {
        return new AuduxBggClient();
    }

    @Bean
    BoardGameToolProvider boardGameToolProvider(BoardGameInformationClient bggClient) {
        return new BoardGameToolProvider(bggClient);
    }

    @Bean
    BoardGameResourceProvider boardGameResourceProvider(BoardGameInformationClient bggClient, ObjectMapper objectMapper) {
        return new BoardGameResourceProvider(bggClient, objectMapper);
    }

    @Bean
    public List<McpServerFeatures.SyncToolSpecification> boardGameToolSpecifications(
            BoardGameToolProvider boardGameToolProvider
    ) {
        return SyncMcpAnnotationProvider.createSyncToolSpecifications(List.of(boardGameToolProvider));
    }

    @Bean
    public List<McpServerFeatures.SyncResourceSpecification> boardGameResourceSpecifications(
            BoardGameResourceProvider boardGameResourceProvider
    ) {
        return SyncMcpAnnotationProvider.createSyncResourceSpecifications(List.of(boardGameResourceProvider));
    }
}
