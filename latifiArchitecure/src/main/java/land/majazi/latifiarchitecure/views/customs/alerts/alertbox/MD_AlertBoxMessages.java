package land.majazi.latifiarchitecure.views.customs.alerts.alertbox;

import java.util.List;

public class MD_AlertBoxMessages {

    private String title;
    private List<String> messages;
    private enumAlertBoxType alertBoxType;


    public MD_AlertBoxMessages(String title, List<String> messages, enumAlertBoxType alertBoxType) {
        this.title = title;
        this.messages = messages;
        this.alertBoxType = alertBoxType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public enumAlertBoxType getAlertBoxType() {
        return alertBoxType;
    }

    public void setAlertBoxType(enumAlertBoxType alertBoxType) {
        this.alertBoxType = alertBoxType;
    }
}
