package com.hazse.mcp.boardgame.app.stdio.config;

import com.hazse.mcp.boardgame.app.stdio.service.BoardGameToolProvider;
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
    BoardGameToolProvider bggToolProvider(BoardGameInformationClient bggClient) {
        return new BoardGameToolProvider(bggClient);
    }

    @Bean
    public List<McpServerFeatures.SyncToolSpecification> syncToolSpecifications(BoardGameToolProvider bggToolProvider) {
        return SyncMcpAnnotationProvider.createSyncToolSpecifications(List.of(bggToolProvider));
    }
}
