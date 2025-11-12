package microservicio.api_gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRoutes(RouteLocatorBuilder builder) {
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
                        .uri("http://mvc-monopatin:8084")  // ajusta puerto si es necesario
                )
                .route("paradas", r -> r
                        .path("/paradas/**")
                        .uri("http://mvc-monopatin:8084")
                )

                // Rutas para FACTURACION
//                .route("tarifas", r -> r
//                        .path("/tarifas/**")
//                        .uri("http://mvc-facturacion:8088")
//                )
//                .route("pagos", r -> r
//                        .path("/pagos/**")
//                        .uri("http://mvc-facturacion:8088")
//                )
                .build();
    }
}