package com.example.ip;

public class ClassB extends Red {
    public int bitsLibres = 16;
    public String mascara = "255.255.";


    public ClassB(){
           super();
        Subredes.clear();

        Subredes.add("1");
        Subredes.add("2");
        Subredes.add("4");
        Subredes.add("8");
        Subredes.add("16");
        Subredes.add("32");
        Subredes.add("64");
        Subredes.add("128");
        Subredes.add("256");

        Subredes.add("512");
        Subredes.add("1024");
        Subredes.add("2048");
        Subredes.add("4096");
        Subredes.add("8192");
        Subredes.add("16384");
        Subredes.add("32768");
        Subredes.add("65536");



    }

    @Override
    public void ObtenerRango() {
        super.rangos.clear();

        //VERIFICAR LOS HOST POR SUBRED
        if (super.NumHostPorSubred >= 256){
            super.NumHostPorSubred /= 256;
            this.octeto2 = super.NumHostPorSubred -1;
            this.octeto1 = 255;
        }else {
            this.octeto1 = super.NumHostPorSubred -1;
            this.octeto2 = 0;

        }

        //primer direccion de la red
        super.DireccionDSubred = super.Id + "0.0";
        //primer host
       super.FirsHost = super.Id +"0.1";
        //Broadcast
        super.DireccionDdifusion = super.Id + octeto2 +"."+ octeto1;
        //ultimo host
        super.LastHost = super.Id + octeto2 + "."+(octeto1 -1);
        //add
        super.AddRango();



            for(int i=0; i<super.NumSubredes -1; i++){
                //Verificar aux
                if (octeto1 ==255){
                    //entra cuando el numero de subred fue dividido por 256
                    //N direccion de la red
                    super.DireccionDSubred = super.Id + (octeto2 +1) +"."+(octeto1 -255);
                    //firt host
                    super.FirsHost = super.Id + (octeto2 +1) + "."+(octeto1 -254);
                    //Acumulador de numero de host por subred
                    octeto2 += this.NumHostPorSubred;
                    //Broadcast
                    super.DireccionDdifusion = super.Id + octeto2 + "."+ octeto1;
                    //Last host
                    super.LastHost = super.Id + octeto2 + "."+(octeto1 -1);
                    //add array dinamico

                }else{
                    //entra cuando no fue dicvidido numsubred

                    //N direccion de la red
                    super.DireccionDSubred = super.Id + octeto2 +"."+(octeto1 +1);
                    //firt host
                    super.FirsHost = super.Id + (octeto2) + "."+(octeto1 +2);
                    //Acumulador de numero de host por subred
                    octeto1 += this.NumHostPorSubred;
                    //Broadcast
                    super.DireccionDdifusion = super.Id + octeto2 + "."+ octeto1;
                    //Last host
                    super.LastHost = super.Id + octeto2 + "."+(octeto1 -1);
                    //add array dinamico



                    //verificar octeto1 si es = 255
                    if(octeto1 ==255){
                        //add 1 a octeto2
                        octeto2 += 1;
                        //clear octeto1
                        octeto1 = -1;
                    }


                }
                //add array dinamico
                super.AddRango();



            }

    }
}
