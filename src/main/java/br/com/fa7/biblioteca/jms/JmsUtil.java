package br.com.fa7.biblioteca.jms;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

import br.com.fa7.biblioteca.model.Pedido;
import br.com.fa7.biblioteca.model.SolicitacaoLivro;

public class JmsUtil {
	



	public JmsUtil() {
		System.setProperty("org.apache.activemq.SERIALIZABLE_PACKAGES","*");
	}

	public void enviarMensagem(String destino, Pedido pedido) throws Exception {
		
		// Create a ConnectionFactory
        ActiveMQConnectionFactory connectionFactory 
        		 = new ActiveMQConnectionFactory("tcp://localhost:61616");

        // Create a Connection
        Connection connection = connectionFactory.createConnection();
        connection.start();

        // Create a Session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // Create the destination (Topic or Queue)
        Destination fila = session.createQueue(destino);
		
		MessageProducer producer = session.createProducer(fila);
		 
		System.out.println(pedido.hashCode());
		
		Message message = session.createObjectMessage(pedido); 
		producer.send(message);
		
		session.close();
		connection.close();

	}

	public Pedido receberMensagem(String destino) throws Exception {
	
		
		 // Create a ConnectionFactory
       ActiveMQConnectionFactory connectionFactory 
       	= new ActiveMQConnectionFactory("tcp://localhost:61616");

       // Create a Connection
       Connection connection = connectionFactory.createConnection();
       connection.start();

       // Create a Session
       Session session = connection.createSession(true, Session.SESSION_TRANSACTED);
       
       // Create the destination (Topic or Queue)
       Destination fila = session.createQueue(destino);
		
		MessageConsumer consumer = session.createConsumer(fila);
		Pedido pedidoRetorno = new Pedido();
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				ObjectMessage objectMessage = (ObjectMessage)message;
				
				System.out.println("[INFO] onMessage");
				try {
					Pedido pedido = (Pedido) objectMessage.getObject();					
					pedidoRetorno.setSolicitacoes(pedido.getSolicitacoes());
					
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
		return pedidoRetorno;
	}

}
