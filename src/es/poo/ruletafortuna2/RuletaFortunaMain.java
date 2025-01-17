package es.poo.ruletafortuna2;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.Scanner;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.google.gson.Gson;


public class RuletaFortunaMain {

	/**
	 * @author: Eliani Alvarez Herrera
	 */
	
	private static Scanner myObj = new Scanner(System.in);
	private static final String DIGA_ALIAS = "Diga su alias: ";
	private static final String DIGA_PASSWORD = "Diga su password de mas de 6 caracteres: ";
	private static final String DIGA_FECHA_NACIMIENTO = "Diga su fecha de nacimiento [dd/mm/aaaa]: ";	
	private static JSONObject jsonObj = new JSONObject();
	private static ArrayList<JSONObject> array = new ArrayList<>();
	

	public static void main(String[] args) {
		
		Jugador jug = new Jugador(null, null, null);
		Partida par = new Partida();
		Frase fra = new Frase();
		Caracter car = new Caracter();
		Tirada tir = new Tirada();
		JuegoRuletaFortuna juego = new JuegoRuletaFortuna(fra, car, tir, par, jug);
		
		juego.getJugador1().deserializacionJugadores();

		if(logIn(juego, jug)) {
			numDePartidas(juego);
			int j = juego.getPartida1().getNumDePartidas();
			for(int i=1; i<=j; i++) {
			 	if(!dejarDeJugar(i, juego)){
			 		datosPartida(juego);
					juegoCompleto(juego);
					addPartida(juego);
				} else {
					return;
				}
			}
			quiereGuardar(juego);
		}

	}
	
	private static int tipoDeUsuario() {
		return Integer.parseInt(dameCadena("Escoja la opción adecuada: 1- Usuario Registrado, 2- Usuario Nuevo, 3- Invitado"));
	}
	
	/**
     * Poner los datos de usuario según el tipo de usuario que seas para poder entrar al juego
     * @param juego Aquí se encuentran todos los objetos, métodos y parámetros de todas las clases
     * @return un boleano para saber si pudo realizar el logIn exitosamente
     */
	private static boolean logIn(JuegoRuletaFortuna juego, Jugador jug) {
		int num = 0;
		while(num < 1 || num > 3) {
			num = tipoDeUsuario();
		}
		if (num == 1) {
			usuariosRegistrados(juego, jug);
		} else if(num == 2) {
			pedirDatos(juego, jug);
		} else {
			usuarioInvitado(juego);
		}
		if(juego.getJugador1().mas10anyos()) {
			return true;
		} else {
			System.out.println("No tiene al menos 10 años por lo que no puede jugar");
			return false;
		}
	}
	
	/**
     * Escribir en pantalla lo que le pedimos al usuario
     * @param texto Es el texto que queremos enseñarle al usuario
     * @return devuelve el input del usuario
     */
	private static String dameCadena(String texto) {
		System.out.println(texto);
		return myObj.nextLine();
	}
	
	/**
     * Pedir los datos del nuevo usuario
     * @param juego Aquí se encuentran todos los objetos, métodos y parámetros de todas las clases
     * @param jug Para agregar el jugador nuevo al array
     */
	private static void pedirDatos(JuegoRuletaFortuna juego, Jugador jug) {
		String aux = "";
		juego.getJugador1().setNombre(dameCadena("Diga su nombre: "));
		aux = dameCadena(DIGA_ALIAS);
		while(juego.getJugador1().existeAlias(aux)) {
			aux = dameCadena(DIGA_ALIAS);
		}
		juego.getJugador1().setAlias(aux);
		while(juego.getJugador1().getAccept() == 0) {
			juego.getJugador1().setDateOfBirth(dameCadena(DIGA_FECHA_NACIMIENTO));
		}
		while(juego.getJugador1().getAccept() == 1) {
			juego.getJugador1().setPassword(dameCadena(DIGA_PASSWORD));
		}
		juego.getJugador1().addJugador();
	}
	
	/**
     * Ver si el usuario está registrado
     * @param juego Aquí se encuentran todos los objetos, métodos y parámetros de todas las clases
     */
	private static void usuariosRegistrados(JuegoRuletaFortuna juego, Jugador jug) {
		if(juego.getJugador1().existeAlias(dameCadena(DIGA_ALIAS))) {
				while(!juego.getJugador1().passwordMatch(dameCadena(DIGA_PASSWORD))) {
					System.out.println("Esta no es la contraseña de este alias");
				}
		} else {
			System.out.println("Este alias no está registrado");
			logIn(juego, jug);
		} 
	}
	
	/**
     * Verificar que el usuario invitado ponga los datos correctamente
     * @param juego Aquí se encuentran todos los objetos, métodos y parámetros de todas las clases
     */
	private static void usuarioInvitado(JuegoRuletaFortuna juego) {
		String ali = dameCadena(DIGA_ALIAS);
		String passw = dameCadena(DIGA_PASSWORD);
		if("invitado".equals(ali) && "invitado".equals(passw)){
			juego.getJugador1().setAlias(ali);
			juego.getJugador1().setPassword(passw);
			if(juego.getJugador1().getAccept() == 0) {
				juego.getJugador1().setDateOfBirth(dameCadena(DIGA_FECHA_NACIMIENTO));
			}
		}
	}
	
	/**
     * Guardar el número de partidas que el usuario quiere jugar
     * @param juego guardar el número de partidas
     */
	private static void numDePartidas(JuegoRuletaFortuna juego){
		int num = 0;
		
		try {
			num = Integer.parseInt(dameCadena("Diga la cantidad de partidas que quiere jugar: "));
			juego.getPartida1().setNumDePartidas(num);
		}
		catch(Exception e) {
			System.out.println("Va a jugar 3 partidas");
		}
	}
	
	/**
     * Elegir mode de juego y establecer la frase con que se va a jugar
     * @param juego poner frase y número de partidas 
     */
	private static void datosPartida(JuegoRuletaFortuna juego) {
		String aux = "";
		String aux2 = "";
		
		while(!aux.equalsIgnoreCase("si") && !aux.equalsIgnoreCase("no")) {
			aux = dameCadena("¿Quiere elegir dificultad de la partida(Si / No): ");
			if(aux.equalsIgnoreCase("si")) {
				try {
					aux2 = dameCadena("Elija el Modo de Dificultad(Novato / Medio / Experto): ");
					juego.setModoDeJuego(aux2);
					juego.getFrase1().setFrase(aux2.toLowerCase());	
				} catch(Exception e) {
					aux = "";
				}			
			} 
		}
		
	}
	
	/**
     * Realizar la tirada y escoger y comprobar letra
     * @param juego poner la tirada, verificar si la letra es correcta
     */
	private static void pedirLetra(JuegoRuletaFortuna juego) {
		String aux = "";
		char aux2 = " ".charAt(0);
		char auxVC = " ".charAt(0);
		juego.getTirada1().setTirada();
		
		System.out.printf("%n%nSu tirada es: %d euros%n", juego.getTirada1().getTirada());
		while(auxVC != "c".charAt(0) && auxVC !="v".charAt(0)) {
			auxVC = dameCadena("¿Quiere pedir una consonante o comprar una vocal? (c->consonante / v->vocal): ").toLowerCase().charAt(0);
			System.out.println(aux);
		}
		if(auxVC == "c".charAt(0)) {
			while(!juego.getCaracter1().esConsonante(aux2)) {
				aux2 = dameCadena("Diga la consonante que quiere: ").toLowerCase().charAt(0);	
			}
		} else if(auxVC == "v".charAt(0)) {
			if(juego.getPartida1().getPuntuacion() < 30) {
				System.out.printf("Tiene %d euros y necesita al menos 30 euros para comprar una vocal%n", juego.getPartida1().getPuntuacion());
				auxVC = " ".charAt(0);
			} else {
				System.out.printf("Tiene %d euros%n%n", juego.getPartida1().getPuntuacion());
				while(!juego.getCaracter1().esVocal(aux2)) {
					
					aux2 = dameCadena("\nDiga la vocal que quiere: ").toUpperCase().charAt(0);						
				}
			}
		}
		letraDicha(aux2, auxVC, juego);
	}
	
	/**
     * Sumar puntuacion y ver si la letra ya se dijo
     * @param aux2 saber si la letra fue dicha
     * @param auxVC saber si la letra era vocal o consonante
     * @param juego acceder a la puntuacion y las letras dichas
     */
	private static void letraDicha(char aux2, char auxVC, JuegoRuletaFortuna juego) {
		if(juego.letraEnArray(aux2)) {
			System.out.println("Ya he dicho esta letra, pierde un turno");
		}else {
			if(auxVC == "c".charAt(0)) {
				juego.getPartida1().setPuntuacion(juego.getPartida1().getPuntuacion()+juego.getTirada1().getTirada()*juego.fraseCompletandose());
			} else if(auxVC == "v".charAt(0)){
				juego.getPartida1().setPuntuacion(juego.getPartida1().getPuntuacion()-30);
				System.out.printf("Tiene %d euros", juego.getPartida1().getPuntuacion());
			}
		}
	}
	
	/**
     * Enseñar como va resolviendo la frase y reiniciar array de tiradas y letras
     * @param juego Saber cuándo se queda sin intentos
     */
	private static void juegoCompleto(JuegoRuletaFortuna juego) {
		juego.fraseEnBlanco();
		int j=1;
		while(j <= juego.getIntentos()) {
			pintarFrase(juego);
			pedirLetra(juego);
			juego.fraseCompletandose();
			if(adivinarFrase(juego, j)) {
				return;
			}
			j++;
			
		}
		if(j>juego.getIntentos()) {
			System.out.println("\n\nLo siento, le han acabado los intentos, ha perdido la partida");
		}
		juego.getTirada1().reiniciarArrayTiradas();
		juego.getCaracter1().reiniciarArrayLetras();
	}
	
	/**
     * Enseñar como va resolviendo la frase
     * @param juego Para poder pintar la frase 
     */
	private static void pintarFrase(JuegoRuletaFortuna juego) {
		for(int i=0; i < juego.getFraseSinResolver().length; i++) {
			System.out.print(juego.getFraseSinResolver()[i] + " ");
		}
	}
	
	/**
     * Si la quiere adivinar
     * @param juego Para poder pintar la frase y saber cuándo se queda sin intentos
     * @param j llevar cuenta de los intentos
     * @return debuelve true si se a adivinado la frase y false si no
     */
	private static boolean adivinarFrase(JuegoRuletaFortuna juego, int j) {
		String aux = "";
		String aux2 = "";
		
		while(!aux.equalsIgnoreCase("si") && !aux.equalsIgnoreCase("no")) {
			pintarFrase(juego);
			aux = dameCadena("\n\n¿Quiere adivinar la frase? Si/No");
			if(aux.equalsIgnoreCase("si")) {
					aux2 = dameCadena("Diga la frase:");
					if(aux2.equalsIgnoreCase(juego.getFrase1().getFrase())) {
						System.out.println("Felicidades ha adivinado la frase");
						return true;
					}
					else {
						System.out.println("Lo siento, esa no es la frase correcta, ha perdido un intento");
						j++;
					}
			}
		}
		return false;
	}
	
	/**
     * En caso de que el usuario quiera dejar de jugar antes de que se le acabe el numero de partidas que quería jugar
     * @param juego Acceder al numero de partidas
     * @param i Saber cuantas partidas ha jugado
     * @return true si quiere dejar de jugar false si quiere seguir jugando
     */
	private static boolean dejarDeJugar(int i, JuegoRuletaFortuna juego){
		String aux = "";
		System.out.printf("Le quedan %d partidas por jugar", juego.getPartida1().getNumDePartidas()-i+1);
		while(!aux.equalsIgnoreCase("si") && !aux.equalsIgnoreCase("no")) {
			aux = dameCadena("\n\n¿Quiere dejar de jugar? Si/No");
			if(aux.equalsIgnoreCase("si")) {
				return true;
			}
		}
		return false;
	}
	
	/**
     * Preguntar si el jugador quiere guardar la partida
     * @param juego guardar el objeto juego y acceder a vaciar los array de letras y tiradas
     */
	private static void quiereGuardar(JuegoRuletaFortuna juego) {
		String aux = "";
		while(!aux.equalsIgnoreCase("si") && !aux.equalsIgnoreCase("no")) {
			aux = dameCadena("\n\n¿Quiere guardar la partida? Si/No");
			if(aux.equalsIgnoreCase("si")) {
				guardarPartida(juego);
			}
		}
		
	}
	
	/**
	 * Guardar las partidas jugadas en archivo json
	 * @param juego acceder al alias del jugador
	 */
	private static void guardarPartida(JuegoRuletaFortuna juego) {
		String filePath = "./data/jugadas_"+juego.getJugador1().getAlias()+".json";
				
			try(PrintWriter out = new PrintWriter(new FileWriter(filePath))) {
				Gson gson=new Gson();
				String objJSONSerialized = gson.toJson(array);
				out.write(objJSONSerialized);
				out.close();
		    } catch (IOException e) {
		    	System.out.println(e);
		    }
	}
	
	/**
	 * Agregar Partida a Array
	 * @param juego acceder al alias del jugador
	 */
	private static void addPartida(JuegoRuletaFortuna juego) {
		
		try {
			jsonObj.put("alias", juego.getJugador1().getAlias());
			jsonObj.put("frase", juego.getFrase1().getFrase());
			jsonObj.put("modoDeJuego", juego.getModoDeJuego());
			jsonObj.put("puntuación", juego.getPartida1().getPuntuacion());
			jsonObj.put("tiradas", juego.getTirada1().getArrayTiradas());
			jsonObj.put("letras", juego.getCaracter1().getArrayLetras());
			jsonObj.put("fraseSinResolver", juego.getFraseSinResolver());
		} catch (JSONException e) {
			System.out.println(e);
		}
		
		array.add(jsonObj);
	}

	
}
	
