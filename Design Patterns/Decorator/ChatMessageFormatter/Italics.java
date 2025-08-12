package ChatMessageFormatter;

public class Italics extends FormatterDecorator {

    public Italics(Formatter formatter) {
        super(formatter);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " + Italics";        
    }
}
