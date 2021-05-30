package com.example.ip;

public class ClassA extends Red {
    public int bitsLibres = 24;
    public int valRang;
    public String mascara = "255.";

    public int AuxNumSubredes;
    public int AuxIteracion;

    public ClassA(){
        super();
        this.valRang=0;
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

        Subredes.add("131072");
        Subredes.add("262144");
        Subredes.add("524288");
        Subredes.add("1048576");
        Subredes.add("2097152");
        Subredes.add("4194304");
        Subredes.add("8388608");
        Subredes.add("16777216");

    }

    @Override
    public void ObtenerRango() {
        super.rangos.clear();

        if (super.NumHostPorSubred >= 256){

            super.NumHostPorSubred /= 256;

            if (super.NumHostPorSubred >= 256){
                super.NumHostPorSubred /= 256;

                super.octeto1 =255;
                super.octeto2 = 255;
                super.octeto3 = super.NumHostPorSubred -1;

            }else {
                super.octeto1 =255;
                super.octeto2 = super.NumHostPorSubred -1;
                super.octeto3 = 0;
            }


        }else{
            super.octeto1 = super.NumHostPorSubred -1;
            super.octeto2 = 0;
            super.octeto3 = 0;
        }


        //primer direccion de la red
        super.DireccionDSubred = super.Id + 0 +"."+0+"."+0;
        //firt host
        super.FirsHost = super.Id + 0 +"."+0 + "."+1;
        //Broadcast
        super.DireccionDdifusion = super.Id + octeto3 + "." + octeto2 +"."+ octeto1;
        //Last host
        super.LastHost = super.Id + 0 + "."+0 + "."+(octeto1 -1);

        super.AddRango();

        //llamar a funcion para fragmentar datos
        if (super.NumSubredes == 2097152){
            this.AuxNumSubredes = super.NumSubredes;
            super.NumSubredes /= 2;
            this.valRang = 1;
            this.AuxIteracion = super.NumSubredes;


        }else if(super.NumSubredes == 4194304){
            this.AuxNumSubredes = super.NumSubredes;
            super.NumSubredes /= 4;
            this.AuxIteracion = super.NumSubredes;
            this.valRang =2;
        }else{
            this.valRang =0;
        }

        for(int i=0; i<super.NumSubredes -1; i++){

            //cuando octeto1 se < 255
            if (super.octeto1 < 255){
                //N direccion de la red
                super.DireccionDSubred = super.Id + octeto3 +"."+ octeto2 +"."+(octeto1 +1);
                //firt host
                super.FirsHost = super.Id + octeto3 +"."+(octeto2) + "."+(octeto1 +2);
                //Acumulador de numero de host por subred
                octeto1 += this.NumHostPorSubred;
                //Broadcast
                super.DireccionDdifusion = super.Id + octeto3 +"."+ octeto2 + "."+ octeto1;
                //Last host
                super.LastHost = super.Id + octeto3 + "."+ octeto2 + "."+(octeto1 -1);
                //verificar octeto1 si es = 255
                if(octeto1 ==255){
                    //verificar si obteto 3 esigual 255 add 1
                    if(octeto2 == 255){
                        octeto2 = -1;
                        octeto3 +=1;
                    }
                    //add 1 a octeto2
                    octeto2 += 1;
                    //clear octeto1
                    octeto1 = -1;
                }

            }else if (super.octeto2 < 255){
                //N direccion de la red
                super.DireccionDSubred = super.Id + octeto3 +"."+ (octeto2 +1) +"."+(octeto1 -255);
                //firt host
                super.FirsHost = super.Id + octeto3 +"."+(octeto2 +1) + "."+(octeto1 -254);
                //Acumulador de numero de host por subred
                octeto2 += this.NumHostPorSubred;

                //Broadcast
                super.DireccionDdifusion = super.Id + octeto3 +"."+ octeto2 + "."+ octeto1;
                //Last host
                super.LastHost = super.Id + octeto3 + "."+ octeto2 + "."+(octeto1 -1);
                //verificar octeto1 si es = 255
                if(octeto2 ==255){
                    //verificar si obteto 3 esigual 255 add 1
                    //add 1 a octeto2
                    octeto3 += 1;
                    //clear octeto1
                    octeto2 = -1;
                }

            }else{
                //N direccion de la red
                super.DireccionDSubred = super.Id + (octeto3 +1) +"."+ (octeto2 -255) +"."+(octeto1 -255);
                //firt host
                super.FirsHost = super.Id + octeto3 +"."+(octeto2 -255) + "."+(octeto1 -254);
                //Acumulador de numero de host por subred
                octeto3 += this.NumHostPorSubred;
                //Broadcast
                super.DireccionDdifusion = super.Id + octeto3 +"."+ octeto2 + "."+ octeto1;
                //Last host
                super.LastHost = super.Id + octeto3 + "."+ octeto2 + "."+(octeto1 -1);
            }

            //add array dinamico
          super.AddRango();
        }

    }

    public void FragDatos(int i){
        super.rangos.clear();

        for(int j=i; j<this.AuxNumSubredes; j++){


                //N direccion de la red
                super.DireccionDSubred = super.Id + octeto3 +"."+ octeto2 +"."+(octeto1 +1);
                //firt host
                super.FirsHost = super.Id + octeto3 +"."+(octeto2) + "."+(octeto1 +2);
                //Acumulador de numero de host por subred
                octeto1 += this.NumHostPorSubred;
                //Broadcast
                super.DireccionDdifusion = super.Id + octeto3 +"."+ octeto2 + "."+ octeto1;
                //Last host
                super.LastHost = super.Id + octeto3 + "."+ octeto2 + "."+(octeto1 -1);
                //verificar octeto1 si es = 255
                if(octeto1 ==255){
                    //verificar si obteto 3 esigual 255 add 1
                    if(octeto2 == 255){
                        octeto2 = -1;
                        octeto3 +=1;
                    }
                    //add 1 a octeto2
                    octeto2 += 1;
                    //clear octeto1
                    octeto1 = -1;
                }

            //add array dinamico
            super.rangos.add(this.DireccionDSubred + " -> "+
                    this.FirsHost + " -- "+
                    this.LastHost + " -> "+
                    this.DireccionDdifusion);
        }
    }
}
