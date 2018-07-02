package fareshare.maniacers.fareshare;

/**
 * Created by Bhavya Raj Sharma on 29-04-2018.
 */

public class SendData {
    String send_name;
    String send_email;
    String send_uid;

    public String getDoj() {
        return doj;
    }

    public void setDoj(String doj) {
        this.doj = doj;
    }

    String doj;

    public SendData() {
    }

    public String getSend_name() {

        return send_name;
    }

    public void setSend_name(String send_name) {
        this.send_name = send_name;
    }

    public String getSend_email() {
        return send_email;
    }

    public void setSend_email(String send_email) {
        this.send_email = send_email;
    }

    public String getSend_uid() {
        return send_uid;
    }

    public void setSend_uid(String send_uid) {
        this.send_uid = send_uid;
    }
}
