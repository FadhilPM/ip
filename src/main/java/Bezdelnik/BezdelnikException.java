package Bezdelnik;

class BezdelnikException extends Exception {
    public BezdelnikException(String message) {
        super(message);
    }

    public BezdelnikException(String message, Throwable cause) {
        super(message, cause);
    }
}
