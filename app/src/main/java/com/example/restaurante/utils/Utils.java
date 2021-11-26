package com.example.restaurante.utils;

import android.app.Activity;
import android.net.ConnectivityManager;

import com.example.restaurante.models.vo.Cardapio;
import com.example.restaurante.models.vo.ItemCardapio;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.net.InetAddress;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class Utils {
    public static boolean isInternetAvailable(Activity activity){
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(activity.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public static void SyncDatabaseJson(Activity activity) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://raw.githubusercontent.com/jadson179/Restaurante-/master/extras/cardapio.json", new AsyncHttpResponseHandler() {

            @Override
            public void onStart() { }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                JsonParser parser = new JsonParser();
                JsonArray array= parser.parse(new String(responseBody)).getAsJsonArray();
                for (int i = 0; i < array.size(); i++) {
                    JsonObject obj= (JsonObject)  array.get(i);
                    ItemCardapio item= new ItemCardapio();
                    item.setNome(obj.get("nome").toString());
                    item.setDescricao(obj.get("descricao").toString());
                    item.setCalorias(
                            Double.parseDouble(
                                    obj.get("calorias").toString()
                            )
                    );
                    item.setPreco(obj.get("preco").toString());
                    item.setCategoria(obj.get("categoria").toString());
                    item.setGluten(
                            Boolean.parseBoolean(
                                    obj.get("isGluten").toString()
                            )
                    );
                    item.setImage(
                            obj.get("image").toString()
                    );
                    DatabaseSqlite.getDBInstance(activity);
                    DatabaseSqlite.insert(activity,item);
                }

            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {}
            @Override
            public void onRetry(int retryNo) {}
        });

    };

    public static void SyncDatabaseXML(Activity activity) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://raw.githubusercontent.com/jadson179/Restaurante-/master/extras/cardapio.xml", new AsyncHttpResponseHandler() {

            @Override
            public void onStart() { }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                XMLParser parser = new XMLParser();
                Document doc = parser.getDomElement(new String(responseBody)); // getting DOM element

                NodeList nl = doc.getElementsByTagName("cardapio");

                for (int i = 0; i < nl.getLength(); i++) {
                    Element e = (Element) nl.item(i);
                    ItemCardapio item= new ItemCardapio();
                    item.setNome(
                            parser.getValue(e, "nome")
                    );

                    item.setDescricao(
                            parser.getValue(e,"descricao")
                    );
                    item.setCalorias(
                            Double.parseDouble(
                                    parser.getValue(e,"calorias")
                            )
                    );
                    item.setPreco(parser.getValue(e,"preco"));
                    item.setCategoria(parser.getValue(e,"categoria"));
                    item.setGluten(
                            Boolean.parseBoolean(
                                    parser.getValue(e,"isGluten")
                            )
                    );
                    item.setImage(
                            parser.getValue(e,"image")
                    );

                    DatabaseSqlite.getDBInstance(activity);
                    DatabaseSqlite.insert(activity,item);

                }

            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {

            }
            @Override
            public void onRetry(int retryNo) {}
        });


    };
}
