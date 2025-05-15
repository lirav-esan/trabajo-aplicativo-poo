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
private static String           ARCHIVO = "C:\\Users\\laboratorioesan\\Desktop\\Parcial_Preg01\\src\\parcial_preg01\\PRODUCTOS.DAT";
private static int              W       = 55;  //Longitud del Registro
private static Scanner          SCN = new Scanner(System.in);
private static RandomAccessFile RAF;
private static RAF_Library      LIB = new RAF_Library();
private static String[]         UnidadMedida = {"Null","Automovil","Omnibus","Motocicleta"};
private static byte[]           BUFFER  = new byte[W];
public static String ID,RECORD,NOMBRE,PLACA,PRECIO,FECHA;
public static char FLAG,TIPO,ESTADO;
//-----------------------------------------------
//-----------------------------------------------
private static void BuildRecord() {
   RECORD = FLAG + 
            ID + " " + 
            NOMBRE + " " + 
            PLACA + " " + 
            TIPO + " " + 
            PRECIO + " " + 
            FECHA + " " + 
            ESTADO;
}
//-----------------------------------------------
private static void LoadFields() {
   FLAG = RECORD.charAt(0);
   ID = RECORD.substring(1,4);
   NOMBRE = RECORD.substring(5,25);
   PLACA = RECORD.substring(26,33);
   TIPO = RECORD.charAt(34);
   PRECIO = RECORD.substring(35,41);
   FECHA = RECORD.substring(42,50);
   ESTADO = RECORD.charAt(51);
}
//-----------------------------------------------
private static void ViewFields() {
String TMP;
       TMP = UnidadMedida[(int)(TIPO)-48];
       System.out.println("Nombre              : " + NOMBRE);
       System.out.println("Placa               : " + PLACA);
       System.out.println("Tipo                : " + TMP + LIB.Replicate(' ',10-TMP.length()));
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
       if(ID.equals(CODIGO)==true) {
          P = i;
       }
       i++;
   }
   RAF.close();
   return P;
}
//-----------------------------------------------
private static String RegistrarID(String S) throws IOException, InterruptedException {
String TMP;
   
      System.out.print("Id                  : ");
      TMP = S.trim();
   if((BuscarCodigo(TMP)>0)||(LIB.ValidacionOK(TMP,"",3,3,"0123456789")==false))
       JOptionPane.showMessageDialog(null, "Ingrese una ID correcta");
   return TMP;
}  
//-----------------------------------------------
//-----------------------------------------------
private static String RegistrarNOMBRE(String S) {
String TMP;
      
         System.out.print("Nombre              : ");
         TMP = S.trim();
      if(!LIB.ValidacionOK(TMP,"",7,20,"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz. -0123456789"))
          JOptionPane.showMessageDialog(null, "Ingrese un nombre correcto");
      TMP = TMP + LIB.Replicate(' ',20-TMP.length());
   return TMP;
}  
//-----------------------------------------------
//-----------------------------------------------
private static String RegistrarPLACA(String S) {
String TMP;
   
      System.out.print("Placa                  : ");
      TMP = S.trim();
   if(!LIB.ValidacionOK(TMP,"",6,6,"ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"))
       JOptionPane.showMessageDialog(null, "Ingrese una placa correcta");
   TMP = LIB.Replicate(' ',4-TMP.length()) + TMP;
   return TMP;
}  
//-----------------------------------------------
//-----------------------------------------------
private static char RegistrarTIPO(String S) {
String TMP;
   
      System.out.print("Tipo Vehiculo          : ");
      TMP = S;
   if(!LIB.ValidacionOK(TMP,"",1,1,"0123"))
       JOptionPane.showMessageDialog(null, "Ingrese un tipo correcto");
   return TMP.charAt(0);
}  
//-----------------------------------------------
//-----------------------------------------------
private static String RegistrarPRECIO(String S) {
String TMP;
int L,P;
long T;
boolean Sw;
   
      System.out.print("Precio              : ");
      TMP = S.trim();
      L  = TMP.length();
      Sw = LIB.ValidacionOK(TMP,"",1,6,"0.123456789");
      T  = LIB.TotalOcurrencias(TMP,'.');
      P  = LIB.PosicionIzquierda(TMP,'.');
   if( (Sw==false) || (T!=1) || (P<=0) || (P!=L-3) )
       JOptionPane.showMessageDialog(null, "Ingrese un precio correcto");
   TMP = LIB.Replicate(' ',6-TMP.length()) + TMP;
   return TMP;
}  
//-----------------------------------------------
//-----------------------------------------------
private static String RegistrarFECHA(String S) {
String TMP;
   
      System.out.print("Fecha de Vencimiento: ");
      TMP = S;
   if(!LIB.FechaOK(TMP,2024,2025,false))
       JOptionPane.showMessageDialog(null, "Ingrese una fecha correcta");
   return TMP.substring(6,10) + TMP.substring(3,5) + TMP.substring(0,2);
}  
//-----------------------------------------------
//-----------------------------------------------

private static char RegistrarESTADO(String S) {
String TMP;
   
      System.out.print("Activo              : ");
      TMP = S;
   if(!LIB.ValidacionOK(TMP,"",1,1,"01"))
       JOptionPane.showMessageDialog(null, "Ingrese un estado correcto");
   return TMP.charAt(0);
}  
//-----------------------------------------------
//-----------------------------------------------
//-----------------------------------------------
//-----------------------------------------------
public static void Ingreso(String s1,String s2,String s3,String s4,String s5,String s6,String s7) throws IOException, InterruptedException {
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
   ID = RegistrarID(s1);
   NOMBRE = RegistrarNOMBRE(s2);
   PLACA = RegistrarPLACA(s3);
   TIPO = RegistrarTIPO(s4);
   PRECIO = RegistrarPRECIO(s5);
   FECHA = RegistrarFECHA(s6);
   ESTADO = RegistrarESTADO(s7);
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
         NOMBRE = RegistrarNOMBRE(s2);
         PLACA = RegistrarPLACA(s3);
         TIPO = RegistrarTIPO(s4);
         PRECIO = RegistrarPRECIO(s5);
         FECHA = RegistrarFECHA(s6);
         ESTADO = RegistrarESTADO(s7);
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
          TMP = UnidadMedida[(int)(TIPO-30)];
          System.out.println(ID + ' ' +
                             NOMBRE + ' ' +
                             PLACA + ' ' +
                             TMP + LIB.Replicate(' ',10-TMP.length()) +
                             PRECIO + ' ' +
                             FECHA.substring(6,8) + '/' + FECHA.substring(4,6) + '/' + FECHA.substring(0,4) + ' ' +
                             (ESTADO=='1'?"Activo":"Inactivo"));
       }
   }
   RAF.close();
   System.out.println("\n\nPulse [ENTER] para Salir!!!");
   TMP = SCN.nextLine();
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
