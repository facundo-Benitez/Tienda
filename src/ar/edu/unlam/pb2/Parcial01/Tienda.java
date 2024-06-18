package ar.edu.unlam.pb2.Parcial01;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Tienda {

	/**
	 * En esta ocasion deberemos resolver un producto software que nos permita
	 * administrar la venta de productos o servicios de nuestra tienda. Venderemos
	 * entonces, productos como mouse o teclados y servicios como el soporte tecnico
	 * a domicilio. Sabemos que la tienda cuenta con items Vendibles que pueden ser
	 * del tipo Producto o Servicio. Ademas, podemos registrar el stock de los
	 * productos, los clientes a quienes les vendemos algun producto o servicio, las
	 * ventas y los vendedores de la tienda. Antes de realizar alguna operacion, se
	 * debera obtener el elemento correspondiente de las colecciones. Ejemplo: Si
	 * quisiera realizar alguna operacion con un cliente, el mismo debe obtenerse de
	 * la coleccion de clientes.
	 * 
	 * Cada Venta contiene renglones los cuales representa a los productos o
	 * servicios que se incluyen en la misma. Tambien cuenta con el Cliente y
	 * Vendedor que participan en la Venta. Cuando agregamos un vendible a una
	 * venta, lo haremos con 1 unidad. En una version posterior, admitiremos
	 * cantidades variables.
	 * 
	 * Cada Item debe compararse por nombre y precio, en caso de ser necesario.
	 * Recordar que los items deben ser Vendibles.
	 * 
	 **/

	private String cuit;
	private String nombre;
	private Set<Vendible> vendibles;
	private Map<Producto, Integer> stock;
	private List<Cliente> clientes;
	private Set<Venta> ventas;
	private Set<Vendedor> vendedores;

	public Tienda(String cuit, String nombre) {
		// TODO: Completar el constructor para el correcto funcionamiento del software
		this.cuit=cuit;
		this.nombre=nombre;
		this.vendibles=new HashSet<Vendible>();
		this.stock=new HashMap<Producto, Integer>();
		this.clientes=new ArrayList<Cliente>();
		this.ventas=new HashSet<Venta>();
		this.vendedores=new HashSet<Vendedor>();
	}

	// TODO: Completar con los getters y setters necesarios
	public String getNombre() {
		return nombre;
	}

	public String getCuit() {
		return cuit;
	}

	public Set<Vendedor> getVendedores() {
		return vendedores;
	}

	public Set<Vendible> getVendibles() {
		return vendibles;
	}

	public Map<Producto, Integer> getStock() {
		return stock;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public Set<Venta> getVentas() {
		return ventas;
	}
	
    public Vendible obtenerVendible(Integer codigo) {
		// TODO: Obtiene un producto o servicio de la coleccion de vendibles utilizando
		// el codigo. En caso de no existir devuelve null.
        for (Vendible vendible : vendibles) {
            if (vendible.getCodigo().equals(codigo)) {
                return vendible;
            }
        }
        return null;
    }

	public void agregarProducto(Producto producto) {
		this.agregarProducto(producto, 0);
	}


	public void agregarProducto(Producto producto, Integer stockInicial) {
		// TODO: Agrega un producto a la coleccion de vendibles y pone en la coleccion
		// de stocks al producto con su stock inicial
		this.vendibles.add(producto);
		this.stock.put(producto, stockInicial);
	}

	public void agregarServicio(Servicio servicio) {
		// TODO: Agrega un servicio a la coleccion de vendibles
		this.vendibles.add(servicio);
	}

	public Integer getObtenerStock(Producto producto) {
		return stock.get(producto);
	}

	public void agregarStock(Producto producto, Integer nuevaCantidad){
		// TODO: se debe agregar stock a un producto existente
		for (Map.Entry <Producto, Integer> entry : stock.entrySet()) {
			Producto productoObtenido = entry.getKey();
			Integer stockObtenido = entry.getValue(); 
			if (productoObtenido.equals(producto)) {
				entry.setValue(stockObtenido + nuevaCantidad);
			}
		}
	}

	public void agregarCliente(Cliente cliente) {
		clientes.add(cliente);
	}

	public void agregarVendedor(Vendedor vendedor) {
		vendedores.add(vendedor);
	}

	//Se agrego esto
	public void agregarVenta(Venta venta) throws VendedorDeLicenciaException {
		// TODO: Agrega una venta a la coleccion correspondiente. En caso de que el
		// vendedor este de licencia, arroja una
		// VendedorDeLicenciaException
		if(venta.getVendedor().isDeLicencia()==true) {
			throw new VendedorDeLicenciaException("El vendedor tiene licencia");
		}	

	    this.ventas.add(venta);
	}

	public Producto obtenerProductoPorCodigo(Integer codigo) throws VendibleInexistenteException {
		// TODO: Obtiene un producto de los posibles por su codigo. En caso de no
		// encontrarlo se debera devolver null
		Vendible productoBuscado = this.obtenerVendible(codigo);
        if (productoBuscado!=null && productoBuscado instanceof Producto) {
            return (Producto) productoBuscado;
        }
        throw new VendibleInexistenteException("El item vendible no existe");
	}

	public Venta buscarVentaPorCodigo(String codigoVenta) {
		Venta ventaBuscada=null;
		for (Venta ventaActual : ventas) {
			if(ventaActual.getCodigo().equalsIgnoreCase(codigoVenta)) {
				return  ventaBuscada=ventaActual;
			}
		}
		return  ventaBuscada;
	}
	
	public void agregarProductoAVenta(String codigoVenta, Producto producto) throws VendibleInexistenteException {
		// TODO: Agrega un producto a una venta. Si el vendible no existe (utilizando su
		// codigo), se debe lanzar una VendibleInexistenteException
		// Se debe actualizar el stock en la tienda del producto que se agrega a la venta
		Venta ventaBuscada = buscarVentaPorCodigo(codigoVenta);
        if (!(this.vendibles.contains(producto))) {
            throw new VendibleInexistenteException("El item vendible no existe");
         }

         ventaBuscada.agregarRenglon(producto, 1);
         this.agregarStock(producto, (-1));
	}

	public void agregarServicioAVenta(String codigoVenta, Servicio servicio) throws VendibleInexistenteException {
		// TODO: Agrega un servicio a la venta. Recordar que los productos y servicios
		// se traducen en renglones
		Venta ventaBuscada = buscarVentaPorCodigo(codigoVenta);
        if (!(this.vendibles.contains(servicio))) {
            throw new VendibleInexistenteException("El item vendible no existe");
        }
        ventaBuscada.agregarRenglon(servicio, 1);
      
	}

	public List<Producto> obtenerProductosCuyoStockEsMenorOIgualAlPuntoDeReposicion() {
		// TODO: Obtiene una lista de productos cuyo stock es menor o igual al punto de
		// reposicion. El punto de reposicion, es un valor que
		// definimos de manera estrategica para que nos indique cuando debemos reponer
		// stock para no quedarnos sin productos
        List<Producto> ProductosConStockMenorOIgualAlPuntoDeReposicion = new ArrayList<>();
        for (Map.Entry<Producto, Integer> entry : stock.entrySet()) {
        	Producto productoObtenido= entry.getKey();
        	Integer stockDelProducto= entry.getValue();
            if (stockDelProducto <= productoObtenido.getPuntoDeReposicion()) {
            	ProductosConStockMenorOIgualAlPuntoDeReposicion.add(productoObtenido);
            }
        }
        return ProductosConStockMenorOIgualAlPuntoDeReposicion;
	}

	public TreeSet<Cliente> obtenerClientesOrdenadosPorRazonSocialDescendente() {
		// TODO: obtiene una lista de clientes ordenados por su razon social de manera
		// descendente
		TreeSet<Cliente>ordenDescendiente=new TreeSet<>(new OrdenarPorRazonSocialDescendente());
		ordenDescendiente.addAll(clientes);
		return ordenDescendiente;
	}

	//forma 1:
    public Map<Vendedor, Set<Venta>> obtenerLasVentasPorVendedor() {
    	// TODO: Obtiene un mapa que contiene las ventas realizadas por cada vendedor.
        Map<Vendedor, Set<Venta>> ventasPorVendedor = new HashMap<>();
        for (Venta venta : ventas) {
            Vendedor vendedor = venta.getVendedor();
            if (!ventasPorVendedor.containsKey(vendedor)) {
                ventasPorVendedor.put(vendedor, new HashSet<>());
            }
            ventasPorVendedor.get(vendedor).add(venta);
        }
        return ventasPorVendedor;
    }
    
	/*
    forma 2:
    primero creo un set y busco todas las ventas de un vendedor
    private Set<Venta> obtenerVentasDeUnVendedor(Vendedor vendedorActual) {	
		Set<Venta> ventasDeUnVendedor = new HashSet<>();
		for (Venta venta : ventas) {
			if (venta.getVendedor().equals(vendedorActual)) {
				ventasDeUnVendedor.add(venta);
			}
		}		
		return ventasDeUnVendedor;
	}
	
	segundo recorro el mapa por vendedor y le agrego el set de venta de un vendedor
	public Map<Vendedor, Set<Venta>>obtenerLasVentasPorVendedor() {
		// TODO: Obtiene un mapa que contiene las ventas realizadas por cada vendedor.
		
		Map<Vendedor, Set<Venta>> ventasPorVendedor = new HashMap<>();		
		for (Vendedor vendedor : vendedores) {
			ventasPorVendedor.put(vendedor, this.obtenerVentasDeUnVendedor(vendedor));
		 }				
		return ventasPorVendedor;
	}

	*/

	public Double obtenerTotalDeVentasDeServicios() {
		// TODO: obtiene el total acumulado de los vendibles que son servicios incluidos
		// en todas las ventas.
		// Si una venta incluye productos y servicios, solo nos interesa saber el total
		// de los servicios
        Double total = 0.0;
        for (Venta venta : ventas) {
            for (Map.Entry<Vendible, Integer> entry : venta.getRenglones().entrySet()) {
            	Vendible vendibleActual = entry.getKey();
            	if (vendibleActual instanceof Servicio) {
                    total += vendibleActual.getPrecio();
                }
            }
        }
        return total;
    }
	
}
