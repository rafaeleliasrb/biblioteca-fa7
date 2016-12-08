package br.com.fa7.biblioteca.jms;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

import br.com.fa7.biblioteca.model.Pedido;

public class TesteConsumidor {

public static void main(String[] args) throws Exception {
		System.setProperty("org.apache.activemq.SERIALIZABLE_PACKAGES","*");	
	
		 // Create a ConnectionFactory
        ActiveMQConnectionFactory connectionFactory 
        	= new ActiveMQConnectionFactory("tcp://localhost:61616");

        // Create a Connection
        Connection connection = connectionFactory.createConnection();
        connection.start();

        // Create a Session
        Session session = connection.createSession(true, Session.SESSION_TRANSACTED);
        
        // Create the destination (Topic or Queue)
        Destination fila = session.createQueue("distribuidora");
		
		MessageConsumer consumer = session.createConsumer(fila);
		
		consumer.setMessageListener(new MessageListener() {

			@Override
			public void onMessage(Message message) {
				ObjectMessage objectMessage = (ObjectMessage)message;
				
				System.out.println("[INFO] onMessage");
				try {
					Pedido pedido = (Pedido) objectMessage.getObject();
					System.out.println(pedido.hashCode());
				} catch (JMSException e) {
					e.printStackTrace();
				}
				try {
					session.commit();
				} catch (JMSException e) {
					// TODO Tratar excecao
					e.printStackTrace();
				}
			}
			
		});
		
		session.close();
		connection.close();
	}
}
