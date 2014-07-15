package com.harkin.luas.api;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by henry on 14/07/2014.
 */
public class GsonRequest<T> extends Request<T> {
    private static final String PROTOCOL_CHARSET = "utf-8";
    private static final String PROTOCOL_CONTENT_TYPE = String.format( "application/json; charset=%s", PROTOCOL_CHARSET );
    private final Gson mGson;
    private final Class<T> mClass;
    private final Response.Listener<T> mListener;

    public GsonRequest( int method, String url, JSONObject queryString, Class<T> someClass, Response.Listener<T> listener, Response.ErrorListener errorListener ) {

        super( method, url, errorListener );
        this.mClass = someClass;
        this.mListener = listener;
        mGson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
    }

    public GsonRequest( int method, String url, Class<T> someClass, Response.Listener<T> listener, Response.ErrorListener errorListener ) {
        super( method, url, errorListener );
        this.mClass = someClass;
        this.mListener = listener;
        mGson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
    }

    private String constructUrl( String url, String queryString ) {
        return null;
    }

    @Override
    protected void deliverResponse( T response ) {
        mListener.onResponse( response );
    }

    @Override
    protected Response<T> parseNetworkResponse( NetworkResponse response ) {
        try {
            String json = new String( response.data, HttpHeaderParser.parseCharset(response.headers) );
            return Response.success( mGson.fromJson( json, mClass ), HttpHeaderParser.parseCacheHeaders( response ) );
        } catch ( UnsupportedEncodingException e ) {
            return Response.error( new ParseError( e ) );
        } catch ( JsonSyntaxException e ) {
            return Response.error( new ParseError( e ) );
        } catch ( Exception e ) {
            e.printStackTrace();
            return Response.error( new ParseError( e ) );
        }
    }
}