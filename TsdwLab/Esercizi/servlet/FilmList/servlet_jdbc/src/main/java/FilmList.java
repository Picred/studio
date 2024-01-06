import java.sql.*;

import javax.xml.transform.Result;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;

@WebServlet("/servlet")
public class FilmList extends HttpServlet{
    Connection conn;
    final String connString = "jdbc:mysql://localhost:3306/myDB?user=user&password=password";
    
    
    public void init(){
        try{
            conn = DriverManager.getConnection(connString);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("text/html");
        PrintWriter out;
        try{
            out = response.getWriter();

            out.print("<h1>Film consigliato:</h1>");
            //get random film from flist
            String sql = "SELECT * FROM flist ORDER BY RAND() LIMIT 1";
            ResultSet res = conn.createStatement().executeQuery(sql);

            while(res.next()){
                String titolo = res.getString("titolo");
                String regista = res.getString("regista");
                out.print("<b> titolo: </b>" + titolo);
                out.print(" <b> regista: </b>" + regista);
                out.print("<br>");
            }

            // form inserimento titolo e regista per la ricerca in flist
            out.print("<h2>Ricerca film<h2>");
            out.print("<form action='/servlet' method='POST'>");
            out.print("<input type='text' name='titolo' placeholder='Titolo'>");
            out.print("<input type='text' name='regista' placeholder='Regista'>");
            out.print("<input type='hidden' name='action' value='cerca'>");
            out.print("<input type='submit' value='Cerca in flist'>");
            out.print("</form>");

            //form vedi wlist
            out.print("<h2>Visione wish list <code>wlist</code><h2>");
            out.print("<form action='/servlet' method='POST'>");
            out.print("<input type='hidden' name='action' value='seewlist'>");
            out.print("<input type='submit' value='Vedi wlist'>");
            out.print("</form>");

            //form svuota wlist
            out.print("<h2>Svuota wish list <code>wlist</code><h2>");
            out.print("<form action='/servlet' method='POST'>");
            out.print("<input type='hidden' name='action' value='deletewlist'>");
            out.print("<input type='submit' value='Svuota wlist'>");
            out.print("</form>");

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("text/html");
        PrintWriter out;
        try{
            out = response.getWriter();

            //search in flist
            if(request.getParameter("action").equals("cerca")){
                // cerco in flist titolo e regista dati da request dal form di ricerca
                String titolo = request.getParameter("titolo");
                String regista = request.getParameter("regista");
                String sql = "SELECT * FROM flist WHERE titolo='"+titolo+"' AND regista='" + regista +"'";

                ResultSet res = conn.createStatement().executeQuery(sql);

                if(!res.next()){
                    out.print("Nessun risultato trovato, vuoi aggiungerlo alla <code>wlist</code>?");
                    //form per aggiungere alla wlist si o no
                    out.print("<form action='/servlet' method='POST'>");
                    out.print("<input type='hidden' name='titolo' value='"+ titolo +"'>");
                    out.print("<input type='hidden' name='regista' value='" + regista + "'>");
                    out.print("<input type='hidden' name='action' value='addwlist'>");
                    out.print("<input type='submit' name='si' value='Si'>");
                    out.print("<input type='submit' name='no' value='No'>");
                    out.print("</form>");
                }else {
                    out.print("<h1>Film richiesto: </h1> <br>");
                    do{
                        String titoloTrovato = res.getString("titolo");
                        String registaTrovato = res.getString("regista");
                        out.print("<b> titolo: </b>" + titoloTrovato);
                        out.print(" <b> regista: </b>" + registaTrovato);
                        out.print("<br>");
                    }while(res.next());
                }
                homepage(out);
            }
            
            //Insert in wlist
            else if(request.getParameter("action").equals("addwlist")){
                if(request.getParameter("si") != null && request.getParameter("si").equals("Si")){
                    String titolo = request.getParameter("titolo");
                    String regista = request.getParameter("regista");
                    String sql = "INSERT INTO wlist (titolo, regista) VALUES (?, ?)";
                    PreparedStatement st = conn.prepareStatement(sql);
                    st.setString(1, titolo);
                    st.setString(2, regista);
                    st.executeUpdate();
                    out.print("Aggiunto in wlist");
                }
                else{
                    out.print("Film non aggiunto in wlist.");
                }
                homepage(out);
            }

            //vedi tutto wlist
            else if(request.getParameter("action").equals("seewlist")){
                String sql = "SELECT * FROM wlist";
                ResultSet res = conn.createStatement().executeQuery(sql);

                if(!res.next()){
                    out.print("<code>wlist</code> vuota");
                }
                else{
                    out.print("<h1>Wish list:</h1><br>");
                    do{
                        String titolo = res.getString("titolo");
                        String regista = res.getString("regista");
                        out.print("<b> titolo: </b>" + titolo);
                        out.print(" <b> regista: </b>" + regista);
                        out.print("<br>");
                    }while(res.next());
                }
                homepage(out);
            }

            //svuota wlist
            else if(request.getParameter("action").equals("deletewlist")){
                String sql = "DELETE FROM wlist";
                conn.createStatement().execute(sql);
                out.print("<code>wlist svuotata");
                homepage(out);
            }


        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void homepage(PrintWriter out){
        out.print("<br><a href='/servlet'>Homepage</a>");
    }
}