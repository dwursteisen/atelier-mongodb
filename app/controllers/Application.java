package controllers;

import play.mvc.Controller;

public class Application extends Controller {

    public static void index() {
        render();
    }

    public static void post(String coui) {

        index();
    }

    public static void allAsJson() {
        String json = "[{\"user\": \"David\", \"avatar\":\"http://....\", \"content\": \"blabla\", \"recouicoui\": 5}]";
        renderJSON(json);
    }


}