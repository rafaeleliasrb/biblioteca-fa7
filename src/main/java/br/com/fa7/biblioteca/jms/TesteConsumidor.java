package br.com.fa7.biblioteca.jms;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
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
import br.com.fa7.biblioteca.model.SolicitacaoLivro;

@Singleton
@Startup
public class TesteConsumidor {

	static Connection connection;
	static Session session;
	static Destination fila;
	
	static {
		try {
			inicializaConexao();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void inicializaConexao() throws JMSException {
		System.out.println("[INFO] Inicializando conexoes.");
		
		System.setProperty("org.apache.activemq.SERIALIZABLE_PACKAGES","*");	
		
		// Create a ConnectionFactory
		ActiveMQConnectionFactory connectionFactory 
		= new ActiveMQConnectionFactory("tcp://localhost:61616");
		
		// Create a Connection
		connection = connectionFactory.createConnection();
		connection.start();
		
		// Create a Session
		session = connection.createSession(true, Session.SESSION_TRANSACTED);
		
		// Create the destination (Topic or Queue)
		fila = session.createQueue("biblioteca-queue");
	}
	
	//public static void main(String[] args) throws Exception {
	@PostConstruct
	public static void consumidor() throws InterruptedException, JMSException {
		System.out.println("[INFO] Inicializando consumidor.");
		
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
					// TODO Tratar excecao
					e.printStackTrace();
				}
			}
			
		});
		
		//Thread.sleep(60000*5);

		
	}
	
	@PreDestroy
	public void finalizaConexoes() throws JMSException {
		System.out.println("[INFO] finalizando conexoes.");
		
		session.close();
		connection.close();
	}
}
