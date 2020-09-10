package negocio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Serializador {

	public static void serializar(Mazo mazo) {
		try {
			ObjectOutputStream output = new ObjectOutputStream(
					new FileOutputStream(System.getProperty("user.dir") + "/Mazos/" + mazo.getNombre()));
			output.writeObject(mazo);
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static Mazo deserializar(String nombre) {
		Mazo ret = null;
		try {
			ObjectInputStream input = new ObjectInputStream(
					new FileInputStream(System.getProperty("user.dir") + "/Mazos/" + nombre));
			ret = (Mazo) input.readObject();
			input.close();
		} catch (IOException | ClassNotFoundException e) {
			try {
				ObjectInputStream input = new ObjectInputStream(Class.class.getResourceAsStream("/Mazos/" + nombre));
				ret = (Mazo) input.readObject();
				input.close();
			} catch (IOException | ClassNotFoundException e1) {
				e.printStackTrace();
				e1.printStackTrace();
			}
		}
		return ret;
	}

}
