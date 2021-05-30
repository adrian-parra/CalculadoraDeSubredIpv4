package com.example.ip;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    boolean si =false;
    String clase = "";
    boolean showandhide =false;
    int valrang = 0;

    String etDir1,etDir2,etDir3,etDir4;
    TextInputLayout lyEditext,lyEditext1;
    TextInputEditText etIp,etDir5;
    LinearLayout lyContenidoScroll;
    TextView tvClase,tvMascara,tvNetwork,tvBroadcast,tvLastHost;
    MaterialButton btnInfo,btnRangos,btnClear;
    Spinner SpNumSubredes,SpHostSubred;
    ProgressBar pbRangos;


    RecyclerView rvRangos;

    AdapterDatos adapterDatos;
    ClassC classC;
    ClassB classB;
    ClassA classA;

    InputMethodManager imm;
    ArrayList<String> caracteresInc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //array de caracteres erroneos
        caracteresInc = new ArrayList<>();
        caracteresInc.add("(");
        caracteresInc.add("/");
        caracteresInc.add(")");
        caracteresInc.add("-");
        caracteresInc.add(" ");
        caracteresInc.add("*");
        caracteresInc.add("#");
        caracteresInc.add("N");
        caracteresInc.add(",");
        caracteresInc.add("+");


        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);


        //Inizializar progresbar
        pbRangos = (ProgressBar)findViewById(R.id.pbRangos);


        //Inizializar layout que conmtiene el scrool view
        lyContenidoScroll = (LinearLayout)findViewById(R.id.lyConteScroolView);
        //ocultar el layout
        lyContenidoScroll.setVisibility(View.GONE);

        //inicializar recyclerview
        rvRangos = (RecyclerView)findViewById(R.id.rvRangos);
        rvRangos.setLayoutManager(new GridLayoutManager(this,1));




        //INICIALIZAR TEXTVIEW
        tvClase = (TextView)findViewById(R.id.tvClase);
        tvMascara = (TextView)findViewById(R.id.tvMascara);
        tvNetwork = (TextView)findViewById(R.id.tvNetwork);
        tvBroadcast = (TextView)findViewById(R.id.tvBroadcast);
        tvLastHost = (TextView)findViewById(R.id.tvLastHost);

        //inicializar Editext
        lyEditext = (TextInputLayout)findViewById(R.id.lyEditext);
        etIp = (TextInputEditText)findViewById(R.id.etIp);
        lyEditext1 = (TextInputLayout)findViewById(R.id.lyEditext1);
        etDir5 = (TextInputEditText)findViewById(R.id.et5);


        etIp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(count ==1){
                    //vericar que usuario no ingrese caracteres incorrectos
                    if(caracteresInc.contains(s.subSequence(s.length()-1,s.length()).toString())){
                        lyEditext.setError("CARACTER NO VALIDO");
                        etIp.setText(s.subSequence(0,s.length()-1));
                        etIp.setSelection(etIp.getText().length());

                    }else{
                        //verificar que ingrese numero primero
                        if (s.length() ==1){
                            if (s.toString().equals(".")){lyEditext.setError("IP NO VALIDA, INISIA CON NUMERO");
                            etIp.setText(s.subSequence(0,s.length()-1));}
                            else{
                                lyEditext.setError(null);
                            }

                        } else if (s.length() > 15){
                            lyEditext.setError("IP INCORRECTA");

                        }
                        else{
                            lyEditext.setError(null);
                        }


                    }

                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etIp.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press


                    if(rvRangos.getVisibility() == View.VISIBLE)rvRangos.setVisibility(View.GONE);
                    if (btnRangos.isEnabled()){
                        if(etIp.getText().length() == 0)    lyEditext.setError("INGRESE IP");
                        else InformacionRed(v);

                        return true;
                    }else{
                        Snackbar.make(v, "ESPERE SE ESTAN GENERANDO RANGOS...", Snackbar.LENGTH_LONG)
                                .setTextColor(getResources().getColor(R.color.white))
                                .setBackgroundTint(getResources().getColor(R.color.primaryVariant))
                                .show();
                    }



                }
                return false;
            }
        });




       // etDir1 = (EditText)findViewById(R.id.tvIp1);
       // etDir2 = (EditText)findViewById(R.id.tvIp2);
        //etDir3 = (EditText)findViewById(R.id.tvIp3);
        //etDir4 = (EditText)findViewById(R.id.tvIp4);
        //etDir5 = (EditText)findViewById(R.id.tvIp5);

        //INICIALIZAR BTN
        btnInfo = (MaterialButton)findViewById(R.id.btnInformacion);
        btnRangos = (MaterialButton)findViewById(R.id.btnObtenerRangos);
        btnClear = (MaterialButton)findViewById(R.id.btnClear);


        //INICIALIZAR SPINEER
        SpNumSubredes = (Spinner)findViewById(R.id.SpNumSubredes);
        SpHostSubred  = (Spinner)findViewById(R.id.SpHostSubred);

        //obtener obtetos de red




        //evento click de network(ver los primeros valorers de rangos)
        tvNetwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clase.equals("A")){
                    rvRangos.scrollToPosition(0);

                }else if (clase.equals("B")){
                    rvRangos.scrollToPosition(0);
                    // rvRangos.scrollToPosition(classB.rangos.size()-1);
                }else if (clase.equals("C")){
                    rvRangos.scrollToPosition(0);
                    // rvRangos.scrollToPosition((classC.rangos.size()/2)-1);
                }

            }
        });

           //evento click de Broadcast(ver los primeros valorers de rangos)
        tvBroadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clase.equals("A")){
                    rvRangos.scrollToPosition(classA.rangos.size()-1);

                }else if (clase.equals("B")){
                    rvRangos.scrollToPosition(classB.rangos.size()-1);
                    // rvRangos.scrollToPosition(classB.rangos.size()-1);
                }else if (clase.equals("C")){
                    rvRangos.scrollToPosition(classC.rangos.size()-1);
                    // rvRangos.scrollToPosition((classC.rangos.size()/2)-1);
                }

            }
        });


        //evento click den btn rangos
        btnRangos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (clase.equals("A")){

                    //verificar cuando subredes es mayor a 1000000
                    if(classA.valRang ==0){
                        valrang = 0;

                       // classA.ObtenerRango();
                        new Task().execute(1);
                        btnRangos.setIcon(null);
                    }else if(classA.valRang ==1){
                        //igual a 2097152

                        valrang = 1048576;

                        //classA.FragDatos(classA.AuxIteracion);
                        classA.NumSubredes = classA.AuxNumSubredes;
                        new Task().execute(2);

                    }else if(classA.valRang ==2){
                        //igual a 4194304

                        if (classA.AuxIteracion == 1048576) {
                            valrang = 1048576;
                            classA.NumSubredes = classA.AuxNumSubredes;
                            classA.AuxNumSubredes /= 2;

                            //classA.FragDatos(classA.AuxIteracion);
                            //mandra llamar a clase en segundo plano
                            new Task().execute(2);




                        }else if(classA.AuxIteracion == 2097152){
                            valrang = 2097152;

                            // Toast.makeText(getApplicationContext(),""+classA.AuxIteracion,Toast.LENGTH_SHORT).show();
                            //Toast.makeText(getApplicationContext(),""+classA.AuxNumSubredes,Toast.LENGTH_SHORT).show();
                            classA.AuxNumSubredes = (classA.NumSubredes -1048576);//=4194....

                            // classA.FragDatos(classA.AuxIteracion);
                            //mandra llamar a clase en segundo plano
                            new Task().execute(2);


                        }else if(classA.AuxIteracion == 3145728){
                            valrang = 3145728;

                            //classA.FragDatos(classA.AuxIteracion);
                            //mandra llamar a clase en segundo plano
                            new Task().execute(2);

                        }
                    }





                }else if (clase.equals("B")){
                    classB.ObtenerRango();
                    MostrarRangos();
                   // rvRangos.scrollToPosition(classB.rangos.size()-1);
                }else if (clase.equals("C")){
                    classC.ObtenerRango();
                    MostrarRangos();
                   // rvRangos.scrollToPosition((classC.rangos.size()/2)-1);
                }




            }
        });

        //evento click del btn limpiar
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              etDir5.setText("");
              etIp.setText("");
              btnRangos.setIcon(null);



                if(lyContenidoScroll.getVisibility() == View.VISIBLE){
                    lyContenidoScroll.animate().translationX(-1000).setDuration(1000).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);

                            if (showandhide){
                                lyContenidoScroll.setVisibility(View.GONE);
                                etIp.requestFocus();
                                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                                showandhide =false;
                            }


                        }
                    });


                }




                //entra cuando el objeto sea inizializado
                if (adapterDatos != null){
                    adapterDatos.list.clear();
                    adapterDatos.notifyDataSetChanged();
                    rvRangos.setVisibility(View.GONE);
                }



            }
        });

        //evento click del btn informacion
        btnInfo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Animacion para layout de contenido para el scrool view
                InformacionRed(v);




            }
        });


        //EVENTO SPINER NUM DE SUBREDES
      SpNumSubredes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

              if (clase.equals("A")){
                  classA.valRang =0;
                  SpHostSubred.setSelection(classA.bitsLibres  - position);
                  classA.NumSubredes = Integer.parseInt(classA.Subredes.get(position));

                  //mascara
                  if(position > 16){
                      tvMascara.setText(classA.mascara + "255."+ "255."+ classA.ValMascara.get(position -16));
                  }else if(position > 8){
                      tvMascara.setText(classA.mascara + "255."+ classA.ValMascara.get(position -8)+".0");
                  }else{
                      tvMascara.setText(classA.mascara + classA.ValMascara.get(position) + ".0"+"0");
                  }
                  etDir5.setText("/"+ (classA.BitsRed - (classA.bitsLibres - position)));
              }else if (clase.equals("B")){

                  SpHostSubred.setSelection(classB.bitsLibres  - position);
                  classB.NumSubredes = Integer.parseInt(classB.Subredes.get(position));

                  //mascara
                  if (position > 8){
                      tvMascara.setText(classB.mascara + "255."+ classB.ValMascara.get(position -8));
                  }else{
                      tvMascara.setText(classB.mascara + classB.ValMascara.get(position) + ".0");
                  }
                  etDir5.setText("/"+ (classB.BitsRed - (classB.bitsLibres - position)));
              }else if (clase.equals("C")){
                  SpHostSubred.setSelection(classC.bitsLibres  - position);
                  classC.NumSubredes = Integer.parseInt(classC.Subredes.get(position));
                  //obtener mascara
                  tvMascara.setText(classC.mascara + classC.ValMascara.get(position));

                  etDir5.setText("/"+ (classC.BitsRed - (classC.bitsLibres - position)));
              }





                }

          @Override
          public void onNothingSelected(AdapterView<?> parent) {

          }
      });

      SpHostSubred.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              if (clase.equals("A")){
                  SpNumSubredes.setSelection(classA.bitsLibres - position);
                  classA.NumHostPorSubred = Integer.parseInt(classA.Subredes.get(position));
              }else if (clase.equals("B")){
                  SpNumSubredes.setSelection(classB.bitsLibres - position);
                  classB.NumHostPorSubred = Integer.parseInt(classB.Subredes.get(position));
              }else if (clase.equals("C")){
                  SpNumSubredes.setSelection(classC.bitsLibres - position);
                  classC.NumHostPorSubred = Integer.parseInt(classC.HostSubred.get(position));
              }



          }

          @Override
          public void onNothingSelected(AdapterView<?> parent) {

          }
      });




        //Toast.makeText(this," "+ red.ObtenerTipoClass("140"),Toast.LENGTH_SHORT).show();
        



    }

    //Animacion contenido del scrool view show
    private void AnimLyScroll(){

        if(lyContenidoScroll.getVisibility() == View.GONE){
            showandhide = true;
            lyContenidoScroll.setTranslationX(-1000);
            lyContenidoScroll.setVisibility(View.VISIBLE);
            lyContenidoScroll.animate().translationX(0).setDuration(1000).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                }
            });
        }

    }

    //MOSTRAR RANGOS

    private void MostrarRangos(){
        rvRangos.setVisibility(View.VISIBLE);

        if (clase.equals("A")){
            adapterDatos = new AdapterDatos(classA.rangos,valrang);
        }else if (clase.equals("B")){
            adapterDatos = new AdapterDatos(classB.rangos);
        }else if (clase.equals("C")){
            adapterDatos = new AdapterDatos(classC.rangos);
        }

        rvRangos.setAdapter(adapterDatos);


    }


//INFORMACION DE RED
    public void InformacionRed(View view){

        //obtener obtetos por separado

       ObtenerObtetos();

        clase = Red.ObtenerTipoClass(etDir1);

        if (clase.equals("A")){
            AnimLyScroll();

            classA = new ClassA();
            classA.TypeClass = "A";
            tvClase.setText(classA.TypeClass);

            //etDir5.setText("/"+(classA.BitsRed - classA.bitsLibres));


            classA.Id = etDir1 + ".";


            ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                    R.layout.spinner_item,classA.Subredes);

            SpNumSubredes.setAdapter(adapter);

            ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getApplicationContext(),
                    R.layout.spinner_item,classA.Subredes);
            SpHostSubred.setAdapter(adapter1);



        }else if (clase.equals("B")){
            btnRangos.setIcon(null);
            AnimLyScroll();

            classB = new ClassB();
            classB.TypeClass = "B";

            tvClase.setText(classB.TypeClass);


           // etDir5.setText("/"+(classB.BitsRed - classB.bitsLibres));


            classB.Id = etDir1 + "."+etDir2 +".";


            ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                    R.layout.spinner_item,classB.Subredes);

            SpNumSubredes.setAdapter(adapter);

            ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getApplicationContext(),
                    R.layout.spinner_item,classB.Subredes);
            SpHostSubred.setAdapter(adapter1);



        }else if (clase.equals("C")){
            AnimLyScroll();
            btnRangos.setIcon(null);
            classC = new ClassC();
            classC.TypeClass = "C";


            //etDir5.setText("/"+(classC.BitsRed - classC.bitsLibres));

            tvClase.setText(classC.TypeClass);


            classC.Id = etDir1 + "."+etDir2 +"."+etDir3+".";


            ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                    R.layout.spinner_item,classC.Subredes);

            SpNumSubredes.setAdapter(adapter);

            ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getApplicationContext(),
                    R.layout.spinner_item,classC.HostSubred);
            SpHostSubred.setAdapter(adapter1);






        }else{

            Snackbar.make(view, "RANGOS INCORRECTOS", Snackbar.LENGTH_LONG)
                    .setTextColor(getResources().getColor(R.color.white))
                    .setBackgroundTint(getResources().getColor(R.color.primaryVariant))
                    .show();
        }
    }

    //obtener obteto 1,2,3,4
    public void ObtenerObtetos(){
        String ip = etIp.getText().toString();
        int search = 0;
        String val = "";

        etDir1 ="";
        etDir2 ="";
        etDir3 ="";
        etDir4 ="";

        for (int i=0; i<ip.length(); i++){
            val = ip.substring(i,i+1);

            if (search == 0 && !val.equals(".")){
                etDir1 += val;
            }else if(search == 1 && !val.equals(".")){
                etDir2 += val;
            }else if(search == 2 && !val.equals(".")){
                etDir3 += val;
            }else if(search == 3 && !val.equals(".")){
                etDir4 += val;
            }


            if (val.equals("."))search++;



        }


    }

    //CLASE ASYNTASK
    class Task extends AsyncTask<Integer, Void, ArrayList<String>> {


        @Override
        protected ArrayList<String> doInBackground(Integer... integers) {

            if (integers[0] == 1){
             si = false;
                classA.ObtenerRango();
            }else{
                si = true;
                classA.FragDatos(classA.AuxIteracion);
            }

            return classA.rangos;
        }

        @Override
        protected void onPreExecute(){

            //desabilitar btn
            btnRangos.setEnabled(false);
            //habilitar progresbar
            pbRangos.setVisibility(View.VISIBLE);

        }


        @Override
        protected void onPostExecute(ArrayList<String> result){
            Toast.makeText(getApplicationContext(),"Rangos generados",Toast.LENGTH_SHORT).show();


            MostrarRangos();


            if(classA.valRang == 2 && si){
                if(classA.AuxIteracion == 1048576){
                    btnRangos.setIcon(getResources().getDrawable(R.drawable.ic_24));

                    classA.AuxIteracion = classA.AuxNumSubredes;//=2097...
                    classA.AuxNumSubredes = classA.NumSubredes;
                }else if(classA.AuxIteracion == 2097152){
                    btnRangos.setIcon(getResources().getDrawable(R.drawable.ic_34));

                    classA.AuxIteracion += 1048576;
                    classA.AuxNumSubredes = classA.NumSubredes;
                }else if(classA.AuxIteracion == 3145728){
                    btnRangos.setIcon(getResources().getDrawable(R.drawable.ic_44));
                    Toast.makeText(getApplicationContext(),"3",Toast.LENGTH_SHORT).show();
                        classA.valRang =0;
                }
            }else if(classA.valRang == 1){
                if(classA.NumSubredes == 1048576){
                    btnRangos.setIcon(getResources().getDrawable(R.drawable.ic_12));
                }else if(classA.NumSubredes == 2097152){
                    btnRangos.setIcon(getResources().getDrawable(R.drawable.ic_22));
                    classA.valRang =0;
                }
            }else if(classA.valRang == 2 && si == false){
                btnRangos.setIcon(getResources().getDrawable(R.drawable.ic_14));
            }

            //habilitar btn
            btnRangos.setEnabled(true);

            pbRangos.setVisibility(View.GONE);
        }



    }


}