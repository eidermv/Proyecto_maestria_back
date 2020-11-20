package co.edu.unicauca.gestordocumental.controller.seguimiento;

import co.edu.unicauca.gestordocumental.config.ConstantesSeguridad;
import co.edu.unicauca.gestordocumental.model.Estudiante;
import co.edu.unicauca.gestordocumental.model.TipoUsuario;
import co.edu.unicauca.gestordocumental.model.Tutor;
import co.edu.unicauca.gestordocumental.model.Usuario;
import co.edu.unicauca.gestordocumental.model.seguimiento.Persona;
import co.edu.unicauca.gestordocumental.repo.EstudianteRepo;
import co.edu.unicauca.gestordocumental.repo.TutorRepo;
import co.edu.unicauca.gestordocumental.repo.UsuarioRepo;
import co.edu.unicauca.gestordocumental.repo.seguimiento.PersonaRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path="/persona")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class PersonaController {

    @Autowired
    private EstudianteRepo estudianteRepo;

    @Autowired
    private TutorRepo tutorRepo;

    @Autowired
    private UsuarioRepo usuarioRepo;

    @Autowired
    private PersonaRepo personaRepo;

    JSONObject rta = new JSONObject();

    // buscar en el front donde se llama esta api ---------------------------------------
    @PreAuthorize("hasAnyAuthority('Estudiante', 'Tutor')")
    @GetMapping(path="/buscar/token/{token}")
    public @ResponseBody String getEstudianteYTutorByToken(
            @PathVariable String token) {

        Claims claims = (Claims) Jwts.parser()
                .setSigningKey(ConstantesSeguridad.SECRET)
                .parseClaimsJws(token.replace(ConstantesSeguridad.TOKEN_PREFIX, ""))
                .getBody();


        String nombreUsuario = claims.getSubject();

        Usuario usuario = usuarioRepo.findByUsuario(nombreUsuario);

        // System.out.println("------------------ este se logeo nombre " + usuario.getPersona().getIdPersona());
        String tipo = ((TipoUsuario)usuario.getTiposUsuario().get(0)).getNombre();
        // System.out.println("------------------ este se logeo tipo " + tipo);
        rta = new JSONObject();

        switch (tipo) {
            case "Estudiante" -> {
                List<Estudiante> estudiante = new ArrayList<Estudiante>();
                estudiante.add(estudianteRepo.buscarPorPersona(usuario.getPersona()));
                rta.put("estado", "exito");
                rta.put("data", estudiante);
                rta.put("mensaje", "Estudiante token correctamente");
            }
            case "Tutor" -> {
                List<Tutor> tutor = new ArrayList<Tutor>();
                tutor.add(tutorRepo.buscarPorPersona(usuario.getPersona().getIdPersona()));
                // System.out.println("------------------ este se logeo TUTOR " + tutor.size());
                rta.put("estado", "exito");
                rta.put("data", tutor);
                rta.put("mensaje", "Tutor token correctamente");
            }
            default -> {
                rta.put("estado", "fallo");
                rta.put("data", "");
                rta.put("mensaje", "Usuario no tiene rol");
            }
        }

        return rta.toString();
        // definitivamente debe devolver o tutor o estudiante
        // long tipo = ((TipoUsuario)usuario.getPersona().getTiposUsuario().get(0)).getId();

        // return estudianteRepo.findByUsuario(usuario);

        // return usuario.getPersona();

    }
}
