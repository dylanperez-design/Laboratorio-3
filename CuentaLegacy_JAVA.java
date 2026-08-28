import java.io.BufferedWriter; 
import java.io.FileWriter; 
import java.io.IOException;
public class Main
{ 
    public static abstract class CuentaBancaria{
        private String numeroDeCuenta;
        private String titular;
        private double saldo;

        public CuentaBancaria(String numeroDeCuenta, String titular, double saldo){
            this.numeroDeCuenta=numeroDeCuenta;
            this.titular=titular;
            this.saldo=saldo;
        }
       
        public String getNumeroDeCuenta(){return numeroDeCuenta;}
        public String getTitular(){return titular;}   
        public double getSaldo(){return saldo;} 

        public void setNumeroDC(String numeroDeCuenta){this.numeroDeCuenta = numeroDeCuenta;}
        public void setTitular(String titular){this.titular = titular;}
        public void setSaldo(double saldo){this.saldo = saldo;}

        public void depositar(double cantidad){
            if(cantidad > 0){
                saldo += cantidad;
            }else{
                System.out.println("Cantidad invalida");
            }
        }
        public abstract void aplicarComisionMensual();
        public abstract void retirar(double cantidad);
    }

    public static class CuentaAhorros extends CuentaBancaria{
        private double tasaIntereses;
        private double comisionManejo;

        public CuentaAhorros(String numeroDeCuenta, String titular, double saldo,double tasaIntereses, double comisionManejo){
            super(numeroDeCuenta,titular,saldo);
            this.tasaIntereses=tasaIntereses;
            this.comisionManejo=comisionManejo;
        }

        public double getTasaIntereses(){return tasaIntereses;}   
        public double getComisionManejo(){return comisionManejo;} 

        public void setTasaIntereses(double tasaIntereses){this.tasaIntereses = tasaIntereses;}
        public void setComisionManejo(double comisionManejo){this.comisionManejo = comisionManejo;} 

        public void aplicarTasaInteres(){
            double interes = getSaldo() * (tasaIntereses/100);
            setSaldo(getSaldo() + interes);
        }

        @Override
        public void retirar(double cantidad){
            if(cantidad <= 0){
                System.out.println("El monto debe ser mayor que cero.");
                return;
            }
            if(getSaldo() >= cantidad){
                setSaldo(getSaldo() - cantidad);
                System.out.println("Retiro realizado. Nuevo saldo: "+ getSaldo());
            }else{
                 System.out.println("No se puede realizar el retiro: saldo insuficiente.");
            }
        }

        @Override
        public void aplicarComisionMensual(){

            if(getSaldo() >= comisionManejo){
                setSaldo(getSaldo() - comisionManejo);
            }else{
                System.out.println("No se pudo cobrar la comision porque el saldo es insuficiente.");
            }
        }
    }

    public static class CuentaCorriente extends CuentaBancaria{

        private double cupoSobregiro;
        private double comisionSobregiro;

        public CuentaCorriente(String numeroDeCuenta, String titular, double saldo, double cupoSobregiro, double comisionSobregiro){
            super(numeroDeCuenta,titular,saldo);
            this.cupoSobregiro=cupoSobregiro;
            this.comisionSobregiro=comisionSobregiro;
        }

        public double getCupoSobregiro(){return cupoSobregiro;}   
        public double getComisionSobregiro(){return comisionSobregiro;} 

        public void setCupoSobregiro(double cupoSobregiro){this.cupoSobregiro = cupoSobregiro;}
        public void setComisionSobregiro(double comisionSobregiro){this.comisionSobregiro = comisionSobregiro;} 

        @Override
        public void retirar(double cantidad){
            if(cantidad <= 0){
                    System.out.println("El monto debe ser mayor que cero.");
                    return;
                }
            if(getSaldo() + getCupoSobregiro() >= cantidad){
                setSaldo(getSaldo() - cantidad);
                System.out.println("Retiro realizado. Nuevo saldo: "+ getSaldo());
            }else{
                System.out.println("No se puede realizar el retiro: cupo de sobregiro exedida.");
            }
        }
        @Override
        public void aplicarComisionMensual(){
            if(getSaldo() < 0){
                setSaldo(getSaldo() - getComisionSobregiro());
            }
        }   
    }

    public class RegistroAuditoriaBancaria(){
        implements AutoCloseable{
            private BufferedWriter archivo;

            public RegistroAuditoriaBancaria(String nombreArchivo) throws IOException{
                archivo = new BufferedWriter(new FileWriter(nombreArchivo, true));
            }

            public void registrar(String mensaje) throws IOException{
                archivo.write(mensaje);
                archivo.newLine();
            }

            public void close() throws IOException {
                archivo.close();
                System.out.println("Registro de auditoria cerrado.");
            }
        }
    }    

    public static void main(String[] args) {
    CuentaBancaria cuentaAhorros = new CuentaAhorros("001", "Jose", 1000, 2.2, 10);
    CuentaBancaria cuentaCorriente = new CuentaAhorros("002", "Olga", 500, 500, 10);


        System.out.println("=== CUENTA DE AHORROS ===");
        cuentaAhorros.retirar(200);
        cuentaAhorros.aplicarComisionMensual();
        System.out.println("Saldo: " + cuentaAhorros.getSaldo());


        System.out.println("\n=== CUENTA CORRIENTE ===");
        cuentaCorriente.retirar(800);
        cuentaCorriente.aplicarComisionMensual();
        System.out.println("Saldo: "+ cuentaCorriente.getSaldo());

        try (RegistroAuditoriaBancaria registro =new RegistroAuditoriaBancaria("auditoria.txt")) {

            registro.registrar(
                "Se realizaron operaciones bancarias."
            );

            registro.registrar(
                "Saldo cuenta ahorros: "
                + cuentaAhorros.getSaldo()
            );

            registro.registrar(
                "Saldo cuenta corriente: "
                + cuentaCorriente.getSaldo()
            );

        } catch (IOException e) {

            System.out.println(
                "Error en el registro de auditoria: "
                + e.getMessage()
            );
        }
    }
}