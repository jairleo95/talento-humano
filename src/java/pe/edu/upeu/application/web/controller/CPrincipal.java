/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.edu.upeu.application.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import pe.edu.upeu.application.dao.RolDAO;
import pe.edu.upeu.application.dao.UsuarioDAO;
import pe.edu.upeu.application.dao_imp.InterfaceRolDAO;
import pe.edu.upeu.application.dao_imp.InterfaceUsuarioDAO;
import pe.edu.upeu.application.model.V_Usuario;

/**
 *
 * @author Alfa.sistemas
 */
public class CPrincipal extends HttpServlet {

    InterfaceUsuarioDAO us = new UsuarioDAO();
    V_Usuario user = new V_Usuario();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {

            String opc = request.getParameter("opc");
            if (opc.equals("ingresar")) {
                String Usuario = request.getParameter("username");
                String Clave = request.getParameter("clave");
                List<V_Usuario> u = us.Val_Usuario(Usuario, Clave);
                V_Usuario user = new V_Usuario();
                user = (V_Usuario) u.get(0);
                if (us.Val_Usuario(Usuario, Clave).size() == 1) {
                    HttpSession sesion = request.getSession(true);
                    sesion.setAttribute("IDUSER", user.getId_usuario());
                    sesion.setAttribute("USER", user.getNo_usuario());
                    sesion.setAttribute("IDPER", user.getId_empleado());
                    sesion.setAttribute("IDROL", user.getId_rol());
                    sesion.setAttribute("CL", user.getPw_usuario());
                    sesion.setAttribute("PUESTO_ID", user.getId_puesto());
                    sesion.setAttribute("AREA_ID", user.getId_area());
                    sesion.setAttribute("AREA", user.getNo_area());
                    sesion.setAttribute("DEPARTAMENTO", user.getNo_dep());
                    sesion.setAttribute("DEPARTAMENTO_ID", user.getId_departamento());
                    sesion.setAttribute("PUESTO", user.getNo_puesto());
                    
                    InterfaceRolDAO Irol = new RolDAO();
                    getServletContext().setAttribute("listarURL", Irol.listarURL(user.getId_rol()));

                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Principal.jsp");
                    dispatcher.forward(request, response);
                    //response.sendRedirect("Principal.jsp");
                } else {
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
                    dispatcher.forward(request, response);
                }
            }

        } finally {
            out.close();
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            this.processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(CPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            this.processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(CPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}