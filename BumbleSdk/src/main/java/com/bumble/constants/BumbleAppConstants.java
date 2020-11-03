package com.bumble.constants;

import android.os.Environment;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public interface BumbleAppConstants {

    String CONFERENCING_TEST = "https://conference-dev.officechat.io";
    String CONFERENCING_LIVE = "https://conference.hippochat.io";
//    String CONFERENCING_LIVE = "https://meet.hippochat.io";

//    String CONFERENCING_LIVE = "https://meet.fugu.chat";
//    String CONFERENCING_LIVE = CONFERENCING_TEST;


    String DEV_SERVER = "https://test-api-3040.tookanapp.com";
    String DEV_SERVER_3003 = "https://hippo-api-dev.fuguchat.com:3003";
    String DEV_SERVER_3004 = "https://hippo-api-dev.fuguchat.com:3004";
    String TEST_SERVER = "https://hippo-api-dev.fuguchat.com:3011";
    String BETA_SERVER = "https://beta-hippo.fuguchat.com";
    String BETA_LIVE_SERVER = "https://beta-live-api.fuguchat.com";
    //String LIVE_SERVER = "https://api.fuguchat.com";
    String LIVE_SERVER = "https://api.bumbl.it/";


    String LIVE_SOCKEY_SERVER = "https://socketv2.hippochat.io";
    String BETA_LIVE_SOCKEY_SERVER = "https://beta-live-api.fuguchat.com:3001";

//    String DEV_SERVER = "https://api-new.fuguchat.com";
//    String DEV_SERVER_3004 = "https://api-new.fuguchat.com";
//    String LIVE_SERVER = "https://api-new.fuguchat.com";

//    String DEV_SERVER = "https://api-new.fuguchat.com";
//    String DEV_SERVER_3004 = "https://api-new.fuguchat.com";
//    String TEST_SERVER = "https://api-new.fuguchat.com";
//    String BETA_SERVER = "https://api-new.fuguchat.com";
//    String BETA_LIVE_SERVER = "https://api-new.fuguchat.com";
//    String LIVE_SERVER = "https://api-new.fuguchat.com";




    public static final String BUMBLE_PAPER_NAME = "bumble.paperdb";

    int SESSION_EXPIRE = 403;
    int DATA_UNAVAILABLE = 406;
    int INVALID_VIDEO_CALL_CREDENTIALS = 413;
    String NETWORK_STATE_INTENT = "network_state_changed";
    String NOTIFICATION_INTENT = "notification_received";
    String NOTIFICATION_TAPPED = "notification_tapped";
    String FUGU_WEBSITE_URL = "https://fuguchat.com";
    String VIDEO_CALL_INTENT = "hippo_video_call_intent";
    String VIDEO_CALL_HUNGUP = "hippo_video_call_hungup";

    int IMAGE_MESSAGE = 10;
    int FILE_MESSAGE = 11;
    int ACTION_MESSAGE = 12;
    int ACTION_MESSAGE_NEW = 19;
    int TEXT_MESSAGE = 1;
    int PRIVATE_NOTE = 3;
    int READ_MESSAGE = 6;
    int FEEDBACK_MESSAGE = 14;
    int BOT_TEXT_MESSAGE = 15;
    int BOT_FORM_MESSAGE = 17;
    int VIDEO_CALL = 18;
    int CARD_LIST = 21;
    int PAYMENT_TYPE = 22;
    int MULTI_SELECTION = 23;

    int VIDEO_CALL_VIEW = 1;
    int AUDIO_CALL_VIEW = 2;

    int SYSTEM_USER = 0;
    int ANDROID_USER = 1;

    int CHANNEL_SUBSCRIBED = 1;
    int CHANNEL_UNSUBSCRIBED = 0;

    int STATUS_CHANNEL_CLOSED = 0;
    int STATUS_CHANNEL_OPEN = 1;

    int TYPING_SHOW_MESSAGE = 0;
    int TYPING_STARTED = 1;
    int TYPING_STOPPED = 2;

    int MESSAGE_SENT = 1;
    int MESSAGE_DELIVERED = 2;
    int MESSAGE_READ = 3;
    int MESSAGE_UNSENT = 4;
    int MESSAGE_IMAGE_RETRY = 5;
    int MESSAGE_FILE_RETRY = 6;
    int MESSAGE_FILE_UPLOADED = 7;

    int UPLOAD_FAILED = 0;
    int UPLOAD_IN_PROGRESS = 1;
    int UPLOAD_PAUSED = 2;
    int UPLOAD_COMPLETED = 3;

    int PERMISSION_CONSTANT_CAMERA = 9;
    int PERMISSION_CONSTANT_GALLERY = 8;
    int PERMISSION_READ_IMAGE_FILE = 4;
    int PERMISSION_SAVE_BITMAP = 5;
    int PERMISSION_READ_FILE = 6;

    final int RC_READ_EXTERNAL_STORAGE = 123;
    final int RC_OPEN_CAMERA = 124;
    int OPEN_CAMERA_ADD_IMAGE = 514;
    int OPEN_GALLERY_ADD_IMAGE = 515;
    int SELECT_FILE = 516;
    int SELECT_AUDIO = 518;
    int SELECT_DOCUMENT = 517;
    int SELECT_VIDEO = 519;
    int SELECT_NONE = 600;
    int SELECT_PAYMENT = 520;

    //Notification Type
    int NOTIFICATION_DEFAULT = -1;
    int NOTIFICATION_READ_ALL = 6;

    //For agent
    int AGENT_TEXT_MESSAGE = 1;
    int ASSIGN_CHAT = 3;
    int AGENT_REALALL = 6;
    int NEW_AGENT_ADDED = 10;
    int AGENT_STATUS_CHANGED = 11;

    // action
    String FUGU_CUSTOM_ACTION_SELECTED = "FUGU_CUSTOM_ACTION_SELECTED";
    String FUGU_CUSTOM_ACTION_PAYLOAD = "FUGU_CUSTOM_ACTION_PAYLOAD";
    String HIPPO_CALL_NOTIFICATION_PAYLOAD = "hippo_call_notification_data";
    String FUGU_LISTENER_NULL = "fugu_listener_null";
    String HIPPO_FILE_UPLOAD = "HIPPO_FILE_UPLOAD";

    String IMAGE_DIRECTORY = Environment.getExternalStorageDirectory() + File.separator + "fugu" +
            File.separator + "picture";
    String CONVERSATION = "conversation";
    String NOTIFICATION_TYPE = "notification_type";
    String USER_ID = "user_id";
    String EN_USER_ID = "en_user_id";
    String PEER_CHAT_PARAMS = "peer_chat_params";
    String APP_SECRET_KEY = "app_secret_key";
    String DEVICE_TYPE = "device_type";
    String DEVICE_DETAILS = "device_details";
    String REFERENCE_ID = "reference_id";
    String DEVICE_ID = "device_id";
    String APP_TYPE = "device_type";
    String APP_VERSION="app_version";
    String APP_VERSION_CODE = "app_version_code";
    String ANDROID="Android";
    String USER_UNIQUE_KEY = "user_unique_key";
    String FULL_NAME = "full_name";
    String MESSAGE = "message";
    String NEW_MESSAGE = "new_message";
    String MESSAGE_STATUS = "message_status";
    String MESSAGE_INDEX = "message_index";
    String MESSAGE_TYPE = "message_type";
    String USER_TYPE = "user_type";
    String USER_IMAGE = "user_image";
    String DATE_TIME = "date_time";
    String EMAIL = "email";
    String PHONE_NUMBER = "phone_number";
    String DEVICE_TOKEN = "device_token";
    String IS_TYPING = "is_typing";
    String CHANNEL_ID = "channel_id";
    String LABEL_ID = "label_id";
    String UNREAD_COUNT = "unread_count";
    String ON_SUBSCRIBE = "on_subscribe";
    String IMAGE_URL = "image_url";
    String THUMBNAIL_URL = "thumbnail_url";
    String ADDRESS = "address";
    String COUNTRY_INFO = "country_info";
    String LAT_LONG = "lat_long";
    String IP_ADDRESS = "ip";
    String ZIP_CODE = "zip_code";
    String COUNTRY = "country";
    String REGION = "region";
    String CITY = "city";
    String ADDRESS_LINE1 = "address_line1";
    String ADDRESS_LINE2 = "address_line2";
    String ATTRIBUTES = "attributes";
    String CUSTOM_ATTRIBUTES = "custom_attributes";
    String ISP2P = "isP2P";
    String CHAT_TYPE = "chat_type";
    String ERROR = "error";
    String INTRO_MESSAGE = "intro_message";
    String CUSTOM_ACTION = "custom_action";
    String GROUPING_TAGS = "grouping_tags";
    String ACCESS_TOKEN = "access_token";
    String APP_SOURCE = "source";
    String STATUS = "status";
    String TYPE = "type";
    String PAGE_START = "page_start";
    String PAGE_END = "page_end";
    int ASSIGNMENT_MESSAGE = 2;
    String TAGS_DATA = "tag_data";
    String FRAGMENT_TYPE = "fragment_type";
    String AUTH_TOKEN = "auth_token";
    String AGENT_SECRET_KEY = "agent_secret_key";
    String CREATE_NEW_CHAT = "create_chat";
    String OTHER_USER_UNIQUE_KEY = "other_user_unique_key";
    String RESPONSE_TYPE = "response_type";
    String APP_SOURCE_TYPE = "source_type";
    String CREATED_BY = "created_by";
    String DEAL_ID = "deal_id";
    String USER_AGENT_CALL = "user_agent_call";
    String SUPPORT_ID = "support_id";
    String SUPPORT_TRANSACTION_ID = "support_transaction_id";
    String SUPPORT_IS_ACTIVE = "is_active";
    String SOURCE_KEY = "source";

    String VIDEO_CALL_MODEL = "video_Call_model";
    String INIT_FULL_SCREEN_SERVICE = "init_full_screen_service";
    String CALL_STATUS = "call_status";
    String CHANNEL_NAME = "channel_name";
    String MESSAGE_UNIQUE_ID = "muid";
    String VIDEO_CALL_TYPE = "video_call_type";
    String IS_SILENT = "is_silent";
    String TITLE = "title";
    int MAX_HEIGHT = 250;
    int MAX_WIDTH = 250;
    int MAX_WIDTH_OUTER_SPIKED = 252;
    int MAX_WIDTH_OUTER = 255;

    String BROADCAST_STATUS = "broadcast_status";
    final String KEY = "pref_upload_data";

    String IMAGE_FOLDER = "image";
    String DOC_FOLDER = "file";
    String AUDIO_FOLDER = "audio";
    String VIDEO_FOLDER = "video";

    // for file downloading
    String HIPPO_PROGRESS_INTENT = "hippo_progress_intent";
    String HIPPO_POSITION = "hippo_position";
    String HIPPO_PROGRESS = "hippo_progress";
    String HIPPO_STATUS_UPLOAD = "hippo_statusUpload";
    String HIPPO_USER_IMAGE_PATH = "user_image";

    String BOT_GROUP_ID = "bot_group_id";

    String INVITE_LINK = "invite_link";
    String VIDEO_CONFERENCE_HUNGUP_INTENT = "video_conference_hungup_intent";
    String INCOMING_VIDEO_CONFERENCE = "incoming_video_conference";
    String ROOM_NAME = "room_name";
    String BASE_URL = "base_url";
    String JITSI_URL = "jitsi_url";

    String URL_WEBVIEW = "url_webview";
    String VALUE_PAYMENT = "VALUE_PAYMENT";
    String PAYMENT_FOR_FLOW="paymentForFlow";

    String LANG = "lang";

    /**
     * The type of file being Saved
     */

    interface DataType {

        String NUMBER = "Number";
        String _NUMBER = "number";
        String TEXT = "Text";
        String IMAGE = "Image";
        String DATE = "Date";
        String DROP_DOWN = "Dropdown";
        String CHECKBOX = "Checkbox";
        String TELEPHONE = "Telephone";
        String PHONE = "phone";
        String PHONE_NUMBER = "phone_number";
        String EMAIL = "email";
        String URL = "URL";
        String DATE_FUTURE = "Date-Future";
        String DATE_PAST = "Date-Past";
        String DATE_TODAY = "Date-Today";
        String CHECKLIST = "Checklist";
        String TABLE = "Table";
        String DATETIME = "Date-Time";
        String DATETIME_FUTURE = "Datetime-Future";
        String DATETIME_PAST = "Datetime-Past";
        String BARCODE = "Barcode";

    }










    interface ACTION {
        String DEFAULT = "0";
        String ASSIGNMENT = "1";
        String AUDIO_CALL = "2";
        String VIDEO_CALL = "3";
        String CONTINUE_CHAT = "4";
        String OPEN_URL = "5";
    }

    interface INPUT_TYPE {
        String DEFAULT = "DEFAULT";
        String NUMBER = "NUMBER";
        String NONE = "NONE";
    }


    public enum DocumentType {
        IMAGE("image"),
        AUDIO("audio"),
        VIDEO("video"),
        FILE("file");

        public final String type;

        DocumentType(final String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return type;
        }
    }

    interface MimeTypeConstants {
        String MIME_TYPE_IMAGE_JPEG = "image/jpeg";
        String MIME_TYPE_IMAGE_JPG  = "image/pjpeg";
        String MIME_TYPE_IMAGE_PNG  = "image/png";
        String MIME_TYPE_PDF        = "application/pdf";
        String MIME_TYPE_CSV_1      = "text/csv";
        String MIME_TYPE_CSV_2      = "text/comma-separated-values";
        String MIME_TYPE_DOC        = "application/msword";
        String MIME_TYPE_DOCX       = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
        String MIME_TYPE_PPT        = "application/vnd.ms-powerpoint";
        String MIME_TYPE_PPTX       = "application/vnd.openxmlformats-officedocument.presentationml.presentation";
        String MIME_TYPE_XLS        = "application/vnd.ms-excel";
        String MIME_TYPE_XLSX       = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        String MIME_TYPE_TXT        = "text/plain";
    }

    HashMap<String, String> FOLDER_TYPE = new HashMap<String, String>() {{
        put("audio", "Bumble Audio");
        put("video", "Bumble Video");
        put("file", "Bumble Documents");
        put("image", "Bumble Images");
    }};

}
