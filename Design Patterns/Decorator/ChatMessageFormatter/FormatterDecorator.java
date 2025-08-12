package ChatMessageFormatter;

public abstract class FormatterDecorator implements Formatter {
    private Formatter formatter;

    public FormatterDecorator(Formatter formatter) {
        this.formatter = formatter;
    }

    @Override
    public String getDescription() {
        return formatter.getDescription();
    }
}
