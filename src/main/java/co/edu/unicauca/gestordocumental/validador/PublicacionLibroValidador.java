package co.edu.unicauca.gestordocumental.validador;

import co.edu.unicauca.gestordocumental.respuesta.Respuesta;
import co.edu.unicauca.gestordocumental.util.MayusculaPrimerLetraPalabra;

import java.util.regex.Pattern;

public class PublicacionLibroValidador {
    
    private final Respuesta res;
    
    public PublicacionLibroValidador(Respuesta res) {
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
            res.badRequest(700, 100);
        } else if (isbn.length() > longitudMaxima) {
            res.badRequest(700, 101);
        } else if (!cumplePatron(regex, isbn)) {
            res.badRequest(700, 104);
        }
        
        return isbn;
    }
    
    public String validarTituloLibro(String tituloLibro, boolean obligatorio) throws Exception {
        
        int longitudMaxima = 250;
        int longitudMinima = 1;
        
        if (!obligatorio && (tituloLibro == null)) {
            // no haga nada
        } else if (obligatorio && (tituloLibro == null)) {
            res.badRequest(1, 1);
        } else if (tituloLibro.length() < longitudMinima) {
            res.badRequest(701, 100);
        } else if (tituloLibro.length() > longitudMaxima) {
            res.badRequest(701, 101);
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
            res.badRequest(702, 100);
        } else if (editorial.length() > longitudMaxima) {
            res.badRequest(702, 101);
        }
        
        return MayusculaPrimerLetraPalabra.aplicar(editorial);
    }
    
    public String validarPais(String pais, boolean obligatorio) throws Exception{
        
        int longitudMaxima = 30;
        int longitudMinima = 1;
        String regex = "^[a-zA-Z/.\\s]*$";
        
        if (!obligatorio && (pais == null)) {
            // no haga nada
        } else if (obligatorio && (pais == null)) {
            res.badRequest(1, 1);
        } else if (pais.length() < longitudMinima) {
            res.badRequest(703, 100);
        } else if (pais.length() > longitudMaxima) {
            res.badRequest(703, 101);
        } else if (!cumplePatron(regex, pais)) {
            res.badRequest(703, 104);
        }
        
        return MayusculaPrimerLetraPalabra.aplicar(pais);
    }
    
    public String validarCiudad(String ciudad, boolean obligatorio) throws Exception{
        
        int longitudMaxima = 30;
        int longitudMinima = 1;
        String regex = "^[a-zA-Z/.\\s]*$";
        
        if (!obligatorio && (ciudad == null)) {
            // no haga nada
        } else if (obligatorio && (ciudad == null)) {
            res.badRequest(1, 1);
        } else if (ciudad.length() < longitudMinima) {
            res.badRequest(704, 100);
        } else if (ciudad.length() > longitudMaxima) {
            res.badRequest(704, 101);
        } else if (!cumplePatron(regex, ciudad)) {
            res.badRequest(704, 104);
        }
        
        return MayusculaPrimerLetraPalabra.aplicar(ciudad);
    }
    
    private boolean cumplePatron(String patron, String valor) {
        return Pattern.matches(patron, valor);
    }
}
