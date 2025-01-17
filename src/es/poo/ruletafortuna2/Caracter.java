package es.poo.ruletafortuna2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Eliani Alvarez Herrera
 */

public class Caracter {
	private char letra;
	private char[] vocales = new char[]{'A', 'E', 'I', 'O', 'U'};
	private static List<Character> arrayLetras = new ArrayList<>();

	/**
     * Acceder al caracter actual
     * @return devuelve el último caracter en entrado
     */
	public char getLetra() {
		return Character.toUpperCase(letra);
	}

	/**
     * Guardar la letra actual
     * @param letra La que hay que guardar
     */
	public void setLetra(char letra) {
		this.letra = letra;
	}

	/**
     * Comprobar si la letra es Vocal
     * @param letra la que hay que comprobar
     * @return true si es vocal, false si no lo es
     */
	public boolean esVocal(char letra) {
		if (Character.isAlphabetic(letra)) {
			for (char c : vocales) {
			    if (c == letra) {
			        return true;
			    }
			}
		}
		return false;
	}
	
	/**
     * Comprobar si la letra es Consonante
     * @param letra la que hay que comprobar
     * @return true si es consonante, false si no lo es
     */
	public boolean esConsonante(char letra) {
		return Character.isAlphabetic(letra) && !esVocal(letra);
	}
	
	/**
     * Agregar al array de letras dichas la nueva letra
     * @param caract la letra a agregar
     */
	public void addToArrayLetras(char caract) {
		arrayLetras.add(caract);
	}
	
	public void reiniciarArrayLetras() {
		arrayLetras = null;
	}
	

	/**
     * Acceder al array de letras dichas
     * @return array de letras dichas
     */
	public List<Character> getArrayLetras() {
		return arrayLetras;
	}
}
