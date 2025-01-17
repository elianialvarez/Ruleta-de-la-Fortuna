package es.poo.ruletafortuna2;


/**
 * @author: Eliani Alvarez Herrera
 */

public class JuegoRuletaFortuna {
	private Dificultad modoDeJuego;
	private Partida partida1;
	private Jugador jugador1;
	private Frase frase1;
	private Caracter caracter1;
	private Tirada tirada1;
	private int intentos;
	private char[] fraseSinResolver;
	
	
	
	/**
     * Constructor para guardar todos los objetos para tenerlos todos juntos en una clase para poder guardarlos
     */
	public JuegoRuletaFortuna(Frase frase1, Caracter caracter1, Tirada tirada1, Partida partida1, Jugador jugador1) {
		modoDeJuego = Dificultad.NOVATO;
		intentos = 10;
		this.frase1 = frase1;
		this.caracter1 = caracter1;
		this.tirada1 = tirada1;
		this.partida1 = partida1;
		this.jugador1 = jugador1;
	}
	
	public enum Dificultad {
		NOVATO,
		MEDIO,
		EXPERTO
	}

	/**
     * @return devuelve la dificultad del juego
     */
	public Dificultad getModoDeJuego() {
		return modoDeJuego;
	}

	/**
     * @param modoDeJuego para guardar el modo de dificultad
     */
	public void setModoDeJuego(String modoDeJuego) {
		this.modoDeJuego = Dificultad.valueOf(modoDeJuego.toUpperCase());
		setIntentos();
	}
	
	/**
     * Guardar el numero de intentos segun dificultad escogida
     */
	private void setIntentos() {
		if(modoDeJuego == Dificultad.NOVATO) {
			intentos = 10;
		} else if(modoDeJuego == Dificultad.MEDIO) {
			intentos = 8;
		} else {
			intentos = 5;
		}
	}
	
	/**
     * @return devuelve la cantidad de intentos
     */
	public int getIntentos() {
		return intentos;
	}
	
	/**
     * @return devuelve el objeto partida1
     */
	public Partida getPartida1() {
		return partida1;
	}
	
	/**
     * @param partida1 guardar el objeto partida1
     */
	public void setPartida1(Partida partida1) {
		this.partida1 = partida1;
	}

	/**
     * @return devuelve el objeto jugador1
     */
	public Jugador getJugador1() {
		return jugador1;
	}

	/**
     * @param jugador1 para guardar el objeto jugador1
     */
	public void setJugador1(Jugador jugador1) {
		this.jugador1 = jugador1;
	}

	/**
     * @return el objeto frase1
     */
	public Frase getFrase1() {
		return frase1;
	}

	/**
     * @param frase1 para guardar el objeto frase1
     */
	public void setFrase1(Frase frase1) {
		this.frase1 = frase1;
	}

	/**
     * @return devuelve el objeto caracter1
     */
	public Caracter getCaracter1() {
		return caracter1;
	}

	/**
     * @param caracter1 para guardar el objeto caracter1
     */
	public void setCaracter1(Caracter caracter1) {
		this.caracter1 = caracter1;
	}

	/**
     * @return devuelve el objeto tirada1
     */
	public Tirada getTirada1() {
		return tirada1;
	}

	/**
     * @param tirada1 para guardar el objeto tirada1
     */
	public void setTirada1(Tirada tirada1) {
		this.tirada1 = tirada1;
	}

	/**
     * Para crear la frase con los espacios en blancos en vez de las letras
     */
	public void fraseEnBlanco() {
		int j = 0;
		fraseSinResolver = new char[frase1.getFrase().length()];
		for(int i=0; i< frase1.getFrase().length(); i++) {
			if(frase1.getFrase().charAt(i) >= "a".charAt(0) && frase1.getFrase().charAt(i) <= "z".charAt(0) || frase1.getFrase().charAt(i) >= "A".charAt(0) && frase1.getFrase().charAt(i) <= "Z".charAt(0) || frase1.getFrase().charAt(i) == "ñ".charAt(0) || frase1.getFrase().charAt(i) == "Ñ".charAt(0)) {
				fraseSinResolver[j] = "_".charAt(0);
			} else {
				fraseSinResolver[j] = frase1.getFrase().charAt(i);
			} 
			j++;
		}
	}
	
	/**
     * Para ir completando la frase en blanco con las letras dichas aceptadas
     * @return numero de veces que se repite la letra dada en la frase
     */
	public int fraseCompletandose() {
		int i = 0;
		int j = 0;
		while(frase1.getFrase().indexOf(caracter1.getLetra(), i) != -1) {
		   i = frase1.getFrase().indexOf(caracter1.getLetra(), i) + 1;
		   fraseSinResolver[i-1] = caracter1.getLetra();
		   j++;
		}
		return j;
	}

	/**
     * @return devuelve la frase por donde va completandose
     */
	public char[] getFraseSinResolver() {
		return fraseSinResolver;
	}
	
	/**
	 * @param aux es la letra que se ha dicho
     * @return true si la letra ya se ha dicho antes y la guarda y agrega al array de letras dichas
     */
	public boolean letraEnArray(char aux) {
		for(Character letter: getCaracter1().getArrayLetras()) {
			if(letter.equals(aux)) {
				return true;
			}	
		}
		getCaracter1().setLetra(aux);
		getCaracter1().addToArrayLetras(aux);
		return false;
	}
	

}
