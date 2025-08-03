package com.hazse.mcp.bggstdio.config;

import com.hazse.mcp.bggstdio.client.BggClient;
import com.hazse.mcp.bggstdio.client.impl.AuduxBggClient;
import com.hazse.mcp.bggstdio.service.BggToolService;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {
    @Bean
    public BggClient auduxBggClient() {
        return new AuduxBggClient();
    }

    @Bean
    BggToolService bggToolService(BggClient bggClient) {
        return new BggToolService(bggClient);
    }

    @Bean
    public ToolCallbackProvider weatherTools(BggToolService bggToolService) {
        return MethodToolCallbackProvider.builder().toolObjects(bggToolService).build();
    }
}
