package com.example.restaurante;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.restaurante.models.vo.Cardapio;
import com.example.restaurante.models.vo.ItemCardapio;
import com.example.restaurante.utils.DatabaseSqlite;
import com.example.restaurante.utils.Utils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        Cardapio cardapio;

        // Verifica se tem conexão com uma rede
        if (Utils.isInternetAvailable(this)) {
            DatabaseSqlite db = new DatabaseSqlite(this);
            cardapio = db.cardapio(this);
            DatabaseSqlite.deleteAll(this);
            Utils.SyncDatabaseJson(this);
        } else {
            DatabaseSqlite db = new DatabaseSqlite(this);
            cardapio = db.cardapio(this);
        }

        LinearLayout ll=(LinearLayout) findViewById(R.id.llItems);


        for(ItemCardapio itemCardapio: cardapio.getItemCardapios()) {

            LinearLayout horizontalLL = new LinearLayout(this);
            horizontalLL.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            horizontalLL.setOrientation(LinearLayout.HORIZONTAL);

            ll.addView(horizontalLL);

            ImageView iv = new ImageView(getApplicationContext());

            if (Utils.isInternetAvailable(this)) {
                AsyncHttpClient client = new AsyncHttpClient();

                client.get(itemCardapio.getImage().replace("\"", ""), new AsyncHttpResponseHandler() {

                    @Override
                    public void onStart() { }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(responseBody,0,responseBody.length);
                        iv.setImageBitmap(bitmap);
                    }
                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {}
                    @Override
                    public void onRetry(int retryNo) {}
                });


            } else {
                iv.setImageDrawable(getDrawable(R.drawable.offline));
            }

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(200, 300);

            iv.setLayoutParams(lp);
            horizontalLL.addView(iv);

            LinearLayout verticalLL = new LinearLayout(this);
            verticalLL.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            verticalLL.setOrientation(LinearLayout.VERTICAL);

            TextView name = new TextView(ll.getContext());
            name.setText(itemCardapio.getNome());
            name.setTextSize(22f);
            name.setPadding(10, 30, 2, 15);

            TextView description = new TextView(ll.getContext());
            description.setText(itemCardapio.getDescricao());
            description.setTextSize(9f);
            description.setPadding(10, 1, 2, 15);
            description.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);

            TextView categoria = new TextView(ll.getContext());
            categoria.setText( "categoria: " + itemCardapio.getCategoria());
            categoria.setTextSize(9f);
            categoria.setPadding(10, 1, 2, 15);
            categoria.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);

            TextView preco = new TextView(ll.getContext());
            preco.setText( "preco: " + (Utils.isInternetAvailable(this) ? itemCardapio.getPreco(): "A consultar"));
            preco.setTextSize(9f);
            preco.setPadding(10, 1, 2, 15);
            preco.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);


            TextView gluten = new TextView(ll.getContext());
            gluten.setText( "Glútem: "+(itemCardapio.isGluten() ? "Sim":"Não"));
            gluten.setTextSize(9f);
            gluten.setPadding(10, 1, 2, 15);
            gluten.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);

            TextView calorias = new TextView(ll.getContext());
            calorias.setText( "calorias: " + itemCardapio.getCalorias());
            calorias.setTextSize(9f);
            calorias.setPadding(10, 1, 2, 15);
            calorias.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);

            verticalLL.addView(name);
            verticalLL.addView(description);
            verticalLL.addView(categoria);
            verticalLL.addView(preco);
            verticalLL.addView(gluten);
            verticalLL.addView(calorias);

            horizontalLL.addView(verticalLL);


        }


    }
}