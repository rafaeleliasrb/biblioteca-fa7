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
	
	// Create a ConnectionFactory
    ActiveMQConnectionFactory connectionFactory;

    // Create a Connection
    Connection connection;  

    // Create a Session
    Session session;
	

	public JmsUtil()  throws Exception{
		super();
		connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		connection = connectionFactory.createConnection();
	    connection.start();
	    
	}

	public void enviarMensagem(String destino, Pedido pedido) throws Exception {
		
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination fila = session.createQueue("distribuidora");

		MessageProducer producer = session.createProducer(fila);

		Message message = session.createObjectMessage(pedido);
		producer.send(message);
		System.out.println("Enviou o pedido!");
		session.close();
		connection.close();
	}

	public Pedido receberMensagem(String destino) throws Exception {

		System.setProperty("org.apache.activemq.SERIALIZABLE_PACKAGES", "*");

		session = connection.createSession(true, Session.SESSION_TRANSACTED);

		Destination fila = session.createQueue("distribuidora");
		MessageConsumer consumer = session.createConsumer(fila);
		Pedido pedidoRetorno = new Pedido();

		consumer.setMessageListener(new MessageListener() {

			@Override
			public void onMessage(Message message) {
				ObjectMessage objectMessage = (ObjectMessage) message;

				System.out.println("[INFO] onMessage");
				try {
					Pedido pedido = (Pedido) objectMessage.getObject();					
					pedidoRetorno.setSolicitacoes(pedido.getSolicitacoes());
					
					for (SolicitacaoLivro solicitacaoLivro : pedidoRetorno.getSolicitacoes()) {
						System.out.println(solicitacaoLivro.getAutor());
						System.out.println(solicitacaoLivro.getTitulo());
						System.out.println(solicitacaoLivro.getQuantidade());
						
					}
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

		return pedidoRetorno;
	}

}
