import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;

import javax.swing.tree.ExpandVetoException;

import jakarta.servlet.annotation.WebServlet;

@WebServlet("/servlet")
public class Studenti extends HttpServlet{
    Connection conn;
    final String connString = "jdbc:mysql://localhost:3306/University?user=user&password=password";


    public void init(){
        try{
            conn = DriverManager.getConnection(connString);
            System.out.println("Connessione effettuata");
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Connessione fallita");
        }
    }


    public void doGet(HttpServletRequest request, HttpServletResponse response){
		response.setContentType("text/html");
        PrintWriter out;
        try{
            out = response.getWriter();
            out.print("<h1>Benvenuto sul database <code>Studenti</code></h1>");

            // show all students
            String sql = "SELECT * FROM students ORDER BY matricola;";
            ResultSet res = conn.createStatement().executeQuery(sql);

            
            if(!res.next())
                out.print("Tabella vuota");
            else do{
                out.print("<b>matricola:</b>" + res.getString("matricola") + " ");
                out.print("<b>nome:</b>" + res.getString("nome") + " ");
                out.print("<b>cognome:</b>" + res.getString("cognome") + " ");
                out.print("<b>corso_di_laurea:</b><a href='/servlet?action=details&cdl=" + res.getString("corso_di_laurea") + "'>" + res.getString("corso_di_laurea") + "</a>" +"<br>");
            }while(res.next());

            // inserimento nuovo studente
            out.print("<br><br><h2>Inserimento nuovo dato</h2>");
            out.print("<form action='/servlet' method='POST'>");
            out.print("<input type='hidden' name='action' value='insert'>");
            out.print("<input type='number' name='matricola' placeholder='Matricola' required>");
            out.print("<input type='text' name='nome' placeholder='nome' required>");
            out.print("<input type='text' name='cognome' placeholder='cognome' required>");
            out.print("<input type='number' name='corso_di_laurea' placeholder='ID Corso di Laurea' required>");
            out.print("<input type='submit' value='Inserisci'>");
            
            out.print("</form>");
            

            if(request.getParameter("action").equals("details")){
                String cdl = request.getParameter("cdl");
                sql = "SELECT * FROM courses WHERE codice_corso=" + cdl;

                res = conn.createStatement().executeQuery(sql);

                while(res.next()){
                    Integer codice_corso = Integer.parseInt(res.getString("codice_corso"));
                    String nome_corso = res.getString("nome_corso");
                    String descrizione = res.getString("descrizione");
                    Integer crediti = Integer.parseInt(res.getString("crediti"));

                    out.print("<b>codice_corso</b>: " + codice_corso + " ");
                    out.print("<b>nome_corso</b>: " + nome_corso + " ");
                    out.print("<b>descrizione</b>: " + descrizione + " ");
                    out.print("<b>crediti</b>: " + crediti + "<br>");

                    homepage(out); //TODO check
                }
            }

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("text/html");
        PrintWriter out;
        try{
            out = response.getWriter();

            if(request.getParameter("action").equals("insert")){
                //insert
                Integer matricola = Integer.parseInt(request.getParameter("matricola"));
                String nome = request.getParameter("nome");
                String cognome = request.getParameter("cognome");
                Integer corso_di_laurea = Integer.parseInt(request.getParameter("corso_di_laurea"));

                String sql = "INSERT INTO students (matricola, nome, cognome, corso_di_laurea) VALUES (?,?,?,?)";
                PreparedStatement st = conn.prepareStatement(sql);
                st.setInt(1, matricola);
                st.setString(2, nome);
                st.setString(3, cognome);
                st.setInt(4, corso_di_laurea);
                int rows = st.executeUpdate();

                out.print("Inserimento effettuato");
                
                homepage(out);
            }
        } catch(Exception e){
            e.printStackTrace();
        }

    }

    public void homepage(PrintWriter out){
        out.print("<br><a href='/servlet'>Homepage</a>");
    }
}