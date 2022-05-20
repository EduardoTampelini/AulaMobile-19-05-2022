package com.example.testejson;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public static final String jsonFilmes="{\"filmes\": "+" ["+
            "{\"nome\": \"BATMAN\" ,"+"\"ano\":2022,"+"\"genero\":\"Acao\""+"  },"+
            "{\"nome\": \"VINGADORES\" ,"+"\"ano\":2012,"+"\"genero\":\"Aventura\""+"  },"+
            "{\"nome\": \"VENOM\" ,"+"\"ano\":2018,"+"\"genero\":\"Acao\""+"  }"+
            "]"+
            "}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ProgressBar carregando = (ProgressBar) findViewById(R.id.progressBar);

        Button botao = (Button) findViewById(R.id.btnUsuario);
        botao.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                RetrofitUsuarioGit gitUsuario = RetrofitUsuarioGit.retrofit.create(RetrofitUsuarioGit.class);
                final Call<Usuario> call = gitUsuario.getUsuario("inducer");
//                try {
//                    Usuario usuario = call.execute().body();
//                    Log.d("MainActivity",usuario.name);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

                call.enqueue(new Callback<Usuario>(){
                    @Override
                    public void onResponse(Call<Usuario> call, Response<Usuario> response){
                        int codigo = response.code();
                        if(codigo==200){
                            Usuario usuario = response.body();
                            Log.d("MainActivity",usuario.name);
                        }else{
                            Log.d("MainActivity","Erro"+String.valueOf(codigo));
                        }
                    }

                    @Override
                    public void onFailure(Call<Usuario> call, Throwable t) {
                            Log.d("MainActivity" , t.getMessage());
                    }
                });
            }
        });
        // codigo que faz o parse do JSON criado acima
//        JSONObject filmes = null;
//        try {
//            filmes = new JSONObject(jsonFilmes);
//            JSONArray arrayfilmes = filmes.getJSONArray("filmes");
//            for (int i=0;i<arrayfilmes.length();i++){
//                JSONObject filme = arrayfilmes.getJSONObject(i);
//                Log.d("MainActivity",filme.getString("nome"));
//                Log.d("Main Activity",Integer.toString(filme.getInt("ano")));
//                Log.d("MainActivity",filme.getString("genero"));
//
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
     // clique do botão que puxa varios registros
        Button botaoSeg = (Button) findViewById(R.id.btnSeguidores);
        botaoSeg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view){
                RetrofitUsuarioGit gitUsuario = RetrofitUsuarioGit.retrofit.create(RetrofitUsuarioGit.class);
                final Call<List<Usuario>> call = gitUsuario.getSeguidores("inducer");
                carregando.setVisibility(View.VISIBLE);
                call.enqueue(new Callback<List<Usuario>>() {
                    @Override
                    public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                        if(carregando.isAnimating()){
                            carregando.setVisibility(View.GONE);
                        }
                        List<Usuario> lista = response.body();
                        for(Usuario usuario:lista){
                            Log.d("MainActivity",usuario.login);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Usuario>> call, Throwable t) {
                        Log.d("MainActivity" , t.getMessage());
                    }
                });
            }
        });
    }
}