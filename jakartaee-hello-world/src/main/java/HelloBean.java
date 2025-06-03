
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.io.Serializable;

@Named // Macht die Klasse als Managed Bean verfügbar
@RequestScoped // Definiert den Scope der Bean
public class HelloBean implements Serializable {

    private String name;
    private String helloMessage;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHelloMessage() {
        return helloMessage;
    }

    public void setHelloMessage(String helloMessage) {
        this.helloMessage = helloMessage;
    }

    public void sayHello() {
        if (name != null && !name.trim().isEmpty()) {
            helloMessage = "Hello, " + name + "!";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Message generated."));
        } else {
            helloMessage = "";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Input Required", "Please enter your name."));
        }
    }
}