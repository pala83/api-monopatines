package microservicio.mantenimiento.config;

import feign.Client;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
// tengo que poner esta clase para que anden los patch de mrd si ves una mejor opcion sacala uli pero a mi no me anda si no
    @Bean
    public OkHttpClient okHttpClient() {
        System.out.println("=== OKHTTP CLIENT CONFIGURADO ===");
        return new OkHttpClient();
    }

    @Bean
    public Client feignClient() {
        System.out.println("=== FEIGN CONFIGURADO CON OKHTTP ===");
        return new feign.okhttp.OkHttpClient(okHttpClient());
    }
}