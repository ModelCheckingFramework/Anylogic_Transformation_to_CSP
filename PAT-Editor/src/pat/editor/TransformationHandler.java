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
import com.efsm.xml.StateInfo;
import com.efsm.xml.TransitionInfo;
import com.efsm.xml.VariableInfo;

public class TransformationHandler extends DefaultHandler {
    boolean componentId = false;
    boolean componentName = false;
    boolean IsComponent = false;
    boolean IsEntity = false;
    boolean IsFlowCharts = false;
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

    private List<VariableInfo> variableList = null;
    private VariableInfo variable = null;

    public List<VariableInfo> getVariables() {
        return variableList;
    }

    boolean stateId = false;
    boolean stateName = false;
    boolean IsState = false;
    

    boolean transitionId = false;
    boolean transitionName = false;
    boolean transitiontarget = false;
    boolean transitionsource = false;
    boolean transitionAction = false;
    boolean transitionExpression = false;
    boolean transitionguard = false;
    boolean IsTransition = false;

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
        } else if (qName.equalsIgnoreCase("StatechartElement")) {
            if (attributes.getValue("Class").equalsIgnoreCase("State")) {
                IsState = true;
                if (stateList == null) {
                    stateList = new ArrayList<>();
                }
                state = new StateInfo();
            } else if (attributes.getValue("Class").equalsIgnoreCase("Transition")) {
                IsTransition = true;
                if (transitionList == null) {
                    transitionList = new ArrayList<>();
                }
                transition = new TransitionInfo();
            }

        } else if (qName.equalsIgnoreCase("Id") && IsComponent && !IsState && !IsTransition && !IsVariable) {
            componentId = true;

        } else if (qName.equalsIgnoreCase("Name") && IsComponent && !IsState && !IsTransition && !IsVariable) {
            componentName = true;

        } else if (qName.equalsIgnoreCase("Id") && IsState) {
            stateId = true;

        } else if (qName.equalsIgnoreCase("Name") && IsState) {
            stateName = true;

        } else if (qName.equalsIgnoreCase("Id") && IsTransition) {
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
        } else if (qName.equalsIgnoreCase("Guard")) {
            transitionguard = true;
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
            componentList.add(component);
            IsComponent = false;
        } else if (qName.equalsIgnoreCase("FlowChartsUsage")) {
            IsFlowCharts = false;
        } else if (qName.equalsIgnoreCase("StatechartElement") && IsState) {
            stateList.add(state);
            IsState = false;
        } else if (qName.equalsIgnoreCase("StatechartElement") && IsTransition) {
            transitionList.add(transition);
            IsTransition = false;
        } else if (qName.equalsIgnoreCase("Variable") && IsVariable) {
            variableList.add(variable);
            IsVariable = false;
        }
    }
    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        if (componentId) {
            //System.out.println(">>>>>>>"+new String(ch, start, length));
            component.setId("111");
            componentId = false;
        } else if (componentName) {
            component.setName("111");
            componentName = false;
        } else if (stateId) {
            state.setId(new String(ch, start, length));
            stateId = false;
        } else if (stateName) {
            state.setName(new String(ch, start, length));
            stateName = false;
        } else if (transitionId) {
            transition.setId(new String(ch, start, length));
            transitionId = false;
        } else if (transitionName) {
            transition.setName(new String(ch, start, length));
            transitionName = false;
        } else if (transitionAction) {
            String result = new String(ch, start, length);
            if (result.contains("Psend.send")) {
                transition.setAction(StringUtils.substringBetween(result, "(\"", "\",lstObj)"));
            } else {
                transition.setAction("("+result+")");
            }
            transitionAction = false;
        } else if (transitionExpression) {
            String result = new String(ch, start, length);
            if (result.contains("msg.name")) {
                transition.setExpression(StringUtils.substringBetween(result, "msg.name == \"", "\""));
            }
            transitionExpression = false;
        } else if (transitionguard) {
            transition.setGuard(new String(ch, start, length));
            transitionguard = false;
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
