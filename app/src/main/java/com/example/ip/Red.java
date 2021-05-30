package com.example.ip;

import java.util.ArrayList;

public abstract class Red {

    public int NumSubredes,NumHostPorSubred,BitsRed, octeto1, octeto2, octeto3;
    public String Id,TypeClass,DireccionDSubred,DireccionDdifusion,FirsHost,LastHost;


    public  ArrayList<String> Subredes= new ArrayList<>();
    public  ArrayList<String> HostSubred = new ArrayList<>();
    public ArrayList<String> rangos = new ArrayList<String>();
    public ArrayList<String> ValMascara = new ArrayList<>();

    public Red() {
        this.BitsRed = 32;
        ValMascara.clear();

        ValMascara.add("0");
        ValMascara.add("128");
        ValMascara.add("192");
        ValMascara.add("224");
        ValMascara.add("240");
        ValMascara.add("248");
        ValMascara.add("252");
        ValMascara.add("254");
        ValMascara.add("255");
    }

    public abstract void ObtenerRango();

    public void AddRango(){

        rangos.add(this.DireccionDSubred + " -> "+
                this.FirsHost + " -- "+
                this.LastHost + " -> "+
                this.DireccionDdifusion );
    }

    public static String ObtenerTipoClass(String dir){
        //class C
        if (Integer.parseInt(dir) >= 192 && Integer.parseInt(dir) <= 223) {
            return "C";
        }
        //class b
        else if (Integer.parseInt(dir) >= 128 && Integer.parseInt(dir) <= 191){
            return "B";
        }
        //class a
        else if(Integer.parseInt(dir) >= 0 && Integer.parseInt(dir) <= 127){
            return "A";
        }else{
            return "E";
        }

    }



}
