package vaulttecnetwork;

public class VTNCModel {

	
private final String DEFAULT_TEXT = "THIS IS A DEFAULT TEXT...";
private String text;
private String defAddText = "Dupa!\n";
	
public VTNCModel()
{
	reset();
}

public void reset()
{
	text = DEFAULT_TEXT;
}

public String getText() {
	return text;
}

public void setText(String text) {
	this.text = text;
}

public void addText(String s)
{
	this.text = this.text + s;
}

public String getDefAddText()
{
	return defAddText;
}

public String getDefText()
{
	return DEFAULT_TEXT;
}

}