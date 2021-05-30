package com.example.ip;

public class ClassC extends Red {
        public int bitsLibres = 8;
        public  String mascara = "255.255.255.";





        public ClassC(){
            super();
            Subredes.clear();
            HostSubred.clear();

            Subredes.add("1");
            Subredes.add("2");
            Subredes.add("4");
            Subredes.add("8");
            Subredes.add("16");
            Subredes.add("32");
            Subredes.add("64");
            Subredes.add("128");
            Subredes.add("256");

            HostSubred.add("1");
            HostSubred.add("2");
            HostSubred.add("4");
            HostSubred.add("8");
            HostSubred.add("16");
            HostSubred.add("32");
            HostSubred.add("64");
            HostSubred.add("128");
            HostSubred.add("256");

        }


    @Override
    public void ObtenerRango() {

            super.rangos.clear();

            //primer direccion de la red
            super.DireccionDSubred = super.Id + "0";
            //primer host
            super.FirsHost = super.Id + "1";

            //BROADCAST
            super.DireccionDdifusion = super.Id + Integer.toString((this.NumHostPorSubred - 1));
        //ultimo host
            super.LastHost = super.Id + (this.NumHostPorSubred -2);
            //add en el array dinamico
            super.AddRango();

            //add a octeto 3 el valor de host por subred
            super.octeto1 = this.NumHostPorSubred;

            for (int i=0; i< super.NumSubredes - 1; i++){
                //N direccion de la red
                super.DireccionDSubred = super.Id + octeto1;
                //primer host
                super.FirsHost = super.Id + (octeto1+1);
                //Acumulador de numero de host por subred
                octeto1 += this.NumHostPorSubred;
                //Broadcast
                super.DireccionDdifusion = super.Id + (octeto1 -1);
                //ultimo host
                super.LastHost = super.Id + (octeto1 -2);
                //add al arreglo dinamico
                super.AddRango();



            }


    }
}
