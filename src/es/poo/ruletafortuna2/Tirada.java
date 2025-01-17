package es.poo.ruletafortuna2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author: Eliani Alvarez Herrera
 */

public class Tirada {
	private int valor;
	private List<Integer> arrayTiradas = new ArrayList<>();
	 
	/**
     * Escoger aleatoriamente el valor de la tirada y la añade al array de tiradas
     */
	public void setTirada() {
		Random random = new Random();
		valor = random.nextInt(5)*10;
		getArrayTiradas().add(valor);
	}
	
	/**
     * @return devuelve el valor de la tirada
     */
	public int getTirada() {
		return valor;
	}

	/**
	 * Vaciar el array de tiradas para la nueva partida
	 */
	public void reiniciarArrayTiradas() {
		arrayTiradas = null;
	}
	
	/**
	 * @return array de tiradas
	 */
	public List<Integer> getArrayTiradas() {
		return arrayTiradas;
	}

}
