package es.poo.ruletafortuna2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;

/**
 * @author: Eliani Alvarez Herrera
 */

public class Jugador {
	private String alias;
	private String nombre;
	private String password;
	private Date dateOfBirth;
	private Date fechaHoy = new Date();
	private int accept = 0;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private DateTimeFormatter fORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private JSONObject jsonObje = new JSONObject();
	private ArrayList<JSONObject> array = new ArrayList<>();
	private String filePath = "./data/jugadores.json";
	
	
	/**
     * Constructor de la clase Jugador
     * @param alias para guardar el alias
     * @param password para guardar el password
     * @param date para guardar la fecha de nacimiento
     */
	public Jugador(String alias, String password, Date date) {
		this.alias = alias;
		this.password = password;
		dateOfBirth = date;
	}

	/**
     * @return devuelve el alias
     */
	public String getAlias() {
		return alias;
	}

	/**
     * @param alias Guarda el alias
     */
	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
     * @return devuelve el nombre
     */
	public String getNombre() {
		return nombre;
	}

	/**
     * @param nombre Guarda el nombre
     */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
     * @return devuelve el nombre
     */
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * Guardar la fecha de nacimiento y comprobar que sea antes del día actual
     * @param dateOfBirth para guardar la fecha de nacimiento 
     */
	public void setDateOfBirth(String dateOfBirth) {
		
		if(Boolean.TRUE.equals(validarFecha(dateOfBirth))) {
			try {
				this.dateOfBirth = sdf.parse(dateOfBirth);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if(this.dateOfBirth.before(fechaHoy)) {
				accept = 1;
			}	
		} 
	}
	
	/**
	 * Saber si el jugador tiene más de 10 años
	 * @return true si es mayor de 10 años, false si es menor
	 */
	public boolean mas10anyos() {
		long diff = fechaHoy.getTime() - dateOfBirth.getTime();
		diff = (diff/(1000l*60*60*24*365));
		return diff>=10;
	}
	
	/**
	 * Guardar la fecha de nacimiento y comprobar que sea antes del día actual
     * @param dateOfBirth para guardar la fecha de nacimiento 
     */
	private TemporalAccessor parseDate(String dateOfBirth) {
	    return fORMATTER.parseBest(dateOfBirth, LocalDate::from, YearMonth::from, Year::from);
	}
	
	/**
	 * Verificar que la fecha dada es una fecha
     * @param dateOfBirth para compobar la fecha de nacimiento 
     * @return true si la fecha es correcta y false si es incorrecta
     */
	private Boolean validarFecha(String dateOfBirth) {
		try {
	      TemporalAccessor parsedDate = parseDate(dateOfBirth);
	      return fORMATTER.format(parsedDate).equals(dateOfBirth);
	    } catch (DateTimeParseException e) {
	      return false;
	    }
	}

	/**
	 * Guardar y verificar que la contraseña sea mayor de 6 caracteres
     * @param password para guardar y comprobar la contraseña
     */
	public void setPassword(String password) {
		if(password.length() < 6) {
			accept = 1;
		} else {
			accept = 0;
			this.password = password;
		}
	}

	/**
     * @return si la contraseña fue aceptada o no
     */
	public int getAccept() {
		return accept;
	}
	
	/**
	 * Comparar contraseña puesta con guardada
	 * @param password Comprobar que la contraseña introducida coincide con la guardada
	 * @return true si es igual, false si no
	 */
	public boolean passwordMatch(String password) {
		return password.equals(this.password);
	}


	
	/**
	 * agregar jugador al array
	 */
	public void addJugador() {
		
		try {
			jsonObje.put("alias", alias);
			jsonObje.put("password", password);
			jsonObje.put("dateOfBirth", sdf.format(dateOfBirth));
		} catch  (Exception e) {
			System.out.println(e);
		}
		array.add(jsonObje);
		System.out.println(jsonObje.get(1));
		serializacionJugadores();
	}
	
	/**
	 * Copiar el archivo json en un array para poder verificar los jugadores existentes
	 */
	public void deserializacionJugadores() {
		
		try
        {   
			JSONParser parser = new JSONParser();
			array = (JSONArray) parser.parse(new FileReader(filePath));

        } 
        catch(Exception e)
        {
            System.out.println(e);
        }
		 
	}
	
	
	/**
	 * Comprobar que el usuario este registrado
	 * @param alias Ver si el usuario ya está registrado
	 * @return true si está registrado, false si no
	 */
	public boolean existeAlias(String alias) {
		boolean encontrado=false;
		int i= 0;
		File file = new File(filePath);
		if(file.exists()) {
			for(i = 0; i<array.size() && !encontrado; i++) {
				if(array.get(i).get("alias").toString().equals(alias)) {
					encontrado=true;
					this.alias = alias;
					password = (String) array.get(i).get("password");
					try {
						dateOfBirth = sdf.parse(array.get(i).get("dateOfBirth").toString());
					} catch (ParseException e) {
						System.out.println(e);
					}
				}
			}				
		}
		return encontrado;
	}
	
	/**
	 * Convertir el array de Jugadores a json y guardarlo en un archivo
	 */
	public void serializacionJugadores() {

		try(PrintWriter out = new PrintWriter(new FileWriter(filePath))) {
			Gson gson=new Gson();
			if(!array.isEmpty()) {
				String objJSONSerialized = gson.toJson(array);
				out.write(objJSONSerialized);
			}
	    } catch (IOException e) {
	    	System.out.println(e);
	    }
		
	}



}
