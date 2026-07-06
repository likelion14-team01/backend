package com.likelion.collab_session.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Value("${server.servlet.context-path:}")
  private String contextPath;

  @Bean
  public OpenAPI customOpenAPI() {
    // 서버 기본 경로 설정 (배포 시 주소 꼬임 방지)
    Server localServer = new Server();
    localServer.setUrl(contextPath);
    localServer.setDescription("Default Server");

    return new OpenAPI()
        .addServersItem(localServer)
        .info(new Info()
            .title("Collab Session API 명세서")
            .version("1.0")
            .description("리액트 연동 및 프로젝트 협업을 위한 API 명세서입니다."));
  }
}
