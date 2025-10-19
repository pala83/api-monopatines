package integrador.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
    private final String entityName;
    private final Long entityId;

    @Override
    public String getMessage() {
        return String.format("%s con ID %d no encontrado.", entityName, entityId);
    }

}
