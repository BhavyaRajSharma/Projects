package fareshare.maniacers.fareshare;

/**
 * Created by Bhavya Raj Sharma on 05-12-2017.
 */

public class SendDetails {
    private String challenge_name,challenge_sender_name,challenge_sender_id,challenge_content,challenge_v,challenge_receiver_name,challenge_receiver_id;

    private String challange_image;
    private String sender_email,receiver_email;

    public SendDetails(String challenge_name, String challenge_sender_name, String challenge_sender_id, String challenge_content, String challenge_v, String challenge_receiver_name, String challenge_receiver_id, String challange_image, String sender_email, String receiver_email, String challange_video) {
        this.challenge_name = challenge_name;
        this.challenge_sender_name = challenge_sender_name;
        this.challenge_sender_id = challenge_sender_id;
        this.challenge_content = challenge_content;
        this.challenge_v = challenge_v;
        this.challenge_receiver_name = challenge_receiver_name;
        this.challenge_receiver_id = challenge_receiver_id;
        this.challange_image = challange_image;
        this.sender_email = sender_email;
        this.receiver_email = receiver_email;
        this.challange_video = challange_video;
    }

    public String getSender_email() {

        return sender_email;
    }

    public void setSender_email(String sender_email) {
        this.sender_email = sender_email;
    }

    public String getReceiver_email() {
        return receiver_email;
    }

    public void setReceiver_email(String receiver_email) {
        this.receiver_email = receiver_email;
    }

    public SendDetails(String challenge_name, String challenge_sender_name, String challenge_sender_id, String challenge_content, String challenge_v, String challenge_receiver_name, String challenge_receiver_id, String challange_image, String challange_video) {
        this.challenge_name = challenge_name;
        this.challenge_sender_name = challenge_sender_name;
        this.challenge_sender_id = challenge_sender_id;
        this.challenge_content = challenge_content;
        this.challenge_v = challenge_v;
        this.challenge_receiver_name = challenge_receiver_name;
        this.challenge_receiver_id = challenge_receiver_id;
        this.challange_image = challange_image;
        this.challange_video = challange_video;
    }

    public String getChallange_image() {
        return challange_image;
    }

    public void setChallange_image(String challange_image) {
        this.challange_image = challange_image;
    }

    public String getChallange_video() {
        return challange_video;
    }

    public void setChallange_video(String challange_video) {
        this.challange_video = challange_video;
    }

    private String challange_video;

    public String getChallenge_name() {
        return challenge_name;
    }

    public void setChallenge_name(String challenge_name) {
        this.challenge_name = challenge_name;
    }

    public String getChallenge_sender_name() {
        return challenge_sender_name;
    }

    public void setChallenge_sender_name(String challenge_sender_name) {
        this.challenge_sender_name = challenge_sender_name;
    }

    public String getChallenge_sender_id() {
        return challenge_sender_id;
    }

    public void setChallenge_sender_id(String challenge_sender_id) {
        this.challenge_sender_id = challenge_sender_id;
    }

    public String getChallenge_content() {
        return challenge_content;
    }

    public void setChallenge_content(String challenge_content) {
        this.challenge_content = challenge_content;
    }

    public String getChallenge_v() {
        return challenge_v;
    }

    public void setChallenge_v(String challenge_v) {
        this.challenge_v = challenge_v;
    }

    public String getChallenge_receiver_name() {
        return challenge_receiver_name;
    }

    public void setChallenge_receiver_name(String challenge_receiver_name) {
        this.challenge_receiver_name = challenge_receiver_name;
    }

    public String getChallenge_receiver_id() {
        return challenge_receiver_id;
    }

    public void setChallenge_receiver_id(String challenge_receiver_id) {
        this.challenge_receiver_id = challenge_receiver_id;
    }

    public SendDetails() {

    }

    public SendDetails(String challenge_name, String challenge_sender_name, String challenge_sender_id, String challenge_content, String challenge_v, String challenge_receiver_name, String challenge_receiver_id) {

        this.challenge_name = challenge_name;
        this.challenge_sender_name = challenge_sender_name;
        this.challenge_sender_id = challenge_sender_id;
        this.challenge_content = challenge_content;
        this.challenge_v = challenge_v;
        this.challenge_receiver_name = challenge_receiver_name;
        this.challenge_receiver_id = challenge_receiver_id;
    }
}
