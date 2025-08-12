package TextEditorSystem;

public class Main {
    public static void main(String[] args) {
        TextEditor textEditor = new TextEditor();
        System.out.println(textEditor.getDescription());

        textEditor = new SpellChecker(textEditor);
        System.out.println(textEditor.getDescription());

        textEditor = new GrammarCheck(textEditor);
        System.out.println(textEditor.getDescription());
    }
}