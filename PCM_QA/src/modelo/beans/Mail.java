package modelo.beans;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class Mail {


    // Variables Privadas que se usarán para enviar el Mail
   private String user, pass, destino, subject, mensaje;
    // Objeto Properties, donde se añadirán los valores para enviar el Mail.
   private Properties props;

    
    // "Constructor clase Mail"
    
    /***
     * Pasamos los siguientes parámetros: usuario, contraseña, destinatario, asunto del mail, 
     * mensaje y tipo decuenta mail.
     * @param vdestino Este es el destinatario del correo
     * @param vsubject Este es quien envia
     * 
     **/
    public Mail(String vdestino, String vsubject, String vmensaje, String tipo) {
    	System.out.println("Entre a mail----destino:"+vdestino+ "cabecera:" + vsubject+ "msj:"+ vmensaje);
        user = "infopos@merza.com";
        pass = "gP&M3RzA2018.";
        destino = vdestino;
        subject = vsubject;
        mensaje = vmensaje;
        props = new Properties();
        
        

        /* Tipo => Variará en función del proveedor de correo, en nuestro caso será GMAIL. */
        switch (tipo) {
            case "gmail":
                setGmailProps();
                break;
            case "other":
                break;
        }
    }
    
 
    
    // <editor-fold desc="Método para añadir los parámetros al Mail que enviaremos.">  
    private void setGmailProps() {
    	System.out.println("Entro aqui e imprimio todo");
        // el host de correo, en nuestro caso gmail
        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.starttls.enable", "true"); //puerto salida 25 false
        //el puerto que vamos a usar
        props.setProperty("mail.smtp.port", "587");
        //el usuario
        props.setProperty("mail.smtp.user", user);
        //le indicamos que es necesario autentificarse
        props.setProperty("mail.smtp.auth", "true");
        
        
        
    }
        
    // <editor-fold desc="Método para Enviar el Mail">  
    public String send() {
    	System.out.println("Entre a send mail");
        //Variable utilizada para controlar errores y verlos.
        String error;
        try {
            // Creamos un objeto Session, para setear los parámetros.
            Session session = Session.getDefaultInstance(props);
            /**
             * *
             *
             * Creamos un objeto Mensaje, donde introduciremos la Session (Esto
             * simula cuando entramos al Gmail y le damos a "Redactar" *
             */
            MimeMessage message = new MimeMessage(session);
            // Añadimos el Remitente del Mail.
            message.setFrom(new InternetAddress(user));
            // Añadimos el Destinatario.
            message.addRecipient(Message.RecipientType.TO, 
            new InternetAddress(destino));
            //Añadimos el Asunto.
            message.setSubject(subject);

            
            
            /**
             * *
             *
             * Finalmente, añadimos el Mensaje, para ello, podríamos ponerlo en
             * HTML "text/html", aunque también acepta texto plano y de más. * *
             */
            message.setContent(mensaje, "text/html");

            /**
             * Seguidamente, creamos la conexión y enviamos el mensaje.
             */
            Transport t = session.getTransport("smtp");
            t.connect(user, pass);
            t.sendMessage(message, message.getAllRecipients());
            t.close();
            /*
             * ERROR es una variable String que nos retornará el error
             * en caso de que lo hubiera. 
             * */
            error = "";
        } catch (Exception e) {
            error = e.toString();
        }
        return error;
    }
    
//"Getters y Setters">  
    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Properties getProps() {
        return props;
    }

    public void setProps(Properties props) {
        this.props = props;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
    
 
}
