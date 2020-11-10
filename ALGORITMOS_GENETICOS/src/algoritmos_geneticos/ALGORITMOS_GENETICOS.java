
package algoritmos_geneticos;

import java.util.Random;
import javax.swing.JOptionPane;

public class ALGORITMOS_GENETICOS {

    static int filas=6;
    static int columnas=5;
    static int Nganadores=3;
    
    static String [][] Poblacion = new  String [filas][columnas];
    static String [][] PoblacionTem = new  String [filas][columnas];
    static String []  Parejas = new  String [filas];    
    static String []  Ganadores = new  String [Nganadores];    
    static double sumaToria=0;
    
    public static void IniciarPoblacion(String [][] Poblacion){
 
      System.out.println("-------------------Poblacion Inicial ------------------------");                   
      String Individuo="";
      Random ri = new Random();
      for(int i=0;i<Parejas.length;i++){
         Individuo="0,0,1,0,";               
         Individuo+=ri.nextInt(2)+",";     
         Poblacion[i][0]=""+i;
         Poblacion[i][1]=Individuo;        
     }
      
    }
  
    //Calculo del valor del individuo de acuerdo a sus genes 0 y 1*/
     public static void convertir_individuo(String [][] Poblacion){   
      double valor=0;
      for(int i=0;i<Parejas.length;i++){
          valor=0;
          String [] valores= Poblacion[i][1].split(",");       
          int indice=0;
          //Cada valor se multiplica por potencias de 2 
          for(int k=valores.length-1;k>=0;k--){ 
            valor=valor+ (Double.parseDouble(valores[k])*Math.pow(2,indice));     
            indice++;
          }
          
          Poblacion[i][2]=""+valor;
          sumaToria+=valor;       
        } 
      
    }
   
     //la formula que indica el fitness
     public static double Calidad_Individuo(String [][] Poblacion){
       //columa que tiene el valor del individuo  
       double mayor=Double.parseDouble(Poblacion[0][2]);  
       double valor=0;
      for(int i=0;i<Parejas.length;i++){        
         //se evalua cada uno con la funcion  
         valor=funcion_Fx(Double.parseDouble(Poblacion[i][2]));  
         Poblacion[i][3]=""+valor;  
         //se busca el  adaptado
         if(mayor<valor){
              mayor=valor;
         }
      }//for     
      System.out.println("-----------------------Mejor Adaptado----------------------------");
      System.out.println("-----------------------"+ mayor+"--------------------------------");    
      return(mayor);
    }
     
    
    public static void Combinacion_Mutacion(String [][] Poblacion,String [][] PoblacionTem ){        
        System.out.println("-------------------------cruce y Mutacion----------------------------------");      
        Random ri = new Random();//aleatorio combinacion
        int puntocruce=0;
        String [] IndividuoA;
        String [] IndividuoB;    
        String ParejaA="";
        //para obtener cada pareja
        for(int i=0;i<Parejas.length/2;i++){        
           IndividuoA=Poblacion[i][1].split(",");
           ParejaA=Parejas[i]; 
           String cadAdn="";
           IndividuoB=Poblacion[Integer.parseInt(ParejaA)][1].split(",");
           puntocruce=ri.nextInt(4);
           System.out.println("punto cruce ["+puntocruce+"]["+Poblacion[i][0]+"]"
           +"["+Poblacion[i][1]+"][Cruzado con] ["+Poblacion[Integer.parseInt(ParejaA)][0]+"]"
           +"["+Poblacion[Integer.parseInt(ParejaA)][1]+"]");         
           //Genes del primer Individuo
           for(int t=0;t<puntocruce;t++){            
            cadAdn+=IndividuoA[t]+",";
           } 
          //Genes del segundo individuo
           for(int t=puntocruce;t<IndividuoA.length;t++){            
             cadAdn+=IndividuoB[t]+",";
           }
           System.out.println("Nuevo Individuo ["+cadAdn+"]");          
           PoblacionTem[i][0]=""+i;
           PoblacionTem[i][1]=cadAdn;
         }       
        for(int i=0;i<Parejas.length;i++){
          Poblacion[i][0]=PoblacionTem[i][0];
          Poblacion[i][1]=PoblacionTem[i][1];        
        }
        //se muta despues combinacion
        int mutado =(Parejas.length/2)+1;
        IndividuoA=Poblacion[mutado][1].split(",");
        System.out.println("---------------------Mutacion------------------------------");
        System.out.println("------Individuo---------------------------Resultado-------------");
        int gen=ri.nextInt(4);
        if(IndividuoA[gen].equals("0")){
            IndividuoA[gen]="1";
        }else{
             IndividuoA[gen]="0";
        }
        //cadena de ADN para mutar el gen
        String cadAdn="";
        for(int t=0;t<IndividuoA.length;t++){            
            cadAdn+=IndividuoA[t]+",";
        }       
       System.out.println("["+Poblacion[mutado][0]+"]"+"["+Poblacion[mutado][1]+"]"+" Gen mutado"+"["+gen+"] Resultado=> ["+Poblacion[mutado][0]+"]"+"["+cadAdn+"]");
       Poblacion[mutado][1]=cadAdn; //se adiciona el mutado a la poblacion 
    }
    
    
    public static void Copiarse(String [][] Poblacion,String [][] PoblacionTem){     
      System.out.println("------------------Copiarse------------------------------");     
      int indice=0;
      int t=0;
      //se saca del vector de ganadores  
      for(int i=0;i<Ganadores.length;i++){            
         int ganador = Integer.parseInt(Ganadores[i]);
         PoblacionTem[indice][0]=""+(i+t); //Individuo
         PoblacionTem[indice+1][0]=""+(i+1+t);//Individuo Copiado
         
         //pob. temp
         for(int f=1;f<columnas;f++){
           PoblacionTem[indice][f]=Poblacion[ganador][f];           
           PoblacionTem[indice+1][f]=Poblacion[ganador][f];
         }                  
         indice+=2;
         t++;
     }
     //pob. Original
     for(int i=0;i<Parejas.length;i++){
         Poblacion[i][0]=PoblacionTem[i][0];
         Poblacion[i][1]=PoblacionTem[i][1] ;        
      }      
        
    }
    
    public static void verGanadores(String []  Ganadores){
      System.out.println("--------------Ganadores-----------------------");
      int gano=0;
      for(int i=0;i<Ganadores.length;i++){         
       gano=Integer.parseInt(Ganadores[i]);
       System.out.println("["+Poblacion[gano][0]+"][ "+Poblacion[gano][1] +"]["+Poblacion[gano][2] +"]["+Poblacion[gano][3]+"]");       
      }
    }
    
    public static void Torneo(String [][] Poblacion){
     System.out.println("--------------------Torneo----------------------------");          
     String desempenoA="";
     String ParejaA="";
     String desempenoB="";
     int indP=0;
     //Torneo entre las parejas
     for(int i=0;i<Parejas.length/2;i++){
       desempenoA=Poblacion[i][3];
       ParejaA=Parejas[i];          
       desempenoB=Poblacion[Integer.parseInt(ParejaA)][3];       
       System.out.println("["+Poblacion[i][0]+"][ "+Poblacion[i][1] +"]["+Poblacion[i][2] +"]"
               + "[ " +desempenoA+"] contra "
                                       + "["+Poblacion[Integer.parseInt(ParejaA)][0]+"] "
                                               + "["+Poblacion[Integer.parseInt(ParejaA)][1]+"]"
                                                       + "["+Poblacion[Integer.parseInt(ParejaA)][2] +"] "
                                                               + "["+desempenoB+"]");
       //aqui compiten 
       if(Double.parseDouble(desempenoA)>=Double.parseDouble(desempenoB)){
          Ganadores[indP]=Poblacion[i][0];
       }else{
          Ganadores[indP]=ParejaA;
       }
        indP++;         
     }
     
    }
     
    public static void Seleccion_Parejas(String [][] Poblacion){
       System.out.println("----------------------Seleccion Parejas----------------------");
        String aux=Poblacion[1][0];
        for(int i=0;i<Parejas.length;i++){              
          Parejas[(Parejas.length-1)-i]=Poblacion[i][0];     
        }
    }   
    
    public static void adaptabilidad(String [][] Poblacion,double sumatoria){
      for(int i=0;i<Parejas.length;i++){        
         Poblacion[i][4]=""+(Double.parseDouble(Poblacion[i][2])/sumatoria);                    
      }      
    }
   
    public static void verPoblacion(String [][] Poblacion,boolean pareja){    
     System.out.println("-------------------Pablacion Actual------------------------");         
     String Cadena="";
      for(int i=0;i<filas;i++){
        for(int k=0;k<columnas;k++){     
          Cadena+="["+Poblacion[i][k]+"]";      
        } 
        if(pareja)
          Cadena+="  Pareja "+Parejas[i]+"\n"; 
        else
         Cadena+=""+"\n"; 
     }
      System.out.print(Cadena);     
   }
    public static double funcion_Fx(double X){
       return(5*(X*X)-20*(X)+3);    
    }
    
    
    public static void main(String[] args) {       
        IniciarPoblacion(Poblacion);
         verPoblacion(Poblacion,false);
       double adaptados=0;
        while(adaptados<=2){            
          convertir_individuo(Poblacion);
          adaptados=Calidad_Individuo(Poblacion);
          adaptabilidad(Poblacion,sumaToria);
          verPoblacion(Poblacion,true);         
          Seleccion_Parejas(Poblacion);                
          Torneo(Poblacion);
          verGanadores(Ganadores);
          Copiarse(Poblacion,PoblacionTem);
          verPoblacion(PoblacionTem,true);       
          Seleccion_Parejas(Poblacion);               
          Combinacion_Mutacion(Poblacion,PoblacionTem); 
        }
         adaptados=Calidad_Individuo(Poblacion);
         verPoblacion(Poblacion,false); 
         
    }
}
