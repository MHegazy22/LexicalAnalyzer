package lexicalAnalyzer;

public class Token {
    private
        String type;
        String value ;

    public Token() {
    }
    public Token(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
}
