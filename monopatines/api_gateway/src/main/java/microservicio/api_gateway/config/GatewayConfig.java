package microservicio.api_gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    RouteLocator customRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                // Rutas para USUARIOS
                .route("usuarios", r -> r
                        .path("/usuarios/**")
                        .uri("http://mvc-usuario:8082")
                )
                .route("cuentas", r -> r
                        .path("/cuentas/**")
                        .uri("http://mvc-usuario:8082")
                )
                // Rutas para MONOPATIN
                .route("monopatines", r -> r
                        .path("/monopatines/**")
                        .uri("http://mvc-monopatin:8084")
                )
                .route("paradas", r -> r
                        .path("/paradas/**")
                        .uri("http://mvc-monopatin:8084")
                )
                // Rutas para Viajes
                .route("viajes", r -> r
                        .path("/viajes/**")
                        .uri("http://mvc-viaje:8086")
                )
                .route("pausas", r -> r
                        .path("/pausas/**")
                        .uri("http://mvc-viaje:8086")
                )
                // Rutas para FACTURACION
                .route("tarifas", r -> r
                        .path("/tarifas/**")
                        .uri("http://mvc-facturacion:8088")
                )
                .route("cargas", r -> r
                        .path("/cargas/**")
                        .uri("http://mvc-facturacion:8088")
                )
                .route("subscripciones", r -> r
                        .path("/subscripciones/**")
                        .uri("http://mvc-facturacion:8088")
                )
                // Rutas para Mantenimiento
                .route("registroMantenimientos", r -> r
                        .path("/registroMantenimientos/**")
                        .uri("http://mvc-mantenimiento:8090")
                )
                .route("controlMantenimientos", r -> r
                        .path("/controlMantenimientos/**")
                        .uri("http://mvc-mantenimiento:8090")
                )
                .build();
    }
}