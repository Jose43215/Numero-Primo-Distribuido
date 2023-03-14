
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
public class B {
    
    static class worker extends Thread{
        
        Socket conexion;
        String IP;
        int Puerto;
        int ID;
        
        static Object obj = new Object();
        
        static long NUMERO;
        static long K;
        static long NUMERO_INICIAL_1;
        static long NUMERO_INICIAL_2;
        static long NUMERO_INICIAL_3;
        static long NUMERO_FINAL_1;
        static long NUMERO_FINAL_2;
        static long NUMERO_FINAL_3;
        
        static String respuesta1;
        static String respuesta2;
        static String respuesta3;
                
                
                
        worker(Socket conexion,String IP, int Puerto, int ID){
            this.conexion = conexion;
            this.IP = IP;
            this.Puerto = Puerto;
            this.ID = ID;
        }
        
        
        @Override
        public void run(){
            try {         
                switch (ID) {
                    case 1:
                        NUMERO_INICIAL_1 = 2;
                        NUMERO_FINAL_1 = K;
                        String palabranumero1 = NUMERO+","+NUMERO_INICIAL_1+","+NUMERO_FINAL_1;
                        System.out.println(IP+"-"+Puerto);
                        Socket conexion1 = new Socket(IP,Puerto);
                        DataOutputStream salida1 = new DataOutputStream(conexion1.getOutputStream());
                        DataInputStream entrada1 = new DataInputStream(conexion1.getInputStream());
                        salida1.writeUTF(palabranumero1);
                        worker.respuesta1 = entrada1.readUTF();
                        System.out.println(worker.respuesta1+" ID: 1");
                        conexion1.close();
                        
                        break;
                    case 2:
                        NUMERO_INICIAL_2 = K+1;
                        NUMERO_FINAL_2 = 2*K;
                        String palabranumero2 = NUMERO+","+NUMERO_INICIAL_2+","+NUMERO_FINAL_2;
                        System.out.println(IP+"-"+Puerto);
                        Socket conexion2 = new Socket(IP,Puerto);
                        DataOutputStream salida2 = new DataOutputStream(conexion2.getOutputStream());
                        DataInputStream entrada2 = new DataInputStream(conexion2.getInputStream());
                        salida2.writeUTF(palabranumero2);
                        worker.respuesta2 = entrada2.readUTF();
                        System.out.println(worker.respuesta2+" ID: 2");
                        conexion2.close();
                        
                        break;
                    case 3:
                        
                        NUMERO_INICIAL_3 = 2*K+1;
                        NUMERO_FINAL_3 = NUMERO-1;
                        String palabranumero3 = NUMERO+","+NUMERO_INICIAL_3+","+NUMERO_FINAL_3;
                        System.out.println(IP+"-"+Puerto);
                        Socket conexion3 = new Socket(IP,Puerto);
                        DataOutputStream salida3 = new DataOutputStream(conexion3.getOutputStream());
                        DataInputStream entrada3 = new DataInputStream(conexion3.getInputStream());
                        salida3.writeUTF(palabranumero3);
                        worker.respuesta3 = entrada3.readUTF();
                        System.out.println(worker.respuesta3+" ID: 3");
                        conexion3.close();
                        
                        break;
                    default:
                        break;
                } 
            } catch (IOException ex) {
                Logger.getLogger(B.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
    
    public static void main(String args[]) throws IOException, InterruptedException {
        ServerSocket servidor = new ServerSocket(2000);
        for(;;){
            Socket conexion = servidor.accept();
            worker w1 = new worker(conexion,"localhost",2001,1);
            worker w2 = new worker(conexion,"localhost",2002,2);
            worker w3 = new worker(conexion,"localhost",2003,3);
            
            DataInputStream entrada = new DataInputStream(conexion.getInputStream());
            worker.NUMERO = entrada.readLong();
            worker.K = worker.NUMERO/3;
            
            w1.start();
            w2.start();
            w3.start();

            w1.join();
            w2.join();
            w3.join();
            

            String cadenaRegreso;
            DataOutputStream salida = new DataOutputStream(conexion.getOutputStream());
            
            if(worker.NUMERO <= 1){
                cadenaRegreso = "NO ES PRIMO";
                salida.writeUTF(cadenaRegreso);
                System.out.println(cadenaRegreso);
            }
            
            if(worker.NUMERO == 2){
                cadenaRegreso = "ES PRIMO";
                salida.writeUTF(cadenaRegreso);
                System.out.println(cadenaRegreso);
            }
            
            if(worker.respuesta1 != null && worker.respuesta2 != null && worker.respuesta3 != null){
                if(worker.respuesta1.equals("NO DIVIDE")){
                    if(worker.respuesta2.equals("NO DIVIDE")){
                        if(worker.respuesta3.equals("NO DIVIDE")){
                            cadenaRegreso = "ES PRIMO";
                            salida.writeUTF(cadenaRegreso);
                            System.out.println(cadenaRegreso);
                        }
                    }
                }
                
                if(worker.respuesta1.equals("DIVIDE")){
                    cadenaRegreso = "NO ES PRIMO";
                    salida.writeUTF(cadenaRegreso);
                    System.out.println(cadenaRegreso);
                }else if(worker.respuesta2.equals("DIVIDE")){
                    cadenaRegreso = "NO ES PRIMO";
                    salida.writeUTF(cadenaRegreso);
                    System.out.println(cadenaRegreso);
                }else if(worker.respuesta3.equals("DIVIDE")){
                    cadenaRegreso = "NO ES PRIMO";
                    salida.writeUTF(cadenaRegreso);
                    System.out.println(cadenaRegreso);
                }
            }
            System.out.println("\n");
        }
    }
}
