package co.edu.unicauca.gestordocumental.model;

import com.openkm.sdk4j.OKMWebservices;
import com.openkm.sdk4j.OKMWebservicesFactory;
import com.openkm.sdk4j.bean.Document;
import com.openkm.sdk4j.bean.Folder;
import com.openkm.sdk4j.bean.form.FormElement;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

public class OpenKM {
    
    /**
     * El host para conectarse a OpenKM
     */
    private final String HOST = "http://localhost:8083/OpenKM";
    
    /**
     * Usuario para autenticarse en el host de OpenKM
     */
    private final String USUARIO = "okmAdmin";
    
    /**
     * Contraseña para autenticarse en el host de OpenKM
     */
    private final String CONTRASENA = "admin";
    
    /**
     * Ruta base donde se almacenan los archivos en OpenKM
     */
    public static final String RUTA_BASE = "/okm:root/MaestriaAutomatica/";
    
    /**
     * Instancia para conectarse a OpenKM
     */
    private final OKMWebservices openKM;
    
    /**
     * Patron singleton
     */
    private static OpenKM conexion;
    
    private OpenKM() {
        openKM = OKMWebservicesFactory.newInstance(HOST, USUARIO, CONTRASENA);
    }
    
    public static OpenKM getInstance() {
        if (conexion == null) {
            conexion = new OpenKM();
        }
        return conexion;
    }
    
    /**
     * Regista un documento en OpenKM
     * @param documentoBase64 la cadena base64 del archivo
     * @param ruta donde se quiere almacenar el archivo en OpenKM
     * @param nombreArchivo el nombre que se le quiere dar al archivo
     * @param extension la extensión del archivo
     */
    public void crearDocumento(byte[] documentoBase64, String ruta, String nombreArchivo, String extension) {
        
        InputStream archivo = new ByteArrayInputStream(documentoBase64);        
        String rutaArchivo = ruta + nombreArchivo + "." + extension;
        try {
            openKM.createDocumentSimple(rutaArchivo, archivo);
            IOUtils.closeQuietly(archivo);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * verifica si una ruta a un folder dado es válida, también puede verificar+
     * la existencia de un documento en específico
     * @param ruta la ruta al folder, carpeta o documento
     * @return true si la ruta existe, false en caso contrario
     */
    public boolean verificarExistenciaRuta(String ruta) {
        
        try { 
            return openKM.isValidFolder(ruta);
        } catch (Exception ex) {
            return false;
        }
    }
    
    /**
     * Crea un nuevo folder en OpenKM
     * @param folder la ruta del folder
     */
    public void crearFolder(String folder) {
        
        if (!verificarExistenciaRuta(folder)) {
            Folder folderCrear = new Folder();
            folderCrear.setPath(folder);
            try {
                openKM.createFolder(folderCrear);
            } catch (Exception ex) {
            }
        }
    }
    
    /**
     * Obtiene los metadatos que deben asignarse a un archivo
     * @param ruta la ruta al archivo que se quieren consultar los metadatos
     * @param grupo el grupo de metadatos
     * @return los metadatos
     */
    public List<FormElement> obtenerMetadatos(String ruta, String grupo) {
        
        try {            
            return openKM.getPropertyGroupProperties(ruta, grupo);
        } catch (Exception ex) {
            return null;
        }
    }
    
    /**
     * Asigna los metadatos a un archivo
     * @param ruta la ruta del archivo o folder
     * @param grupo el grupo de metadatos ()
     * @param metadatos los metadatos
     */
    public void asignarMetadatos(String ruta, String grupo, List<FormElement> metadatos) {
        
        try {
            openKM.addGroup(ruta, grupo);
            openKM.setPropertyGroupProperties(ruta, grupo, metadatos);
        } catch (Exception ex) {
        }
    }
    
    /**
     * Consulta un archivo en específico dentro de un folder sin tener en cuenta la extensión
     * @param rutaFolder ruta donde se quiere encontrar el archivo
     * @param nombreArchivo el nombre del archivo a buscar
     * @return el mimeType del archivo o null si no se encuentra
     */
    public String getMimeTypeArchivo(String rutaFolder, String nombreArchivo) {
        
        List<Document> documentos;
        try {
            documentos = openKM.getDocumentChildren(rutaFolder);
            
            for (Document documento : documentos) {
                boolean esElArchivo = documento.getPath().toLowerCase().substring(documento.getPath().lastIndexOf("/") + 1).contains(nombreArchivo.toLowerCase());
                if (esElArchivo) {                                       
                    return documento.getMimeType();
                }
            }
        } catch (Exception ex) {            
        }
        
        return null;
    }  
    
    /**
     * Consulta un archivo en específico dentro de un folder sin tener en cuenta la extensión
     * @param rutaFolder ruta donde se quiere encontrar el archivo
     * @param nombreArchivo el nombre del archivo a buscar
     * @return el nombre real del archivo o null si no se encuentra
     */
    public String getNombreRealArchivo(String rutaFolder, String nombreArchivo) {
        
        List<Document> documentos;
        try {
            documentos = openKM.getDocumentChildren(rutaFolder);
            
            for (Document documento : documentos) {
                boolean esElArchivo = documento.getPath().toLowerCase().substring(documento.getPath().lastIndexOf("/") + 1).contains(nombreArchivo.toLowerCase());
                if (esElArchivo) {
                    String path = documento.getPath();
                    String nombreRealArchivo = path.substring(path.lastIndexOf("/") + 1);                    
                    return nombreRealArchivo;
                }
            }
        } catch (Exception ex) {            
        }
        
        return null;
    }    
    
    /**
     * Consulta un archivo en específico dentro de un folder sin tener en cuenta la extensión
     * @param rutaFolder ruta donde se quiere encontrar el archivo
     * @param nombreArchivo el nombre del archivo a buscar
     * @return el archivo o null si no se encuentra
     */
    public InputStream getArchivo(String rutaFolder, String nombreArchivo) {
        
        List<Document> documentos;
        try {
            documentos = openKM.getDocumentChildren(rutaFolder);
            
            for (Document documento : documentos) {
                boolean esElArchivo = documento.getPath().toLowerCase().substring(documento.getPath().lastIndexOf("/") + 1).contains(nombreArchivo.toLowerCase());
                if (esElArchivo) {
                    String path = documento.getPath();
                    InputStream is = openKM.getContent(path);
                    return is;
                }
            }
        } catch (Exception ex) {            
        }
        
        return null;
    }
    
    /**
     * Elimina un nodo en openkm 
     * @param rutaFolder el nodo que se quiere eliminar
     */
    public void eliminarFolder(String rutaFolder) {
        try {
            openKM.deleteFolder(rutaFolder);
        } catch (Exception ex) {
            
        }
    }
}
