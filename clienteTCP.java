
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @author JARM
 */
public class clienteTCP {

    public static void main(String args[]) throws IOException {
        Socket conexion = new Socket("localhost",2000);
        
        DataInputStream entrada = new DataInputStream(conexion.getInputStream());
        DataOutputStream salida = new DataOutputStream(conexion.getOutputStream());
        
        salida.writeLong(Long.parseLong(args[0]));
        
        String mensaje = entrada.readUTF();
        System.out.println(mensaje);
        
    }
}
