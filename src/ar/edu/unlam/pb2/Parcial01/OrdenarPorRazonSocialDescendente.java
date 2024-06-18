package ar.edu.unlam.pb2.Parcial01;

import java.util.Comparator;

public class OrdenarPorRazonSocialDescendente implements Comparator<Cliente> {

	@Override
	public int compare(Cliente c1, Cliente c2) {
		return c2.getRazonSocial().compareTo(c1.getRazonSocial());
	}

}
