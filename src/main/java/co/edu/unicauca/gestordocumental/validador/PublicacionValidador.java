package co.edu.unicauca.gestordocumental.validador;

import co.edu.unicauca.gestordocumental.model.Publicacion;
import co.edu.unicauca.gestordocumental.respuesta.Respuesta;
import co.edu.unicauca.gestordocumental.util.MayusculaPrimerLetraPalabra;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class PublicacionValidador {
    
    private Respuesta res;
    
    public PublicacionValidador(Respuesta res) {
        this.res = res;
    }
    
    public String validarAutor(String autor, boolean obligatorio) throws Exception {
        
        int longitudMaxima = 40;
        int longitudMinima = 1;
        String regex = "^[a-zA-ZÁÉÍÓÚÑñáéíóúñ\\s]*$";
        
        if (!obligatorio && (autor == null)) {
            // no haga nada
        } else if (obligatorio && (autor == null)) {
            res.badRequest(1, 1);
        } else if (autor.length() < longitudMinima) {
            res.badRequest(400, 100);
        } else if (autor.length() > longitudMaxima) {
            res.badRequest(400, 101);
        } else if (!cumplePatron(regex, autor)) {
            res.badRequest(400, 104);
        }
        
        return MayusculaPrimerLetraPalabra.aplicar(autor);
    }
    
    public String validarAutoresSecundarios(String autoresSecundarios, boolean obligatorio) throws Exception {
        
        int longitudMaxima = 250;
        //int longitudMinima = 1;
        String regex = "^[a-zA-ZÁÉÍÓÚÑñáéíóúñ,\\s]*$";
        
        if (!obligatorio && (autoresSecundarios == null || autoresSecundarios.length() == 0)) {
            // no haga nada
        } else if (obligatorio && (autoresSecundarios == null)) {
            res.badRequest(1, 1);
        }/* else if (autoresSecundarios.length() < longitudMinima) {
            res.badRequest(401, 100);
        }*/ else if (autoresSecundarios.length() > longitudMaxima) {
            res.badRequest(401, 101);
        } else if (!cumplePatron(regex, autoresSecundarios)) {
            res.badRequest(401, 104);
        }
        
        return MayusculaPrimerLetraPalabra.aplicar(autoresSecundarios);
    }    
    
    public Date validarFechaPublicacion(String fechaPublicacion, boolean obligatorio) throws Exception {
        
        Calendar fechaRecibida = Calendar.getInstance();
        Calendar fechaHoy = Calendar.getInstance();
        String regex = "^([0-9]*({1}|{2}))\\-([0-9]*({1}|{2}))\\-([0-9]*{4})$";
        
        if((fechaPublicacion == null || fechaPublicacion.length() == 0) && obligatorio)
            res.badRequest(1, 1);
        
        if((fechaPublicacion == null || fechaPublicacion.length() == 0) && !obligatorio)
            return null;
        
        if (!cumplePatron(regex, fechaPublicacion)) {
            res.badRequest(402, 104);
        }
        
        try {
            SimpleDateFormat formateadorFecha = new SimpleDateFormat("dd-MM-yyyy");
            formateadorFecha.setLenient(false);
            fechaRecibida.setTime(formateadorFecha.parse(fechaPublicacion));
        } catch (ParseException ex) {
            res.badRequest(402, 104);
        }
        
       
        
        return fechaRecibida.getTime();
    }
    
    public void validarFechaAceptacionFechaPublicacion(Date fechaAceptacion, Date fechaPublicacion)throws Exception
    {
        if(fechaPublicacion == null || fechaAceptacion == null)
            return;
        if(fechaAceptacion.after(fechaPublicacion) || fechaAceptacion.equals(fechaPublicacion))
            res.badRequest(403, 105);
        else if(fechaPublicacion.after(new Date()))
            res.badRequest(402, 105);
    }
    
    
    public Date validarFechaAceptacion(String fechaAceptacion, boolean obligatorio) throws Exception {
        
        Calendar fechaRecibida = Calendar.getInstance();
        Calendar fechaHoy = Calendar.getInstance();
        String regex = "^([0-9]*({1}|{2}))\\-([0-9]*({1}|{2}))\\-([0-9]*{4})$";
        
        if (!cumplePatron(regex, fechaAceptacion)) {
            res.badRequest(403, 104);
        }
        
        try {
            SimpleDateFormat formateadorFecha = new SimpleDateFormat("dd-MM-yyyy");
            formateadorFecha.setLenient(false);
            fechaRecibida.setTime(formateadorFecha.parse(fechaAceptacion));
        } catch (ParseException ex) {
            res.badRequest(403, 104);
        }

        if (fechaRecibida.compareTo(fechaHoy) > 0) {            
            res.badRequest(403, 104);
        }
        
        return fechaRecibida.getTime();
    }
    
    public int validarIdPublicacion(String id, boolean obligatorio) throws Exception 
    {
        if((id == null || id.equals("null")) && !obligatorio)
            return -1;
        else if((id == null || id.length() == 0) && obligatorio)
            res.badRequest(1, 1);
        else
        {
            try 
            {
                return Integer.parseInt(id);
            } 
            catch (Exception e)
            {
                res.badRequest(405, 106);
            }
        }
        return -1;
    }
    
    public int validarCreditos(String creditos, boolean obligatorio) throws Exception 
    {
        int valorMaximo = 6;
        int valorMinimo = 0;
        if(creditos == null && !obligatorio)
            return 0;
        else if((creditos == null || creditos.length() == 0) && obligatorio)
            res.badRequest(1, 1);
        else
        {
            int valor = -1;
            try 
            {
                valor = Integer.parseInt(creditos);
            } 
            catch (Exception e)
            {
                res.badRequest(406, 106);
            }
            if(valor > valorMaximo)
                res.badRequest(406, 105);
            else if(valor < valorMinimo)
                res.badRequest(406, 107);
            return valor;
        }
        return -1;
    }
    
    public String validarEstado(String estado, boolean obligatorio)throws Exception 
    {
        if((estado == null || estado.length()==0) && obligatorio)
            res.badRequest(1, 1);
        else
        {
            if(Publicacion.ESTADO_APROBADO.equalsIgnoreCase(estado))
                estado = Publicacion.ESTADO_APROBADO;
            else if(Publicacion.ESTADO_POR_VERIFICAR.equalsIgnoreCase(estado))
                estado = Publicacion.ESTADO_POR_VERIFICAR;
            else if(Publicacion.ESTADO_RECHAZADO.equalsIgnoreCase(estado))
                estado = Publicacion.ESTADO_RECHAZADO;
            else
                res.badRequest(407, 104);
        }
        
        return estado;
    }
    
    public String validarComentario(String comentario, boolean obligatorio)throws Exception 
    {
        int longitudMaxima = 300;
        
        if((comentario == null || comentario.length()==0) && obligatorio)
            res.badRequest(1, 1);
        else if(comentario!=null)
        {
           if(comentario.length() > longitudMaxima)
               res.badRequest(408, 101);
        }
        
        return comentario;
    }
    
    private boolean cumplePatron(String patron, String valor) {
        return Pattern.matches(patron, valor);
    }
}
