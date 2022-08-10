package core;

public enum MailType {
    HOTMAIL("@hotmail.com"),
    OUTLOOK("@outlook.com"),
    GMAIL("@gmail.com"),
    YAHOO("@yahoo.com");

    public String value;

    MailType(String mailType) {
        this.value = mailType;
    }
}
