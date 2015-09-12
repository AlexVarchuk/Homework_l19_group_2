package com.example.olexandr.homework_l19_group_2.model;

public class ItemNotification {
    private String mMassage;
    private String mTitle;
    private String mSubtitle;
    private String mTicketText;
    private int mVibrate;
    private int mSound;

    public ItemNotification(String _message, String _title, String _subtitle, String _tickerText, int _vibrate, int _sound) {
        this.mMassage = _message;
        this.mTitle = _title;
        this.mSubtitle = _subtitle;
        this.mTicketText = _tickerText;
        this.mVibrate = _vibrate;
        this.mSound = _sound;

    }

    public String getMassage() {
        return mMassage;
    }
    public String getTitle(){
        return mTitle;
    }
    public String getSubtitle(){
        return mSubtitle;
    }
    public String getTicketText(){
        return mTicketText;
    }
    public int getVibrate(){
        return mVibrate;
    }
    public int getSound(){
        return mSound;
    }


}
