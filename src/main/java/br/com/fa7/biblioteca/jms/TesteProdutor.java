package br.com.fa7.biblioteca.jms;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

import br.com.fa7.biblioteca.model.Pedido;

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
		
		Pedido pedido = new Pedido();
		System.out.println(pedido.hashCode());
		
		Message message = session.createObjectMessage(pedido); 
		producer.send(message);
		
		session.close();
		connection.close();
	}
}
