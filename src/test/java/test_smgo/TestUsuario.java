import org.junit.Test;
import util.PassEncoder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class UsuarioTest {
    @Test
    public void getUsername() throws Exception {
        String asignedName = "anyUsername";
        String asignedPass = "anyPass";
        Usuario usuario = new Usuario(5,asignedName,asignedPass);
        assertEquals(usuario.getUsername(), asignedName);
    }

//    @Test
//    public void getPassword() throws Exception {
//        String asignedName = "anyUsername";
//        String asignedPass = "anyPass";
//        Usuario usuario = new Usuario(5,asignedName,asignedPass);
//        assertEquals(usuario.getPassword(), PassEncoder.encriptarPassword(asignedPass));
//    }

    @Test
    public void getIdsActoresFavoritos() throws Exception {
        String asignedName = "anyUsername";
        String asignedPass = "anyPass";
        Usuario usuario = new Usuario(5,asignedName,asignedPass);

        SummaryActor sa1 = new SummaryActor(1,"unPath","nameActor");
        SummaryActor sa2 = new SummaryActor(2,"unPath","nameActor");
        SummaryActor sa3 = new SummaryActor(3,"unPath","nameActor");
        List<SummaryActor> asignedActoresFavoritos = new ArrayList<SummaryActor>();
        asignedActoresFavoritos.add(sa1);
        asignedActoresFavoritos.add(sa2);
        asignedActoresFavoritos.add(sa3);

        usuario.addIdActorFavorito(sa1);
        usuario.addIdActorFavorito(sa2);
        usuario.addIdActorFavorito(sa3);
        assertEquals(asignedActoresFavoritos, usuario.getIdsActoresFavoritos());
    }

    @Test
    public void getUltimaSesion() throws Exception {
        String asignedName = "anyUsername";
        String asignedPass = "anyPass";
        Usuario usuario = new Usuario(5,asignedName,asignedPass);

        Date asignedLastSesion = new Date();
        usuario.setUltimaSesion(asignedLastSesion);

        assertEquals(asignedLastSesion,usuario.getUltimaSesion());
    }

    @Test
    public void removeIdActorFavorito() throws Exception {
        String asignedName = "anyUsername";
        String asignedPass = "anyPass";
        Usuario usuario = new Usuario(5,asignedName,asignedPass);

        SummaryActor sa1 = new SummaryActor(1,"unPath","nameActor");
        SummaryActor sa2 = new SummaryActor(2,"unPath","nameActor");
        SummaryActor sa3 = new SummaryActor(3,"unPath","nameActor");
        List<SummaryActor> asignedActoresFavoritos = new ArrayList<SummaryActor>();
        asignedActoresFavoritos.add(sa1);
        asignedActoresFavoritos.add(sa3);

        usuario.addIdActorFavorito(sa1);
        usuario.addIdActorFavorito(sa2);
        usuario.addIdActorFavorito(sa3);

        usuario.removeIdActorFavorito(sa2);

        assertEquals(asignedActoresFavoritos, usuario.getIdsActoresFavoritos());
    }

    @Test
    public void sosAdmin() throws Exception {
        String asignedName = "anyUsername";
        String asignedPass = "anyPass";
        Usuario usuario = new Usuario(5,asignedName,asignedPass);

        Rol unRol = new Rol("admin");
        usuario.setRol(unRol);
        assertEquals(unRol, usuario.getRol());
    }

}