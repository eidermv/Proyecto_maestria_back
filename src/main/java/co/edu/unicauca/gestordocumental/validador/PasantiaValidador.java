package co.edu.unicauca.gestordocumental.validador;

import co.edu.unicauca.gestordocumental.model.Pasantia;
import co.edu.unicauca.gestordocumental.respuesta.Respuesta;
import co.edu.unicauca.gestordocumental.util.MayusculaPrimerLetraPalabra;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class PasantiaValidador {
    
    private final Respuesta res;
    
    public PasantiaValidador(Respuesta res) {
        this.res = res;
    }
    
    public String validarTipoPasantia(String tipoPasantia, boolean obligatorio) throws Exception {
        
        String[] tiposPasantia = new String[]{"Nacional", 
            "Internacional"};
        boolean valido = false;
        
        if (((tipoPasantia == null) || (tipoPasantia.length() == 0)) && obligatorio) {
            res.badRequest(1, 1);
        }
        
        for (String tp : tiposPasantia) {
            if (tp.toLowerCase().compareTo(tipoPasantia.toLowerCase()) == 0) {
                valido = true;
                break;
            }
        }
        
        if (!valido) {
            res.badRequest(1002, 104);
        }
        
        return tipoPasantia;
    }
    
    public Date validarFechaInicio(String fechaInicio, boolean obligatorio) throws Exception {
        
        Calendar fechaRecibida = Calendar.getInstance();
        String regex = "^([0-9]*({1}|{2}))\\-([0-9]*({1}|{2}))\\-([0-9]*{4})$";
        
        if (!obligatorio && (fechaInicio == null || fechaInicio.length() == 0)) {
            return null;
        }
        
        if (!cumplePatron(regex, fechaInicio)) {
            res.badRequest(1000, 104);
        }
        
        try {
            SimpleDateFormat formateadorFecha = new SimpleDateFormat("dd-MM-yyyy");
            formateadorFecha.setLenient(false);
            fechaRecibida.setTime(formateadorFecha.parse(fechaInicio));
        } catch (ParseException ex) {
            res.badRequest(1000, 104);
        }
        
        return fechaRecibida.getTime();
    }
    
    public Date validarFechaFin(String fechaFin, boolean obligatorio) throws Exception {
        
        Calendar fechaRecibida = Calendar.getInstance();
        String regex = "^([0-9]*({1}|{2}))\\-([0-9]*({1}|{2}))\\-([0-9]*{4})$";
        
        if (!obligatorio && (fechaFin == null || fechaFin.length() == 0)) {
            return null;
        }
        
        if (!cumplePatron(regex, fechaFin)) {
            res.badRequest(1001, 104);
        }
        
        try {
            SimpleDateFormat formateadorFecha = new SimpleDateFormat("dd-MM-yyyy");
            formateadorFecha.setLenient(false);
            fechaRecibida.setTime(formateadorFecha.parse(fechaFin));
        } catch (ParseException ex) {
            res.badRequest(1001, 104);
        }
        
        return fechaRecibida.getTime();
    }
    
    public void validarFechaInicioFechaFin(Date fechaInicio, Date fechaFin) throws Exception {
        
        if(fechaInicio.after(fechaFin) || fechaInicio.equals(fechaFin)){            
            res.badRequest(1000, 105);
        } else if (fechaFin.after(new Date())) {
            res.badRequest(1001, 105);
        }
    }
    
    public String validarInstitucion (String institucion, boolean obligatorio) throws Exception {
        
        int longitudMaxima = 100;
        int longitudMinima = 1;
        
        if (!obligatorio && (institucion == null)) {
            // no haga nada
        } else if (obligatorio && (institucion == null)) {
            res.badRequest(1, 1);
        } else if (institucion.length() < longitudMinima) {
            res.badRequest(1003, 100);
        } else if (institucion.length() > longitudMaxima) {
            res.badRequest(1003, 101);
        }
        
        return  MayusculaPrimerLetraPalabra.aplicar(institucion);
    }
    
    public String validarDependencia (String dependencia, boolean obligatorio) throws Exception {
        
        String[] tiposDependencia = new String[]{"Departamento", 
            "Facultad", 
            "Grupo de investigacion", 
            "Laboratorio"};
        boolean valido = false;
        
        if (((dependencia == null) || (dependencia.length() == 0)) && obligatorio) {
            res.badRequest(1004, 104);
        }
        
        for (String td : tiposDependencia) {
            if (td.toLowerCase().compareTo(dependencia.toLowerCase()) == 0) {
                valido = true;
                break;
            }
        }
        
        if (!valido) {
            res.badRequest(1004, 104);
        }
        
        return dependencia;
    }
    
    public String validarNombreDependencia (String nombreDependencia, boolean obligatorio) throws Exception {
        
        int longitudMaxima = 150;
        int longitudMinima = 1;
        
        if (!obligatorio && (nombreDependencia == null)) {
            // no haga nada
        } else if (obligatorio && (nombreDependencia == null)) {
            res.badRequest(1, 1);
        } else if (nombreDependencia.length() < longitudMinima) {
            res.badRequest(1005, 100);
        } else if (nombreDependencia.length() > longitudMaxima) {
            res.badRequest(1005, 101);
        } 
        
        return MayusculaPrimerLetraPalabra.aplicar(nombreDependencia);
    }
    
    public String validarResponsable (String responsable, boolean obligatorio) throws Exception {
        
        int longitudMaxima = 100;
        int longitudMinima = 1;
        String regex = "^[a-zA-ZÁÉÍÓÚÑáéíóúñ\\s]*$";
        
        if (!obligatorio && (responsable == null)) {
            // no haga nada
        } else if (obligatorio && (responsable == null)) {
            res.badRequest(1, 1);
        } else if (responsable.length() < longitudMinima) {
            res.badRequest(1006, 100);
        } else if (responsable.length() > longitudMaxima) {
            res.badRequest(1006, 101);
        } else if (!cumplePatron(regex, responsable)) {
            res.badRequest(1006, 104);
        }
        
        return MayusculaPrimerLetraPalabra.aplicar(responsable);
    }
    
    public int validarIdPasantia (String id, boolean obligatorio) throws Exception {
        
        if(id == null && !obligatorio) {
            return -1;
        } else if((id == null || id.length() == 0) && obligatorio) {
            res.badRequest(1, 1);
        } else {
            try {
                return Integer.parseInt(id);
            } catch (Exception e) {
                res.badRequest(1007, 106);
            }
        }
        return -1;
    }
    
    public int validarCreditos (String creditos, boolean obligatorio) throws Exception {
        
        int creditosObtenidos = -1;
        
        if(creditos == null && !obligatorio) {
            return 0;
        } else if((creditos == null || creditos.length() == 0) && obligatorio) {
            res.badRequest(1, 1);
        } else {
            try {
                creditosObtenidos = Integer.parseInt(creditos);                
            } catch (Exception e) {
                res.badRequest(1009, 106);
            }
        }
        
        if (creditosObtenidos > 4) {
            res.badRequest(1009, 105);
        } else if (creditosObtenidos < 0) {
            res.badRequest(1009, 107);
        }
        
        return creditosObtenidos;
    }
    
    public String validarEstado(String estado, boolean obligatorio) throws Exception {
        
        if((estado == null || estado.length()==0) && obligatorio) {
            res.badRequest(1, 1);
        } else {
            if(Pasantia.ESTADO_APROBADO.equalsIgnoreCase(estado)) {
                estado = Pasantia.ESTADO_APROBADO;
            } else if(Pasantia.ESTADO_POR_VERIFICAR.equalsIgnoreCase(estado)){
                estado = Pasantia.ESTADO_POR_VERIFICAR;                
            } else if(Pasantia.ESTADO_RECHAZADO.equalsIgnoreCase(estado)) {
                estado = Pasantia.ESTADO_RECHAZADO;
            } else {
                res.badRequest(1010, 104);
            }
        }
        
        return estado;
    }
    
    public String validarObservacion (String observacion, boolean obligatorio) throws Exception 
    {
        int longitudMaxima = 300;
        
        if((observacion == null || observacion.length() == 0) && obligatorio) {
            res.badRequest(1, 1);
        } else if(observacion != null) {
            if(observacion.length() > longitudMaxima) {
                res.badRequest(1011, 101);
            }
        }
        return observacion;
    }
    
    private boolean cumplePatron(String patron, String valor) {
        return Pattern.matches(patron, valor);
    }
}
