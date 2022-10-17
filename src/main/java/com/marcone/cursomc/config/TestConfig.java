package com.marcone.cursomc.config;

import java.text.SimpleDateFormat;
//import java.time.Instant;
import java.util.Arrays;
//import java.util.Locale.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.marcone.cursomc.domain.Categoria;
import com.marcone.cursomc.domain.Cidade;
import com.marcone.cursomc.domain.Cliente;
import com.marcone.cursomc.domain.Endereco;
import com.marcone.cursomc.domain.Estado;
import com.marcone.cursomc.domain.ItemPedido;
import com.marcone.cursomc.domain.Pagamento;
import com.marcone.cursomc.domain.PagamentoComBoleto;
import com.marcone.cursomc.domain.PagamentoComCartao;
import com.marcone.cursomc.domain.Pedido;
import com.marcone.cursomc.domain.Produto;
import com.marcone.cursomc.domain.enums.EstadoPagamento;
import com.marcone.cursomc.domain.enums.TipoCliente;
import com.marcone.cursomc.repositories.CategoriaRepository;
import com.marcone.cursomc.repositories.CidadeRepository;
import com.marcone.cursomc.repositories.ClienteRepository;
import com.marcone.cursomc.repositories.EnderecoRepository;
import com.marcone.cursomc.repositories.EstadoRepository;
import com.marcone.cursomc.repositories.ItemPedidoRepository;
import com.marcone.cursomc.repositories.PagamentoRepository;
//import com.marcone.cursomc.repositories.PagamentoRepository;
import com.marcone.cursomc.repositories.PedidoRepository;
import com.marcone.cursomc.repositories.ProdutoRepository;

@Configuration
@Profile("dev")
public class TestConfig implements CommandLineRunner {

	@Autowired
	private ClienteRepository clr;

	@Autowired
	private EnderecoRepository endr;

	@Autowired
	private CategoriaRepository cr;

	@Autowired
	private ProdutoRepository pr;

	@Autowired
	private EstadoRepository er;

	@Autowired
	private CidadeRepository cdr;
	
	@Autowired
	private PedidoRepository pedr;
	
	@Autowired
	private PagamentoRepository pagr;
	
	@Autowired
	private ItemPedidoRepository ipr;

	@Override
	public void run(String... args) throws Exception {

		Categoria cat1 = new Categoria(null, "Informatica");
		Categoria cat2 = new Categoria(null, "Escritorio");
		Categoria cat3 = new Categoria(null, "Cama mesa e banho");
		Categoria cat4 = new Categoria(null, "Eletronicos");
		Categoria cat5 = new Categoria(null, "Jardinagem");
		Categoria cat6 = new Categoria(null, "Decoração");
		Categoria cat7 = new Categoria(null, "Perfumaria");
		

		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		Produto p4 = new Produto(null, "Mesa de escritório", 300.00);
		Produto p5 = new Produto(null, "Toalha", 50.00);
		Produto p6 = new Produto(null, "Colcha", 200.00);
		Produto p7 = new Produto(null, "TV true color", 1200.00);
		Produto p8 = new Produto(null, "Roçadeira", 800.00);
		Produto p9 = new Produto(null, "Abajour", 100.00);
		Produto p10 = new Produto(null, "Pendente", 180.00);
		Produto p11 = new Produto(null, "Shampoo", 90.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));

		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));

		cat2.getProdutos().addAll(Arrays.asList(p2, p4));
		cat3.getProdutos().addAll(Arrays.asList(p5, p6));
		cat4.getProdutos().addAll(Arrays.asList(p1, p2, p3, p7));
		cat5.getProdutos().addAll(Arrays.asList(p8));
		cat6.getProdutos().addAll(Arrays.asList(p9, p10));
		cat7.getProdutos().addAll(Arrays.asList(p11));

		p1.getCategorias().addAll(Arrays.asList(cat1, cat4));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2, cat4));
		p3.getCategorias().addAll(Arrays.asList(cat1, cat4));
		p4.getCategorias().addAll(Arrays.asList(cat2));
		p5.getCategorias().addAll(Arrays.asList(cat3));
		p6.getCategorias().addAll(Arrays.asList(cat3));
		p7.getCategorias().addAll(Arrays.asList(cat4));
		p8.getCategorias().addAll(Arrays.asList(cat5));
		p9.getCategorias().addAll(Arrays.asList(cat6));
		p10.getCategorias().addAll(Arrays.asList(cat6));
		p11.getCategorias().addAll(Arrays.asList(cat7));

		Estado e1 = new Estado(null, "Minas Gerais");
		Estado e2 = new Estado(null, "Sao Paulo");

		Cidade cd1 = new Cidade(null, "Campinas", e2);
		Cidade cd2 = new Cidade(null, "Sao Paulo", e2);
		Cidade cd3 = new Cidade(null, "Uberlandia", e1);

		e1.getCidades().add(cd3);
		e2.getCidades().addAll(Arrays.asList(cd1, cd2));

		cr.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
		pr.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));
		er.saveAll(Arrays.asList(e1, e2));
		cdr.saveAll(Arrays.asList(cd1, cd2, cd3));

		Cliente cli1 = new Cliente(null, "Maria Brown", "maria@gmail.com", "988888888", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("12121212", "23562356"));

		Endereco ed1 = new Endereco(null, "Rua Flores", "300", "apto 303", "jardim", "56895689", cli1, cd3);
		Endereco ed2 = new Endereco(null, "Av matos", "105", "sala 800", "centro", "45895689", cli1, cd2);

		cli1.getEnderecos().addAll(Arrays.asList(ed1, ed2));

		clr.save(cli1);
		endr.saveAll(Arrays.asList(ed1, ed2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, ed1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, ed2);
		
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
//		Pagamento pagto2 = new PagamentoComBoleto();
//		ped2.setPagamento(pagto2);

		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		pedr.saveAll(Arrays.asList(ped1, ped2));
	pagr.saveAll(Arrays.asList(pagto1));

		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.0, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.0, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.0, 1, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip2));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		ipr.saveAll(Arrays.asList(ip1, ip2, ip3));
		
	}

}