package co.edu.unicauca.gestordocumental.respuesta;

public class Respuesta {    
    
    public void badRequest(int campo, int error) throws BadRequest {
        BadRequest badRequest = new BadRequest();
        badRequest.setCampo(campo);
        badRequest.setError(error);
        throw badRequest;
    }
    
    public void ok() throws Ok {
        Ok ok = new Ok();
        throw ok;
    }
}
