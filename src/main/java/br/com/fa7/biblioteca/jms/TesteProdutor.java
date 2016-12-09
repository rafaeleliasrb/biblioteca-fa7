package br.com.fa7.biblioteca.jms;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

import br.com.fa7.biblioteca.model.Pedido;
import br.com.fa7.biblioteca.model.SolicitacaoLivro;

public class TesteProdutor {

	public static void main(String[] args) throws Exception {
		
		// Create a ConnectionFactory
        ActiveMQConnectionFactory connectionFactory 
        		 = new ActiveMQConnectionFactory("tcp://localhost:61616");

        // Create a Connection
        Connection connection = connectionFactory.createConnection();
        connection.start();

        // Create a Session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // Create the destination (Topic or Queue)
        Destination fila = session.createQueue("distribuidora");
		
		MessageProducer producer = session.createProducer(fila);
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
		System.out.println(pedido.hashCode());
		
		Message message = session.createObjectMessage(pedido); 
		producer.send(message);
		
		session.close();
		connection.close();
	}
}
