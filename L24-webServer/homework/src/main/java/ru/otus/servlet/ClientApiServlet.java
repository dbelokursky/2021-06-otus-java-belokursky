package ru.otus.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.otus.model.Client;
import ru.otus.services.DBServiceClient;

import java.io.BufferedReader;
import java.io.IOException;


public class ClientApiServlet extends HttpServlet {

    private static final int ID_PATH_PARAM_POSITION = 1;

    private final Gson gson;
    private final DBServiceClient dbServiceClient;

    public ClientApiServlet(DBServiceClient dbServiceClient, Gson gson) {
        this.gson = gson;
        this.dbServiceClient = dbServiceClient;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader = req.getReader();
        Client client = gson.fromJson(reader, Client.class);
        dbServiceClient.saveClient(client);
    }

    private long extractIdFromRequest(HttpServletRequest request) {
        String[] path = request.getPathInfo().split("/");
        String id = (path.length > 1) ? path[ID_PATH_PARAM_POSITION] : String.valueOf(-1);
        return Long.parseLong(id);
    }

}
