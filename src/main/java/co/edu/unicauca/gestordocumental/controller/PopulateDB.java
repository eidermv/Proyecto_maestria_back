package co.edu.unicauca.gestordocumental.controller;

import co.edu.unicauca.gestordocumental.model.Estudiante;
import co.edu.unicauca.gestordocumental.model.TipoUsuario;
import co.edu.unicauca.gestordocumental.model.Tutor;
import co.edu.unicauca.gestordocumental.model.Usuario;
import co.edu.unicauca.gestordocumental.repo.EstudianteRepo;
import co.edu.unicauca.gestordocumental.repo.TipoUsuarioRepo;
import co.edu.unicauca.gestordocumental.repo.TutorRepo;
import co.edu.unicauca.gestordocumental.repo.UsuarioRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class PopulateDB {
    
    @Autowired
    private TipoUsuarioRepo tipoUsuarioRepo ;
    @Autowired
    private TutorRepo tutorRepo;
    @Autowired
    private UsuarioRepo usuarioRepo;
    @Autowired
    private EstudianteRepo estudianteRepo;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public PopulateDB() {
        this.populate();
    }
    
    
    
    private void populateTipoUsuario()
    {
        TipoUsuario coordinador = new TipoUsuario();
        coordinador.setNombre("Coordinador");
        TipoUsuario estudiante = new TipoUsuario();
        coordinador.setNombre("Estudiante");
        tipoUsuarioRepo.save(coordinador);
        tipoUsuarioRepo.save(estudiante);
    }
    
    private void populateTutor ()
    {
        Tutor cc = new Tutor("Carlos Cobos");
        Tutor mm = new Tutor("Marta mendoza");
        
        tutorRepo.save(mm);
        tutorRepo.save(cc);
        
    }
    
    private void populateCoordinador()
    {
        Usuario usuario = new Usuario();
        usuario.setEstado(true);
        TipoUsuario tipoUsuario = tipoUsuarioRepo.findByNombre("Coordinador");
        usuario.addTipoUsuario(tipoUsuario);
        usuario.setUsuario("pmage");
        usuario.setContrasena(bCryptPasswordEncoder.encode("123"));
        
        usuarioRepo.save(usuario);
    }
    
    private void populateEstudiante()
    {
        String codigo = "123456";
        String nombre = "Daniela";
        String apellido = "Velásquez";
        String correo = "angiedanielav@unicauca.edu.co";
        int cohorte = 2013;
        int semestre = 1;
        String estado = "activo";
        int creditos = 0;
        String pertenece = "maestría";
        
        
        Tutor tutor = tutorRepo.findByNombre("Carlos Cobos");
        TipoUsuario tipoUusario = tipoUsuarioRepo.findByNombre("Estudiante");
        
        Usuario usuario = new Usuario();
        usuario.setUsuario("angiedanielav");
        usuario.setContrasena(bCryptPasswordEncoder.encode("123"));
        usuario.addTipoUsuario(tipoUusario);
        usuario.setEstado(true);
        
        usuarioRepo.save(usuario);
            
        Estudiante estudiante = new Estudiante(codigo, nombre, apellido,
                    correo, cohorte, semestre, estado, creditos, pertenece, tutor);
        //estudiante.setUsuario(usuario);
        
        //estudiante.setUsuario(usuario);
        estudianteRepo.save(estudiante);
    }
    
    public void populate()
    {
        populateTipoUsuario();
        populateTutor();
        populateCoordinador();
        populateEstudiante();
    }
}
