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

    public static void index() {
        render();
    }

    public static void post(String coui, String user, int concern) {
        couicoui.withWriteConcern(new WriteConcern(concern)).insert(new Couicoui(coui).withUser(user));
        session.put("courriel", user);
        index();
    }

    public static void allAsJson() {
        Iterable<Couicoui> couicouis = couicoui.find().sort("{now: -1}").limit(50).as(Couicoui.class);
        renderJSON(Lists.newArrayList(couicouis));
    }

    public static void recouicoui(String id) {
        couicoui.withWriteConcern(WriteConcern.NONE).update(new ObjectId(id)).with("{$inc: {recouicoui: 1}}");
        index();
    }


}