package edu.epam.constants;

public interface ElibView {

    public String APP_CONTEXT = "/e_lib";
    public String MAIN = "/app";
    public String PAGE_FOLDER = "/jsp";

    public String ERROR_VIEW = PAGE_FOLDER + "/ErrorView.jsp";
    public String INDEX_VIEW = "/index.jsp";

    public String REGISTRATION_VIEW = PAGE_FOLDER + "/RegistrationView.jsp";
    public String LOGIN_VIEW = PAGE_FOLDER + "/LoginView.jsp";
    public String CATALOG_VIEW = PAGE_FOLDER + "/CatalogView.jsp";
    public String ABOUT_VIEW = PAGE_FOLDER + "/AboutView.jsp";
    public String SEARCH_VIEW = PAGE_FOLDER + "/SearchView.jsp";
    public String BOOK_VIEW = PAGE_FOLDER + "/BookView.jsp";
    public String CATEGORY_VIEW = PAGE_FOLDER + "/CategoryView.jsp";
    public String CREATE_BOOK_VIEW = PAGE_FOLDER + "/CreateBookView.jsp";
    public String MY_DOWNLOADS_VIEW = PAGE_FOLDER + "/MyDownloadsView.jsp";
    public String MY_WISH_LIST_VIEW = PAGE_FOLDER + "/MyWishListView.jsp";
    public String USER_SUGGESTIONS_VIEW = PAGE_FOLDER + "/SuggestionsView.jsp";
    public String PROFILE_VIEW = PAGE_FOLDER + "/ProfileView.jsp";


    public String ERROR_CTL = APP_CONTEXT + MAIN + "/error";

    public String INDEX = APP_CONTEXT + INDEX_VIEW;
    public String LOGIN = APP_CONTEXT + MAIN + "/login";
    public String REGISTER = APP_CONTEXT + MAIN + "/register";
    public String CATALOG = APP_CONTEXT + MAIN + "/catalog";
    public String ABOUT = APP_CONTEXT + MAIN + "/about";
    public String BOOK = APP_CONTEXT + MAIN + "/book";
    public String SEARCH = APP_CONTEXT + MAIN + "/search";
    public String LOGOUT = APP_CONTEXT + MAIN + "/logout";
    public String CATEGORY = APP_CONTEXT + MAIN + "/category";
    public String UPLOAD_BOOK = APP_CONTEXT + MAIN + "/upload-book";
    public String DOWNLOAD = APP_CONTEXT + MAIN + "/download";
    public String MY_DOWNLOADS = APP_CONTEXT + MAIN + "/my-downloads";
    public String MY_WISH_LIST = APP_CONTEXT + MAIN + "/my-wish-list";
    public String USER_SUGGESTIONS = APP_CONTEXT + MAIN + "/suggestions";
    public String COMMENT = APP_CONTEXT + MAIN + "/comment";
    public String LANGUAGE = APP_CONTEXT + MAIN + "/language";
    public String PROFILE = APP_CONTEXT + MAIN + "/profile";
    public String DECIDE = APP_CONTEXT + MAIN + "/decide";

}
