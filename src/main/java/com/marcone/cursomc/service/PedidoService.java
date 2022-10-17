package com.marcone.cursomc.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marcone.cursomc.domain.ItemPedido;
import com.marcone.cursomc.domain.PagamentoComBoleto;
import com.marcone.cursomc.domain.Pedido;
import com.marcone.cursomc.domain.enums.EstadoPagamento;
import com.marcone.cursomc.repositories.ItemPedidoRepository;
import com.marcone.cursomc.repositories.PagamentoRepository;
import com.marcone.cursomc.repositories.PedidoRepository;
import com.marcone.cursomc.service.exception.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository pedRepo;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagtoRepo;
	
	@Autowired
	private ProdutoService repo;
	
	@Autowired
	private ItemPedidoRepository ipRepo;
	
	public Pedido buscar(Integer id) {
		Optional<Pedido> obj = pedRepo.findById(id);		
		return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto nao encontrado! Id: " + id ));
	}
	
	public Pedido inserir( Pedido obj) {
		obj.setId(null);
		obj.setInstant(new Date());
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if(obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstant());
		}
		obj = pedRepo.save(obj);
		pagtoRepo.save(obj.getPagamento());
		for( ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setPreco(repo.buscar(ip.getProduto().getId()).getPreco());
			ip.setPedido(obj);
		}
		ipRepo.saveAll(obj.getItens());
		return obj;
	}

}
