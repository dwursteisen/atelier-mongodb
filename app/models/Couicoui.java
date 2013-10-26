package models;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.jongo.marshall.jackson.oid.ObjectId;
import play.libs.Codec;

/**
 * Created by david on 14/10/13.
 */
public class Couicoui {

    @ObjectId
    public String _id;


    public String content;
    public String user;
    public String avatar;
    public int recouicoui = 0;
    public DateTime now;

    public Couicoui(String coui) {
        content = coui;
        now = new DateTime();
    }

    public Couicoui withUser(String from) {
        Couicoui couicoui = new Couicoui(content);
        couicoui.user = from;
        couicoui.avatar = computeAvatarUrl(from);
        couicoui.recouicoui = recouicoui;
        couicoui.now = now;
        return couicoui;
    }

    private String computeAvatarUrl(String from) {
        return String.format("http://www.gravatar.com/avatar/%s", Codec.hexMD5(StringUtils.trim(from)));
    }
}
