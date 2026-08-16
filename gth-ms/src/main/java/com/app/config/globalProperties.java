/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.config;

/**
 *
 * @author ALPHA
 */
public class globalProperties {

    /* Enviroment variable*/
    /*change when update server (for cache conflicts)*/
    public static String VERSION_CSS = "2.1.7";
    public static String VERSION_JS = "2.7.7";

    /* WebService Carga Academica*/
    public static final String keyApp = System.getenv().getOrDefault("GTH_WS_KEYAPP", "");
    public static final String keyID = System.getenv().getOrDefault("GTH_WS_KEYID", "");
    public static final String serverURI = System.getenv().getOrDefault("GTH_WS_SERVER_URI", "https://webapp.upeu.edu.pe/");
    public static final String service = System.getenv().getOrDefault("GTH_WS_SERVICE_URI", "https://webapp.upeu.edu.pe/webservices/wsdl4rrhh/");

    public static final String DOCENTESXCURSO_METHOD[] = {"DocenteXCurso", "ns1", "ns1:DocenteXCursoResponse"};

    /*oracle bd connection */
    public static final String HOSTNAME = System.getenv().getOrDefault("GTH_ORACLE_HOST", "localhost");
    public static final String USER = System.getenv().getOrDefault("GTH_ORACLE_USER", "");
    public static final String USER_PWD = System.getenv().getOrDefault("GTH_ORACLE_PASSWORD", "");
    public static final String PORT = System.getenv().getOrDefault("GTH_ORACLE_PORT", "1521");
    public static final String SID = System.getenv().getOrDefault("GTH_ORACLE_SID", "xe");
    
     /*public static final String HOSTNAME = "localhost";
     public static final String USER = "gth";
     public static final String USER_PWD = "123";
     public static final String PORT = "1521";
     public static final String SID = "xe";*/

}
