package co.edu.unicauca.gestordocumental.respuesta;

import org.springframework.stereotype.Component;

@Component
public class RespuestaCampoError {
    
    private int error;
    private int campo;
    
    public int getError() {
        return error;
    }
    
    public void setError(int error) {
        this.error = error;
    }

    public int getCampo() {
        return campo;
    }

    public void setCampo(int campo) {
        this.campo = campo;
    }    
}
