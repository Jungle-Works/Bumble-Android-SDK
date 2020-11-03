package com.bumble.Utils.fileUpload;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by gurmail on 16/01/19.
 *
 * @author gurmail
 */
public class FileuploadModel {

    String fileName;
    String fileSize;
    String fileSizeReadable;
    String fileMime;
    String muid;
    int messageType;
    String documentType;
    ArrayList<Integer> dimns = new ArrayList<>();
    int messageIndex;
    boolean fileUploaded;
    Long channelId;
    JSONObject messageObject;
    Long labelId;
    String filePath;

    public Long getLabelId() {
        return labelId;
    }

    public void setLabelId(Long labelId) {
        this.labelId = labelId;
    }

    public boolean isFileUploaded() {
        return fileUploaded;
    }

    public void setFileUploaded(boolean fileUploaded) {
        this.fileUploaded = fileUploaded;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public JSONObject getMessageObject() {
        return messageObject;
    }

    public void setMessageObject(JSONObject messageObject) {
        this.messageObject = messageObject;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }


    public ArrayList<Integer> getDimns() {
        return dimns;
    }

    public void setDimns(ArrayList<Integer> dimns) {
        this.dimns = dimns;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getFileMime() {
        return fileMime;
    }

    public void setFileMime(String fileMime) {
        this.fileMime = fileMime;
    }

    public String getMuid() {
        return muid;
    }

    public void setMuid(String muid) {
        this.muid = muid;
    }

    public int getMessageIndex() {
        return messageIndex;
    }

    public void setMessageIndex(int messageIndex) {
        this.messageIndex = messageIndex;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public String getFilePath() {
        return filePath;
    }

    public FileuploadModel(Long channelId) {
        this.channelId = channelId;
    }


    public String getFileSizeReadable() {
        return fileSizeReadable;
    }

    public void setFileSizeReadable(String fileSizeReadable) {
        this.fileSizeReadable = fileSizeReadable;
    }

    public FileuploadModel(String fileName, String fileSize, String filePath, String muid) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.muid = muid;
    }

    @Override
    public boolean equals(Object obj) {
        try {
            return ((FileuploadModel) obj).getChannelId().compareTo(getChannelId()) == 0;
        } catch (Exception e) {
            return false;
        }
    }
}
