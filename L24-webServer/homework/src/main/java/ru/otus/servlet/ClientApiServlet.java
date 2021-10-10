package ru.otus.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.otus.crm.model.Client;
import ru.otus.crm.service.DBServiceClient;

import java.io.IOException;
import java.util.stream.Collectors;


public class ClientApiServlet extends HttpServlet {

    private static final int ID_PATH_PARAM_POSITION = 1;

    private final Gson gson;
    private final DBServiceClient dbServiceClient;

    public ClientApiServlet(DBServiceClient dbServiceClient, Gson gson) {
        this.gson = gson;
        this.dbServiceClient = dbServiceClient;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Client client = dbServiceClient.getClient(extractIdFromRequest(request)).orElse(null);

        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = response.getOutputStream();
        out.print(gson.toJson(client));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestData = req.getReader().lines().collect(Collectors.joining());
        Client client = dbServiceClient.saveClient(new Client(requestData));

        resp.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = resp.getOutputStream();
        outputStream.print(gson.toJson(client));
    }

    private long extractIdFromRequest(HttpServletRequest request) {
        String[] path = request.getPathInfo().split("/");
        String id = (path.length > 1) ? path[ID_PATH_PARAM_POSITION] : String.valueOf(-1);
        return Long.parseLong(id);
    }

}
