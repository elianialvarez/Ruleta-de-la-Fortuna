package es.poo.ruletafortuna2;

/**
 * @author: Eliani Alvarez Herrera
 */ 

public class Partida {
	private int numDePartidas;
	private int puntuacion;
	private String juego = "Juego Ruleta de la Fortuna";

	
	/**
	 * Constructor para poner el numero de partidas en caso de que no se escoja
     */
	public Partida(){
		numDePartidas = 3;
		puntuacion = 0;
	}

	/**
     * @return devuelve el número de partidas
     */
	public int getNumDePartidas() {
		return numDePartidas;
	}

	/**
     * @param numDePartidas para guardar el número de partidas
     */
	public void setNumDePartidas(int numDePartidas) {
		this.numDePartidas = numDePartidas;
	}
	
	/**
     * @param puntuacion guardar a puntuación actual
     */
	public void setPuntuacion(int puntuacion) {
		this.puntuacion = puntuacion;
	}

	/**
     * @return devuelve la puntuación
     */
	public int getPuntuacion() {
		return puntuacion;
	}

	/**
     * @return devuelve el nombre del juego que se está jugando
     */
	public String getJuego() {
		return juego;
	}	
}
