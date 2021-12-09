package ru.kpfu.itis.gadelev;

import javafx.scene.Parent;

public abstract class View {

    private static Game application;

    public abstract Parent getView();

    public static void setApplication(Game application) {
        View.application = application;
    }

    public static Game getApplication() throws Exception {
        if (application != null) {
            return application;
        }
        throw new Exception("No Application in BaseView");
    }

    public abstract String getTitle();
}

