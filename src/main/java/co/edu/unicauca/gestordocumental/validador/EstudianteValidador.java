package co.edu.unicauca.gestordocumental.validador;

import co.edu.unicauca.gestordocumental.respuesta.Respuesta;
import co.edu.unicauca.gestordocumental.util.MayusculaPrimerLetraPalabra;

import java.util.Calendar;
import java.util.regex.Pattern;

public class EstudianteValidador {  
    
    private final Respuesta res;
    
    public EstudianteValidador(Respuesta res) {
        this.res = res;
    }
        
    public int validarId(String id, boolean obligatorio) throws Exception {
        int idNumerico = -1;
        try 
        {
            idNumerico =  Integer.parseInt(id);
        } 
        catch (NumberFormatException e)
        {
            res.badRequest(109, 104);
        }
        return idNumerico;
    }
    
    public String validarCodigo(String codigo, boolean obligatorio) throws Exception {
        
        int longitudMaxima = 20;
        int longitudMinima = 1;
        String regex = "^[0-9]*$";
        
        if (!obligatorio && (codigo == null)) {
            // no haga nada
        } else if (obligatorio && (codigo == null)) {
            res.badRequest(1, 1);
        } else if (codigo.length() < longitudMinima) {
            res.badRequest(100, 100);
        } else if (codigo.length() > longitudMaxima) {
            res.badRequest(100, 101);
        } else if (!cumplePatron(regex, codigo)) {
            res.badRequest(100, 104);
        }
        
        return codigo;
    }
    
    public String validarNombres(String nombres, boolean obligatorio) throws Exception {
        
        int longitudMaxima = 40;
        int longitudMinima = 1;
        String regex = "^[a-zA-ZÁÉÍÓÚÑñáéíóúñ\\s]*$";
        
        if (!obligatorio && (nombres == null)) {
            // no haga nada
        } else if (obligatorio && (nombres == null)) {
            res.badRequest(1, 1);
        } else if (nombres.length() < longitudMinima) {
            res.badRequest(101, 100);
        } else if (nombres.length() > longitudMaxima) {
            res.badRequest(101, 101);
        } else if (!cumplePatron(regex, nombres)) {
            res.badRequest(101, 104);
        }
        
        return MayusculaPrimerLetraPalabra.aplicar(nombres);
    }
    
    public String validarApellidos(String apellidos, boolean obligatorio) throws Exception {
        
        int longitudMaxima = 40;
        int longitudMinima = 1;
        String regex = "^[a-zA-ZÁÉÍÓÚÑñáéíóúñ\\s]*$";
        
        if (!obligatorio && (apellidos == null)) {
            // no haga nada
        } else if (obligatorio && (apellidos == null)) {
            res.badRequest(1, 1);
        } else if (apellidos.length() < longitudMinima) {
            res.badRequest(102, 100);
        } else if (apellidos.length() > longitudMaxima) {
            res.badRequest(102, 101);
        } else if (!cumplePatron(regex, apellidos)) {
            res.badRequest(102, 104);
        }
        
        return MayusculaPrimerLetraPalabra.aplicar(apellidos);
    }
    
    public String validarCorreo(String correo, boolean obligatorio) throws Exception {
        
        int longitudMaxima = 40;
        int longitudMinima = 1;
        String regex = "^[a-z0-9._%+-]+@unicauca.edu.co$";
        
        if(!obligatorio && (correo == null)) {
            // no haga nada
        } else if (obligatorio && (correo == null)) {
            res.badRequest(1, 1);
        } else if (correo.length() < longitudMinima) {
            res.badRequest(103, 100);
        } else if (correo.length() > longitudMaxima) {
            res.badRequest(103, 101);
        } else if (!cumplePatron(regex, correo)) { 
            res.badRequest(103, 104);
        }
        
        return correo;
    }
    
    public int validarCohorte(String cohorte, boolean obligatorio) throws Exception {
        
        int cohorteRetornar = 0;
        int menorAnio = 2000;
        
        try {
            cohorteRetornar = Integer.parseInt(cohorte);            
        } catch (NumberFormatException ex) {
            res.badRequest(104, 104);
        }
        
        if ((cohorteRetornar < menorAnio) || (cohorteRetornar > Calendar.getInstance().get(Calendar.YEAR))) {
            res.badRequest(104, 104);
        }
        
        return cohorteRetornar;
    }
    
    public int validarSemestre(String semestre, boolean obligatorio) throws Exception {
        
        int semestreRetornar = 0;
        
        try {
            semestreRetornar = Integer.parseInt(semestre);
        } catch (NumberFormatException ex) {
            res.badRequest(105, 104);
        }
        
        if ((semestreRetornar != 1) && (semestreRetornar != 2)) {
            res.badRequest(105, 104);
        }
        
        return semestreRetornar;
    }
    
    public String validarEstado(String estado, boolean obligatorio) throws Exception {
        
        String[] estados = new String[]{"activo", "inactivo", "graduado"};
        boolean valido = false;
        
        for (String s : estados) {
            if (s.compareTo(estado.toLowerCase()) == 0) {
                valido = true;
                break;
            }
        }
        
        if (!valido) {
            res.badRequest(106, 104);
        }
        
        return estado;
    }
    
    public String validarPertenece(String pertenece, boolean obligatorio) throws Exception {
        String[] perteneciente = new String[]{"maestría", "doctorado"};
        boolean valido = false;
        
        for (String s : perteneciente) {
            if (s.compareTo(pertenece.toLowerCase()) == 0) {
                valido = true;
                break;
            }
        }
        
        if (!valido) {
            res.badRequest(108, 104);
        }
        
        return pertenece;
    }
    
    public String validarContrasena(String contrasena) throws Exception {
        
        int longitudMaxima = 15;
        int longitudMinima = 5;
        String regex = "[a-zA-Z]{1,}[0-9]{1,}";
        
        if (contrasena == null)
        {
            res.badRequest(1, 1);
        } else if (contrasena.length() < longitudMinima) {
            res.badRequest(110, 100);
        } else if (contrasena.length() > longitudMaxima) {
            res.badRequest(110, 101);
        } else if (!cumplePatron(regex, contrasena)) { 
            res.badRequest(110, 104);
        }
        
        return contrasena;
    }
    
    private boolean cumplePatron(String patron, String valor) {
        return Pattern.matches(patron, valor);
    }
}