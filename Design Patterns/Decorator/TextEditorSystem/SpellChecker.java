package TextEditorSystem;

public class SpellChecker extends TextEditorDecorator {

    public SpellChecker(TextEditor textEditor) {
        super(textEditor);
    }

    public String getDescription() {
        return super.getDescription() + " + Spell checker";
    }
}
