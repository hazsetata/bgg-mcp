# MCP server for searching BGG (BoardGameGeek)

## Introduction

This is a small experiment to create an MCP server that can search 
BGG (BoardGameGeek) for games. Written in Java, using Spring Boot and
[Spring AI](https://docs.spring.io/spring-ai/reference/api/mcp/mcp-server-boot-starter-docs.html).

## Usage

### Claude Desktop

Open the `claude_desktop_config.json` in your `/Users/<YOUR_USERNAME>/Library/Application Support/Claude`
and add the following:

```json
{
  "mcpServers": {
    "bgg-mcp": {
      "command": "java",
      "args": [
        "-jar",
        "-Dlogging.file.name=/path/to/where/you/want/the/logs/bgg-mcp-stdio-server.log",
        "/absolute/path/to/the/jar/bgg-stdio-0.1.0-SNAPSHOT.jar"
      ]
    }
  }
}
```

Restart Claude Desktop and the tool should be available.

## Acknowledgements

 * The code uses this [BGG Client](https://github.com/Bram--/bggclient).
 * Other tools and clients are listed in the [Geek Tools guild](https://boardgamegeek.com/guild/1229)
 * BGG (BoardGameGeek) provides the data through their [API](https://boardgamegeek.com/using_the_xml_api)

![](docs/powered_by_BGG_01_SM.png "Powered by BGG")

## Claude Desktop Troubleshooting

If you are using SDKMan on MacOS, the Java runtime might not be setup in a
way that Claude Desktop can use. If you see this error in the Claude Desktop
logs for your MCP server:

```terminaloutput
The operation couldn’t be completed. Unable to locate a Java Runtime.
```

Then you need to follow the instructions on this page: https://cuizhanming.com/claude-java-mcp-server/