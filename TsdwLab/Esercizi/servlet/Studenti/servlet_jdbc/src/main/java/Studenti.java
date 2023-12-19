import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

import java.io.*;
import java.sql.*;

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
            String sql = "SELECT * FROM students;";
            ResultSet res = conn.createStatement().executeQuery(sql);
            int matricola;
            
            if(!res.next())
                out.print("Tabella vuota");
            else do{
                out.print("<b>matricola:</b>" + res.getString("matricola") + " ");
                out.print("<b>nome:</b>" + res.getString("nome") + " ");
                out.print("<b>cognome:</b>" + res.getString("cognome") + " ");
                out.print("<b>corso_di_laurea:</b><a href='/servlet?action=details&cdl=" + res.getString("corso_di_laurea") + "&matricola=" + res.getString("matricola") +  "'>" + res.getString("corso_di_laurea") + "</a>" +"<br>");
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
            

            if(request.getParameter("action") != null && request.getParameter("action").equals("details")){
                String cdl = request.getParameter("cdl");
                matricola = Integer.parseInt(request.getParameter("matricola"));
                out.print("<h2>Informazioni sul corso con codice " + cdl + "</h2>");
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

                    // update cdl form
                    out.print("<h3>Aggiorna il corso di laurea dello studente " + matricola + " appena selezionato </h3>");
                    out.print("<form action='/servlet' method='POST'>");
                    out.print("<input type='hidden' name='action' value='update'>");
                    out.print("<input type='hidden' name='matricola' value='" + matricola + "'>");
                    out.print("<input type='number' name='newcdl' placeholder='Nuovo cdl'>");
                    out.print("<input type='submit' value='Aggiorna'>");
                    out.print("</form>");

                    // delete student form
                    out.print("<form action='/servlet' method='POST'>");
                    out.print("<input type='hidden' name='action' value='delete'>");
                    out.print("<input type='hidden' name='matricola' value='" + matricola + "'>");
                    out.print("<input type='submit' value='Elimina studente'>");
                    out.print("</form>");


                    homepage(out);
                } 
            } // end if GET

        } catch(SQLException e){
            System.out.println("Error while connecting to database");
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
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

            else if(request.getParameter("action").equals("update")){
                // update 
                Integer matricola = Integer.parseInt(request.getParameter("matricola"));
                Integer newcdl = Integer.parseInt(request.getParameter("newcdl"));
                String sql = "UPDATE students SET corso_di_laurea=" + newcdl + " WHERE matricola='" + matricola + "';";

                conn.createStatement().executeUpdate(sql);
                out.print("Aggiornamento effettuato");
                homepage(out);
            }
            else if(request.getParameter("action").equals("delete")){
                // delete
                Integer matricola = Integer.parseInt(request.getParameter("matricola"));
                String sql = "DELETE FROM students WHERE matricola=" + matricola;

                conn.createStatement().executeUpdate(sql);
                out.print("Eliminazione effettuata");
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