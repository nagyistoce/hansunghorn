package phone.bean;

public interface Builder {

    public abstract void makeTitle(String title);
    public abstract void makeNode(String str);
    public abstract void makeChildNode(String[] items);
    public abstract void getMakeChildCount();
    public abstract void getMakeNodeCount();
    public abstract Object getResult();
 
}
