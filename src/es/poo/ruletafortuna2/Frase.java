package es.poo.ruletafortuna2;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

/**
 * @author: Eliani Alvarez Herrera
 */

public class Frase {
private String fraseEscogida;

	/**
	 * Escoger frase del nivel Novato si no escoge el nivel
	 */
	public Frase() {
		fraseEscogida = FrasesNovato.values()[new Random().nextInt(FrasesNovato.values().length)].toString();
	}
	
	public enum FrasesNovato {
		UNO {
			@Override
			public String toString() {
		        return "TORTILLA DE PATATAS";
		    }
		},
		DOS {
			@Override
			public String toString() {
		        return "ARROZ CON LANGOSTA";
		      }
		},
		TRES {
			@Override
			public String toString() {
		        return "JUGADOR DE TENIS";
		      }
		}
	}
	
	public enum FrasesMedio {
		UNO {
			@Override
			public String toString() {
		        return "VOY A LLEVAR COMIDA A CASA";
		    }
		},
		DOS {
			@Override
			public String toString() {
		        return "HACE MUCHO CALOR EN LA PLAYA";
		      }
		},
		TRES {
			@Override
			public String toString() {
		        return "POLLO AL CURRY CON ARROZ Y LECHE DE COCO";
		      }
		}
	}
	
	public enum FrasesExperto {
		UNO {
			@Override
			public String toString() {
		        return "LA FORTUNA NO SOLO ES CIEGA, SINO QUE OFUSCA Y CIEGA";
		    }
		},
		DOS {
			@Override
			public String toString() {
		        return "QUEDATE CON QUIEN SEPA HACERTE MUCHO DAÑO Y NUNCA LO HAGA";
		      }
		},
		TRES {
			@Override
			public String toString() {
		        return "ME HAN REGALADO UN XILOFONO EL DIA DE MI CUMPLEAÑOS";
		      }
		}
	}

	/**
	 * Escoger frase del nivel de dificultad dada y guardarla
	 * @param tipoFrase el nivel de dificultad de la frase a escoger
	 */
	public void setFrase(String tipoFrase) {
		Random random = new Random();
		try {
			random = SecureRandom.getInstanceStrong();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		if(tipoFrase.equals("novato")) {
			fraseEscogida = FrasesNovato.values()[random.nextInt(FrasesNovato.values().length)].toString();
		} else if(tipoFrase.equals("medio")) {
			fraseEscogida = FrasesMedio.values()[random.nextInt(FrasesMedio.values().length)].toString();
		} else {
			fraseEscogida = FrasesExperto.values()[random.nextInt(FrasesExperto.values().length)].toString();
		}
	}
	
	/**
	 * @return devuelve la frase escogida
	 */
	public String getFrase() {
		return fraseEscogida;
	}
}
