package object8421.activemq;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppProducer {  
    public static void sendWithAuto(ApplicationContext context) {  
        ActiveMQConnectionFactory factory = null;  
        Connection conn = null;  
        Destination destination = null;  
        Session session = null;  
        MessageProducer producer = null;  
        try {  
            destination = (Destination) context.getBean("destination");  
            factory = (ActiveMQConnectionFactory) context.getBean("targetConnectionFactory");  
            conn = factory.createConnection();  
            session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);  
            producer = session.createProducer(destination);  
            Message message = session.createTextMessage("...Hello JMS!");  
            producer.send(message);  
            while (true) {
            	System.out.print("aaa");
            	
            	Thread.sleep(1000);
            }
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                producer.close();  
                producer = null;  
            } catch (Exception e) {  
            }  
            try {  
                session.close();  
                session = null;  
            } catch (Exception e) {  
            }  
            try {  
                conn.stop();  
            } catch (Exception e) {  
            }  
            try {  
                conn.close();  
            } catch (Exception e) {  
            }  
        }  
    }  
    public static void main(String[] args) {  
        final ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/activemq.xml");  
        sendWithAuto(context);  
    }  
}  
