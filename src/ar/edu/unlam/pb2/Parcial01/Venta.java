package ar.edu.unlam.pb2.Parcial01;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Venta {

	private String codigo;
	private Cliente cliente;
	private Vendedor vendedor;
	private Map<Vendible, Integer> renglones;

	public Venta(String codigo, Cliente cliente, Vendedor vendedor) {
		// TODO: Completar el constructor para el correcto funcionamiento del software
		this.codigo=codigo;
		this.cliente=cliente;
		this.vendedor=vendedor;
		this.renglones=new HashMap<Vendible, Integer>();
	}
	
	// TODO: Completar con los getters y setters necesarios
	public String getCodigo() {
		return codigo;
	}
	
	public Cliente getCliente() {
		return cliente;
	}
	
	public Vendedor getVendedor() {
		return vendedor;
	}

	public void setVendedor(Vendedor vendedor) {
		this.vendedor = vendedor;
	}
	
	public Map<Vendible, Integer> getRenglones() {
		return renglones;
	}

	public void setRenglones(Map<Vendible, Integer> renglones) {
		this.renglones = renglones;
	}

	public void agregarRenglon(Vendible vendible, Integer cantidad) {
		// TODO: Agregar el vendible a la venta
	    this.renglones.put(vendible, cantidad);
	
	}
	
	@Override
	public String toString() {
		return "Venta: " + codigo + "\n" + "Cliente" + cliente + "\n" + "Vendedor" + vendedor + "\n";
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Venta other = (Venta) obj;
		return Objects.equals(codigo, other.codigo);
	}
	
}
