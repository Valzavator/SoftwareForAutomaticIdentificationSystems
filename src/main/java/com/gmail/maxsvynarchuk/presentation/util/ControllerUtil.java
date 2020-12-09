package com.gmail.maxsvynarchuk.presentation.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ControllerUtil {
    /**
     * Add next page to redirect
     *
     * @param request        HttpServletRequest
     * @param response       HttpServletResponse
     * @param pageToRedirect page to redirect
     * @throws IOException IOException
     */
    public static void redirectTo(HttpServletRequest request,
                                  HttpServletResponse response,
                                  String pageToRedirect) throws IOException {
        response.sendRedirect(
                request.getContextPath() + pageToRedirect);
    }

    /**
     * Add next page to redirect
     *
     * @param pageToRedirect page to redirect
     */
    public static String redirectTo(String pageToRedirect) {
        return "redirect:" + pageToRedirect;
    }

}
