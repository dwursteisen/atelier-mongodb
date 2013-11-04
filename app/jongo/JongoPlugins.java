package jongo;

import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.apache.commons.lang.StringUtils;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.marshall.jackson.JacksonMapper;
import play.Logger;
import play.Play;
import play.PlayPlugin;
import play.exceptions.UnexpectedException;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Wursteisen David
 * Date: 04/07/12
 * Time: 22:52
 */
public class JongoPlugins extends PlayPlugin {

    private static Jongo jongo;

    public static MongoCollection collection(String collectionName) {
        return jongo.getCollection(collectionName);
    }

    @Override
    public void onApplicationStart() {

        String url = Play.configuration.getProperty("jongo.url");
        MongoClientURI uri = new MongoClientURI(url);
        try {

            Logger.info("Ouverture de la connexion au serveur mongodb %s", uri.getHosts());

            DB mongoDB = new MongoClient(uri).getDB(uri.getDatabase());
            jongo = new Jongo(mongoDB, new JacksonMapper.Builder().registerModule(new JodaModule()).build());
        } catch (UnknownHostException e) {
            jongo = null;
            throw new UnexpectedException("Oups ! Doesn't success to connect to the mongoDB instance", e);
        }
        Logger.info("Succefully connected to mongoDB instance %s", uri.getHosts());
    }

    @Override
    public void onApplicationStop() {
    }
}
