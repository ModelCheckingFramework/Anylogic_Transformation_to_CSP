/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pat.editor;
import java.util.HashMap;
import java.util.Map;
/**
 *
 * @author Tameen
 */
public class Output {

    private String id;
    private String guard;
    private String process;

    public Output(String id, String guard, String process) {
        this.id = id;
        this.guard = guard;
        this.process = process;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGuard() {
        return guard;
    }

    public void setGuard(String guard) {
        this.guard = guard;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "guard='" + guard + '\'' +
                ", process='" + process + '\'' +
                '}';
    }

}
