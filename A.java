
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author JARM
 */

public class A {
    
    static class worker extends Thread{
        Socket conexion;
        
        worker(Socket conexion){
            this.conexion = conexion;
        }
        
        public void run(){
            try {
                DataOutputStream salida = new DataOutputStream(conexion.getOutputStream());
                DataInputStream entrada = new DataInputStream(conexion.getInputStream());
                
                String[] cadena = entrada.readUTF().split(",");
                long NUMERO = Long.parseLong(cadena[0]);
                long NUMERO_INICIAL = Long.parseLong(cadena[1]);
                long NUMERO_FINAL = Long.parseLong(cadena[2]);

                System.out.println(NUMERO+","+NUMERO_INICIAL+","+NUMERO_FINAL);
                
                for(long n = NUMERO_INICIAL; n<= NUMERO_FINAL; n++){
                    if(NUMERO%n==0){
                        salida.writeUTF("DIVIDE");
                        System.out.println("DIVIDE");
                        conexion.close();
                        break;
                    }
                }
                salida.writeUTF("NO DIVIDE");
                System.out.println("NO DIVIDE");
                conexion.close();
                
            } catch (IOException ex) {
                //Logger.getLogger(A.class.getName()).log(Level.SEVERE, null, ex);
                System.err.print("Se cerró el Socket");
            }
        }
    }
    
    public static void main(String args[]) throws IOException{
        ServerSocket servidor = new ServerSocket(Integer.parseInt(args[0]));
        
        for(;;){
            Socket conexion = servidor.accept();
            worker w = new worker(conexion);
            w.start();
            System.out.println("\n");
        }
    }
}
