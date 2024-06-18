package ar.edu.unlam.pb2.Parcial01;

public class Vendedor {

	private String dni;
	private String nombre;
	private boolean deLicencia;
	
	public Vendedor (String dni, String nombre) {
		// TODO: Completar el constructor para el correcto funcionamiento del software
		// Por defecto, los vendedores no estan de licencia
		this.dni=dni;
		this.nombre=nombre;
		this.deLicencia=false;
	}
	// TODO: Completar con los getters y setters necesarios

	public boolean isDeLicencia() {
		return deLicencia;
	}

	public void setDeLicencia(boolean deLicencia) {
		this.deLicencia = deLicencia;
	}

	public String getDni() {
		return dni;
	}

	public String getNombre() {
		return nombre;
	}
	
	
}
