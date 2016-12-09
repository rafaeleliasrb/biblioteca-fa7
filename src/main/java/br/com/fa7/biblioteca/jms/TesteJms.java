package br.com.fa7.biblioteca.jms;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.com.fa7.biblioteca.model.Pedido;
import br.com.fa7.biblioteca.model.SolicitacaoLivro;

public class TesteJms {

	public static void main(String[] args) {
		
		List<SolicitacaoLivro> solicitacoes = new ArrayList<SolicitacaoLivro>();
		Pedido pedido = new Pedido();
		Random gerador = new Random();
		
		 for (int i = 0; i < 5; i++) {
			SolicitacaoLivro solicitacaoLivro = new SolicitacaoLivro();
			solicitacaoLivro.setTitulo("Livro " + i);
			solicitacaoLivro.setAutor("Autor " + i);
			solicitacaoLivro.setQuantidade(gerador.nextInt(10));
			
			solicitacoes.add(solicitacaoLivro);
		}
		 
		 pedido.setSolicitacoes(solicitacoes);
		 
		 JmsUtil jmsUtil;
		try {
			jmsUtil = new JmsUtil();
			jmsUtil.enviarMensagem("distribuidora-queue", pedido);
			
			Pedido pedidoRetorno  = jmsUtil.receberMensagem("biblioteca-queue");
			if (pedidoRetorno != null && pedidoRetorno.getSolicitacoes()!=null) {
				for (SolicitacaoLivro solicitacaoLivro : pedidoRetorno.getSolicitacoes()) {
					System.out.println(solicitacaoLivro.getAutor());
					System.out.println(solicitacaoLivro.getTitulo());
					System.out.println(solicitacaoLivro.getQuantidade());

				}
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
