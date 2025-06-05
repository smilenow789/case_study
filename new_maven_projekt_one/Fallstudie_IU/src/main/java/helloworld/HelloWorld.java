package helloworld;

import java.io.Serializable;
// REMOVE: import javax.faces.bean.ManagedBean;
// REMOVE: import javax.faces.bean.SessionScoped;

// Import the CDI @Named annotation from Jakarta
import jakarta.inject.Named;
// Import the CDI @SessionScoped annotation from Jakarta
import jakarta.enterprise.context.SessionScoped;

@Named
@SessionScoped
public class HelloWorld implements Serializable{

    private static final long serialVersionUID = -6913972022251814607L;

    private String s1 = "Hello World!!";

    public String getS1() {
        System.out.println(s1);
        return s1;
    }

    public void setS1(String s1) {
        this.s1 = s1;
    }

}