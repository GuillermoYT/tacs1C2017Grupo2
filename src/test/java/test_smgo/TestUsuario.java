package test_smgo;

import org.junit.Before;
import org.junit.Test;

import hierarchyOfExceptions.UserNotFoundException;
import model.Rol;
import model.SummaryActor;
import model.Usuario;
import repos.RepoUsuarios;
import tacs.ActorController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class TestUsuario extends AbstractTest{

	private ActorController controladorActores ;
	
	@Before
	public void setup(){
		controladorActores = new ActorController();
	}
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
    
    //Como usuario quiero marcar un actor como favorito
    @Test
    public void testMarcarFavorito() throws UserNotFoundException{
    	Usuario user = RepoUsuarios.getInstance().buscarUsuario("guille");
		user.addIdActorFavorito(controladorActores.getSumActorById(10));
		assertTrue(user.getIdsActoresFavoritos().stream().anyMatch(ac -> ac.getId() == 10));   	
    }

    //Como usuario quiero desmarcar un actor como favorito
    @Test
    public void testDesmarcarFavorito() throws UserNotFoundException{
    	Usuario user = RepoUsuarios.getInstance().buscarUsuario("guille");
		user.removeIdActorFavorito(controladorActores.getSumActorById(10));
		assertTrue(!user.getIdsActoresFavoritos().stream().anyMatch(ac -> ac.getId() == 10));   	
    }

    //Como usuario quiero ver quienes son mis actores favoritos
    @Test
    public void testGetActoresFavoritos() throws UserNotFoundException{
    	Usuario user = RepoUsuarios.getInstance().buscarUsuario("guille");
		user.addIdActorFavorito(controladorActores.getSumActorById(10));
		user.addIdActorFavorito(controladorActores.getSumActorById(12));
		
		List<SummaryActor> listAux = new ArrayList();
		listAux.add(controladorActores.getSumActorById(10));
		listAux.add(controladorActores.getSumActorById(12));

		assertTrue(user.getIdsActoresFavoritos().containsAll(listAux));   	
    }


}