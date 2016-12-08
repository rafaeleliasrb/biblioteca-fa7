package br.com.fa7.biblioteca.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.naming.InitialContext;

import br.com.fa7.biblioteca.model.Pedido;

public class TesteConsumidor {

public static void main(String[] args) throws Exception {
		System.setProperty("org.apache.activemq.SERIALIZABLE_PACKAGES","*");	
	
		InitialContext context = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		
		Connection connection = factory.createConnection(); 
		connection.start();
		Session session = connection.createSession(true, Session.SESSION_TRANSACTED);
		
		Destination fila = (Destination) context.lookup("distribuidora");
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
		
		session.close();
		connection.close();
		context.close();
	}
}
