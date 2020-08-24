package co.edu.unicauca.gestordocumental.validador;

import co.edu.unicauca.gestordocumental.respuesta.Respuesta;
import co.edu.unicauca.gestordocumental.util.MayusculaPrimerLetraPalabra;

import java.util.regex.Pattern;

public class PublicacionRevistaValidador {
    
    private final Respuesta res;
    
    public PublicacionRevistaValidador(Respuesta res) {
        this.res = res;
    }
    
    public String validarTituloArticulo(String tituloArticulo, boolean obligatorio) throws Exception {
        
        int longitudMaxima = 250;
        int longitudMinima = 1;
        
        if (!obligatorio && (tituloArticulo == null)) {
            // no haga nada
        } else if (obligatorio && (tituloArticulo == null)) {
            res.badRequest(1, 1);
        } else if (tituloArticulo.length() < longitudMinima) {
            res.badRequest(500, 100);
        } else if (tituloArticulo.length() > longitudMaxima) {
            res.badRequest(500, 101);
        }
        
        return MayusculaPrimerLetraPalabra.aplicar(tituloArticulo);
    }
    
    public String validarNombreRevista(String nombreRevista, boolean obligatorio) throws Exception {
        
        int longitudMaxima = 100;
        int longitudMinima = 1;
        
        if (!obligatorio && (nombreRevista == null)) {
            // no haga nada
        } else if (obligatorio && (nombreRevista == null)) {
            res.badRequest(1, 1);
        } else if (nombreRevista.length() < longitudMinima) {
            res.badRequest(501, 100);
        } else if (nombreRevista.length() > longitudMaxima) {
            res.badRequest(501, 101);
        }
        
        return MayusculaPrimerLetraPalabra.aplicar(nombreRevista);
    }
    
    public String validarCategoria(String categoria, boolean obligatorio) throws Exception {
        
        String[] categorias = new String[]{"a1", "a2", "b", "c", "sin clasificacion"};
        boolean valido = false;
        
        if (((categoria == null) || (categoria.length() == 0)) && obligatorio) {
            res.badRequest(1, 1);
        }
        
        for (String s : categorias) {
            if (s.compareTo(categoria.toLowerCase()) == 0) {
                valido = true;
                break;
            }
        }
        
        if (!valido) {
            res.badRequest(502, 104);
        }
        
        return categoria;
    }
    
    
    public String validarDoi(String doi, boolean obligatorio) throws Exception {
        
        int longitudMaxima = 50;
        int longitudMinima = 1;
        //String regex = "^(10.)([0-9]{4})/([0-9]{7}).([0-9]{7})$";
        
        if (!obligatorio && (doi == null)) {
            // no haga nada
        } else if (obligatorio && (doi == null)) {
            res.badRequest(1, 1);
        } else if (doi.length() < longitudMinima) {
            res.badRequest(503, 100);
        } else if (doi.length() > longitudMaxima) {
            res.badRequest(503, 101);
        } /*else if (!cumplePatron(regex, doi)) {
            res.badRequest(503, 104);
        }*/
        
        return doi;
    }
    
    private boolean cumplePatron(String patron, String valor) {
        return Pattern.matches(patron, valor);
    }
}
