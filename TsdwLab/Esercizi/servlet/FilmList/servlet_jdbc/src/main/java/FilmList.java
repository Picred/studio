import java.sql.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.*;
import jakarta.servlet.http.*;

@WebServlet("/servlet")
public class FilmList extends HttpServlet{
    Connection conn;
    String connString = "jdbc:mysql://localhost:3306/myDB?user=user&password=password";
    
    
    public void init(){
        try{
            conn = DriverManager.getConnection(connString);
            System.out.println("Connessione effettuata");
        }catch(Exception e){
            System.out.println("Connessione fallita");
            e.printStackTrace();
        }
    }


    public void doGet(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("text/html");
        PrintWriter out;

        try{
            out = response.getWriter();
            out.write("<h1>Benvenuto</h1>");
            out.write("<h2>Film consigliato: </h1>");

            String sql = "SELECT * FROM flist ORDER BY RAND() LIMIT 1";
            ResultSet res = conn.createStatement().executeQuery(sql);

            while(res.next()){
                String titolo = res.getString("titolo");
                String regista = res.getString("regista");

                out.write("<b>Titolo: </b>" + titolo);
                out.write("<b>Regista: </b>" + regista + "<br>");
            }

            //Fine film consigliato
            // Cerco titolo e regista che dice user

            out.write("<form action='/servlet' method='POST'>");
            out.write("<input type='hidden' name='action' value='search'>");
            out.write("<input type='text' name='titolo' placeholder='titolo' required>");
            out.write("<input type='text' name='regista' placeholder='regista' required>");
            out.write("<input type='submit' value='Cerca'>");
            out.write("</form>");

        }catch (Exception e){
            e.printStackTrace();
        }
    }



    public void doPost(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("text/html");
        PrintWriter out;
        try{
            out = response.getWriter();
            if(request.getParameter("action").equals("search")){
                // cerca dati indicati dall'utente
                String titolo = request.getParameter("titolo");
                String regista = request.getParameter("regista");

                String sql = "SELECT * FROM flist WHERE titolo='" + titolo + "' AND regista='" + regista + "'";
                ResultSet res = conn.createStatement().executeQuery(sql);

                while(res.next()){
                    out.write("Titolo: " + res.getString("titolo") + " ");
                    out.write("Regista: " + res.getString("regista") + "<br>");
                }
                // out.write("Risultato non trovato in tabella, vuoi aggiungerlo alla <code>wlist</code>?<br>");
                // out.write("<form action='/servlet' method='POST'>");
                // out.write("<input type='hidden' name='action' value='wlist'>");
                // out.write("<input type='hidden' name='titolo' value='"+ titolo + "'>");
                // out.write("<input type='hidden' name='regista' value='"+ regista + "'>");
                // out.write("<input type='submit' name='si' value='si'>");
                // out.write("<input type='submit' name='no' value='no'>");
                // out.write("</form>");
                homepage(out);
            }

            if(request.getParameter("action").equals("wlist")){
                if(request.getParameter("si") != null){
                    // add in wlist
                    String titolo = request.getParameter("titolo");
                    String regista = request.getParameter("regista");
                    String sql = "INSERT INTO wlist (titolo, regista) VALUES (?,?)";
                    PreparedStatement st = conn.prepareStatement(sql);
                    st.setString(1, titolo);
                    st.setString(2, regista);
                    st.executeUpdate();
                }
                else if(request.getParameter("no") != null){
                    out.write("Inserimento annullato in <code>wlist</code>");
                    homepage(out);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void homepage(PrintWriter out){
        out.write("<br><a href='/servlet'>Homepage</a>");
    }
}