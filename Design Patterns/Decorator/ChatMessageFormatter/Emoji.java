package ChatMessageFormatter;

public class Emoji extends FormatterDecorator {

    public Emoji(Formatter formatter) {
        super(formatter);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " + Emoji";        
    }
}
