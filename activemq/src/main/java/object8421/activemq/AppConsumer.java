package object8421.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import org.apache.activemq.ActiveMQConnectionFactory;

public class AppConsumer extends Thread implements MessageListener {  
	  
    private Connection conn = null;  
    private Destination destination = null;  
    private Session session = null;  
    public void run() {  
        receive();  
    }  
    public void receive() {  
        ConnectionFactory factory = null;  
        Connection conn = null;  
        try {  
            final ApplicationContext context = new ClassPathXmlApplicationContext("classpath:activemq.xml");  
            factory = (ActiveMQConnectionFactory) context.getBean("targetConnectionFactory");  
            conn = factory.createConnection();  
            conn.start();  
            session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);  
            destination = (Destination) context.getBean("destination");  
            MessageConsumer consumer = session.createConsumer(destination);  
            consumer.setMessageListener(this);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
    public void onMessage(Message message) {  
  
        try {  
            TextMessage tm = (TextMessage) message;  
            System.out.println("AppConsumer receive message: " + tm.getText());  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
  
    }  
    public static void main(String[] args) {  
    	AppConsumer tranConsumer = new AppConsumer();  
        tranConsumer.start();  
    }  
}  