public class Main
{ 
    public static abstract class CuentaBancaria{
        private String numeroDeCuenta;
        private String titular;
        private double saldo;;

        public CuentaBancaria(String numeroDeCuenta, String titular, double saldo){
            this.numeroDeCuenta=numeroDeCuenta;
            this.titular=titular;
            this.saldo=saldo;
        }
       
        public String getNumeroDC(){return numeroDeCuenta;}
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

        public double getTasaInter(){return tasaIntereses;}   
        public double getComisionM(){return comisionManejo;} 

        public void setTasaInter(double tasaIntereses){this.tasaIntereses = tasaIntereses;}
        public void setComisionM(double comisionManejo){this.comisionManejo = comisionManejo;} 

        public void aplicarTasaInteres(){

        }
        @Override
        public void retirar(double cantidad){
        
        }
        @Override
        public void aplicarComisionMensual(){

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

        public double getCupoSbro(){return cupoSobregiro;}   
        public double getComisionSobre(){return comisionSobregiro;} 

        public void setCupoSobre(double cupoSobregiro){this.cupoSobregiro = cupoSobregiro;}
        public void setComisionSobre(double comisionSobregiro){this.comisionSobregiro = comisionSobregiro;} 

        
        
        @Override
        public void retirar(double cantidad){
        
        }
        @Override
        public void aplicarComisionMensual(){

        }
       
    }

    public static void main(String[] args) {
        	
    }
}