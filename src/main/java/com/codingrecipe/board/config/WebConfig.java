package com.codingrecipe.board.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private String resourcePath = "/upload/**"; // view에서 사용할 경로
    private String savePath = "file:///Users/eunsikkim/ESKIM/02. Project/03. CodingRecipe/06. SpringBoot_Board_Mybatis/workspace/upload_files/"; // 실제 파일 저장 경로

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(resourcePath)
                .addResourceLocations(savePath);
    }
}
