package efluid.editorGroups.language.annotator;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.ExternalAnnotator;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.psi.PsiFile;
import efluid.editorGroups.language.EditorGroupsPsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditorGroupsExternalAnnotator extends ExternalAnnotator<EditorGroupsPsiFile, FileAnnotationResult> {
  private static final Logger LOG = Logger.getInstance(EditorGroupsExternalAnnotator.class);

  @Nullable
  @Override
  public EditorGroupsPsiFile collectInformation(@NotNull PsiFile file) {
    if (file instanceof EditorGroupsPsiFile) {
      return (EditorGroupsPsiFile) file;
    }
    return null;
  }

  @Nullable
  @Override
  public FileAnnotationResult doAnnotate(EditorGroupsPsiFile file) {
    FileAnnotationResult result = new FileAnnotationResult();

    int sourceOffset = 0;

    SourceAnnotationResult sourceAnnotationResult = new SourceAnnotationResult(sourceOffset);

    String source = Objects.requireNonNull(file).getFirstChild().getText();

    sourceAnnotationResult.addAll(annotateSyntaxHighlight(source,
      LanguagePatternHolder.keywordsPattern,
      DefaultLanguageHighlighterColors.KEYWORD));

    sourceAnnotationResult.addAll(annotateSyntaxHighlight(source,
      LanguagePatternHolder.colorPattern,
      DefaultLanguageHighlighterColors.STATIC_FIELD));

    sourceAnnotationResult.addAll(annotateSyntaxHighlight(source,
      LanguagePatternHolder.macrosPattern,
      DefaultLanguageHighlighterColors.STATIC_METHOD));

    sourceAnnotationResult.addAll(annotateSyntaxHighlight(source,
      LanguagePatternHolder.metadataPattern,
      DefaultLanguageHighlighterColors.METADATA));

    result.add(sourceAnnotationResult);


    return result;
  }


  private Collection<SyntaxHighlightAnnotation> annotateSyntaxHighlight(String source, Pattern pattern, TextAttributesKey textAttributesKey) {
    Collection<SyntaxHighlightAnnotation> result = new ArrayList<>();
    Matcher matcher = pattern.matcher(source);
    while (matcher.find()) {
      result.add(new SyntaxHighlightAnnotation(matcher.start(), matcher.end(), textAttributesKey));
    }
    return result;
  }

  @Override
  public void apply(@NotNull PsiFile file, FileAnnotationResult fileAnnotationResult, @NotNull AnnotationHolder holder) {
    if (null != fileAnnotationResult) {
      fileAnnotationResult.annotate(holder);
    }
  }


}
