package co.edu.unicauca.gestordocumental.validador;

import co.edu.unicauca.gestordocumental.respuesta.Respuesta;
import co.edu.unicauca.gestordocumental.util.MayusculaPrimerLetraPalabra;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class PublicacionEventoValidador {
    
    private final Respuesta res;
    
    public PublicacionEventoValidador(Respuesta res) {
        this.res = res;
    }
    
    public String validarDoi(String doi, boolean obligatorio) throws Exception {
        
        int longitudMaxima = 50;
        int longitudMinima = 1; //10.1145/1067268.1067287
        //String regex = "^(10.)([0-9]{4})/([0-9]{7}).([0-9]{7})$";
        
        if (!obligatorio && (doi == null)) {
            // no haga nada
        } else if (obligatorio && (doi == null)) {
            res.badRequest(1, 1);
        } else if (doi.length() < longitudMinima) {
            res.badRequest(600, 100);
        } else if (doi.length() > longitudMaxima) {
            res.badRequest(600, 101);
        } /*else if (!cumplePatron(regex, doi)) {
            res.badRequest(600, 104);
        }*/
        
        return doi;
    }
    
    public Date validarFechaInicio(String fechaInicio, boolean obligatorio) throws Exception {
        
        Calendar fechaRecibida = Calendar.getInstance();
        Calendar fechaHoy = Calendar.getInstance();
        String regex = "^([0-9]*({1}|{2}))\\-([0-9]*({1}|{2}))\\-([0-9]*{4})$";
        
        if (!cumplePatron(regex, fechaInicio)) {
            res.badRequest(601, 104);
        }
        
        try {
            SimpleDateFormat formateadorFecha = new SimpleDateFormat("dd-MM-yyyy");
            formateadorFecha.setLenient(false);
            fechaRecibida.setTime(formateadorFecha.parse(fechaInicio));
        } catch (ParseException ex) {
            res.badRequest(601, 104);
        }
        
        
        
        return fechaRecibida.getTime();
    }
    
    public Date validarFechaFin(String fechaFin, boolean obligatorio) throws Exception {
        
        Calendar fechaRecibida = Calendar.getInstance();
        Calendar fechaHoy = Calendar.getInstance();
        String regex = "^([0-9]*({1}|{2}))\\-([0-9]*({1}|{2}))\\-([0-9]*{4})$";
        
        if (!cumplePatron(regex, fechaFin)) {
            res.badRequest(602, 104);
        }
        
        try {
            SimpleDateFormat formateadorFecha = new SimpleDateFormat("dd-MM-yyyy");
            formateadorFecha.setLenient(false);
            fechaRecibida.setTime(formateadorFecha.parse(fechaFin));
        } catch (ParseException ex) {
            res.badRequest(602, 104);
        }
        
        
        
        return fechaRecibida.getTime();
    }
    
    public void validarFechaInicioFechaFin(Date fechaInicio, Date fechaFin)throws Exception
    {
        if(fechaInicio.after(fechaFin) || fechaInicio.equals(fechaFin))
            res.badRequest(601, 105);
        else if(fechaFin.after(new Date()))
            res.badRequest(602, 105);
    }
    
    public String validarIssn(String issn, boolean obligatorio) throws Exception {
        
        int longitudMaxima = 50;
        int longitudMinima = 1;
        String regex = "^([0-9]{4})+([-]{1})+([0-9]{3})+([0-9X]{1})$";
        
        if (!obligatorio && (issn == null)) {
            // no haga nada
        } else if (obligatorio && (issn == null)) {
            res.badRequest(1, 1);
        } else if (issn.length() < longitudMinima) {
            res.badRequest(603, 100);
        } else if (issn.length() > longitudMaxima) {
            res.badRequest(603, 101);
        } else if (!cumplePatron(regex, issn)) {
            res.badRequest(603, 104);
        }
        
        return issn;
    }
    
    public String validarTituloPonencia(String tituloPonencia, boolean obligatorio) throws Exception {
        
        int longitudMaxima = 250;
        int longitudMinima = 1;
        
        if (!obligatorio && (tituloPonencia == null)) {
            // no haga nada
        } else if (obligatorio && (tituloPonencia == null)) {
            res.badRequest(1, 1);
        } else if (tituloPonencia.length() < longitudMinima) {
            res.badRequest(604, 100);
        } else if (tituloPonencia.length() > longitudMaxima) {
            res.badRequest(604, 101);
        }
        
        return MayusculaPrimerLetraPalabra.aplicar(tituloPonencia);         
    }
    
    public String validarNombreEvento(String nombreEvento, boolean obligatorio) throws Exception {
        
        int longitudMaxima = 150;
        int longitudMinima = 1;
        
        if (!obligatorio && (nombreEvento == null)) {
            // no haga nada
        } else if (obligatorio && (nombreEvento == null)) {
            res.badRequest(1, 1);
        } else if (nombreEvento.length() < longitudMinima) {
            res.badRequest(605, 100);
        } else if (nombreEvento.length() > longitudMaxima) {
            res.badRequest(605, 101);
        }
        
        return MayusculaPrimerLetraPalabra.aplicar(nombreEvento);         
    }
    
    public String validarTipoEvento(String tipoEvento, boolean obligatorio) throws Exception {
        
        String[] tiposEvento = new String[]{"Congreso", "Seminario", "Simposio"};
        boolean valido = false;
        
        for (String s : tiposEvento) {
            if (s.equalsIgnoreCase(tipoEvento)) {
                tipoEvento = s;
                valido = true;
                break;
            }
        }
        
        if (!valido) {
            res.badRequest(606, 104);
        }
        
        return tipoEvento;
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
            res.badRequest(607, 100);
        } else if (pais.length() > longitudMaxima) {
            res.badRequest(607, 101);
        } else if (!cumplePatron(regex, pais)) {
            res.badRequest(607, 104);
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
            res.badRequest(608, 100);
        } else if (ciudad.length() > longitudMaxima) {
            res.badRequest(608, 101);
        } else if (!cumplePatron(regex, ciudad)) {
            res.badRequest(608, 104);
        }
        
        return MayusculaPrimerLetraPalabra.aplicar(ciudad);
    }
    
    private boolean cumplePatron(String patron, String valor) {
        return Pattern.matches(patron, valor);
    }
}
