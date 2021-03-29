package main.java.edu.jbre.advcompclient;

import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import edu.jbre.advcomp.authentification.AdvcompException;
import edu.jbre.advcomp.core.service.AdvCompServer;
import edu.jbre.advcomp.core.service.AdvCompService;

/**
 * Le client pour l'accès et l'utilisation d'AdvComp 
 */
public class AdvCompClient {
	
	public static void main(String[] args) throws AdvcompException, NamingException {
		
		// Configuration des propriétés
		Properties props = new Properties();
        props.setProperty("java.naming.factory.initial", "com.sun.enterprise.naming.SerialInitContextFactory");
        props.setProperty("java.naming.factory.url.pkgs", "com.sun.enterprise.naming");
        props.setProperty("java.naming.factory.state", "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");
		
		// Obtention du contexte
		InitialContext ctx = new InitialContext(props);

		// Obtention de la référence sur le servcie "EchoService"
		AdvCompServer server = (AdvCompServer) ctx.lookup("java:global/AdvCompEjb/AdvCompServerImpl");

		// Le service Advcomp
		AdvCompService advCompService;
		
		// Test de connexion avec des identifiants incorrects
		try {
			System.out.println("*********  Connexion avec des valuers incorrectes *********");
			advCompService = server.connexion("toto", "mot_de_passe_incorrect");
		} catch (AdvcompException exception) {
			System.out.println("exception : " + exception.getMessage() + "\n");
		}
		
		// Connexion et récupération d'un service
		System.out.println("********* Connexion avec de bonnes valeurs *********");

		advCompService = server.connexion("toto", "secret");
		
		if (advCompService != null) {
			System.out.println("connexion OK\n");
		}
		
		// Calcul
		System.out.println("********* Calcul 1 + 2 *********");
		System.out.println("résultat : " + advCompService.faireOperationBasique(1d, 2d, "+"));

	}

}
