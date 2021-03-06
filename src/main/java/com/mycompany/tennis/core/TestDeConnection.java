package com.mycompany.tennis.core;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;

public class TestDeConnection {

    public static void main(String... args){

        Connection conn=null;
        try {
//            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tennis?useSSL=false", "COURSDB", "COURSDB");
//            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tennis?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=Europe/Paris","root","root");

         /* MysqlDataSource dataSource = new MysqlDataSource();
            dataSource.setServerName("localhost");
            dataSource.setPort(3306);
            dataSource.setDatabaseName("tennis");
            dataSource.setUseSSL(false);
            dataSource.setServerTimezone("Europe/Paris");
            dataSource.setUser("root");
            dataSource.setPassword("root"); */

            BasicDataSource dataSource = new BasicDataSource();
            dataSource.setUrl("jdbc:mysql://localhost:3306/tennis?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=Europe/Paris");
            dataSource.setUsername("root");
            dataSource.setPassword("root");

            conn=dataSource.getConnection();

            conn.setAutoCommit(false);
            PreparedStatement preparedStatement=conn.prepareStatement("SELECT NOM,PRENOM,ID FROM JOUEUR where ID=?");
            long identifiant=28L;
            preparedStatement.setLong(1,identifiant);

            ResultSet rs=preparedStatement.executeQuery();

            if (rs.next()){
                final String nom=rs.getString("NOM");
                final String prenom=rs.getString("PRENOM");
                final Long id=rs.getLong("ID");
                System.out.println("Le joueur / la joueuse représenté(e) par le numéro "+id+" est "+prenom+" "+nom);
            }
            else {
                System.out.println("Il n'y a pas d'enregistrement d'ID "+identifiant);
            }

            System.out.println("success");
            conn.commit();
        }
        catch (SQLException e){
            e.printStackTrace();
            try {
                if (conn!=null){
                    conn.rollback();
                }
            }catch (SQLException  e1){
                e1.printStackTrace();
            }
        }
        finally {

            try {
                if (conn!=null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
