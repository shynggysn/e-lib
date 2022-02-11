package edu.epam;

import com.sun.awt.SecurityWarning;
import edu.epam.constants.ElibView;
import edu.epam.dao.BookDAO;
import edu.epam.dao.CategoryDAO;
import edu.epam.dao.UserDAO;
import edu.epam.dao.UserOpinionDAO;
import edu.epam.entities.*;
import edu.epam.exceptions.DatabaseException;
import edu.epam.exceptions.DuplicateRecordException;
import edu.epam.util.DataUtility;
import edu.epam.util.PropertyReader;
import edu.epam.util.SaltedMD5Hashing;
import edu.epam.util.ServletUtility;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;

public class MyServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(MyServlet.class);

    private PrintWriter out = null;
    private HttpSession session = null;

    protected User populateUser(HttpServletRequest request) {
        User bean = new User();
        String salt= "";
        try {
            salt = SaltedMD5Hashing.getSalt();
        } catch(NoSuchAlgorithmException e){
            LOGGER.error(e.getMessage());
        } catch (NoSuchProviderException e) {
            LOGGER.error(e.getMessage());
        }
        String password = SaltedMD5Hashing.getSecurePassword(request.getParameter("password"), salt);
        String passwordToSave = salt +  ":" + password;
        bean.setName(request.getParameter("name"));
        bean.setSurname(request.getParameter("surname"));
        bean.setMail(DataUtility.getString(request.getParameter("email")));
        bean.setPassword(passwordToSave);
        bean.setPhoneNumber(request.getParameter("phone"));

        return bean;
    }

    protected Book populateBook(HttpServletRequest request) throws IOException, ServletException {
        String folderName = "resources";
        //String uploadPath = request.getServletContext().getRealPath("") + File.separator + folderName; //for netbeans use this code
        //String uploadPath = request.getServletContext().getRealPath("") + folderName; //for eclipse use this code
        String uploadPath = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "/resources";
        LOGGER.info("uploadPath path is:" + uploadPath);
        File dir = new File(uploadPath);
        if (!dir.exists())
            dir.mkdirs();
        Part filePart = request.getPart("file");
        Part picturePart = request.getPart("picture");
        String name = request.getParameter("name");
        String author = request.getParameter("author");
        String suggestedBy = request.getParameter("user_id"); // may be null if admin adds book

        String fileName = filePart.getSubmittedFileName();
        String filePath = folderName + File.separator + fileName;
        InputStream fileStream = filePart.getInputStream();
        Files.copy(fileStream, Paths.get(uploadPath + File.separator + fileName), StandardCopyOption.REPLACE_EXISTING);

        String pictureName = picturePart.getSubmittedFileName();
        String picturePath = folderName + File.separator + pictureName;
        InputStream pictureStream = picturePart.getInputStream();
        Files.copy(pictureStream, Paths.get(uploadPath + File.separator + pictureName), StandardCopyOption.REPLACE_EXISTING);

        Book book = new Book(name, author, fileName, filePath, pictureName, picturePath, suggestedBy);
        return book;
    }

    protected void uploadBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Book book = populateBook(request);
            int status = BookDAO.create(book);
            request.setAttribute("fileName", book.getFileName());
            if (status > 0) {
                String msg = "File \"" + book.getFileName() + "\" is uploaded successfully...";
                ServletUtility.setSuccessMessage(msg, request);
                ServletUtility.forward(ElibView.CREATE_BOOK_VIEW, request, response);
                return;
            } else {
                String msg = "OOPS! File \"" + book.getFileName() + "\" is not uploaded...";
                ServletUtility.setErrorMessage(msg, request);
                ServletUtility.forward(ElibView.CREATE_BOOK_VIEW, request, response);
                return;
            }
        } catch (DatabaseException e){
            LOGGER.error(e.getMessage());
            request.setAttribute("exception",e);
            ServletUtility.forward(ElibView.ERROR_VIEW, request, response);
        } catch (DuplicateRecordException e){
            LOGGER.error(e.getMessage());
            request.setAttribute("exception",e);
            ServletUtility.forward(ElibView.ERROR_VIEW, request, response);
        }
    }

    protected void downloadBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int BUFFER_SIZE = 1024 * 100;
        int bookID = Integer.parseInt(request.getParameter("bookID"));
        String name = request.getParameter("name");
        String author = request.getParameter("author");
        String uploadPath = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "/resources";
        String fileName = author + "_" + name +".pdf";
        String filePath = uploadPath + File.separator + fileName;

        System.out.println("fileName:" + fileName);
        System.out.println("filePath :" + filePath);

        File file = new File(filePath);
        OutputStream outStream = null;
        FileInputStream inputStream = null;

        if (file.exists()) {

            /**
             * ** Setting The Content Attributes For The Response Object
             * ***
             */
            String mimeType = "application/octet-stream";
            response.setContentType(mimeType);

            /**
             * ** Setting The Headers For The Response Object ***
             */
            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"", file.getName());
            response.setHeader(headerKey, headerValue);

            try {

                /**
                 * ** Get The Output Stream Of The Response ***
                 */
                outStream = response.getOutputStream();
                inputStream = new FileInputStream(file);
                byte[] buffer = new byte[BUFFER_SIZE];
                int bytesRead = -1;

                /**
                 * ** Write Each Byte Of Data Read From The Input Stream
                 * Write Each Byte Of Data Read From The Input Stream Into
                 * The Output Stream ***
                 */
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outStream.write(buffer, 0, bytesRead);
                }
            } catch (IOException ioExObj) {
                System.out.println("Exception While Performing The I/O Operation?= " + ioExObj.getMessage());
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                outStream.flush();
                if (outStream != null) {
                    outStream.close();
                }
            }
        } else {
            response.setContentType("text/html");
            response.getWriter().println("<h3>File " + fileName + " Is Not Present .....!</h3>");
        }
    }

    public void getUserBooks(HttpServletRequest request, HttpServletResponse response, boolean hasDownloaded) throws ServletException, IOException {
        List<BookOnSite> booksOnSite = null;
        int userID = Integer.parseInt(request.getParameter("userID"));
        try {
            booksOnSite = BookDAO.getUserBooks(userID,hasDownloaded);
            request.setAttribute("books", booksOnSite);
        } catch (DatabaseException e){
            System.out.println("database exception occured");
            ServletUtility.handleException(e,request,response);
        }
        if (hasDownloaded)
            ServletUtility.forward(ElibView.MY_DOWNLOADS_VIEW, request, response);
        else ServletUtility.forward(ElibView.MY_WISH_LIST_VIEW, request, response);
    }

    public void getUserSuggestions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Book> books = null;
        List<Request> requests = null;
        try {
            books = BookDAO.getSuggestedBooks();
            request.setAttribute("books", books);
            requests = UserOpinionDAO.getUserRequests();
            request.setAttribute("requests", requests);
        } catch (DatabaseException e){
            System.out.println("database exception occured");
            ServletUtility.handleException(e,request,response);
        }
        ServletUtility.forward(ElibView.USER_SUGGESTIONS_VIEW, request, response);
    }

    public static void leaveComment(HttpServletRequest request, HttpServletResponse response) {
        String comment = request.getParameter("comment");

        if(comment==null||comment.trim().equals("")){
            //alert user
        }else{
            try {
                UserOpinionDAO.saveRequest(request,response);
            }catch(DatabaseException e){
                LOGGER.error(e.getMessage());
            }
        }
    }

    public static void changeLanguage(String language){
        if (language.equals("russian"))
            PropertyReader.changeLanguage(PropertyReader.ru_RU);
        else PropertyReader.changeLanguage(PropertyReader.en_US);
    }

    public void changeUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            User user = populateUser(request);
            UserDAO.updateUser(user);
            ServletUtility.forward(ElibView.PROFILE_VIEW, request, response);
        } catch(DatabaseException e){
            ServletUtility.forward(ElibView.ERROR_VIEW,request,response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //указываем кодировку для HTML-страницы, отправляемой клиенту
        response.setContentType("text/html; charset=windows-1251");
        //указываем кодировку для данных полученых от клиента
        request.setCharacterEncoding("CP1251");

        String uri = request.getRequestURI();

        List<Category> categories = null;
        List<Book> books = null;
        List<BookOnSite> booksOnSite = null;
        List<Comment> comments = null;

        switch(uri){
            case ElibView.LOGIN:
                LOGGER.info("entered login page");
                ServletUtility.forward(ElibView.LOGIN_VIEW, request, response);
                break;
            case ElibView.CATALOG:
                try {
                    categories = CategoryDAO.getCategories();
                    books = BookDAO.list();
                } catch (DatabaseException e) {
                    ServletUtility.handleException(e, request, response);
                }
                request.setAttribute("categories", categories);
                request.setAttribute("books", books);
                ServletUtility.forward(ElibView.CATALOG_VIEW, request, response);
                break;
            case ElibView.ABOUT:
                ServletUtility.forward(ElibView.ABOUT_VIEW, request, response);
                break;
            case ElibView.SEARCH:
                String query = request.getParameter("query");
                try {
                    booksOnSite = BookDAO.list(query);
                } catch (DatabaseException e) {
                    ServletUtility.handleException(e, request, response);
                }
                request.setAttribute("books", booksOnSite);
                ServletUtility.forward(ElibView.SEARCH_VIEW, request, response);
                break;
            case ElibView.LOGOUT:
                HttpSession session = request.getSession(false);
                if (session != null) {
                    session.removeAttribute("user");
                }
                ServletUtility.forward(ElibView.LOGIN_VIEW,request,response);
                break;
            case ElibView.REGISTER:
                ServletUtility.forward(ElibView.REGISTRATION_VIEW, request, response);
                break;
            case ElibView.BOOK:
                int bookID = Integer.parseInt(request.getParameter("bookID"));
                BookOnSite book = null;
                comments = null;
                categories = null;
                try {
                    book = BookDAO.getBookByID(bookID);
                    comments = UserOpinionDAO.getCommentsByBookID(bookID);
                    categories = CategoryDAO.getCategoryByBookID(bookID);
                } catch (DatabaseException e){
                    System.out.println("database exception occured");
                    ServletUtility.handleException(e,request,response);
                }
                request.setAttribute("book", book);
                request.setAttribute("comments",comments);
                request.setAttribute("categories", categories);
                ServletUtility.forward(ElibView.BOOK_VIEW, request, response);
                break;
            case ElibView.CATEGORY:
                int categoryID = Integer.parseInt(request.getParameter("categoryID"));
                books = null;
                try {
                    books = BookDAO.getBooksByCategoryID(categoryID);
                } catch (DatabaseException e){
                    System.out.println("database exception occured");
                    ServletUtility.handleException(e,request,response);
                }
                request.setAttribute("books", books);
                ServletUtility.forward(ElibView.CATEGORY_VIEW, request, response);
                break;
            case ElibView.DOWNLOAD:
                downloadBook(request,response);
                break;
            case ElibView.MY_DOWNLOADS:
                getUserBooks(request,response,true);
                break;
            case ElibView.MY_WISH_LIST:
                getUserBooks(request,response,false);
                break;
            case ElibView.UPLOAD_BOOK:
                ServletUtility.forward(ElibView.CREATE_BOOK_VIEW, request, response);
                break;
            case ElibView.USER_SUGGESTIONS:
                getUserSuggestions(request,response);
                break;
            case ElibView.ERROR_CTL:
                ServletUtility.forward(ElibView.ERROR_VIEW, request, response);
                break;
            case ElibView.COMMENT:
                leaveComment(request,response);
                break;
            case ElibView.LANGUAGE:
                changeLanguage(request.getParameter("locale"));
                ServletUtility.redirect(ElibView.INDEX,request,response);
                break;
            case ElibView.PROFILE:
                ServletUtility.forward(ElibView.PROFILE_VIEW, request, response);
                break;
            default:
                ServletUtility.forward(ElibView.ERROR_VIEW, request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //указываем кодировку для HTML-страницы, отправляемой клиенту
        response.setContentType("text/html; charset=windows-1251");
        //указываем кодировку для данных полученых от клиента
        request.setCharacterEncoding("CP1251");

        String uri = request.getRequestURI();

        switch (uri){
            case ElibView.LOGIN:
                String email = request.getParameter("email");
                String password = request.getParameter("password");

                try {
                    User user = UserDAO.checkLogin(email, password);
                    String destPage = ElibView.LOGIN_VIEW;
                    if (user != null) {
                        HttpSession session = request.getSession();
                        session.setAttribute("user", user);
                        session.setMaxInactiveInterval(3*60*60);
                        ServletUtility.redirect(ElibView.INDEX,request,response);
                        return;
                    } else {
                        String message = "Invalid email/password";
                        request.setAttribute("message", message);
                    }
                    ServletUtility.forward(destPage,request,response);
                } catch (DatabaseException e) {
                    e.printStackTrace();
                    ServletUtility.handleException(e, request, response);
                }
                break;
            case ElibView.REGISTER:
                LOGGER.info("registering user");
                User user = (User) populateUser(request);
                LOGGER.info(user.toString());
                registerUser(user, request, response);
            case ElibView.UPLOAD_BOOK:
                uploadBook(request, response);
            case ElibView.PROFILE:
                changeUser(request,response);
                break;
            case ElibView.DECIDE:
                decideSuggestion(request,response);
                break;
            default:
                ServletUtility.forward(ElibView.ERROR_VIEW,request,response);
                break;
        }
    }

    private void decideSuggestion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int bookID = Integer.parseInt(request.getParameter("bookID"));
        String action = request.getParameter("action");
        String type = request.getParameter("type");

        if (type.equals("upload")){
            if(action.equals("accept")){
                try {
                    BookDAO.acceptBook(bookID,true);
                } catch (DatabaseException e) {
                    e.printStackTrace();
                }
                ServletUtility.forward(ElibView.USER_SUGGESTIONS_VIEW,request,response);
            }else{
                try {
                    BookDAO.acceptBook(bookID,false);
                } catch (DatabaseException e) {
                    LOGGER.error(e.getMessage());
                    ServletUtility.forward(ElibView.ERROR_VIEW,request,response);
                }
                ServletUtility.forward(ElibView.USER_SUGGESTIONS_VIEW,request,response);
            }
            return;
        }
        if(type.equals("suggestion")){
            try {
                UserOpinionDAO.deleteRequest(request);
            } catch (DatabaseException e) {
                LOGGER.error(e.getMessage());
                ServletUtility.forward(ElibView.ERROR_VIEW,request,response);
            }
            ServletUtility.forward(ElibView.USER_SUGGESTIONS_VIEW, request, response);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }

    public void registerUser(User user, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            UserDAO.registerUser(user);
            ServletUtility.setSuccessMessage("User Successfully Registered", request);
            ServletUtility.forward(ElibView.LOGIN_VIEW, request, response);
            return;
        } catch (DuplicateRecordException e) {
            ServletUtility.setUser(user, request);
            ServletUtility.setErrorMessage("Login ID already exists", request);
            ServletUtility.forward(ElibView.REGISTRATION_VIEW, request, response);
        } catch (DatabaseException e) {
            LOGGER.error(e.getMessage());
            ServletUtility.handleException(e,request,response);
        } catch (Exception e){
            LOGGER.error(e.getMessage());
            ServletUtility.handleException(e,request,response);
        }
    }
}
