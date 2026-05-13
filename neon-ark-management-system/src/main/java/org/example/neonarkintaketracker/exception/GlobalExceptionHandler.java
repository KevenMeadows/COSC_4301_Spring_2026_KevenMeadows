package org.example.neonarkintaketracker.exception;

// Link to Error DTO
import org.example.neonarkintaketracker.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Add response for 404 - Not Found
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse NotFound(NotFoundException except) {
        // Return object refence to not found exception.
        return new ErrorResponse(
                404,
                "Not Found",
                except.getMessage(),
                LocalDateTime.now()
        );
    }

    // Add response for 400 - Bad request
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse BadRequest(BadRequestException except) {
        // Return object refence to bad request exception.
        return new ErrorResponse(
                400,
                "Bad Request.",
                except.getMessage(),
                LocalDateTime.now()
        );
    }

    // Add response for 409 - Conflict - Duplicate Creature
    @ExceptionHandler(DuplicateCreatureException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse DuplicateCreature(DuplicateCreatureException except) {
        // Return object refence to duplicate creature exception.
        return new ErrorResponse(
                409,
                "Duplicate creature.",
                except.getMessage(),
                LocalDateTime.now()
        );
    }

    // Add response for 403 - Access Forbidden
    @ExceptionHandler(AccessForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse accessForbiddenHandler(AccessForbiddenException except) {
        // Return error message stating access forbidden with error code
        return new ErrorResponse(
                403,
                "Access Forbidden",
                except.getMessage(),
                LocalDateTime.now()
        );
    }
}