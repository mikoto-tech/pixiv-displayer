package net.mikoto.pixiv.displayer.controller;

import com.alibaba.fastjson.JSONObject;
import net.mikoto.pixiv.api.pojo.PixivData;
import net.mikoto.pixiv.displayer.PixivDisplayerApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

/**
 * @author mikoto
 * @date 2021/12/26 4:52
 */
@RestController
public class ArtworkController {
    /**
     * Get artwork by id.
     *
     * @param request   The httpServlet request.
     * @param artworkId The id of this artwork.
     * @return Json string.
     */
    @RequestMapping(value = "/getArtworkById", method =
            RequestMethod.GET, produces = {
            "application/JSON"
    })
    public String getArtworkById(HttpServletRequest request,
                                 @RequestParam String artworkId) {
        PixivData pixivData = null;
        try {
            pixivData = PixivDisplayerApplication.PIXIV_ENGINE.getPixivDataService().getPixivDataById(Integer.valueOf(artworkId), PixivDisplayerApplication.PIXIV_ENGINE.getPixivDataDao());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("error", true);
            jsonObject.put("msg", "No such artwork");
            return jsonObject.toJSONString();
        }
        return getString(pixivData);
    }

    /**
     * Get artwork by tag.
     *
     * @param request The httpServlet request.
     * @param tag     The id of this artwork.
     * @return Json string.
     */
    @RequestMapping(value = "/getArtworkByTag", method =
            RequestMethod.GET, produces = {
            "application/JSON"
    })
    public String getArtworkByTag(HttpServletRequest request,
                                  @RequestParam String tag) {
        PixivData pixivData = null;
        try {
            pixivData = PixivDisplayerApplication.PIXIV_ENGINE.getPixivDataService().getPixivDataByTag(tag, PixivDisplayerApplication.PIXIV_ENGINE.getPixivDataDao());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("error", true);
            jsonObject.put("msg", "No such artwork");
            return jsonObject.toJSONString();
        }
        return getString(pixivData);
    }

    /**
     * Get artwork by id.
     *
     * @param request    The httpServlet request.
     * @param authorName The id of this artwork.
     * @return Json string.
     */
    @RequestMapping(value = "/getArtworkByAuthorName", method =
            RequestMethod.GET, produces = {
            "application/JSON"
    })
    public String getArtworkByAuthorName(HttpServletRequest request,
                                         @RequestParam String authorName) {
        PixivData pixivData = null;
        try {
            pixivData = PixivDisplayerApplication.PIXIV_ENGINE.getPixivDataService().getPixivDataByAuthorName(authorName, PixivDisplayerApplication.PIXIV_ENGINE.getPixivDataDao());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("error", true);
            jsonObject.put("msg", "No such artwork");
            return jsonObject.toJSONString();
        }
        return getString(pixivData);
    }

    /**
     * Get failed response string
     *
     * @param pixivData Pixiv data object.
     * @return A string.
     */
    private String getString(PixivData pixivData) {
        if (pixivData != null) {
            JSONObject jsonObject = pixivData.toJsonObject();
            jsonObject.put("error", false);
            jsonObject.put("msg", "");
            return jsonObject.toJSONString();
        } else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("error", true);
            jsonObject.put("msg", "No such artwork");
            return jsonObject.toJSONString();
        }
    }
}
