package controllers;

import com.google.common.collect.Lists;
import com.mongodb.WriteConcern;
import jongo.JongoPlugins;
import models.Couicoui;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;
import play.mvc.Controller;

public class Application extends Controller {

    private static final MongoCollection couicoui = JongoPlugins.collection("couicoui");
    private static final String CONCERN_SESSION = "concern";

    public static void index() {
        render();
    }


    public static void post(String coui, String user) {
        couicoui.withWriteConcern(concern()).insert(new Couicoui(coui).withUser(user));
        session.put("courriel", user);
        renderText("OK");
    }

    private static WriteConcern concern() {
        try {
            return new WriteConcern(Integer.parseInt(session.get(CONCERN_SESSION)));
        } catch (Exception ex) {
            return WriteConcern.UNACKNOWLEDGED;
        }
    }

    public static void filterAsJson(String user) {
        Iterable<Couicoui> couicouis = couicoui.find("{user: #}", user).as(Couicoui.class);
        renderJSON(Lists.newArrayList(couicouis));
    }

    public static void allAsJson() {
        Iterable<Couicoui> couicouis = couicoui.find().sort("{now: -1}").limit(50).as(Couicoui.class);
        renderJSON(Lists.newArrayList(couicouis));
    }

    public static void recouicoui(String id) {
        couicoui.withWriteConcern(concern()).update(new ObjectId(id)).with("{$inc: {recouicoui: 1}}");
        renderText("OK");
    }

    public static void saveConcern(int concern) {
        session.put(CONCERN_SESSION, "" + concern);
        renderText("Concert: " + session.get(CONCERN_SESSION));
    }


}