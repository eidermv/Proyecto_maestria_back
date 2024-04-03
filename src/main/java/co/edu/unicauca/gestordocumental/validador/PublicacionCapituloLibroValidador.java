package co.edu.unicauca.gestordocumental.validador;

import co.edu.unicauca.gestordocumental.respuesta.Respuesta;
import co.edu.unicauca.gestordocumental.util.MayusculaPrimerLetraPalabra;

import java.util.regex.Pattern;

public class PublicacionCapituloLibroValidador {
    
    private final Respuesta res;
    
    public PublicacionCapituloLibroValidador(Respuesta res) {
        this.res = res;
    }
    
    public String validarIsbn(String isbn, boolean obligatorio) throws Exception {
        
        int longitudMaxima = 40;
        int longitudMinima = 1;
        String regex = "^[0-9\\-]*$";
        
        if (!obligatorio && (isbn == null)) {
            // no haga nada
        } else if (obligatorio && (isbn == null)) {
            res.badRequest(1, 1);
        } else if (isbn.length() < longitudMinima) {
            res.badRequest(800, 100);
        } else if (isbn.length() > longitudMaxima) {
            res.badRequest(800, 101);
        } else if (!cumplePatron(regex, isbn)) {
            res.badRequest(800, 104);
        }
        
        return isbn;
    }
    
    public String validarTituloCapituloLibro(String tituloCapituloLibro, boolean obligatorio) throws Exception {
        
        int longitudMaxima = 250;
        int longitudMinima = 1;
        
        if (!obligatorio && (tituloCapituloLibro == null)) {
            // no haga nada
        } else if (obligatorio && (tituloCapituloLibro == null)) {
            res.badRequest(1, 1);
        } else if (tituloCapituloLibro.length() < longitudMinima) {
            res.badRequest(801, 100);
        } else if (tituloCapituloLibro.length() > longitudMaxima) {
            res.badRequest(801, 101);
        }
        
        return MayusculaPrimerLetraPalabra.aplicar(tituloCapituloLibro);
    }
    
    public String validarTituloLibro(String tituloLibro, boolean obligatorio) throws Exception {
        
        int longitudMaxima = 200;
        int longitudMinima = 1;
        
        if (!obligatorio && (tituloLibro == null)) {
            // no haga nada
        } else if (obligatorio && (tituloLibro == null)) {
            res.badRequest(1, 1);
        } else if (tituloLibro.length() < longitudMinima) {
            res.badRequest(802, 100);
        } else if (tituloLibro.length() > longitudMaxima) {
            res.badRequest(802, 101);
        }
        
        return MayusculaPrimerLetraPalabra.aplicar(tituloLibro);
    }
    
    public String validarEditorial(String editorial, boolean obligatorio) throws Exception {
        
        int longitudMaxima = 50;
        int longitudMinima = 1;
        
        if (!obligatorio && (editorial == null)) {
            // no haga nada
        } else if (obligatorio && (editorial == null)) {
            res.badRequest(1, 1);
        } else if (editorial.length() < longitudMinima) {
            res.badRequest(803, 100);
        } else if (editorial.length() > longitudMaxima) {
            res.badRequest(803, 101);
        }
        
        return MayusculaPrimerLetraPalabra.aplicar(editorial);
    }
    
    private boolean cumplePatron(String patron, String valor) {
        return Pattern.matches(patron, valor);
    }
}
