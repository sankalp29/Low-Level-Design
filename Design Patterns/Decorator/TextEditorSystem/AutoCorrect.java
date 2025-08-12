package TextEditorSystem;

public class AutoCorrect extends TextEditorDecorator {

    public AutoCorrect(TextEditor textEditor) {
        super(textEditor);
    }

    public String getDescription() {
        return super.getDescription() + " + Auto correct";
    }
}
