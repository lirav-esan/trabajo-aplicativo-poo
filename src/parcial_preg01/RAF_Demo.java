/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parcial_preg01;
import java.io.IOException;
import java.util.Scanner;
import java.io.RandomAccessFile;
import javax.swing.JOptionPane;


public class RAF_Demo {
//-----------------------------------------------
private static String           ARCHIVO = "Usuarios.DAT"; // CAMBIAR DE ACUERDO AL PC
private static int              W       = 104;  //Longitud del Registro
private static Scanner          SCN = new Scanner(System.in);
private static RandomAccessFile RAF;
private static RAF_Library      LIB = new RAF_Library();
private static byte[]           BUFFER  = new byte[W];
public static String USUARIO,RECORD,CONTRA,PLACA,PRECIO,FECHA;
public static char FLAG,TIPO,ESTADO;
//-----------------------------------------------
//-----------------------------------------------
private static void BuildRecord() {
   RECORD = FLAG + 
            USUARIO + " " + 
            CONTRA ;
}
//-----------------------------------------------
private static void LoadFields(int K) {
       ID = RECORD.substring(W*K + 0,W*K + 2);
       NOMBRES= RECORD.substring(W*K + 3,W*K + 43);
       USUARIO = RECORD.substring(W*K + 44,W*K + 59).trim();
       CONTRA = RECORD.substring(W*K + 60,W*K + 70).trim();
       FECHAINI = RECORD.substring(W*K + 71,W*K + 79);
       HORAINI = RECORD.substring(W*K +  80,W*K +  86);
       FECHAFIN = RECORD.substring(W*K + 87,W*K + 95);
       HORAFIN = RECORD.substring(W*K + 96,W*K  + 102);
       ESTADO = RECORD.charAt(W*K + 103);
    }
//-----------------------------------------------
private static void ViewFields() {
String TMP;
       System.out.println("Nombre              : " + CONTRA);
       System.out.println("Placa               : " + PLACA);
       System.out.println("Precio              : " + PRECIO);
       System.out.println("Fecha de Vencimiento: " + FECHA.substring(6,8) + '/' + FECHA.substring(4,6) + '/' + FECHA.substring(0,4));
       System.out.println("Activo              : " + (ESTADO=='1'?"Activo":"Inactivo"));
}
//-----------------------------------------------
public static long BuscarCodigo(String CODIGO) throws IOException, InterruptedException {
long i,T,N,P;
   RAF = new RandomAccessFile(ARCHIVO,"r");
   T = RAF.length();
   N = T/W;
   P = 0;
   i = 1;
   while((i<=N)&&(P==0)) {
       RAF.seek((i-1)*W);
       RAF.read(BUFFER);
       RECORD = LIB.BufferToString(BUFFER);
       LoadFields();
       if(USUARIO.equals(CODIGO)==true) {
          P = i;
       }
       i++;
   }
   RAF.close();
   return P;
}
//-----------------------------------------------
public static String RegistrarUsuario(){
        String TMP;
        TMP = RAF_Frame.txtNombre.getText();
        TMP = TMP + LIB.Replicate(' ',20-TMP.length());
        return TMP;
    }
public static String RegistrarContra(){
        String TMP;
        TMP = RAF_Frame.txtPlaca.getText();
        TMP = TMP + LIB.Replicate(' ',20-TMP.length());
        return TMP;
    }
//-----------------------------------------------
//-----------------------------------------------
public static void Ingreso(String s1,String s2) throws IOException, InterruptedException {
String TMP,LINEA;
int L,P;
long T,N;
boolean Sw;
   LIB.ClearScreen();
   System.out.println("INGRESO DE PRODUCTOS");
   System.out.println("====================================================");
   //....................................................................................
   FLAG = ' ';
   //....................................................................................
   USUARIO = RegistrarUsuario(s1);
   CONTRA = RegistrarContra(s2);
   //....................................................................................
   //....................................................................................
   BuildRecord();
   //....................................................................................
   RAF = new RandomAccessFile(ARCHIVO,"rw");
   T = RAF.length();
   RAF.seek(T);
   BUFFER = LIB.StringToBuffer(RECORD,W);   
   RAF.write(BUFFER);
   RAF.close();
   System.out.println("\n\nPulse [ENTER] para Salir!!!");
   TMP = SCN.nextLine();
}
//-----------------------------------------------
public static void Modificacion(String s1,String s2,String s3,String s4,String s5,String s6,String s7) throws IOException, InterruptedException {
String TMP;
long P;
   LIB.ClearScreen();
   System.out.println("MODIFICACION DE PRODUCTOS");
   System.out.println("====================================================");
   //....................................................................................
   do {
      System.out.print("Id                  : ");
        TMP = s1.trim();
   } while(!LIB.ValidacionOK(TMP,"",3,3,"0123456789"));
   System.out.println("----------------------------------------------------");
   //....................................................................................
   P = BuscarCodigo(TMP);
   if(P>0) {

      RAF = new RandomAccessFile(ARCHIVO,"rw");
      RAF.seek((P-1)*W);
      RAF.read(BUFFER);
      RECORD = LIB.BufferToString(BUFFER);
      LoadFields();
      ViewFields();
      System.out.println("----------------------------------------------------");
      do {
         System.out.print  ("Desea Modificar el Registro? [S/N]: ");
         TMP = SCN.nextLine().trim();
      } while(!LIB.ValidacionOK(TMP,"",1,1,"SsNn"));
      if((TMP.charAt(0)=='S')||
         (TMP.charAt(0)=='s')) {
         //....................................................................................
         FLAG = ' ';
         USUARIO = RegistrarUsuario(s1);
         CONTRA = RegistrarContra(s3);
         //....................................................................................
         BuildRecord();
         //....................................................................................
         RAF = new RandomAccessFile(ARCHIVO,"rw");
         RAF.seek((P-1)*W);
         BUFFER = LIB.StringToBuffer(RECORD,W);   
         RAF.write(BUFFER);
         RAF.close();
      }
   }
   else {
      System.out.println("ERROR: El codigo especificado No Existe");
   }
   System.out.println("\n\nPulse [ENTER] para Salir!!!");
   SCN.nextLine();
}
//-----------------------------------------------
public static void Eliminacion() throws IOException, InterruptedException {
String TMP;
long P;
   LIB.ClearScreen();
   System.out.println("ELIMINACION DE PRODUCTOS");
   System.out.println("====================================================");
   //....................................................................................
   do {
      System.out.print("Id                  : ");
      TMP = SCN.nextLine().trim();
   } while(!LIB.ValidacionOK(TMP,"",3,3,"0123456789"));
   System.out.println("----------------------------------------------------");
   //....................................................................................
   P = BuscarCodigo(TMP);
   if(P>0) {
      RAF = new RandomAccessFile(ARCHIVO,"rw");
      RAF.seek((P-1)*W);
      RAF.read(BUFFER);
      RECORD = LIB.BufferToString(BUFFER);
      LoadFields();
      ViewFields();
      System.out.println("----------------------------------------------------");
      do {
         System.out.print  ("Desea Eliminar el Registro? [S/N]: ");
         TMP = SCN.nextLine().trim();
      } while(!LIB.ValidacionOK(TMP,"",1,1,"SsNn"));
      if((TMP.charAt(0)=='S')||
         (TMP.charAt(0)=='s')) {
          FLAG = '*'; 
          BuildRecord();
          BUFFER = LIB.StringToBuffer(RECORD,W);   
          RAF.seek((P-1)*W);
          RAF.write(BUFFER);
          RAF.close();
          System.out.println("El Registro ha sido Eliminado!!!");
      }
   }
   else {
      System.out.println("ERROR: El codigo especificado No Existe");
   }
   System.out.println("\n\nPulse [ENTER] para Salir!!!");
   SCN.nextLine();
}
//-----------------------------------------------
public static void Busqueda() throws IOException, InterruptedException {
String TMP;
long P;
   LIB.ClearScreen();
   System.out.println("BUSQUEDA DE PRODUCTOS");
   System.out.println("====================================================");
   //....................................................................................
   do {
      System.out.print("Id                  : ");
      TMP = SCN.nextLine().trim();
   } while(!LIB.ValidacionOK(TMP,"",3,3,"0123456789"));
   System.out.println("----------------------------------------------------");
   //....................................................................................
   P = BuscarCodigo(TMP);
   if(P>0) {
      RAF = new RandomAccessFile(ARCHIVO,"rw");
      RAF.seek((P-1)*W);
      RAF.read(BUFFER);
      RECORD = LIB.BufferToString(BUFFER);
      LoadFields();
      if(FLAG!='*') {
         ViewFields();
      }
      else {
         System.out.println("ERROR: El Registro ha sido Eliminado");
      }
      System.out.println("----------------------------------------------------");
   }
   else {
      System.out.println("ERROR: El codigo especificado No Existe");
   }
   System.out.println("\n\nPulse [ENTER] para Salir!!!");
   SCN.nextLine();
}
//-----------------------------------------------
public static void Listado() throws IOException, InterruptedException {
String TMP;
long T,N;
   LIB.ClearScreen();
   System.out.println("LISTADO DE PRODUCTOS");
   System.out.println("====================================================");
   //....................................................................................
   RAF = new RandomAccessFile(ARCHIVO,"r");
   T = RAF.length();
   N = T/W;
   for(int i=1;i<=N;i++) {
       RAF.seek((i-1)*W);
       RAF.read(BUFFER);
       RECORD = LIB.BufferToString(BUFFER);
       LoadFields();
       if(FLAG==' ') {
          System.out.println(USUARIO + ' ' +
                             CONTRA + ' ' +
                             PLACA + ' ' +
                             PRECIO + ' ' +
                             FECHA.substring(6,8) + '/' + FECHA.substring(4,6) + '/' + FECHA.substring(0,4) + ' ' +
                             (ESTADO=='1'?"Activo":"Inactivo"));
       }
   }
   RAF.close();
   System.out.println("\n\nPulse [ENTER] para Salir!!!");
}
//-----------------------------------------------
public static char MenuPrincipal() throws IOException, InterruptedException {
String CAD;
      do {
         LIB.ClearScreen();
         System.out.println(" G E S T I O N   D E   P R O D U C T O S ");
         System.out.println("=========================================");
         System.out.println();
         System.out.println("      -----------------------------------");
         System.out.println("             MENU PRINCIPAL");
         System.out.println("      -----------------------------------");
         System.out.println("       1. Ingreso de Productos");
         System.out.println("       2. Modificacion de Productos");
         System.out.println("       3. Eliminacion de Productos");
         System.out.println("       4. Busqueda por Codigo/Nombre");
         System.out.println("       5. Listado de Productos");
         System.out.println("       6. Salir");
         System.out.println("      -----------------------------------");
         System.out.print  ("       SELECCIONE OPCION: ");
         CAD = SCN.nextLine();
      } while(!LIB.ValidacionOK(CAD,"",1,1,"123456"));
      return CAD.charAt(0);
}
//-----------------------------------------------

//-----------------------------------------------
} //class
