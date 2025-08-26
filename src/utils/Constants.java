package utils;

public class Constants {

    public static class menuUICons {
        // Button dimensions and spacing
        public static final int BUTTON_WIDTH = 300;
        public static final int BUTTON_HEIGHT = 160;
        public static final int BUTTON_SPACING = 5;
        public static final int BUTTON_Y = 250;

        // Title dimensions
        public static final int TITLE_WIDTH = 700;
        public static final int TITLE_HEIGHT = 350;

        // Background padding
        public static final int BACKGROUND_PADDING = 100; // For width+100, height+100 in drawBackground
    }

    public static class playingUICons {
        // Button constants
        public static final int BUTTON_WIDTH = 120;
        public static final int BUTTON_HEIGHT = 120;
        public static final int BUTTON_SPACING = 20;
        public static final int BUTTON_Y_FROM_BOTTOM = 100;

        // Hand image constants
        public static final int HAND_WIDTH = 500;
        public static final int HAND_HEIGHT = 500;
        public static final int HAND_X = 5;
        public static final int HAND_Y = 300;

        // Game choice constants
        public static final int TOTAL_RPS_BUTTONS = 3;
        public static final int ROCK = 0;
        public static final int PAPER = 1;
        public static final int SCISSORS = 2;

        // Heart/Lives constants
        public static final int TOTAL_HEARTS = 3;
        public static final int HEART_SIZE = 40;
        public static final int HEART_SPACING = 10;
        public static final int HEART_Y_POSITION = 20;

        // Result display constants
        public static final long RESULT_DISPLAY_DURATION = 500;
        public static final int RESULT_TEXT_Y_POSITION = 200;
        public static final int CONTINUE_TEXT_Y_POSITION = 250;
        public static final int RESULT_TEXT_SIZE = 48;
        public static final int CONTINUE_TEXT_SIZE = 24;

        // Pause button constants
        public static final int PAUSE_BUTTON_SIZE = 40;
        public static final int PAUSE_BUTTON_X = 10;
        public static final int PAUSE_BUTTON_Y = 10;

        // Pause menu button constants
        public static final int PAUSE_MENU_BUTTON_WIDTH = 120;  // Same as RPS buttons
        public static final int PAUSE_MENU_BUTTON_HEIGHT = 120; // Same as RPS buttons
        public static final int PAUSE_MENU_SPACING = 20;        // Same as RPS buttons

        // Change the order: RETRY = 0, MENU = 1, RESUME = 2
        public static final int RETRY_BUTTON = 0;
        public static final int MENU_BUTTON = 1;
        public static final int RESUME_BUTTON = 2;
    }

    public static class menuButtons {
        public static final int PLAY = 0;
        public static final int SETTINGS = 1;
        public static final int QUIT = 2;
        public static final int TOTAL_MENU_BUTTONS = 3;
    }

    public static class GameStates {
        public static final int MENU = 0;
        public static final int PLAYING = 1;
        public static final int SETTINGS = 2;
    }

    public static class ImagePaths {
        // Menu images
        public static final String MENU_BACKGROUND = "/images/menu/mainBack.png";
        public static final String MENU_TITLE = "/images/menu/mainTitle.png";

        // Menu button images
        public static final String PLAY_BUTTON = "/images/menu/playButton.png";
        public static final String SETTINGS_BUTTON = "/images/menu/settingsButton.png";
        public static final String QUIT_BUTTON = "/images/menu/quitButton.png";

        // Menu button clicked images
        public static final String PLAY_CLICKED = "/images/menu/playClicked.png";
        public static final String SETTINGS_CLICKED = "/images/menu/settingsClicked.png";
        public static final String QUIT_CLICKED = "/images/menu/quitClicked.png";

        // Playing background
        public static final String PLAYING_BG = "/images/playing/playingBG.png";

        // Heart images
        public static final String HEART = "/images/playing/heart.png";
        public static final String EMPTY_HEART = "/images/playing/emptyHeart.png";

        // Game button images
        public static final String ROCK_BUTTON = "/images/playing/rock.png";
        public static final String PAPER_BUTTON = "/images/playing/paper.png";
        public static final String SCISSORS_BUTTON = "/images/playing/scissors.png";

        // Game button clicked images
        public static final String ROCK_CLICKED = "/images/playing/rockClicked.png";
        public static final String PAPER_CLICKED = "/images/playing/paperClicked.png";
        public static final String SCISSORS_CLICKED = "/images/playing/scissorsClicked.png";

        // Player hand images
        public static final String ROCK_HAND = "/images/playing/rockHand.png";
        public static final String PAPER_HAND = "/images/playing/paperHand.png";
        public static final String SCISSORS_HAND = "/images/playing/scissorsHand.png";

        // Computer hand images
        public static final String ROCK_COMP = "/images/playing/rockComp.png";
        public static final String PAPER_COMP = "/images/playing/paperComp.png";
        public static final String SCISSORS_COMP = "/images/playing/scissorsComp.png";

        // Pause-related images
        public static final String PAUSE_BUTTON = "/images/playing/pauseButton.png";
        public static final String PAUSE_BUTTON_HOVER = "/images/playing/pauseButtonHover.png";
        public static final String PAUSE_OVERLAY = "/images/playing/pauseOverlay.png";
        public static final String RESUME_BUTTON = "/images/playing/resumeButton.png";
        public static final String RESUME_BUTTON_HOVER = "/images/playing/resumeButtonHover.png";
        public static final String RETRY_BUTTON = "/images/playing/retryButton.png";
        public static final String RETRY_BUTTON_HOVER = "/images/playing/retryButtonHover.png";
        public static final String MENU_BUTTON = "/images/playing/menuButton.png";
        public static final String MENU_BUTTON_HOVER = "/images/playing/menuButtonHover.png";
    }
    public static class SettingsUICons {
        // Button dimensions
        public static final int BUTTON_WIDTH = 80;
        public static final int BUTTON_HEIGHT = 80;
        public static final int BUTTON_SPACING = 10;

        // Vertical positions
        public static final int SFX_Y_POSITION = 250;
        public static final int MUSIC_Y_POSITION = 430;

        // Button counts
        public static final int SFX_BUTTONS = 2;  // mute, unmute
        public static final int MUSIC_BUTTONS = 3;  // mute, half, full

        // Volume levels
        public static final float SFX_MUTE_VOLUME = 0.0f;
        public static final float SFX_FULL_VOLUME = 1.0f;

        public static final float MUSIC_MUTE_VOLUME = 0.0f;
        public static final float MUSIC_HALF_VOLUME = 0.15f;
        public static final float MUSIC_FULL_VOLUME = 0.7f;

        // Button states
        public static final int MUTE_STATE = 0;
        public static final int UNMUTE_STATE = 1;
        public static final int HALF_VOLUME_STATE = 1;
        public static final int FULL_VOLUME_STATE = 2;
    }

    public static class SettingsImagePaths {
        // Background
        public static final String SETTINGS_BACKGROUND = "/images/settings/settingsBackground.png";

        // SFX button images
        public static final String SFX_MUTE = "/images/settings/sfxMute.png";
        public static final String SFX_UNMUTE = "/images/settings/sfxUnmute.png";
        public static final String SFX_MUTE_HOVER = "/images/settings/sfxMuteHovered.png";
        public static final String SFX_UNMUTE_HOVER = "/images/settings/sfxUnmuteHovered.png";

        // Music button images
        public static final String MUSIC_MUTE = "/images/settings/musicMute.png";
        public static final String MUSIC_HALF = "/images/settings/musicHalf.png";
        public static final String MUSIC_FULL = "/images/settings/musicFull.png";
        public static final String MUSIC_MUTE_HOVER = "/images/settings/musicMuteHovered.png";
        public static final String MUSIC_HALF_HOVER = "/images/settings/musicHalfHovered.png";
        public static final String MUSIC_FULL_HOVER = "/images/settings/musicFullHovered.png";
    }
    // Centralized sound file path management
    public static class SoundPaths
    {
        public static final String CLICK_SOUND = "/sounds/click.wav";
        public static final String MENU_MUSIC = "/sounds/menu_background.wav";
        public static final String PLAYING_MUSIC = "/sounds/playing_background.wav";
        public static final String WIN_SOUND = "/sounds/winSound.wav";
        public static final String LOSE_SOUND = "/sounds/loseSound.wav";
    }
}