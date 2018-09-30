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
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import com.efsm.xml.ComponentInfo;
import com.efsm.xml.InitialProcess;
import com.efsm.xml.StateInfo;
import com.efsm.xml.TransitionInfo;
import com.efsm.xml.VariableInfo;

public class Handler extends DefaultHandler {


    boolean IsComponent = false;
    boolean IsEntity = false;
    boolean IsFlowCharts = false;
    boolean IsAdded = false;
   
    boolean componentId = false; 
    boolean componentName = false;
    private List<ComponentInfo> componentList = null;
    private ComponentInfo component = null;

    public List<ComponentInfo> getComponents() {
        return componentList;
    }

    private List<StateInfo> stateList = null;
    private StateInfo state = null;

    public List<StateInfo> getStates() {
        return stateList;
    }

    private List<TransitionInfo> transitionList = null;
    private TransitionInfo transition = null;

    public List<TransitionInfo> getTransitions() {
        return transitionList;
    }
    
    
    private List<InitialProcess> initalList = null;
    private InitialProcess initial = null;

    public List<InitialProcess> getInitialProcess() {
        return initalList;
    }

    private List<VariableInfo> variableList = null;
    private VariableInfo variable = null;

    public List<VariableInfo> getVariables() {
        return variableList;
    }

    boolean initialId = false;
    boolean IsInitial = false;
    String initialComponent;
    
    
    boolean stateId = false;
    boolean stateName = false;
    boolean IsState = false;
    String stateComponent;

    boolean transitionId = false;
    boolean transitionName = false;
    boolean transitiontarget = false;
    boolean transitionsource = false;
    boolean transitionAction = false;
    boolean transitionExpression = false;
    boolean transitionguard = false;
    boolean transitionTimeout = false;
    boolean transitionTimeoutCode = false;
    boolean transitionTimeoutUnit = false;
    boolean IsTransition = false;
    String transistionComponent;

    boolean variableId = false;
    boolean variableName = false;
    boolean variableValue = false;
    boolean IsVariable = false;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
            throws SAXException {

        if (qName.equalsIgnoreCase("ActiveObjectClass")) {
            IsComponent = true;
            if (componentList == null) {
                componentList = new ArrayList<>();
            }
            component = new ComponentInfo();
        } else if (qName.equalsIgnoreCase("FlowChartsUsage")) {
            IsFlowCharts = true;
        } else if (qName.equalsIgnoreCase("Id") && IsComponent && !IsAdded) {
            componentId = true;
        } else if (qName.equalsIgnoreCase("Name") && IsComponent && !IsAdded) {
            componentName = true;
        } else if (qName.equalsIgnoreCase("StatechartElement")) {
            if (attributes.getValue("Class").equalsIgnoreCase("State")) {
                IsState = true;
                if (stateList == null) {
                    stateList = new ArrayList<>();
                }
                state = new StateInfo();
        } else if (attributes.getValue("Class").equalsIgnoreCase("EntryPoint")) {
                IsInitial = true;
                if (initalList == null) {
                    initalList = new ArrayList<>();
                }
                initial = new InitialProcess();           
        } else if (attributes.getValue("Class").equalsIgnoreCase("Transition")) {
                IsTransition = true;
                if (transitionList == null) {
                    transitionList = new ArrayList<>();
                }
                transition = new TransitionInfo();
            }

        } else if (qName.equalsIgnoreCase("Id") && IsState) {
            stateId = true;

        } else if (qName.equalsIgnoreCase("Name") && IsState) {
            stateName = true;

        } else if (qName.equalsIgnoreCase("Id") && IsInitial) {
           initialId = true;

        } else if (qName.equalsIgnoreCase("Properties") && IsInitial) {
            initial.setName(attributes.getValue("Target"));
        }
        else if (qName.equalsIgnoreCase("Id") && IsTransition) {
            transitionId = true;

        } else if (qName.equalsIgnoreCase("Name") && IsTransition) {
            transitionName = true;

        } else if (qName.equalsIgnoreCase("Action") && IsTransition) {
            transitionAction = true;

        } else if (qName.equalsIgnoreCase("EqualsExpression") && IsTransition) {
            transitionExpression = true;

        } else if (qName.equalsIgnoreCase("Properties") && IsTransition) {
            transition.setSource(attributes.getValue("Source"));
            transition.setTarget(attributes.getValue("Target"));
            transition.setTrigger(attributes.getValue("Trigger"));
        } else if (qName.equalsIgnoreCase("Guard")) {
            transitionguard = true;
            
        } else if (qName.equalsIgnoreCase("Timeout") && IsTransition) {
            transitionTimeout = true;

        } else if (qName.equalsIgnoreCase("Code") && transitionTimeout) {
            transitionTimeoutCode= true;

        } else if (qName.equalsIgnoreCase("Unit") && transitionTimeout) {
            transitionTimeoutUnit = true;
        } else if (qName.equalsIgnoreCase("Variable")) {

            if (attributes.getValue("Class").equalsIgnoreCase("PlainVariable")) {
                IsVariable = true;
                if (variableList == null) {
                    variableList = new ArrayList<>();
                }
                variable = new VariableInfo();
            }
        } else if (qName.equalsIgnoreCase("Id") && IsVariable) {
            if (qName.equalsIgnoreCase("Id")) {
                variableId = true;
            }
        } else if (qName.equalsIgnoreCase("Name") && IsVariable) {
            variableName = true;
        } else if (qName.equalsIgnoreCase("Code") && IsVariable) {
            variableValue = true;
        }
        
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("ActiveObjectClass")) {
             IsAdded=false;
        } else if (qName.equalsIgnoreCase("FlowChartsUsage") && IsEntity) {
            IsFlowCharts = false;
             componentList.add(component);
            IsComponent = false;
            IsEntity = false;
        } else if (qName.equalsIgnoreCase("StatechartElement") && IsState) {
            state.setComponent(stateComponent);
            stateList.add(state);
            IsState = false;
        } else if (qName.equalsIgnoreCase("StatechartElement") && IsInitial) {
            initial.setComponent(initialComponent);
            initalList.add(initial);
            IsInitial = false;
        } else if (qName.equalsIgnoreCase("StatechartElement") && IsTransition) {
            transition.setComponent(transistionComponent);
            transitionList.add(transition);
            IsTransition = false;
        } else if (qName.equalsIgnoreCase("Variable") && IsVariable) {
            variableList.add(variable);
            IsVariable = false;
        }
    }
    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        if (new String(ch, start, length).equalsIgnoreCase("ENTITY")) {
            IsEntity = true;
        } else if (componentId && !IsAdded) {
            //System.out.println(">>>>>>>"+new String(ch, start, length));
            component.setId(new String(ch, start, length));
            componentId = false;
        } else if (componentName && !IsAdded) {
            component.setName(new String(ch, start, length));
            stateComponent = new String(ch, start, length);
            transistionComponent = new String(ch, start, length);
            initialComponent = new String(ch, start, length);
            componentName = false;
            IsAdded=true;
        } else if (stateId) {
            state.setId(new String(ch, start, length));
            stateId = false;
        } else if (stateName) {
            state.setName(new String(ch, start, length));
            stateName = false;
        } else if (initialId) {
            initial.setId(new String(ch, start, length));
            initialId = false;
        }  else if (transitionId) {
            transition.setId(new String(ch, start, length));
            transitionId = false;
        } else if (transitionName) {
            transition.setName(new String(ch, start, length));
            transitionName = false;
        } else if (transitionAction) {
            String result = new String(ch, start, length);
            if (result.contains("SendPort.send")) {
                //System.err.print(StringUtils.substringBetween(result, "\"", "\"")+"?0"+'+'+result);
                transition.setAction(StringUtils.substringBetween(result, "\"", "\"")+"!0");
            } else {
                transition.setAction(result.trim());
            }
            transitionAction = false;
        } else if (transitionExpression) {
            String result = new String(ch, start, length);
            transition.setExpression(StringUtils.substringBetween(result, "\"", "\"")+"?0");
            //if (result.contains("msg.name")) {
               // transition.setExpression(StringUtils.substringBetween(result, "msg.name == \"", "\"")+"?0");
            //}
            transitionExpression = false;
        } else if (transitionguard) {
            String guard = new String(ch, start, length);
            if(guard.trim().length()>0)
            {
            transition.setGuard(" ["+new String(ch, start, length)+"] ");
            }
            transitionguard = false;
        } else if (transitionTimeoutCode) {
          // System.err.println(new String(ch, start, length),10);
            String to = new String(ch, start, length);
            
            if(to!=null && !to.isEmpty() && to.trim().length() > 0)
            {
                transition.setTimeout(to);
                transitionTimeoutCode = false;
            }
        } else if (transitionTimeoutUnit) {
            String to = new String(ch, start, length);
            if(to!=null && !to.isEmpty() && to.trim().length() > 0)
            {
                transition.setTimeoutUnit(to);
                transitionTimeout = false;
                transitionTimeoutUnit = false;
            }
        } else if (variableId) {
            variable.setId(new String(ch, start, length));
            variableId = false;
        } else if (variableName) {
            variable.setName(new String(ch, start, length));
            variableName = false;
        }  else if (variableValue) {
          //  System.err.println(new String(ch, start, length));
         //   float f = Float.parseFloat(new String(ch, start, length));
            //System.err.println(">>>"+Math.round(f));
            variable.setValue(new String(ch, start, length));
            variableValue = false;
        }

    }
}

