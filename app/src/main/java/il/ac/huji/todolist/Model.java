package il.ac.huji.todolist;

import java.util.Date;


public class Model{

    private String title;
    private Long date;

    public Model(String title, Long date) {
        super();
        this.title = title;
        this.date = date;
    }

    public String GetTitle() {
        return this.title;
    }

    public Long GetDate() {
        return this.date;
    }


}