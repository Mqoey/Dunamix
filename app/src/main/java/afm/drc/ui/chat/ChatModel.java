package afm.drc.ui.chat;

public class ChatModel {
    private String name,messages,time;
    private Integer id;

    //Constructor
    public ChatModel(String name, String messages, String time, Integer id) {
        this.name = name;
        this.messages = messages;
        this.time = time;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
