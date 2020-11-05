package lexicalAnalyzer;

public class Match {
    Token tok ;
    int start , end;

    public Match() {
    }

    public Match(Token tok, int start, int end) {
        this.tok = tok;
        this.start = start;
        this.end = end;
    }

    public void setTok(Token tok) {
        this.tok = tok;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setInd(int end) {
        this.end = end;
    }

    public Token getTok() {
        return tok;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }
}
