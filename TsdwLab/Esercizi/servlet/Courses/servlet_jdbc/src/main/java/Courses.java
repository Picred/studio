import java.sql.*;
import java.io.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/servlet")
public class Courses extends HttpServlet{
    Connection conn;
    final String connString = "jdbc:mysql://localhost:3306/University?user=user&password=password";

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
            if(request.getParameter("action") == null){
                // Read db
                out.print("<h1>Visualizza corsi<br>");
                out.print("<form action='/servlet' method='POST'>");
                out.print("<input type='hidden' name='action' value='read'>");
                out.print("<input type='submit' value='Vedi corsi'>");
                out.print("</form>");

                // Insert db
                out.print("<h1>Inserisci nuovo corso<br>");
                out.print("<form action='/servlet' method='POST'>");
                out.print("<input type='hidden' name='action' value='insert'>");
                out.print("<input type='text' name='nome_corso' placeholder='Nome corso' required>");
                out.print("<input type='text' name='descrizione' placeholder='Descrizione' required>");
                out.print("<input type='number' name='crediti' placeholder='Crediti' required>");
                out.print("<input type='submit' value='Inserisci'>");
                out.print("</form>");
            }
            else if(request.getParameter("action").equals("update")){
                String codice_corso = request.getParameter("codice_corso");
                // form update
                out.print("<h1>Aggiorna il corso n." + codice_corso + "<br>");
                out.print("<form action='/servlet' method='POST'>");
                out.print("<input type='hidden' name='action' value='update'>");
                out.print("<input type='text' name='nome_corso' placeholder='Nome corso' required>");
                out.print("<input type='hidden' name='codice_corso' value='" + codice_corso + "'>");
                out.print("<input type='text' name='descrizione' placeholder='Descrizione' required>");
                out.print("<input type='number' name='crediti' placeholder='Crediti' required>");
                out.print("<input type='submit' value='Aggiorna'>");
                out.print("</form>");

                out.print("<br>");
                // form delete
                out.print("<form action='/servlet' method='POST'>");
                out.print("<input type='hidden' name='action' value='delete'>");
                out.print("<input type='submit' value='Elimina corso selezionato'>");
                out.print("<input type='hidden' name='codice_corso' value='" + codice_corso + "'>");
                out.print("</form>");

            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("text/html");
        PrintWriter out;

        try{
            out = response.getWriter();
            // read

            if(request.getParameter("action").equals("read")){
                String sql = "SELECT * FROM courses";
                ResultSet res = conn.createStatement().executeQuery(sql);

                if(!res.next()){
                    out.print("<h1>Tabella vuota</h1>");
                } else{
                    do{
                        Integer codice_corso = Integer.parseInt(res.getString("codice_corso"));
                        String nome_corso = res.getString("nome_corso");
                        String descrizione = res.getString("descrizione");
                        Integer crediti = Integer.parseInt(res.getString("crediti"));

                        out.print("<a href='/servlet?action=update&codice_corso=" + codice_corso + "'><b>Codice Corso:</b> " + codice_corso + "</a> ");
                        out.print("<b>Nome corso:</b> " + nome_corso + " ");
                        out.print("<b>Descrizione:</b> " + descrizione + " ");
                        out.print("<b>Crediti:</b> " + crediti + "<br>");
                    }while(res.next());
                }
                homepage(out);
            }

            // insert 
            else if(request.getParameter("action").equals("insert")){
                String nome_corso = request.getParameter("nome_corso");
                String descrizione = request.getParameter("descrizione");
                Integer crediti = Integer.parseInt(request.getParameter("crediti"));

                String sql = "INSERT INTO courses (nome_corso, descrizione, crediti) VALUES (?,?,?)";
                PreparedStatement st = conn.prepareStatement(sql);
                st.setString(1, nome_corso);
                st.setString(2, descrizione);
                st.setInt(3, crediti);
                st.executeUpdate();

                out.print("Inserimento effettuato con successo");
                homepage(out);
            }

            else if(request.getParameter("action").equals("update")){
                Integer codice_corso = Integer.parseInt(request.getParameter("codice_corso"));
                String nome_corso = request.getParameter("nome_corso");
                String descrizione = request.getParameter("descrizione");
                Integer crediti = Integer.parseInt(request.getParameter("crediti"));
                String sql = "UPDATE courses SET nome_corso='" + nome_corso + "', descrizione='" + descrizione + "', crediti=" + crediti + " WHERE codice_corso=" + codice_corso;

                conn.createStatement().executeUpdate(sql);
                out.print("Aggiornamento del corso " + codice_corso + " efettuato");
                homepage(out);
            }

            else if(request.getParameter("action").equals("delete")){
                Integer codice_corso = Integer.parseInt(request.getParameter("codice_corso"));
                String sql = "DELETE FROM courses WHERE codice_corso=" + codice_corso;
                conn.createStatement().executeUpdate(sql);
                out.print("Eliminazione del corso " + codice_corso + " efettuato");
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