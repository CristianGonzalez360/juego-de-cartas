package negocio.carta;

public class Invocadora extends Unidad {

	private Unidad invocada;
	private boolean esBerserker;
	
	public Unidad getInvocada() {
		return invocada;
	}

	public void setInvocada(Unidad invocada) {
		this.invocada = invocada;
		this.invocada.setRevivible(false);
	}

	public boolean esBerserker() {
		return esBerserker;
	}

	public void setEsBerserker(boolean esBerserker) {
		this.esBerserker = esBerserker;
	}
	
	
}
