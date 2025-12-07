package com.example.catalog.config;

import static net.logstash.logback.argument.StructuredArguments.v;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@ConditionalOnBean(value = LogConfig.LoggerInterceptor.class)
@Configuration
@RequiredArgsConstructor
@Slf4j
public class LogConfig implements WebMvcConfigurer {

    private final LoggerInterceptor loggerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggerInterceptor);
    }

    @Bean
    public CommonsRequestLoggingFilter logFilter() {
        CustomCommonsRequestLoggingFilter filter = new CustomCommonsRequestLoggingFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(10000);
        filter.setIncludeHeaders(true);
        filter.setBeforeMessagePrefix("Start request : ");
        filter.setAfterMessagePrefix("End request : ");
        return filter;
    }

    @ConditionalOnProperty(prefix = "common.interceptor", name = "enabled", matchIfMissing = true)
    @Component
    public static class LoggerInterceptor implements AsyncHandlerInterceptor {
        @Override
        public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response,
                                 @NonNull Object object) throws Exception {
            request.setAttribute("REQUEST_START_TIME", System.currentTimeMillis());
            return AsyncHandlerInterceptor.super.preHandle(request, response, object);
        }
    }

    public static class CustomCommonsRequestLoggingFilter extends CommonsRequestLoggingFilter {
        @Override
        protected void afterRequest(@NonNull HttpServletRequest request, @NonNull String message) {
            long value = System.currentTimeMillis() -
                    (Long) request.getAttribute("REQUEST_START_TIME");

            String uri = request.getQueryString() != null ?
                    String.join("?", request.getRequestURI(), request.getQueryString()) : request.getRequestURI();

            request.removeAttribute("REQUEST_START_TIME");
            log.debug("[End] | Uri: {}  | Elapsed time: {} ms", v("URI", request.getMethod() + " " + uri),
                    v("ELAPSED_TIME", value));
            super.afterRequest(request, message);
            MDC.clear();
        }

        @Override
        protected boolean shouldNotFilter(HttpServletRequest request) {
            return request.getRequestURI().contains("actuator") ||
                    request.getRequestURI().contains("websocket");
        }
    }

}