package krasa.editorGroups.language;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class EditorGroupsFileType extends LanguageFileType {

	public static final EditorGroupsFileType EDITOR_GROUPS_FILE_TYPE = new EditorGroupsFileType();

	public static final String EXTENSION = "egroups";


	private EditorGroupsFileType() {
		super(EditorGroupsLanguage.INSTANCE);
	}

	@NotNull
	@Override
	public String getName() {
		return "EditorGroups file";
	}

	@NotNull
	@Override
	public String getDescription() {
		return "EditorGroups files";
	}

	@NotNull
	@Override
	public String getDefaultExtension() {
		return EXTENSION;
	}

	@Override
	public Icon getIcon() {
		return AllIcons.Actions.GroupBy;
	}

	@Override
	public String getCharset(@NotNull VirtualFile virtualFile, @NotNull byte[] bytes) {
		return "UTF-8";
	}
}