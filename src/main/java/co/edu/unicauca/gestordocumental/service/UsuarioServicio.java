package co.edu.unicauca.gestordocumental.service;

import co.edu.unicauca.gestordocumental.model.TipoUsuario;
import co.edu.unicauca.gestordocumental.model.Usuario;
import co.edu.unicauca.gestordocumental.repo.UsuarioRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Service
public class UsuarioServicio implements UserDetailsService
{
    @Autowired
    private UsuarioRepo usuarioRepositorio;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        Usuario usuarioApp = usuarioRepositorio.findByUsuario(username);
        if (usuarioApp == null || !usuarioApp.getEstado())
        {
            throw new UsernameNotFoundException(username);
        }
        
        Set<GrantedAuthority> autoridades = new HashSet<>();
        ArrayList<TipoUsuario> tiposUsuario = usuarioApp.getPersona().getTiposUsuario();
        // autoridades.add(new SimpleGrantedAuthority(tiposUsuario.getNombre()));
        tiposUsuario.forEach((tipoUsuario) -> {
            autoridades.add(new SimpleGrantedAuthority(tipoUsuario.getNombre()));
        });
        System.out.println("contraseña "+usuarioApp.getContrasena());
        return new User(usuarioApp.getUsuario(), usuarioApp.getContrasena(), autoridades);
    }
    
}
