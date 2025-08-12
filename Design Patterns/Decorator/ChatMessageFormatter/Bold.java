package ChatMessageFormatter;

public class Bold extends FormatterDecorator {

    public Bold(Formatter formatter) {
        super(formatter);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " + Bold";        
    }
}
