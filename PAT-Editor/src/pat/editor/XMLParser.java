/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pat.editor;

/**
 *
 * @author Tameen
 */
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import com.efsm.xml.ComponentInfo;
import com.efsm.xml.InitialProcess;
import com.efsm.xml.StateInfo;
import com.efsm.xml.TransitionInfo;
import com.efsm.xml.VariableInfo;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import pat.editor.Output;

public class XMLParser {

    public static String FilterStateName(String filter, List<StateInfo> slist) {
        for (StateInfo s : slist) {
            if (s.getId().equals(filter)) {
                return s.getName();
            }
        }
        return "";
    }

    public static String FilterTransitionSourceTarget(String filter, List<TransitionInfo> tlist, String trigger) {
        switch (trigger) {
            case "source":
                for (TransitionInfo t : tlist) {
                    if (t.getTarget().equals(filter)) {
                        return t.getSource();
                    }
                }
                break;
            case "target":
                for (TransitionInfo t : tlist) {
                    if (t.getSource().equals(filter)) {
                        return t.getTarget();
                    }
                }
                break;
        }

        return "";
    }

    public static void main(String[] args) {

        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        String output_display = "";

        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            Handler handler = new Handler();
            
            System.out.println("--------Please Enter File Path of your .alp file -------- ");
            Scanner in = new Scanner(System.in);
            System.out.print("Enter .alp File Path: ");
            String str = in.next().replaceAll("\\s+", "");
            System.out.println("File Path: " + str);
            if (!str.isEmpty()) {
                saxParser.parse(new File(str), handler);

                List<VariableInfo> vlist = handler.getVariables();
                HashMap<String, String> output = new HashMap<String, String>();
                Map<String, Output> output1 = new HashMap<>();

                output_display += "\n";
                output_display += "// Variables";
                output_display += "\n";
                for (VariableInfo obj : vlist) {
                    output_display += obj + "\n";
                }

                List<ComponentInfo> clist = handler.getComponents();
                List<StateInfo> slist = handler.getStates();
                List<InitialProcess> ilist = handler.getInitialProcess();

                output_display += "\n";
                output_display += "// Processes";
                output_display += "\n";
                for (ComponentInfo obj : clist) {
                    for (InitialProcess iobj : ilist) {
                        for (StateInfo sobj : slist) {
                            if (iobj.getComponent().equalsIgnoreCase(obj.getName()) && iobj.getName().equalsIgnoreCase(sobj.getId())) {
                                output_display += obj.getName() + " = " + sobj.getName()+obj.getName() + "();" + "\n";
                                break;
                            }
                        }
                    }
                }

                output_display += "\n";
                output_display += "// Process Defination";
                output_display += "\n";
                List<TransitionInfo> tlist = handler.getTransitions();
                for (TransitionInfo tobj : tlist) {
                    String source = tobj.getSource();
                    String target = tobj.getTarget();
                    String action = tobj.getAction();
                    String guard = tobj.getGuard();
                    String expression = tobj.getExpression();
                    String trigger = tobj.getTrigger();
                    String x = tobj.getTimeout();
                        String constraint="";
     
                        if (x.length() > 0) {
                            constraint = "-> Wait[" + x + "]; ";
                        }

                    if (trigger.equals("message")) {
                        
                        if (!expression.isEmpty() && expression.length()>0) {
                            output_display += "channel " + expression.replace("?", " ") + ";" + "\n";
                            String v = "";
                            if(constraint.length()>0 && action.length()>0) 
                            {
                                v += expression  + "->{"+ action +"}" + constraint +FilterStateName(target, slist)+tobj.getComponent() + "()";
                            }
                            else if(constraint.length() == 0 && action.length()>0) 
                            {
                                v += expression  + "->{"+ action +"}" + "->" + FilterStateName(target, slist)+tobj.getComponent() + "()";

                            }
                            else if(constraint.length()>0 && action.length()==0) 
                            {
                                v += expression + constraint  + FilterStateName(target, slist)+tobj.getComponent() + "()";
                            }
                            else if(constraint.length() == 0 && action.length()==0) 
                            {
                                v += expression + "->" + FilterStateName(target, slist)+tobj.getComponent() + "()";

                            }
                            output1.put(FilterStateName(source, slist)+tobj.getComponent(), new Output(FilterStateName(source, slist)+tobj.getComponent(), guard, v));
                        
                        /*if (!action.isEmpty()) {

                            String v =  action + "->" + FilterStateName(FilterTransitionSourceTarget(target, tlist, "source"), slist)+tobj.getComponent() + "()";

                            output1.put(FilterStateName(source, slist)+tobj.getComponent(), new Output(FilterStateName(source, slist)+tobj.getComponent(), guard, v));
                        }*/
                        }
                    } else if (trigger.equals("timeout")) {
                       
                        if (!action.isEmpty()) {

                           // String v = action + constraint + "->" + FilterStateName(target, slist)+tobj.getComponent() + "()";
                           String v = "";
                            if(constraint.length()>0)
                            {
                                v += action + constraint  + FilterStateName(target, slist)+tobj.getComponent() + "()";
                            }
                            else
                            {
                                v += action + "->" + FilterStateName(target, slist)+tobj.getComponent() + "()";

                            }

                            output1.put(FilterStateName(source, slist)+tobj.getComponent(), new Output(FilterStateName(source, slist)+tobj.getComponent(), guard, v));

                        }

                       // System.err.println(FilterStateName(target, slist)+">>"+FilterStateName(target, slist)+tobj.getComponent() + "()=" + "(atomic{" + action + "->" + FilterStateName(FilterTransitionSourceTarget(target, tlist, "source"), slist) + "()});");
                    }

                }
                PrintWriter writer = new PrintWriter("CSP-File.prts", "UTF-8");

                for (Map.Entry m : output1.entrySet()) {
                    //System.err.println(output1.get(FilterStateName(source, slist)));
                    //System.err.println(output1.get(FilterStateName(source, slist)).getGuard());
                    String guard = output1.get(m.getKey()).getGuard();
                    // System.err.print(m);
                    output_display += m.getKey() + "() =" + guard + " (atomic{" + output1.get(m.getKey()).getProcess() + "});" + "\n";

                    //System.out.println(m.getKey()+"() ="+ output1.get(m.getKey()).getGuard()+" (atomic{"+output1.get(m.getKey()).getProcess()+"})");  
                }

                output_display += "\n";
                output_display += "// System Defination";
                output_display += "\n";
                output_display += "System = ";
                for (ComponentInfo obj : clist) {
                    for (StateInfo sobj : slist) {
                        if (sobj.getComponent().equalsIgnoreCase(obj.getName())) {
                            output_display += obj.getName() + " || ";
                            break;
                        }
                    }
                }
                System.out.print("Anylogic XML has been successfully Transformed to CSP code!\n");
                System.out.print("Please view file with name CSP-File in your folder!\n");
                System.out.print("File Output:\n");
                System.out.print(output_display);
                writer.println(output_display);
                writer.close();
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

}
