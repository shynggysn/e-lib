package edu.epam.util;

import edu.epam.constants.ElibView;
import edu.epam.entities.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ServletUtility {

    public static void forward(String page, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        RequestDispatcher rd = request.getRequestDispatcher(page);
        rd.forward(request, response);
    }

    public static void redirect(String page, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.sendRedirect(page);
    }

    public static void handleException(Exception e, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.setAttribute("exception", e);
        ServletUtility.forward(ElibView.ERROR_CTL, request, response);
    }

    public static void setErrorMessage(String msg, HttpServletRequest request) {
        request.setAttribute("error", msg);
    }

    public static String getErrorMessage(HttpServletRequest request) {
        String val = (String) request.getAttribute("error");
        if (val == null) {
            return "";
        } else {
            return val;
        }
    }

    public static void setSuccessMessage(String msg, HttpServletRequest request) {
        request.setAttribute("success", msg);
    }

    public static String getSuccessMessage(HttpServletRequest request) {
        String val = (String) request.getAttribute("success");
        if (val == null) {
            return "";
        } else {
            return val;
        }
    }

    public static void setUser(User user, HttpServletRequest request) {
        request.setAttribute("user", user);
    }


    public static User getUser(HttpServletRequest request) {
        return (User) request.getAttribute("user");
    }

    public static String getParameter(String property, HttpServletRequest request) {
        String val = (String) request.getParameter(property);
        if (val == null) {
            return "";
        } else {
            return val;
        }
    }

    public static void setList(List list, HttpServletRequest request) {
        request.setAttribute("list", list);
    }

    public static List getList(HttpServletRequest request) {
        return (List) request.getAttribute("list");
    }

    public static void setPageNo(int pageNo, HttpServletRequest request) {
        request.setAttribute("pageNo", pageNo);
    }

    public static int getPageNo(HttpServletRequest request) {
        return (Integer) request.getAttribute("pageNo");
    }

    public static void setPageSize(int pageSize, HttpServletRequest request) {
        request.setAttribute("pageSize", pageSize);
    }

    public static int getPageSize(HttpServletRequest request) {
        return (Integer) request.getAttribute("pageSize");
    }

    public static void setOpration(String msg, HttpServletRequest request) {
        request.setAttribute("Opration", msg);
    }

    public static String getOpration(HttpServletRequest request) {
        String val = (String) request.getAttribute("Opration");
        if (val == null) {
            return "";
        } else {
            return val;
        }
    }

}
