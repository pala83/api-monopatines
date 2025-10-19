package integrador.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
    private final String parametro;
    private final String valor;

    @Override
    public String getMessage() {
        return String.format("El parámetro '%s' con valor '%s' es inválido.", parametro, valor);
    }
}
