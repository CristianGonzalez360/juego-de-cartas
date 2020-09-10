package red;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import interfaz.VistaRed;
import negocio.Juego;
import red.accion.Accion;

public class Red {

	private ServerSocket server;
	private Socket socket;
	private VistaRed vista;

	private ObjectInputStream ois;
	private ObjectOutputStream oos;

	private Juego juego;

	private Red instancia;

	public Red() {
		this.instancia = this;
	}

	public void crear(int puerto) {
		try {
			server = new ServerSocket(puerto);
			Servidor servidor = new Servidor();
			servidor.start();
			this.vista.mostrarMensaje("Esperando...");
		} catch (IOException e) {
			vista.mostrarMensaje(e.getMessage());
			e.printStackTrace();
		}
	}

	public void setJuego(Juego juego) {
		this.juego = juego;
	}

	public void conectar(String ip, int puerto) {
		vista.mostrarMensaje("Conectando...");
		Conexion conexion = new Conexion(ip, puerto);
		conexion.start();
	}

	public void setVista(VistaRed vista) {
		this.vista = vista;
		this.vista.setRed(this);
	}

	public void escuchar() {
		Oyente o = new Oyente();
		o.start();
	}

	public void enviar(Accion accion) {
		try {
			oos.reset();
			oos.writeUnshared(accion);
			oos.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private class Servidor extends Thread {
		@Override
		public void run() {
			try {
				socket = server.accept();
				vista.mostrarMensaje("Conectado");
				vista.mostrarMensaje("Cargando...");
				oos = new ObjectOutputStream(socket.getOutputStream());
				ois = new ObjectInputStream(socket.getInputStream());
				juego.setRed(instancia);
				server.close();
			} catch (IOException e) {
				vista.mostrarMensaje("Se cerró el servidor");
				vista.reiniciar();
			}
		}
	}

	private class Conexion extends Thread {

		private String ip;
		private int puerto;

		public Conexion(String ip, int puerto) {
			this.ip = ip;
			this.puerto = puerto;
		}

		@Override
		public void run() {
			try {
				socket = new Socket(ip, puerto);
				vista.mostrarMensaje("Conectado!!");
				vista.mostrarMensaje("Cargando...");
				oos = new ObjectOutputStream(socket.getOutputStream());
				ois = new ObjectInputStream(socket.getInputStream());
				juego.setRed(instancia);
			} catch (IOException e) {
				vista.mostrarMensaje("No se pudo conectar.");
				vista.reiniciar();
			}
		}
	}

	public void cancelarServidor() {
		try {
			if(this.server != null) {
				this.server.close();
			}
			
		} catch (IOException e) {
			vista.mostrarMensaje(e.getMessage());
			e.printStackTrace();
		}
	}

	private class Oyente extends Thread {

		@Override
		public void run() {

			boolean conectado = true;

			while (conectado) {
				try {
					Accion accion = (Accion) ois.readUnshared();
					accion.ejecutar(juego);
				} catch (ClassNotFoundException | IOException e) {
					conectado = false;
					juego.salir("Se cerró la conexión");
					reiniciar();
					vista.reiniciar();
				}
			}
		}
	}
	
	public void reiniciar() {
		try {
			this.ois.close();
			this.oos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
