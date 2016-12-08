package br.com.fa7.biblioteca.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;

import br.com.fa7.biblioteca.model.Pedido;

public class TesteProdutor {

	public static void main(String[] args) throws Exception {
		
		InitialContext context = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		
		Connection connection = factory.createConnection(); 
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		Destination fila = (Destination) context.lookup("distribuidora");
		
		MessageProducer producer = session.createProducer(fila);
		
		Pedido pedido = new Pedido();
		System.out.println(pedido.hashCode());
		
		Message message = session.createObjectMessage(pedido); 
		producer.send(message);
		
		session.close();
		connection.close();
		context.close();
	}
}
