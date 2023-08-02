package negocio;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import negocio.efecto.*;
import negocio.habilidades.Habilidad;
import negocio.habilidades.Monstruos;
import negocio.habilidades.Nilfgaard;
import negocio.habilidades.ReinosDelNorte;
import negocio.habilidades.Scoiatael;
import negocio.habilidades.Skellige;
import negocio.carta.*;

public class CreadorBarajas {

	private Baraja baraja;
	private List<Unidad> invocadas;

	public Baraja getBaraja(int baraja) {
		this.invocadas = new ArrayList<Unidad>();
		crearBaraja(baraja);
		this.baraja.ordenar();
		return this.baraja;
	}

	private void crearBaraja(int id) {

		try {			
			InputStream is = getClass().getResourceAsStream("/Barajas.xml");
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = factory.newDocumentBuilder();
			Document document = documentBuilder.parse(is);

			document.getDocumentElement().normalize();

			NodeList barajas = document.getElementsByTagName("baraja");
			baraja = new Baraja();
			
			for (int i = 0; i < barajas.getLength(); i++) {

				Node b = barajas.item(i);

				if (b.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) b;
					int idBaraja = Integer.parseInt(element.getElementsByTagName("idBaraja").item(0).getTextContent());
					String nombre = "";
					
					if (idBaraja == id) {
						nombre = element.getElementsByTagName("nombre").item(0).getTextContent();
						baraja.setNombre(nombre);
						baraja.setId(idBaraja);
						baraja.setHabilidad(crearHabilidad(idBaraja));
						crearCartas(element, nombre);
					} else if (idBaraja == Baraja.NEUTRAL) {
						nombre = element.getElementsByTagName("nombre").item(0).getTextContent();
						crearCartas(element, nombre);
					} else if (idBaraja == Baraja.ESPECIAL) {
						nombre = element.getElementsByTagName("nombre").item(0).getTextContent();
						crearCartas(element, nombre);
					} else if (idBaraja == Baraja.INVOCADAS) {
						nombre = element.getElementsByTagName("nombre").item(0).getTextContent();
						crearCartas(element, nombre);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Habilidad crearHabilidad(int idBaraja) {
		Habilidad ret = null;
		if(idBaraja == Baraja.REINOS_DEL_NORTE) {
			ret = new ReinosDelNorte();
		}
		else if(idBaraja == Baraja.NILFGAARD) {
			ret = new Nilfgaard();
		}
		else if(idBaraja == Baraja.MONSTRUOS) {
			ret = new Monstruos();
		}
		else if(idBaraja == Baraja.SCOIATAEL) {
			ret = new Scoiatael();
		}
		else if(idBaraja == Baraja.SKELLIGE) {
			ret = new Skellige();
		}
		return ret;
	}

	private void crearCartas(Element b, String nombreBaraja) {

		NodeList cartas = b.getElementsByTagName("carta");

		for (int j = 0; j < cartas.getLength(); j++) {
			Node nodoCarta = cartas.item(j);
			if (nodoCarta.getNodeType() == Node.ELEMENT_NODE) {
				Element element1 = (Element) nodoCarta;

				Carta carta = null;

				int tipo = Integer.parseInt(element1.getElementsByTagName("tipo").item(0).getTextContent());

				if (tipo == Carta.UNIDAD) {
					Unidad unidad = new Unidad();
					unidad.setFuerza(Integer.parseInt(element1.getElementsByTagName("valor").item(0).getTextContent()));
					unidad.setEsEspia(Boolean.parseBoolean(element1.getElementsByTagName("esEspia").item(0).getTextContent()));
					unidad.setAgil(Boolean.parseBoolean(element1.getElementsByTagName("esAgil").item(0).getTextContent()));
					unidad.setHeroe(Boolean.parseBoolean(element1.getElementsByTagName("esHeroe").item(0).getTextContent()));					
					carta = unidad;
				}else if (tipo == Carta.INVOCADORA) {
					Invocadora invocadora = new Invocadora();
					invocadora.setFuerza(Integer.parseInt(element1.getElementsByTagName("valor").item(0).getTextContent()));
					invocadora.setEsEspia(Boolean.parseBoolean(element1.getElementsByTagName("esEspia").item(0).getTextContent()));
					invocadora.setAgil(Boolean.parseBoolean(element1.getElementsByTagName("esAgil").item(0).getTextContent()));
					invocadora.setHeroe(Boolean.parseBoolean(element1.getElementsByTagName("esHeroe").item(0).getTextContent()));
					
					NodeList relaciones = element1.getElementsByTagName("cartaRelacionada");
					int idInvocada = Integer.parseInt(relaciones.item(0).getTextContent());
					Unidad invocada = getInvocada(idInvocada);
					invocadora.setInvocada(invocada);
					carta = invocadora;
				}else if (tipo == Carta.RELACIONADA) {
					Relacionada relacionada = new Relacionada();
					relacionada.setFuerza(Integer.parseInt(element1.getElementsByTagName("valor").item(0).getTextContent()));
					relacionada.setFila(Integer.parseInt(element1.getElementsByTagName("posicion").item(0).getTextContent()));
					relacionada.setEsEspia(Boolean.parseBoolean(element1.getElementsByTagName("esEspia").item(0).getTextContent()));
					relacionada.setAgil(Boolean.parseBoolean(element1.getElementsByTagName("esAgil").item(0).getTextContent()));
					relacionada.setHeroe(Boolean.parseBoolean(element1.getElementsByTagName("esHeroe").item(0).getTextContent()));
					
					NodeList relaciones = element1.getElementsByTagName("cartaRelacionada");
					relacionada.setCantidad(relaciones.getLength());
					for (int e = 0; e < relaciones.getLength(); e++) {
						int relacion = Integer.parseInt(relaciones.item(e).getTextContent());
						relacionada.agregarRelacion(relacion);
					}
					carta = relacionada;
				} else if (tipo == Carta.LIDER) {
					Lider lider = new Lider();
					lider.setFila(Integer.parseInt(element1.getElementsByTagName("posicion").item(0).getTextContent()));
					carta = lider;
				} else if (tipo == Carta.CLIMATICA) {
					Climatica climatica = new Climatica();
					climatica.setFila(Integer.parseInt(element1.getElementsByTagName("posicion").item(0).getTextContent()));
					carta = climatica;
				} else if (tipo == Carta.ESPECIAL) {
					Especial especial = new Especial();
					especial.setFila(Integer.parseInt(element1.getElementsByTagName("posicion").item(0).getTextContent()));				
					carta = especial;
				}
				
				carta.setId(Integer.parseInt(element1.getElementsByTagName("idCarta").item(0).getTextContent()));
				carta.setNombre(element1.getElementsByTagName("nombre").item(0).getTextContent());
				carta.setNombreBaraja(nombreBaraja);
				carta.setFila(Integer.parseInt(element1.getElementsByTagName("posicion").item(0).getTextContent()));
				carta.setEfecto(crearEfecto(carta,Integer.parseInt(element1.getElementsByTagName("efectoEntrada").item(0).getTextContent())));
				
				if(nombreBaraja.equals("Invocados")) {
					this.invocadas.add((Unidad) carta);
				}
				else {
					baraja.agregarCarta(carta);
				}
			}
		}
	}

	private Unidad getInvocada(int idInvocada) {
		Unidad ret = null;
		for(Unidad unidad : this.invocadas) {
			if(unidad .getId() == idInvocada) {
				ret = unidad ;
			}
		}
		return ret;
	}

	private Efecto crearEfecto(Carta carta, int tipo) {
		Efecto ret = null;
		Relacionada relacionada = null;
		if (carta instanceof Relacionada) {
			relacionada = (Relacionada) carta;
		}
		Invocadora invocadora = null;
		if (carta instanceof Invocadora) {
			invocadora = (Invocadora)carta;
		}
		
		if(tipo == Efecto.MEDICO) 
			ret = new Medico(carta);
		else if(tipo == Efecto.MORAL)
			ret = new Moral(carta);
		else if(tipo ==  Efecto.ESPIA)
			ret = new Espia(carta);
		else if(tipo == Efecto.QUEMAR)
			ret = new Quemar(carta);
		else if(tipo == Efecto.CUERNO)
			ret = new Cuerno(carta);
		else if(tipo == Efecto.MARDROEME)
			ret = new Mardroeme(carta);
		else if(tipo == Efecto.INVOCAR)
			ret = new Invocar(invocadora);
		else if(tipo == Efecto.BERSERKER) 
			ret = new Berserker(invocadora);
		else if(tipo == Efecto.REUNION)
			ret = new Reunion(relacionada);
		else if(tipo == Efecto.COMPAÑERO)
			ret = new Compañero(relacionada);
		else if(tipo == Efecto.CLIMA)
			ret = new Clima(carta.getFila());
		else if(tipo == Efecto.SENUELO)
			ret = new Senuelo(carta);
		else if(tipo == Efecto.JUGAR_CLIMATICA)
			ret = new JugarClimatica(carta);
		else if(tipo == Efecto.ESPIAR_MANO) 
			ret = new EspiarMano(carta);
		else if(tipo == Efecto.ANULAR_LIDER) 
			ret = new AnularLider();
		else if(tipo == Efecto.ROBAR) 
			ret = new Robar(carta);
		else if(tipo == Efecto.MEDICO_RANDOM) 
			ret = new MedicoRandom();
		else if(tipo == Efecto.RESUSITAR) 
			ret = new Resusitar(carta);
		else if(tipo == Efecto.CUERNO_ESPIAS) 
			ret = new CuernoEspias(carta);
		else if(tipo == Efecto.SACRIFICAR) 
			ret = new Sacrificar(carta);
		else if(tipo == Efecto.CARTA_EXTRA) 
			ret = new CartaExtra(carta);
		else if(tipo == Efecto.OPTIMIZAR) 
			ret = new Optimizar();
		else if(tipo == Efecto.DEVOLVER_AL_MAZO) 
			ret = new DevolverAlMazo();
		else if(tipo == Efecto.ALTERAR_CLIMA) 
			ret = new AlterarClima(carta);
		return ret;
	}
}
