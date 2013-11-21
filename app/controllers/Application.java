package controllers;

import com.google.common.collect.Lists;
import com.mongodb.WriteConcern;
import jongo.JongoPlugins;
import models.Couicoui;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.jongo.MongoCollection;
import play.mvc.Controller;

public class Application extends Controller {

    private static final MongoCollection couicoui = JongoPlugins.collection("couicoui");
    private static final String CONCERN_SESSION = "concern";
    private static final WriteConcern DEFAULT_CONCERN = WriteConcern.UNACKNOWLEDGED;


    public static void index() {
        concern(); // force default concern in session
        render();
    }


    public static void post(String coui, String user) {
        couicoui.withWriteConcern(concern()).insert(new Couicoui(coui).withUser(user));
        session.put("courriel", user);
        renderText("OK");
    }

    private static WriteConcern concern() {
        try {
            String strConcern = session.get(CONCERN_SESSION);
            if(strConcern == null) {
                session.put(CONCERN_SESSION, DEFAULT_CONCERN.getW());
                return DEFAULT_CONCERN;
            }
            return new WriteConcern(Integer.parseInt(strConcern));
        } catch (Exception ex) {
            return DEFAULT_CONCERN;
        }
    }

    public static void filterAsJson(String user) {
        Iterable<Couicoui> couicouis = couicoui.find("{user: #}", user).as(Couicoui.class);
        renderJSON(Lists.newArrayList(couicouis));
    }

    public static void allAsJson() {
        Iterable<Couicoui> couicouis = couicoui.find().sort("{timestamp: -1}").limit(50).as(Couicoui.class);
        renderJSON(Lists.newArrayList(couicouis));
    }

    public static void recouicoui(String user, long timestamp) {
        couicoui.withWriteConcern(concern()) // \n
                .update("{user: #, timestamp: #}", user, timestamp) // \n
                .with("{$inc: {recouicoui: 1}}");
        renderText("OK");
    }

    public static void saveConcern(int concern) {
        session.put(CONCERN_SESSION, "" + concern);
        renderText("Concert: " + session.get(CONCERN_SESSION));
    }


}