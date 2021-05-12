package com.example.myfirstapp.Presenter;

import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class MainActivityPresenter {
    public void updatePhoto(String path, String caption) {
        String[] attr = path.split("_");
        if (attr.length >= 3) {
            File to = new File(attr[0] + "_" + caption + "_" + attr[2] + "_" + attr[3] + ".jpeg");
            File from = new File(path);
            from.renameTo(to);
        }
    }
}
