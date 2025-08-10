package com.hazse.mcp.boardgame.app.stdio.config;

import com.hazse.mcp.boardgame.app.stdio.service.BoardGameToolService;
import com.hazse.mcp.boardgame.client.bgg.AuduxBggClient;
import com.hazse.mcp.boardgame.client.core.BoardGameInformationClient;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StdioAppConfiguration {
    @Bean
    public BoardGameInformationClient auduxBggClient() {
        return new AuduxBggClient();
    }

    @Bean
    BoardGameToolService bggToolService(BoardGameInformationClient bggClient) {
        return new BoardGameToolService(bggClient);
    }

    @Bean
    public ToolCallbackProvider weatherTools(BoardGameToolService boardGameToolService) {
        return MethodToolCallbackProvider.builder().toolObjects(boardGameToolService).build();
    }
}
