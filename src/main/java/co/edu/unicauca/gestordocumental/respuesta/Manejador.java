package co.edu.unicauca.gestordocumental.respuesta;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class Manejador {
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<RespuestaVacia> general(Exception e) throws Exception {
        RespuestaVacia respuestaVacia = new RespuestaVacia();
        return new ResponseEntity<>(respuestaVacia, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(BadRequest.class)
    public ResponseEntity<RespuestaCampoError> badRequest(BadRequest badRequest) throws Exception { 
        RespuestaCampoError respuestaCampoError = new RespuestaCampoError();
        respuestaCampoError.setCampo(badRequest.getCampo());
        respuestaCampoError.setError(badRequest.getError());
        return new ResponseEntity<>(respuestaCampoError, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(Ok.class)
    public ResponseEntity<RespuestaVacia> ok(Ok ok) throws Exception { 
        RespuestaVacia respuestaVacia = new RespuestaVacia();
        return new ResponseEntity<>(respuestaVacia, HttpStatus.OK);
    }
    
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<RespuestaVacia> accessDenied(AccessDeniedException accessDeniedException) throws Exception {
        RespuestaVacia respuestaVacia = new RespuestaVacia();
        return new ResponseEntity<>(respuestaVacia, HttpStatus.UNAUTHORIZED);
    }
}
