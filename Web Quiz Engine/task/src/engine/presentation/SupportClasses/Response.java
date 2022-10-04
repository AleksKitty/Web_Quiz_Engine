package engine.presentation.SupportClasses;

public class Response {
    boolean success;
    String feedback;

    public Response() {
    }

    public Response(boolean success, String feedback) {
        this.success = success;
        this.feedback = feedback;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}