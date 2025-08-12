package TextEditorSystem;

public class GrammarCheck extends TextEditorDecorator {

    public GrammarCheck(TextEditor textEditor) {
        super(textEditor);
    }

    public String getDescription() {
        return super.getDescription() + " + Grammar check";
    }
}
