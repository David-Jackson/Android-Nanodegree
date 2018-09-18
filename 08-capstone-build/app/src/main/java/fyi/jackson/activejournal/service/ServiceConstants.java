package fyi.jackson.activejournal.service;

public class ServiceConstants {

    public static final String STATUS_NAME = "RecordingServiceStatus";
    public static final int STATUS_ACTIVE = 753;
    public static final int STATUS_INACTIVE = 754;

    public interface ACTION {
        String MAIN_ACTION = "fyi.jackson.drew.foregroundservice.action.main";
        String INIT_ACTION = "fyi.jackson.drew.foregroundservice.action.init";
        String START_FOREGROUND = "fyi.jackson.drew.foregroundservice.action.startforeground";
        String STOP_FOREGROUND = "fyi.jackson.drew.foregroundservice.action.stopforeground";
        String PAUSE_FOREGROUND = "fyi.jackson.drew.foregroundservice.action.pauseforeground";
        String RESUME_FOREGROUND = "fyi.jackson.drew.foregroundservice.action.resumeforeground";
    }

    public interface NOTIFICATION_ID {
        int FOREGROUND_SERVICE = 101;
    }
}
