# Anylogic Transformation to CSP

- **Component:** Each component initialize by its first process.
- **Variables:** provides values to an action when it is triggered.

- **Variable Properties:**
  - **Name:**  The name of variable. It uses to identify the variable.
  - **Value:** The initial value of variable.
  
- **State:** represents a location of control with a particular set of reactions to conditions and/or events.
- **State Properties:**
  - **Id:**  The id of State. It is a unique 13 digit integer value.
  - **Name:**  The name of State. It uses to identify the State.
  - **Initialize:** It defines either the State is initial or not. It has a Boolean type values i.e. true or false. In case of true it considers as an initial state.

- **Transition:**  A transition denotes a switch from one state to another. A transition indicates that if the specified trigger event occurs and the specified guard condition is true, the statechart switches from one state to another and performs the specified action.
- **Transition Properties:**
  - **Name:**  The name of Transition. It uses to identify the Transition.
  - **Guard:** A guard is implemented on transitions. Transitions fire if the condition defined in guard comes true.
  - **Event:** The name of Event. It has String data type.
  - **Properties:**
    - **Source:** The Name of the State to which it belongs.
    - **Target:** The Name of the targeted State to which it has to move.
    - **Type:** An event has two types:
      - **Receive:** It waits for a defined message expected from connected components to receive.
      - **Send:** It sends message to the connected components with the time delay of zero seconds. A message can be of type &#39;String&#39; or a complex object containing message parameters.
    - **Action:**  It implements the defined function. It uses to set input variables and write on to the output variables.
    - **TimeFunction:**
      - **Type:** It is the type of time constraint.
      - **Value:** It stores the integer type time value.
      - **Unit:**  It is in seconds.
      
### How to use:

> java -jar PAT-Editor.jar

![pat-editor](https://user-images.githubusercontent.com/43378781/46258480-e8ac6580-c4e4-11e8-9774-650e0585f9cd.png)



