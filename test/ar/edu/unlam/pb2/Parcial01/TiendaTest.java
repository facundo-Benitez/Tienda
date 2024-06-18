package ar.edu.unlam.pb2.Parcial01;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;

public class TiendaTest {
	private static final String NOMBRE_TIENDA="A ver si ahorra";
	private static final String CUIT_TIENDA="20-11111111-7";
	private Tienda miTienda;
	
	@Before
	public void init() {
		this.miTienda=new Tienda(CUIT_TIENDA, NOMBRE_TIENDA);
	}

	@Test (expected=VendedorDeLicenciaException.class)
	public void queAlIntentarAgregarUnaVentaParaUnVendedorDeLicenciaSeLanceUnaVendedorDeLicenciaException() throws VendedorDeLicenciaException {
		String dni="12.345.678";
		String nombre="Juan Perez";
		String ciutCliente="20-44444444-7";
		String razonSocial="Cliente 1";
	    Integer codigoItem=01;
		String nombreItem="Modem";
		Double precioItem=1000.00;
		Integer puntoDeReposicion=10;
		
		Vendible producto=new Producto(codigoItem,nombreItem,precioItem,puntoDeReposicion);
		this.miTienda.agregarProducto((Producto)producto,(Integer)5);
		Cliente cliente1=new Cliente(ciutCliente,razonSocial);
		this.miTienda.agregarCliente(cliente1);
		Vendedor vendedor1=new Vendedor(dni,nombre);
		this.miTienda.agregarVendedor(vendedor1);
		vendedor1.setDeLicencia(true);
		Venta venta1=new Venta("Venta1",cliente1,vendedor1);
		
		this.miTienda.agregarVenta(venta1);
		
	}

	@Test (expected=VendibleInexistenteException.class)
	public void queAlIntentarAgregarUnVendibleInexistenteAUnaVentaSeLanceUnaVendibleInexistenteException() throws VendedorDeLicenciaException, VendibleInexistenteException {
		String dni="12.345.678";
		String nombre="Juan Perez";
		String ciutCliente="20-44444444-9";
		String razonSocial="Cliente 1";
	    Integer codigoItem=01;
		String nombreItem="Modem";
		Double precioItem=1000.00;
		
        Vendedor vendedor1 = new Vendedor(dni, nombre);
        this.miTienda.agregarVendedor(vendedor1);
        Cliente cliente1 = new Cliente(ciutCliente,razonSocial);
        this.miTienda.agregarCliente(cliente1);
        Venta venta1 = new Venta("V001", cliente1, vendedor1);
        this.miTienda.agregarVenta(venta1);

        this.miTienda.agregarProductoAVenta("V001", new Producto(codigoItem,nombreItem, precioItem, 1));
		
	}


	@Test
	public void queSePuedaObtenerUnaListaDeProductosCuyoStockEsMenorOIgualAlPuntoDeReposicion() {
	    Integer codigoItem=01;
		String nombreItem="Modem";
		Double precioItem=1000.00;
		Integer puntoDeReposicion=5;
		
		Producto producto1 = new Producto(codigoItem,nombreItem, precioItem, puntoDeReposicion);
		this.miTienda.agregarProducto(producto1, 10);
		Producto producto2 = new Producto(2, "Teclado", 1500.0, 5);
        this.miTienda.agregarProducto(producto2, 2);

        List<Producto> productos =this.miTienda.obtenerProductosCuyoStockEsMenorOIgualAlPuntoDeReposicion();
        
        assertEquals((int)1, productos.size());
        assertEquals(producto2, productos.get(0));
		
	}

	@Test
	public void queSePuedaObtenerUnSetDeClientesOrdenadosPorRazonSocialDescendente() {
		String ciutCliente="20-44444444-9";
		String razonSocial="Cliente 3";
		
		Cliente cliente3 = new Cliente(ciutCliente, razonSocial);
		this.miTienda.agregarCliente(cliente3);
		Cliente cliente2 = new Cliente("20-22345678-9", "Cliente 2");
		this.miTienda.agregarCliente(cliente2);
		Cliente cliente1 = new Cliente("20-12345678-9", "Cliente 1");
		this.miTienda.agregarCliente(cliente1);
        
        TreeSet<Cliente> clientesOrdenados = this.miTienda.obtenerClientesOrdenadosPorRazonSocialDescendente();
        
		Integer iterador = 0;
		for (Cliente clienteActual : clientesOrdenados) {
			switch (iterador) {
			case 0:
				assertEquals(clienteActual, cliente3);
				break;
			case 1:
				assertEquals(clienteActual, cliente2);
				break;	
			case 2:
				assertEquals(clienteActual, cliente1);
				break;
	
			} 
		  iterador++;
		}
		
		assertEquals(3, clientesOrdenados.size());
	}

	@Test
	public void queSePuedaObtenerUnMapaDeVentasRealizadasPorCadaVendedor() throws VendedorDeLicenciaException {
		// TODO: usar como key el vendedor y Set<Venta> para las ventas
		String dni="12.345.678";
		String nombre="Juan Perez";
		String ciutCliente="20-44444444-9";
		String razonSocial="Cliente 1";
		
        Vendedor vendedor1 = new Vendedor(dni, nombre);
        this.miTienda.agregarVendedor(vendedor1);
        Vendedor vendedor2 = new Vendedor("87654321", "Maria Lopez");
        this.miTienda.agregarVendedor(vendedor2);
        Cliente cliente1 = new Cliente(ciutCliente, razonSocial);
        this.miTienda.agregarCliente(cliente1);
        
        Venta venta1 = new Venta("Venta01", cliente1, vendedor1);
        this.miTienda.agregarVenta(venta1);
        Venta venta2 = new Venta("Venta02", cliente1, vendedor1); 
        this.miTienda.agregarVenta(venta2);
        Venta venta3 = new Venta("Venta03", cliente1, vendedor2); 
        this.miTienda.agregarVenta(venta3);
       
		Integer iterador = 0;
		for (Map.Entry<Vendedor, Set<Venta>> entry : this.miTienda.obtenerLasVentasPorVendedor().entrySet()) {
			
			Vendedor vendedorActual = entry.getKey();
			Set<Venta> ventasDelVendedor = entry.getValue();
			
			switch (iterador) {		
			case 0:
				assertEquals(vendedorActual, vendedor1);
				assertEquals(2, ventasDelVendedor.size());
				break;
			case 1:
				assertEquals(vendedorActual, vendedor2);
				assertEquals(1, ventasDelVendedor.size());
				break;	
			} 
		  iterador++;
		}
	}

	@Test
	public void queSePuedaObtenerElTotalDeVentasDeServicios() throws VendedorDeLicenciaException, VendibleInexistenteException {
		String dni="12.345.678";
		String nombre="Juan Perez";
		String ciutCliente="20-44444444-9";
		String razonSocial="Cliente 1";
	    Integer codigoItem=01;
		String nombreItem="Modem";
		Double precioItem=1000.00;
		Integer puntoDeReposicion=5;
		
		Vendedor vendedor1 = new Vendedor(dni, nombre);
		this.miTienda.agregarVendedor(vendedor1);
        Cliente cliente1 = new Cliente(ciutCliente, razonSocial);
        this.miTienda.agregarCliente(cliente1);
        Producto producto1 = new Producto(codigoItem, nombreItem,precioItem, puntoDeReposicion);
        this.miTienda.agregarProducto(producto1, 10);
        Servicio servicio1 = new Servicio(3, "Soporte Tecnico", 300.0, "01-01-2023", "02-01-2023");
        this.miTienda.agregarServicio(servicio1);
           
        Venta venta1 = new Venta("Venta01", cliente1, vendedor1);
        this.miTienda.agregarVenta(venta1);
        this.miTienda.agregarProductoAVenta("Venta01", producto1);
        Venta venta2 = new Venta("Venta02", cliente1, vendedor1);
        this.miTienda.agregarVenta(venta2);
        this.miTienda.agregarServicioAVenta("Venta02", servicio1);

        Double totalEsperado=300.00;
        Double totalObtenido =this.miTienda.obtenerTotalDeVentasDeServicios();
        assertEquals(totalEsperado,totalObtenido, 0.01);
	}
	
	@Test
	public void queAlRealizarLaVentaDeUnProductoElStockSeActualiceCorrectamente() throws VendedorDeLicenciaException, VendibleInexistenteException {
	    String dni = "12.345.678";
	    String nombre = "Juan Perez";
	    String ciutCliente = "20-44444444-9";
	    String razonSocial = "Cliente 1";
	    Integer codigoItem = 1;
	    String nombreItem = "Modem";
	    Double precioItem = 1000.00;
	    Integer puntoDeReposicion = 5;
	    Integer cantidadInicial = 10;

	    Vendedor vendedor1 = new Vendedor(dni, nombre);
	    this.miTienda.agregarVendedor(vendedor1);
	    Cliente cliente1 = new Cliente(ciutCliente, razonSocial);
	    this.miTienda.agregarCliente(cliente1);
	    Producto producto1 = new Producto(codigoItem, nombreItem, precioItem, puntoDeReposicion);
	    this.miTienda.agregarProducto(producto1, cantidadInicial);
	    	    
	    Venta venta1 = new Venta("Venta01", cliente1, vendedor1);
	    this.miTienda.agregarVenta(venta1);
	    this.miTienda.agregarProductoAVenta("Venta01", producto1);

	    Integer stockEsperado =9;
	    Integer stockObtenido = this.miTienda.getObtenerStock(producto1);

	    assertEquals(stockEsperado, stockObtenido);
	}
	
}
