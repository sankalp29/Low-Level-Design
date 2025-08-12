package TextEditorSystem;

public abstract class TextEditorDecorator extends TextEditor {
    private TextEditor textEditor;

    public TextEditorDecorator(TextEditor textEditor) {
        this.textEditor = textEditor;
    }

    @Override
    public String getDescription() {
        return textEditor.getDescription();
    }
}