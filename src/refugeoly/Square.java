package refugeoly;

import java.util.*;

public class Square {
    private int number;
    private String text;
    private String description;
    private final int board = 1;
    ArrayList <Action> actions = new ArrayList<>();
    
    public int getNumber()
    {
        return number;
    }
    
    public void setNumber(int number)
    {
        this.number = number;
    }
    
    public String getText()
    {
        return text;
    }
    
    public void setText(String text)
    {
        this.text = text;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public void addAction(Action action)
    {
        actions.add(action);
    }
}
