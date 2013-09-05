package com.cjmalloy.gameui.client.util;

import com.google.gwt.user.client.Window.Navigator;

public class UserAgent
{

    public static boolean isTablet()
    {
        if (Navigator.getUserAgent().contains("iPad") ||
            Navigator.getUserAgent().contains("Android"))
        {
            return true;
        }
        return false;
    }
}
