package com.bumble;

public class DeeplinKData {
    private String pakageName;
    private String classFullPath;

    public DeeplinKData(String pakageName, String classFullPath) {
        this.pakageName = pakageName;
        this.classFullPath = classFullPath;
    }

    public String getPakageName() {
        return pakageName;
    }

    public void setPakageName(String pakageName) {
        this.pakageName = pakageName;
    }

    public String getClassFullPath() {
        return classFullPath;
    }

    public void setClassFullPath(String classFullPath) {
        this.classFullPath = classFullPath;
    }
}
