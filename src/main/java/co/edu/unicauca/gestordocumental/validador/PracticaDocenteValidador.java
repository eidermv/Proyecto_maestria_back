package co.edu.unicauca.gestordocumental.validador;

import co.edu.unicauca.gestordocumental.model.PracticaDocente;
import co.edu.unicauca.gestordocumental.respuesta.Respuesta;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class PracticaDocenteValidador {
    
    private final Respuesta res;
     
    public PracticaDocenteValidador(Respuesta res) {
        this.res = res;
    }
    
    public String validarTipoPracticaDocente(String tipoPracticaDocente, boolean obligatorio) throws Exception {
        
        String[] tiposPracticaDocente = new String[]{"Diseno curricular de curso teorico/practico nuevos - pregrado", 
            "Diseno curricular de curso teorico/practico nuevos - posgrado", 
            "Preparacion de cursos teoricos/practicos nuevos – pregrado", 
            "Preparacion de cursos teoricos/practicos nuevos – posgrado",
            "Docencia curso pregrado",
            "Docencia curso posgrado",
            "Curso corto (seminario actualizacion)",
            "Monitorias cursos",
            "Elaboracion de material de apoyo para Pregrado/ Posgrado",
            "Direccion de Trabajo de Grado en pregrado/posgrado",
            "Jurado trabajo de grado de pregrado",
            "Jurado Anteproyecto de Maestria",
            "Jurado Trabajo de Grado de Maestria",
            "Asesoria de pasantia empresarial",
            "Evaluacion como jurado de pasantia empresarial",
            "Evaluacion de plan de trabajo para pasantia empresarial",
            "Evaluacion de anteproyecto-pregrado",
            "Evaluacion productividad intelectual",
            "Evaluacion informe ano sabatico - Libros",
            "Participacion en el Comite de Programa",
            "Otras actividades de apoyo al departamento"};
        boolean valido = false;
        
        if (((tipoPracticaDocente == null) || (tipoPracticaDocente.length() == 0)) && obligatorio) {
            res.badRequest(1, 1);
        }
        
        for (String tpd : tiposPracticaDocente) {
            if (tpd.toLowerCase().compareTo(tipoPracticaDocente.toLowerCase()) == 0) {
                valido = true;
                break;
            }
        }
        
        if (!valido) {
            res.badRequest(902, 104);
        }
        
        return tipoPracticaDocente;
    }
    
    public Date validarFechaInicio(String fechaInicio, boolean obligatorio) throws Exception {
        
        Calendar fechaRecibida = Calendar.getInstance();
        String regex = "^([0-9]*({1}|{2}))\\-([0-9]*({1}|{2}))\\-([0-9]*{4})$";
        
        if (!obligatorio && (fechaInicio == null || fechaInicio.length() == 0)) {
            return null;
        }
        
        if (!cumplePatron(regex, fechaInicio)) {
            res.badRequest(900, 104);
        }
        
        try {
            SimpleDateFormat formateadorFecha = new SimpleDateFormat("dd-MM-yyyy");
            formateadorFecha.setLenient(false);
            fechaRecibida.setTime(formateadorFecha.parse(fechaInicio));
        } catch (ParseException ex) {
            res.badRequest(900, 104);
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
            res.badRequest(901, 104);
        }
        
        try {
            SimpleDateFormat formateadorFecha = new SimpleDateFormat("dd-MM-yyyy");
            formateadorFecha.setLenient(false);
            fechaRecibida.setTime(formateadorFecha.parse(fechaFin));
        } catch (ParseException ex) {
            res.badRequest(901, 104);
        }
        
        return fechaRecibida.getTime();
    }
    
    public void validarFechaInicioFechaFin(Date fechaInicio, Date fechaFin) throws Exception {
        
        if(fechaInicio.after(fechaFin) || fechaInicio.equals(fechaFin)){            
            res.badRequest(900, 105);
        } else if (fechaFin.after(new Date())) {
            res.badRequest(901, 105);
        }
    }
    
    public int validarIdPracticaDocente (String id, boolean obligatorio) throws Exception {
        
        if(id == null && !obligatorio) {
            return -1;
        } else if((id == null || id.length() == 0) && obligatorio) {
            res.badRequest(1, 1);
        } else {
            try {
                return Integer.parseInt(id);
            } catch (Exception e) {
                res.badRequest(905, 106);
            }
        }
        return -1;
    }
    
    public int validarHoras (String horas, boolean obligatorio) throws Exception {
       
        int horasObtenidas = -1;
        
        if(horas == null && !obligatorio) {
            return 0;
        } else if((horas == null || horas.length() == 0) && obligatorio) {
            res.badRequest(1, 1);
        } else {
            try {
                horasObtenidas = Integer.parseInt(horas);                
            } catch (Exception e) {
                res.badRequest(906, 106);
            }
        }
        
        if (horasObtenidas > 288) {
            res.badRequest(906, 105);
        } else if (horasObtenidas < 0) {
            res.badRequest(906, 107);
        }
        
        return horasObtenidas;
    }
    
    public String validarEstado(String estado, boolean obligatorio) throws Exception {
        
        if((estado == null || estado.length()==0) && obligatorio) {
            res.badRequest(1, 1);
        } else {
            if(PracticaDocente.ESTADO_APROBADO.equalsIgnoreCase(estado)) {
                estado = PracticaDocente.ESTADO_APROBADO;
            } else if(PracticaDocente.ESTADO_POR_VERIFICAR.equalsIgnoreCase(estado)){
                estado = PracticaDocente.ESTADO_POR_VERIFICAR;                
            } else if(PracticaDocente.ESTADO_RECHAZADO.equalsIgnoreCase(estado)) {
                estado = PracticaDocente.ESTADO_RECHAZADO;
            } else {
                res.badRequest(907, 104);
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
                res.badRequest(908, 101);
            }
        }
        return observacion;
    }
    
    private boolean cumplePatron(String patron, String valor) {
        return Pattern.matches(patron, valor);
    }
}
