package br.com.fa7.biblioteca.jms;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
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

import br.com.fa7.biblioteca.model.Livro;
import br.com.fa7.biblioteca.model.Pedido;
import br.com.fa7.biblioteca.model.SolicitacaoLivro;
import br.com.fa7.biblioteca.service.LivroService;

@Singleton
@Startup
public class DistribuidoraJMS {
	
	private static final String URL_ACTIVEMQ = "tcp://localhost:61616";
	private static final String NOME_FILA_ENVIAR_PEDIDO = "distribuidora-queue";
	private static final String NOME_FILA_RETORNO_PEDIDO = "biblioteca-queue";

	@Inject
	private LivroService livroService;

	private Connection connection;
	private Session sessionConsumidor;
	
	DistribuidoraJMS() {
		try {
			inicializaConexaoComFila();
		} catch (JMSException e) {
			// TODO Trate sua excessao
			e.printStackTrace();
		}
	}
	
	public void inicializaConexaoComFila() throws JMSException {
		System.out.println("[INFO] Inicializando conexao com a fila.");
		
		System.setProperty("org.apache.activemq.SERIALIZABLE_PACKAGES","*");	
		
		ActiveMQConnectionFactory connectionFactory 
			= new ActiveMQConnectionFactory(URL_ACTIVEMQ);
		
		connection = connectionFactory.createConnection();
		connection.start();
	}
	
	@PostConstruct
	private void consumidor() throws InterruptedException, JMSException {
		System.out.println("[INFO] Inicializando consumidor.");
		
		sessionConsumidor = connection.createSession(true, Session.SESSION_TRANSACTED);
		Destination fila = sessionConsumidor.createQueue(NOME_FILA_RETORNO_PEDIDO);
		MessageConsumer consumer = sessionConsumidor.createConsumer(fila);
		
		consumer.setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message message) {
				System.out.println("[INFO] Escutando a fila.");

				ObjectMessage objectMessage = (ObjectMessage)message;
				try {
					receberPedido(objectMessage);
				
					sessionConsumidor.commit();
				} catch (JMSException e) {
					// TODO Trate sua excecao
					e.printStackTrace();
				}
			}
		});
	}
	
	private void receberPedido(ObjectMessage objectMessage) throws JMSException {
		Pedido pedidoRetorno = (Pedido) objectMessage.getObject();					
		
		for (SolicitacaoLivro solicitacaoLivro : pedidoRetorno.getSolicitacoes()) {
			Livro novoLivro = new Livro();
			novoLivro.gerarLivroDeSolicitacao(solicitacaoLivro);
			System.out.println("[INFO] Salvando livro " 
					+ novoLivro.getTitulo() + " de " + novoLivro.getAutor());
			livroService.salvar(novoLivro);
		}
	}
	
	public void enviarPedido(Pedido pedido) throws JMSException {
		System.out.println("[INFO] Enviando pedido: " + pedido.getId());
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination fila = session.createQueue(NOME_FILA_ENVIAR_PEDIDO);
		MessageProducer producer = session.createProducer(fila);
		Message message = session.createObjectMessage(pedido);
		producer.send(message);

		session.close();
		System.out.println("[INFO] Pedido enviado.");
	}
	
	@PreDestroy
	private void finalizaConexoes() throws JMSException {
		System.out.println("[INFO] finalizando sessao Consumidor.");
		sessionConsumidor.close();
		System.out.println("[INFO] finalizando conexao.");
		connection.close();
	}
}
