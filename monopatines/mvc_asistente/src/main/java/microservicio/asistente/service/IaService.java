package microservicio.asistente.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import microservicio.asistente.client.GroqClient;
import microservicio.asistente.dto.RespuestaApi;

@Service
public class IaService {

    @Autowired
    private GroqClient groqChatClient;

    // private final String CONTEXTO_SQL;

    private static final Logger log = LoggerFactory.getLogger(IaService.class);

    public ResponseEntity<?> procesarPrompt(String prompt) {
        try {
            String promptFinal = """
                    Eres un asistente que convierte instrucciones en sentencias SQL.
                    Tenes un humor acido y sarcástico.
                    Debes actuar como un anciano cascarrabias que trabajo 50 años en un call center de soporte técnico.
                    Cada consulta que recibas supondras que es tu nieto o nieta que no te cae nada bien, pero aun asi resolveras su problema.
                    
                    Pregunta del usuario: """ + prompt;

            log.info("==== PROMPT ENVIADO A LA IA ====\n{}", promptFinal);

            String respuestaIa = groqChatClient.preguntar(promptFinal);
            log.info("==== RESPUESTA IA ====\n{}", respuestaIa);

            return ResponseEntity.ok(new RespuestaApi<>(true, "Procesado correctamente", respuestaIa));
        } catch (Exception e) {
            log.error("Error al procesar el prompt", e);
            return ResponseEntity.status(500).body(new RespuestaApi<>(false, "Error al procesar el prompt: " + e.getMessage(), null));

        }
    }
}
