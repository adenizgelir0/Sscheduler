package co.ubien.sscheduler;

import androidx.compose.ui.graphics.Color;

public class ColorUtil {
    static int strToColor(String st)
    {
        int hash = 0;
        for(int i=0; i<st.length(); i++)
        {
            hash = ((int)st.charAt(i)) + ((hash << 5) - hash);
        }
        int r = (hash & 0xFF0000) >> 16;
        int g = (hash & 0xFF00) >> 8;
        int b = (hash & 0xFF);

        return (255 & 0xff) << 24 | (r & 0xff) << 16 | (g & 0xff) << 8 | (b & 0xff);

    }
}
